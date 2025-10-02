package com.ggprison.apples.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.NonNull;

import com.ggprison.apples.config.Apples;
import com.ggprison.apples.config.Messages;

import me.colingrimes.midnight.message.Message;

public final class MessageUtils {
    public static Message<?> apple_placeholders(@NonNull Message<?> message, Apples.Apple apple) {
        if (apple == null) {
            return message;
        }

        Message<?> content = message
            .replace("{identifier}", apple.getIdentifier())
            .replace("{material}", apple.getMaterial().toString());

        if (apple.getEffects() != null) {
            List<Message<?>> effectsText = MessageUtils.effect_placeholders(apple.getEffects());
            content = content.replace("{effects}", effectsText.toString());
        }

        return content;

    }

    public static Message<String> effect_placeholders(@NonNull Message<String> message, PotionEffect effect) {
        if (effect == null) return message;

        String content = message.getContent()
                .replace("{effect_name}", effect.getType().getKey().getKey())
                .replace("{effect_level}", String.valueOf(effect.getAmplifier() + 1))
                .replace("{effect_duration}", String.valueOf(effect.getDuration() / 20));

        return Message.of(content);
    }

    public static List<Message<?>> effect_placeholders(@NonNull List<PotionEffect> effects) {
        List<Message<?>> result = new ArrayList<>();

        for (PotionEffect effect : effects) {
            Message<?> formatted = Messages.FORMAT_EFFECT
                    .replace("{effect_name}", effect.getType().getKey().getKey())
                    .replace("{effect_level}", String.valueOf(effect.getAmplifier() + 1))
                    .replace("{effect_duration}", String.valueOf(effect.getDuration() / 20));
   
            result.add(formatted);
        }

        return result;
    }
}