package com.ggprison.apples.config;

import me.colingrimes.midnight.config.annotation.Configuration;
import static me.colingrimes.midnight.config.option.OptionFactory.message;
import me.colingrimes.midnight.message.Message;

/**
 * Defines the configuration interface for messages loaded from messages.yml.
 * Provides message templates for various plugin interactions, including prefixes, success messages,
 * failure messages, and command usage instructions.
 */
@Configuration("messages.yml")
public interface Messages {
    Message<?> PREFIX = message("prefix", "&6&lA&e&lp&6&lp&e&ll&6&le&e&ls &7➤");
    Message<?> INFO_PLUGIN = message("info.plugin", "{chat_prefix} &fPlugin: &a{plugin_name} &fv{plugin_version} &fby &a{plugin_author}");

    Message<?> SUCCESS_ADMIN_RELOAD = message("success.admin-reload", "{chat_prefix} &aCustomApples has been reloaded!");
    Message<?> SUCCESS_BROADCAST_CONSUME = message("success.broadcast-consume", "{chat_prefix} &a{player}&f consumed &a{apple}&f!");

    Message<?> FAILURE_NO_PERMISSION = message("failure.no-permission", "{chat_prefix} &cYou don't have enough permissions!");
    Message<?> FAILURE_UNKNOWN_APPLE = message("failure.unknown-apple", "{chat_prefix} &cUnknown type of apple: {apple_type}");
    Message<?> FAILURE_NO_PLAYER_FOUND = message("failure.no-player-found", "{chat_prefix} &cThe player &7{player}&c does not exist");
    Message<?> FAILURE_APPLE_IN_COOLDOWN = message("failure.apple-in-cooldown", "{chat_prefix} &fPlease wait &a{remaining}s&f before using {apple}&f again!");

    Message<?> USAGE_APPLES_GIVE = message("usage.apples-give", "{chat_prefix} &7Usage: &c/apples give <player> <apple> <amount>");
    Message<?> USAGE_APPLES = message("usage.apples",
        "&7&l&m━━━━━━━━━━━━━━━━&7 &e&lApples &aCommands &7&l&m━━━━━━━━━━━━━━━━",
        "&7- &a/apples reload &e: &7Reload config files.",
        "&7- &a/apples info &e: &7Check plugin information.",
        "&7- &a/apples give <user> <type> <amount> &e: &7Give an apple to a player.",
        "&7&l&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    );
}