package com.ggprison.apples.command.customapple.give;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.ggprison.apples.CustomApples;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.util.bukkit.Players;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ggprison.apples.config.Apples;
import com.ggprison.apples.config.Apples.Apple;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.ArgumentList;
import me.colingrimes.midnight.command.handler.util.CommandProperties;
import me.colingrimes.midnight.command.handler.util.Sender;

public class CustomApplesAdminGive implements Command<CustomApples> {
    @Override
    public void execute(@NonNull CustomApples plugin, @NonNull Sender sender, @NonNull ArgumentList args) {
        Optional<Player> receiverOpt = args.getPlayer(0);
        if (receiverOpt.isEmpty()) {
            Messages.FAILURE_NO_PLAYER_FOUND.replace("%player%", args.getFirst()).send(sender);
            return;
        }

        Player receiver = receiverOpt.get();
        Apples.Apple appleType = plugin.getApplesManager().getByKey(args.get(2));
        if (appleType == null) {
            Messages.FAILURE_UNKNOWN_APPLE.replace("%apple_type%", args.get(2)).send(sender);
            return;
        }

        int amount = 1;
        if (args.size() > 2) {
            try {
                amount = Integer.parseInt(args.get(2));
                if (amount <= 0) amount = 1;
            } catch (NumberFormatException ignored) {}
        }

        ItemStack apple = plugin.getApplesManager().createApple(appleType, amount);
        receiver.getInventory().addItem(apple);
    }

    @Nullable
    @Override
    public List<String> tabComplete(@NonNull CustomApples plugin, @NonNull Sender sender, @NonNull ArgumentList args) {
        return switch (args.size()) {
            case 1 -> Players.filter(p -> p.getName().startsWith(args.getFirst())).map(Player::getName).toList();
           case 2 -> plugin.getApplesManager().getAll().stream().map(Apple::getIdentifier).filter(key -> key.startsWith(args.get(2))).toList();
            default -> null;
        }; // TODO
    }    

    @Override
    public void configureProperties(@NonNull CommandProperties properties) {
        properties.setUsage(Messages.USAGE_APPLES_GIVE);
        properties.setPermission("customapples.admin.give");
        properties.setArgumentsRequired(2);
    }
}
