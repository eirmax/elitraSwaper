
package com.eirmax.elitraswaper.networkRegister;

import com.eirmax.elitraswaper.Config;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "elitraswaper", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeybindHandler {
    public static KeyMapping swapKey;

    public static void register() {
        swapKey = new KeyMapping(
                "key.elitraswaper.swap",
                Config.SWAP_KEY.get(),
                "key.category.elitraswaper"
        );
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(swapKey);
    }

    @Mod.EventBusSubscriber(modid = "elitraswaper", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    @OnlyIn(Dist.CLIENT)
    public static class KeyInputHandler {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (swapKey.consumeClick()) {
                NetworkHandler.INSTANCE.sendToServer(new SwapElytraPacket());
            }
        }
    }
}
