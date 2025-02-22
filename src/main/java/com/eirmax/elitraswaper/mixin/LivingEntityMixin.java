package com.eirmax.elitraswaper.mixin;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class LivingEntityMixin {
    @Inject(method = "tryToStartFallFlying", at = @At("HEAD"), cancellable = true)
    private void onStartFallFlying(CallbackInfoReturnable<Boolean> cir) {
        if (((LivingEntity)(Object)this).hasEffect(MobEffects.LEVITATION)) {
            cir.setReturnValue(false);
        }
    }
}