package com.eirmax.elitraswaper.mixin;

import com.eirmax.elitraswaper.Config;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
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
        return player.fallDistance > 3 &&
                !player.isFallFlying() &&
                !player.onGround() &&
                !player.hasEffect(MobEffects.LEVITATION);
    }
}