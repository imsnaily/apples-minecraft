package com.ggprison.apples.config;

import java.util.ArrayList;
import java.util.Collections;

import static me.colingrimes.midnight.config.option.OptionFactory.keys;
import static me.colingrimes.midnight.config.option.OptionFactory.option;

import me.colingrimes.midnight.config.annotation.Configuration;
import me.colingrimes.midnight.config.option.Option;

import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Configuration("apples.yml")
public interface Apples {
    Option<Map<String, Apple>> APPLES = keys("apples", Apple::of);
    Option<Set<String>> APPLES_ALL = option("apples", section -> section.getKeys(false));

    class Apple {
        private final String identifier;
        private final String displayName;
        private final List<String> lore;
        private final Material material;
        private final List<PotionEffect> effects;
        private final String broadcast;
        private final long cooldown;
        private final String rarity;

        static Apple of(ConfigurationSection section, String identifier) {
            String displayName = section.getString("display-name", identifier);
            List<String> lore = section.getStringList("lore");
            String materialName = section.getString("material", "GOLDEN_APPLE");
            if (materialName == null) materialName = "GOLDEN_APPLE";

            Material material = Material.matchMaterial(materialName);
            if (material == null) material = Material.GOLDEN_APPLE;

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

                    NamespacedKey effectKey = NamespacedKey.minecraft(typeName.toLowerCase());
                    PotionEffectType effectType = Registry.EFFECT.get(effectKey);

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
            return new Apple(identifier, displayName, Collections.unmodifiableList(lore), material,
                    Collections.unmodifiableList(effects), broadcast, cooldown, rarity);
        }

        private Apple(
            String identifier,
            String displayName,
            List<String> lore,
            Material material,
            List<PotionEffect> effects,
            String broadcast,
            long cooldown,
            String rarity
        ) {
            this.identifier = identifier;
            this.displayName = displayName;
            this.lore = lore;
            this.material = material;
            this.effects = effects;
            this.broadcast = broadcast;
            this.cooldown = cooldown;
            this.rarity = rarity;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public String getDisplayName() {
            return displayName;
        }

        public List<String> getLore() {
            return lore;
        }

        public Material getMaterial() {
            return material;
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
