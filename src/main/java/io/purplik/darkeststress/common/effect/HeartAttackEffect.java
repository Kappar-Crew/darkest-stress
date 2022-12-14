package io.purplik.darkeststress.common.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;


public class HeartAttackEffect extends MobEffect {

    public HeartAttackEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_19468_) {
        super.applyEffectTick(entity, p_19468_);
        entity.hurt(DamageSource.MAGIC, 5.0F);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
