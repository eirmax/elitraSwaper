package com.eirmax.elitraswaper.mixin;

import com.eirmax.elitraswaper.Config;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "elitraswaper")
public class PlayerTickHandler {
    public static boolean hasSwapped = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START &&
                !event.player.level().isClientSide() &&
                Config.AUTO_EQUIP.get()) {

            Player player = event.player;
        }
    }

    private static boolean shouldAutoEquip(Player player) {
        int configurableFallDistance = Config.FALL_DISTANCE_THRESHOLD.get();
        boolean hasElytra = player.getInventory().contains(new ItemStack(Items.ELYTRA));

        return player.fallDistance > configurableFallDistance &&
                !player.isFallFlying() &&
                !player.onGround() &&
                !player.hasEffect(MobEffects.LEVITATION) &&
                hasElytra;
    }
}