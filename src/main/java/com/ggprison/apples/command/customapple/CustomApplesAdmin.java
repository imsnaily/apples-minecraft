package com.ggprison.apples.command.customapple;

import com.ggprison.apples.CustomApples;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.CommandProperties;

public class CustomApplesAdmin implements Command<CustomApples> {
    @Override
    public void configureProperties(CommandProperties properties) {
        properties.setUsage(Messages.USAGE_APPLES);
        properties.setPermission("customapple.admin");
        properties.setAliases("customapple", "customapple", "capples");
    }
}