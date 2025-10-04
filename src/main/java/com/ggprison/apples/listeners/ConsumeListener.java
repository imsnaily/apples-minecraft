package com.ggprison.apples.listeners;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;

import com.ggprison.apples.CustomApples;
import com.ggprison.apples.config.Apples;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.message.Message;
import me.colingrimes.midnight.message.Placeholders;
import me.colingrimes.midnight.util.bukkit.NBT;

/**
 * Listens for player item consumption events to handle custom apple effects and cooldowns.
 * Applies potion effects, visual lightning effects, and broadcasts messages when a custom apple is consumed.
 */
public class ConsumeListener implements Listener {
    private final CustomApples plugin;

    /**
     * Constructs a new ConsumeListener with the specified CustomApples plugin instance.
     *
     * @param plugin the CustomApples plugin instance
     */
    public ConsumeListener(CustomApples plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles the PlayerItemConsumeEvent to process custom apple consumption.
     * Checks for valid apple items, applies cooldowns, potion effects, and broadcasts messages as configured.
     * Cancels the event if the item is not a valid apple or if the player is on cooldown.
     *
     * @param event the PlayerItemConsumeEvent triggered when a player consumes an item
     */
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        String identifier = NBT.getTag(event.getItem(), "apple_identifier").get();
        if (identifier == null) {
            event.setCancelled(true);
            return;
        }

        Apples.Apple apple = plugin.getApplesManager().getByKey(identifier);
        Player player = event.getPlayer();

        if (apple.getCooldown() > 0) {
            UUID playerUUID = player.getUniqueId();

            if (plugin.getCooldownManager().hasCooldown(playerUUID, identifier)) {
                long left = plugin.getCooldownManager().getRemainingCooldown(playerUUID, identifier);
                Placeholders placeholder = Placeholders.of("{chat_prefix}", Messages.PREFIX)
                    .add("{remaining}", left)
                    .add("{apple}", event.getItem().getItemMeta().getDisplayName());

                Messages.FAILURE_APPLE_IN_COOLDOWN.replace(placeholder).send(player);
                event.setCancelled(true); // Cancel consume event if player is on cooldown
                return;
            } else {
                plugin.getCooldownManager().set(playerUUID, identifier, apple.getCooldown());
            }
        }

        for (PotionEffect pe : apple.getEffects()) {
            player.addPotionEffect(pe);
            plugin.getLogger().log(Level.WARNING, "Giving apple effect: {0}", new Object[]{ pe.getType().getKey().getKey() });
        }

        player.getWorld().strikeLightningEffect(player.getLocation());
        if (apple.getBroadcast() != null) {
            Placeholders placeholder = Placeholders.of("{chat_prefix}", Messages.PREFIX)
                .add("{player}", player.getName())
                .add("{apple}", event.getItem().getItemMeta().getDisplayName());
            Message<?> msg = Messages.SUCCESS_BROADCAST_CONSUME.replace(placeholder);

            // TODO: Replace with msg.broadcast() when supported by midnight library
            for (Player ply : Bukkit.getServer().getOnlinePlayers()) {
                msg.send(ply);
            }
        }
    }
}