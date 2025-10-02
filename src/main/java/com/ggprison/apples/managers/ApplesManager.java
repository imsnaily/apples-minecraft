package com.ggprison.apples.managers;

import java.util.*;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import com.ggprison.apples.CustomApples;
import com.ggprison.apples.config.Apples;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.message.Placeholders;
import me.colingrimes.midnight.util.text.Text;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ApplesManager {
    private final CustomApples plugin;

    public ApplesManager(CustomApples plugin) {
        this.plugin = plugin;
    }

    public Apples.Apple getByKey(String key) {
        Apples.Apple apple = Apples.APPLES.get().get(key);
        return apple;
    }

    public List<Apples.Apple> getAll() {
        return new ArrayList<>(Apples.APPLES.get().values());
    }

    public ItemStack createApple(Apples.Apple apple, int amount) {
        ItemStack item = new ItemStack(apple.getMaterial(), amount);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        
        // String rarityName = plugin.getConfig().getString("rarities." + apple.getRarity(), apple.getRarity());
        meta.displayName(MiniMessage.miniMessage().deserialize(apple.getDisplayName())
            .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));

        List<Component> finalLore = new ArrayList<>();
        for (PotionEffect pe : apple.getEffects()) {
            Placeholders placeholder = Placeholders.create()
                .add("{effect_name}", Text.format(pe.getType().getKey().getKey()))
                .add("{effect_level}", Text.format(pe.getAmplifier() + 1))
                .add("{effect_duration}", Text.format(pe.getDuration() / 20));

            String formatted = placeholder.apply(Messages.FORMAT_EFFECT).toText();
            finalLore.add(MiniMessage.miniMessage().deserialize(formatted)
            .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        }

        meta.lore(finalLore);
        NamespacedKey keyns = new NamespacedKey(plugin, "custom_apple");
        meta.getPersistentDataContainer().set(keyns, PersistentDataType.STRING, apple.getIdentifier());
        item.setItemMeta(meta);

        return item;
    }
}