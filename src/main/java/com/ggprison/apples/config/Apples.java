package com.ggprison.apples.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.colingrimes.midnight.config.annotation.Configuration;
import me.colingrimes.midnight.config.option.Option;
import static me.colingrimes.midnight.config.option.OptionFactory.keys;
import static me.colingrimes.midnight.config.option.OptionFactory.option;
import me.colingrimes.midnight.util.bukkit.Items;

@Configuration("apples.yml")
public interface Apples {
    Option<Map<String, Apple>> APPLES = keys("apples", Apple::of);
    Option<Set<String>> APPLES_ALL = option("apples", section -> section.getKeys(false));

    class Apple {
        private final ItemStack item;
        private final String identifier;
        private final List<PotionEffect> effects;
        private final String broadcast;
        private final long cooldown;
        private final String rarity;

        static Apple of(ConfigurationSection section, String identifier) {
            ItemStack item = Items.create().config(section.getConfigurationSection("item")).build();
            List<PotionEffect> effects = new ArrayList<>();
            List<?> effectsList = section.getList("effects");
            
            if (effectsList != null) {
                for (Object obj : effectsList) {
                    if (!(obj instanceof Map)) {
                        continue;
                    }

                    @SuppressWarnings("unchecked")
                    Map<String, Object> effectMap = (Map<String, Object>) obj;
                    String typeName = ((String) effectMap.getOrDefault("type", "")).toUpperCase(Locale.ROOT);

                    @SuppressWarnings("deprecation")
                    PotionEffectType effectType = PotionEffectType.getByName(typeName);
                    if (effectType == null) {
                        continue;
                    }

                    int level = ((Number) effectMap.getOrDefault("level", 1)).intValue();
                    int duration = ((Number) effectMap.getOrDefault("duration-seconds", 5)).intValue();

                    effects.add(new PotionEffect(effectType, duration * 20, Math.max(0, level - 1)));
                }
            }

            String broadcast = section.getString("broadcast", "");
            long cooldown = section.getLong("cooldown-seconds", 0L);
            if (cooldown < 0) cooldown = 0;

            String rarity = section.getString("rarity", "COMMON");
            return new Apple(item, identifier, Collections.unmodifiableList(effects), broadcast, cooldown, rarity);
        }

        private Apple(
            ItemStack item,
            String identifier,
            List<PotionEffect> effects,
            String broadcast,
            long cooldown,
            String rarity
        ) {
            this.item = item;
            this.identifier = identifier;
            this.effects = effects;
            this.broadcast = broadcast;
            this.cooldown = cooldown;
            this.rarity = rarity;
        }

        public ItemStack getItem() {
            return this.item;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public List<PotionEffect> getEffects() {
            return effects;
        }

        public String getBroadcast() {
            return broadcast;
        }

       public long getCooldown() {
            return cooldown;
        }

        public String getRarity() {
            return rarity;
        }
    }
}
