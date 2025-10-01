package com.ggprison.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import com.ggprison.CustomApple;

public class MessageUtils {
    @Nonnull
    public static String placeholders(
        @Nonnull Plugin plugin,
        @Nonnull String message,
        @Nullable CustomApple apple,
        @Nullable PotionEffect effect
    ) {
        if (apple == null) {
            return message;
        }

        String result = message;
        result = result.replace("%apple_type%", apple.key);

        String rarityName = plugin.getConfig().getString("rarities." + apple.rarity, apple.rarity);
        String rarityFormat = plugin.getConfig().getString("messages.rarity-format", "&7Rarity: %rarity%");
        result = result.replace("%rarity%", rarityFormat.replace("%rarity%", rarityName));

        // replace effect-specific placeholders if effect is provided
        if (effect != null) {
            String effName = effect.getType().getKey().getKey();
            int lvl = effect.getAmplifier() + 1;
            int secs = effect.getDuration() / 20;
            result = result.replace("%effect_name%", effName)
                .replace("%effect_level%", String.valueOf(lvl))
                .replace("%effect_duration%", String.valueOf(secs));
        }

        return result;
    }
    
    private MessageUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
}
