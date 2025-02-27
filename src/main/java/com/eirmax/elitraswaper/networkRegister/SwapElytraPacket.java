package com.eirmax.elitraswaper.networkRegister;

import com.eirmax.elitraswaper.utils.ArmorUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SwapElytraPacket() {
    public void encode(FriendlyByteBuf buffer) {
    }

    public static SwapElytraPacket decode(FriendlyByteBuf buffer) {
        return new SwapElytraPacket();
    }
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ArmorUtils.swapElytra(player);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}