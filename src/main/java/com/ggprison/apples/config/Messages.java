package com.ggprison.apples.config;

import me.colingrimes.midnight.config.annotation.Configuration;
import me.colingrimes.midnight.message.Message;

import static me.colingrimes.midnight.config.option.OptionFactory.message;

@Configuration("messages.yml")
public interface Messages {
    Message<?> SUCCESS_ADMIN_RELOAD = message("success.admin-reload", "&aCustomApples has been reloaded!");

    Message<?> FAILURE_NO_PERMISSION = message("failure.no-permission", "&cYou don't have enough permissions!");
    Message<?> FAILURE_UNKNOWN_APPLE = message("failure.unknown-apple", "&cUnknown type of apple: %apple_type%");
    Message<?> FAILURE_NO_PLAYER_FOUND = message("failure.no-player-found", "&cThe player &7%player%&c does not exist");
    Message<?> FAILURE_APPLE_NOT_PROVIDED = message("failure.apple-not-provided", "&cPlease provide an apple type!");

    Message<?> USAGE_APPLES_GIVE = message("usage.apples-give", "&eUsage: &c/apples give <player> <apple> <amount>");
    Message<?> USAGE_APPLES = message("usage.apples",
        "&7&l&m━━━━━━━━━━━━━━━━&7 &e&lApples &aCommands &7&l&m━━━━━━━━━━━━━━━━",
        "&7- &a/sm reload &e: &7Reload config files.",
        "&7- &a/sm give <user> <type> <amount> &e: &7Give an apple to a player.",
        "&7&l&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    );

    Message<?> FORMAT_EFFECT = message("formats.effect", "&7Effect: &e{effect_name} {effect_level} &7({effect_duration})\n");
    Message<?> FORMAT_RARITY = message("formats.rarity", "&7Rarity: %rarity%");
}