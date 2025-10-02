package com.ggprison.apples.command.customapple.reload;

import org.jspecify.annotations.NonNull;

import com.ggprison.apples.CustomApples;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.ArgumentList;
import me.colingrimes.midnight.command.handler.util.CommandProperties;
import me.colingrimes.midnight.command.handler.util.Sender;

public class CustomApplesAdminReload implements Command<CustomApples> {
    @Override
    public void execute(@NonNull CustomApples plugin, @NonNull Sender sender, @NonNull ArgumentList args) {
        plugin.getConfigurationManager().reload();
        Messages.SUCCESS_ADMIN_RELOAD.send(sender);
    }

    @Override
    public void configureProperties(@NonNull CommandProperties properties) {
        properties.setPermission("customapples.admin.reload");
    }
}
