package com.ggprison.apples.command.customapple.give;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ggprison.apples.CustomApples;
import com.ggprison.apples.config.Apples;
import com.ggprison.apples.config.Apples.Apple;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.ArgumentList;
import me.colingrimes.midnight.command.handler.util.CommandProperties;
import me.colingrimes.midnight.command.handler.util.Sender;
import me.colingrimes.midnight.message.Placeholders;
import me.colingrimes.midnight.util.bukkit.Inventories;
import me.colingrimes.midnight.util.bukkit.Players;

/**
 * Implements the give subcommand for the CustomApples admin command.
 * Allows administrators to give custom apples to players with specified amounts.
 */
public class CustomApplesAdminGive implements Command<CustomApples> {

    /**
     * Executes the give command, providing a specified player with a custom apple.
     * Validates the target player and apple type, and handles the item distribution.
     *
     * @param plugin the CustomApples plugin instance
     * @param sender the command sender
     * @param args   the command arguments
     */
    @Override
    public void execute(@Nonnull CustomApples plugin, @Nonnull Sender sender, @Nonnull ArgumentList args) {
        Optional<Player> receiverOpt = args.getPlayer(0);
        if (receiverOpt.isEmpty()) {
            Placeholders placeholders = Placeholders.of("{chat_prefix}", Messages.PREFIX).add("{player}", args.getFirst());
            Messages.FAILURE_NO_PLAYER_FOUND.replace(placeholders).send(sender);
            return;
        }

        Player receiver = receiverOpt.get();
        Apples.Apple appleType = plugin.getApplesManager().getByKey(args.get(1));
        if (appleType == null) {
            Placeholders placeholders = Placeholders.of("{chat_prefix}", Messages.PREFIX).add("{apple_type}", args.get(1));
            Messages.FAILURE_UNKNOWN_APPLE.replace(placeholders).send(sender);
            return;
        }

        int amount = 1;
        if (args.size() >= 3) {
            try {
                amount = Integer.parseInt(args.get(2));
                if (amount <= 0) amount = 1;
            } catch (NumberFormatException ignored) {
            }
        }

        ItemStack apple = plugin.getApplesManager().createApple(appleType, receiver);
        apple.setAmount(amount);
        Inventories.give(receiver, apple, true);
    }

    /**
     * Provides tab completion for the give command.
     * Suggests player names for the first argument and apple identifiers for the second argument.
     *
     * @param plugin the CustomApples plugin instance
     * @param sender the command sender
     * @param args   the current command arguments
     * @return a list of tab completion suggestions, or null if none apply
     */
    @Override
    public List<String> tabComplete(@Nonnull CustomApples plugin, @Nonnull Sender sender, @Nonnull ArgumentList args) {
        return switch (args.size()) {
            case 1 -> Players.filter(p -> p.getName().startsWith(args.getFirst())).map(Player::getName).toList();
            case 2 -> {
                String input = args.size() > 1 ? args.get(1) : "";
                yield plugin.getApplesManager().getAll().stream().map(Apple::getIdentifier).filter(key -> key.startsWith(input)).toList();
            }
            default -> null;
        };
    }

    /**
     * Configures the properties of the give subcommand.
     * Sets the usage message, permission, and required arguments.
     *
     * @param properties the CommandProperties object to configure
     */
    @Override
    public void configureProperties(@Nonnull CommandProperties properties) {
        properties.setUsage(Messages.USAGE_APPLES_GIVE.replace(Placeholders.of("{chat_prefix}", Messages.PREFIX)));
        properties.setPermission("customapples.admin.give");
        properties.setArgumentsRequired(2);
    }
}