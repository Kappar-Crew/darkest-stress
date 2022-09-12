package io.purplik.darkeststress.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class StressCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> LIGHT_CAUSE_STRESS;

    public static final ForgeConfigSpec.ConfigValue<Boolean> HUNGER_CAUSE_STRESS;
    public static final ForgeConfigSpec.ConfigValue<Integer> HUNGER_CAUSE_STRESS_MARK;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SLEEP_RELIEVE_STRESS;

    public static final ForgeConfigSpec.ConfigValue<Boolean> STRESS_CAUSE_HEART_ATTACK;

    static {
        BUILDER.push("Darkest Stress Configs");

        LIGHT_CAUSE_STRESS = BUILDER.comment("If TRUE dark areas will cause stress to the player.").define("lightCauseStress", true);

        HUNGER_CAUSE_STRESS = BUILDER.comment("If TRUE hunger will cause stress to the player.").define("hungerCauseStress", true);
        HUNGER_CAUSE_STRESS_MARK = BUILDER.comment("At how much hunger should the player start stressing out. Default: 10 (5 drumsticks)").defineInRange("hungerCauseStressMark", 10, 0, 20);

        SLEEP_RELIEVE_STRESS = BUILDER.comment("If TRUE sleeping will relieve stress of the player.").define("sleepRelieveStress", true);

        STRESS_CAUSE_HEART_ATTACK = BUILDER.comment("If TRUE reaching max stress will slowly kill you.").define("stressCauseHeartAttack", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
