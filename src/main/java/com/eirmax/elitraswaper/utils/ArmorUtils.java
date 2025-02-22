package com.eirmax.elitraswaper.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;

import java.util.*;

public class ArmorUtils {
    private static final Map<ArmorMaterial, Integer> ARMOR_TIER_PRIORITIES = new HashMap<>();

    static {
        ARMOR_TIER_PRIORITIES.put(ArmorMaterials.NETHERITE, 5);
        ARMOR_TIER_PRIORITIES.put(ArmorMaterials.DIAMOND, 4);
        ARMOR_TIER_PRIORITIES.put(ArmorMaterials.IRON, 3);
        ARMOR_TIER_PRIORITIES.put(ArmorMaterials.GOLD, 2);
        ARMOR_TIER_PRIORITIES.put(ArmorMaterials.CHAIN, 1);
        ARMOR_TIER_PRIORITIES.put(ArmorMaterials.LEATHER, 0);
    }

    public static int compareTiers(ItemStack a, ItemStack b) {
        if (a == null && b == null) return 0; // Both are null
        if (a == null) return 1; // a is null, b is not
        if (b == null) return -1; // b is null, a is not

        if (isElytra(a) && !isElytra(b)) return -1;
        if (!isElytra(a) && isElytra(b)) return 1;


        ArmorMaterial matA = getArmorMaterial(a);
        ArmorMaterial matB = getArmorMaterial(b);

        return Integer.compare(
                ARMOR_TIER_PRIORITIES.getOrDefault(matB, 0),
                ARMOR_TIER_PRIORITIES.getOrDefault(matA, 0)
        );
    }

    private static ArmorMaterial getArmorMaterial(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            return armorItem.getMaterial();
        }
        return null;
    }

    public static void swapElytra(Player player) {
        ItemStack current = player.getItemBySlot(EquipmentSlot.CHEST);

        if (isBestItemEquipped(player, current)) {
            return;
        }

        List<ItemStack> alternatives = new ArrayList<>();

        for (ItemStack stack : player.getInventory().items) {
            if (isEligibleForSwap(stack)) {
                alternatives.add(stack);
            }
        }

        alternatives.sort(ArmorUtils::compareTiers);

        if (!alternatives.isEmpty()) {
            ItemStack best = alternatives.get(0);
            if (!ItemStack.matches(current, best)) {
                performSwap(player, best);
            }
        }
    }

    private static boolean isBestItemEquipped(Player player, ItemStack current) {
        List<ItemStack> alternatives = new ArrayList<>();

        for (ItemStack stack : player.getInventory().items) {
            if (isEligibleForSwap(stack)) {
                alternatives.add(stack);
            }
        }

        alternatives.sort(ArmorUtils::compareTiers);

        if (!alternatives.isEmpty()) {
            ItemStack best = alternatives.get(0);
            return ItemStack.matches(current, best);
        }

        return true;
    }

    private static boolean isEligibleForSwap(ItemStack stack) {
        return isElytra(stack) ||
                (stack.getItem() instanceof ArmorItem armor &&
                        armor.getType().getSlot() == EquipmentSlot.CHEST);
    }

    private static void performSwap(Player player, ItemStack newItem) {
        ItemStack current = player.getItemBySlot(EquipmentSlot.CHEST);

        if (!current.isEmpty()) {
            player.getInventory().add(current.copy());
            player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
        }

        ItemStack toEquip = newItem.copy();
        toEquip.setCount(1);
        player.setItemSlot(EquipmentSlot.CHEST, toEquip);
        newItem.shrink(1);

        if (newItem.isEmpty()) {
            player.getInventory().removeItem(newItem);
        }

        player.getInventory().setChanged();
    }

    public static boolean isElytra(ItemStack stack) {
        return stack.is(Items.ELYTRA);
    }
}