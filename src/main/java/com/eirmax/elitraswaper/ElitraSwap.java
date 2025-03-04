package com.eirmax.elitraswaper;

import com.eirmax.elitraswaper.networkRegister.KeybindHandler;
import com.eirmax.elitraswaper.networkRegister.NetworkHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ElitraSwap.MODID)
public class ElitraSwap {
    public static final String MODID = "elitraswaper";
    private static final Logger LOGGER = LogUtils.getLogger();



    public ElitraSwap() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        NetworkHandler.register();
        modEventBus.addListener(KeybindHandler::registerKeys);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        KeybindHandler.register();
    }
}
