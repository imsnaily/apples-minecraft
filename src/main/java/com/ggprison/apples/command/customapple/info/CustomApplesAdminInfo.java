package com.ggprison.apples.command.customapple.info;

import javax.annotation.Nonnull;

import com.ggprison.apples.CustomApples;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.ArgumentList;
import me.colingrimes.midnight.command.handler.util.CommandProperties;
import me.colingrimes.midnight.command.handler.util.Sender;
import me.colingrimes.midnight.message.Placeholders;

/**
 * Implements the info subcommand for the CustomApples admin command.
 * Sends a message displaying the plugin's name, version, and author.
 */
public class CustomApplesAdminInfo implements Command<CustomApples> {

    /**
     * Executes the info command, sending a message with plugin details to the sender.
     *
     * @param plugin the CustomApples plugin instance
     * @param sender the command sender
     * @param args   the command arguments
     */
    @SuppressWarnings("deprecation")
    @Override
    public void execute(@Nonnull CustomApples plugin, @Nonnull Sender sender, @Nonnull ArgumentList args) {
        Placeholders placeholders = Placeholders.of("{chat_prefix}", Messages.PREFIX)
            .add("{plugin_name}", plugin.getName())
            .add("{plugin_version}", plugin.getDescription().getVersion())
            .add("{plugin_author}", plugin.getDescription().getAuthors().get(0));

        Messages.INFO_PLUGIN.replace(placeholders).send(sender);
    }

    /**
     * Configures the properties of the info subcommand.
     * Sets the permission for the command.
     *
     * @param properties the CommandProperties object to configure
     */
    @Override
    public void configureProperties(@Nonnull CommandProperties properties) {
        properties.setPermission("customapples.admin.info");
    }
}