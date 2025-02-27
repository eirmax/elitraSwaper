package com.eirmax.elitraswaper.networkRegister;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("elitraswaper", "main"),
            () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals
    );

    public static void register() {
        INSTANCE.registerMessage(
                0,
                SwapElytraPacket.class,
                SwapElytraPacket::encode,
                SwapElytraPacket::decode,
                SwapElytraPacket::handle
        );
    }
}