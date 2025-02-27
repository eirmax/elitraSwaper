package com.eirmax.elitraswaper.mixin;

import com.eirmax.elitraswaper.utils.ArmorUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(method = "setItemSlot", at = @At("HEAD"), cancellable = true)
    private void onSetItemSlot(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) {
        Player player = (Player) (Object) this;

        if (slot == EquipmentSlot.CHEST && isEligibleForSwap(stack)) {
            handleEquipmentSwap(player, stack);
            ci.cancel();
        }
    }

    @Unique
    private boolean isEligibleForSwap(ItemStack stack) {
        return ArmorUtils.isElytra(stack) ||
                (stack.getItem() instanceof ArmorItem armor &&
                        armor.getEquipmentSlot() == EquipmentSlot.CHEST);
    }

    @Unique
    private void handleEquipmentSwap(Player player, ItemStack newStack) {
        ItemStack currentChest = player.getItemBySlot(EquipmentSlot.CHEST);

        if (shouldReplace(currentChest, newStack)) {
            if (!currentChest.isEmpty()) {
                if (!player.getInventory().add(currentChest)) {
                    player.drop(currentChest, false);
                }
            }
            ItemStack copy = newStack.copy();
            copy.setCount(1);
            player.setItemSlot(EquipmentSlot.CHEST, copy);
            newStack.shrink(1);
        }
    }

    @Unique
    private boolean shouldReplace(ItemStack current, ItemStack replacement) {
        if (current == null || replacement == null) {
            return false;
        }

        if (current.isEmpty()) {
            return true;
        }

        return ArmorUtils.compareTiers(replacement, current) > 0;
    }
}