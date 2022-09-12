package io.purplik.darkeststress.init;

import io.purplik.darkeststress.DarkestStress;
import io.purplik.darkeststress.common.effect.HeartAttackEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DarkestStress.MOD_ID);

    public static final RegistryObject<MobEffect> HEART_ATTACK = MOB_EFFECT.register("heart_attack", () -> new HeartAttackEffect(MobEffectCategory.HARMFUL, 24654654));

    public static void register(IEventBus eventBus) {
        MOB_EFFECT.register(eventBus);
    }
}
