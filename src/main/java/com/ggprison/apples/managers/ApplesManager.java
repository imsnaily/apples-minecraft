package com.ggprison.apples.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ggprison.apples.config.Apples;

import me.colingrimes.midnight.util.bukkit.Items;

/**
 * Manages operations related to apples, including retrieval and creation of apple items.
 * This class interacts with the Apples configuration to handle apple data and item generation.
 */
public class ApplesManager {

    /**
     * Retrieves an apple configuration by its unique key.
     *
     * @param key the unique identifier of the apple
     * @return the Apples.Apple object associated with the key, or null if not found
     */
    public Apples.Apple getByKey(String key) {
        Apples.Apple apple = Apples.APPLES.get().get(key);
        return apple;
    }

    /**
     * Retrieves a list of all configured apples.
     *
     * @return a new ArrayList containing all Apples.Apple objects
     */
    public List<Apples.Apple> getAll() {
        return new ArrayList<>(Apples.APPLES.get().values());
    }

    /**
     * Creates an ItemStack for a specific apple, customized with NBT data and placeholders.
     * The created item includes the apple's identifier, the creator's name, and rarity.
     *
     * @param apple   the Apples.Apple configuration to base the item on
     * @param creator the player who is creating the apple item
     * @return the customized ItemStack representing the apple
     */
    public ItemStack createApple(Apples.Apple apple, Player creator) {
        ItemStack item = apple.getItem();

        return Items.of(item)
            .nbt("apple_identifier", apple.getIdentifier())
            .placeholder("{apple_owner}", creator.getName())
            .placeholder("{apple_rarity}", apple.getRarity())
            .build();
    }
}