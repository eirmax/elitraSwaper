package com.eirmax.elitraswaper;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ElitraSwap.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue AUTO_EQUIP;
    public static final ForgeConfigSpec.IntValue SWAP_COOLDOWN;
    public static final ForgeConfigSpec.ConfigValue<Integer> SWAP_KEY;
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        SWAP_COOLDOWN = builder
                .comment("Swap cooldown in milliseconds")
                .defineInRange("swapCooldown", 1000, 500, 5000);
        AUTO_EQUIP = builder.comment("Enable auto-equip elytra during free fall")
                .define("autoEquip", true);

        SWAP_KEY = builder.comment("The default key for swapping elytra.")
                .define("swapKey", GLFW.GLFW_KEY_R);

        SPEC = builder.build();
    }

}