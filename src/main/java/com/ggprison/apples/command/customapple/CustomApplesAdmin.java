package com.ggprison.apples.command.customapple;

import javax.annotation.Nonnull;

import com.ggprison.apples.CustomApples;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.CommandProperties;
import me.colingrimes.midnight.message.Placeholders;

/**
 * Defines the admin command for the CustomApples plugin.
 * Configures the command's properties, including usage message, permission, and aliases.
 */
public class CustomApplesAdmin implements Command<CustomApples> {

    /**
     * Configures the properties of the CustomApples admin command.
     * Sets the usage message, permission, and aliases for the command.
     *
     * @param properties the CommandProperties object to configure
     */
    @Override
    public void configureProperties(@Nonnull CommandProperties properties) {
        properties.setUsage(Messages.USAGE_APPLES.replace(Placeholders.of("{chat_prefix}", Messages.PREFIX)));
        properties.setPermission("customapple.admin");
        properties.setAliases("customapples", "apples", "capples");
    }
}