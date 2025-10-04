package com.ggprison.apples.command.customapple.reload;

import javax.annotation.Nonnull;

import com.ggprison.apples.CustomApples;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.ArgumentList;
import me.colingrimes.midnight.command.handler.util.CommandProperties;
import me.colingrimes.midnight.command.handler.util.Sender;
import me.colingrimes.midnight.message.Placeholders;

/**
 * Implements the reload subcommand for the CustomApples admin command.
 * Reloads the plugin's configuration and sends a success message to the sender.
 */
public class CustomApplesAdminReload implements Command<CustomApples> {

    /**
     * Executes the reload command, refreshing the plugin's configuration and notifying the sender.
     *
     * @param plugin the CustomApples plugin instance
     * @param sender the command sender
     * @param args   the command arguments
     */
    @Override
    public void execute(@Nonnull CustomApples plugin, @Nonnull Sender sender, @Nonnull ArgumentList args) {
        plugin.getConfigurationManager().reload();

        Messages.SUCCESS_ADMIN_RELOAD
            .replace(Placeholders.of("{chat_prefix}", Messages.PREFIX))
            .send(sender);
    }

    /**
     * Configures the properties of the reload subcommand, setting the required permission.
     *
     * @param properties the CommandProperties object to configure
     */
    @Override
    public void configureProperties(@Nonnull CommandProperties properties) {
        properties.setPermission("customapples.admin.reload");
    }
}