package io.purplik.darkeststress.common.events;

import io.purplik.darkeststress.DarkestStress;
import io.purplik.darkeststress.config.StressCommonConfigs;
import io.purplik.darkeststress.init.EffectInit;
import io.purplik.darkeststress.common.stress.PlayerStress;
import io.purplik.darkeststress.common.stress.PlayerStressProvider;
import io.purplik.darkeststress.util.StressTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.lighting.LayerLightEventListener;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.Random;

@Mod.EventBusSubscriber(modid = DarkestStress.MOD_ID)
public class StressEvents {

    private static Random random = new Random();
    private static boolean rolling = false;
    private static int rollCooldown = 0;
    private static int positiveAfflictionCooldown = 0;

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerStressProvider.PLAYER_STRESS).isPresent()) {
                event.addCapability(new ResourceLocation(DarkestStress.MOD_ID, "properties"), new PlayerStressProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerStress.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER && !event.player.isCreative()) {
        /*
            if(event.player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof StressMeter) {
                StressHudOverlay.renderOverlay = true;
            } else {
                StressHudOverlay.renderOverlay = false;
            }
        */
            /*
              LIGHT LEVELS | value returned by player.getLightLevelDependentMagicValue()
                        15 - 1.0f
                        14 - 0.77777773f
                        13 - 0.6190476f
                        12 - 0.50000006f
                        11 - 0.40740743f
                        10 - 0.33333334f
                        09 - 0.2727273f
                        08 - 0.22222225f
                        07 - 0.17948718f
                        06 - 0.14285715f
                        05 - 0.11111111f
                        04 - 0.08333335f
                        03 - 0.05882353f
                        02 - 0.03703704f
                        01 - 0.017543862f
                        00 - 0.0f
            */

            event.player.getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(playerStress -> {

                LayerLightEventListener block = event.player.level.getLightEngine().getLayerListener(LightLayer.BLOCK);
                LayerLightEventListener sky =  event.player.level.getLightEngine().getLayerListener(LightLayer.SKY);

                int blockLightLevel = block.getLightValue(event.player.getOnPos().offset(0, 1, 0));
                int skyLightLevel = sky.getLightValue(event.player.getOnPos().offset(0, 1, 0));

                if(blockLightLevel < 6 && skyLightLevel < 6 && StressCommonConfigs.LIGHT_CAUSE_STRESS.get()) {
                    randomizeStress(playerStress, event, 1f);
                }

                if(event.player.getFoodData().getFoodLevel() <= StressCommonConfigs.HUNGER_CAUSE_STRESS_MARK.get() && StressCommonConfigs.HUNGER_CAUSE_STRESS.get()) {
                    float foodModifier = event.player.getFoodData().getFoodLevel() / 20;
                    float stressModifier = 1.5f - foodModifier;
                    randomizeStress(playerStress, event, stressModifier);
                }

                if(playerStress.getStress() == 0 && Arrays.stream(playerStress.negativeAfflictions).anyMatch(playerStress.getAfflictionType()::equals)) {
                    playerStress.setAfflictionType("none");
                }

                if(event.player.isSleeping() && StressCommonConfigs.SLEEP_RELIEVE_STRESS.get()) {
                    if(playerStress.getStress() < playerStress.max_stress && event.player.getRandom().nextFloat() < 0.005f && !rolling) {
                        int randomizedStress = (int)Math.ceil((random.nextInt(6) + 10));
                        playerStress.removeStress(randomizedStress);
                        event.player.displayClientMessage(new TextComponent(event.player.getScoreboardName() + " has lost stress").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY), true);
                    }
                }
                ServerStatsCounter serverstatscounter = ((ServerPlayer)event.player).getStats();
                if(serverstatscounter.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)) > 36000) {
                    float stressModifier = serverstatscounter.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)) * 0.0001f;
                    System.out.println(stressModifier);
                    randomizeStress(playerStress, event, stressModifier);
                }

                testAffliction(playerStress, event);
                checkStress(playerStress, event);
                applyAfflictionEffect(playerStress, event);

            });

        }
    }

    @SubscribeEvent
    public static void onPlayerConsume(LivingEntityUseItemEvent.Finish event) {
        if(event.getEntity() instanceof Player) {
            event.getEntity().getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(playerStress -> {
                if(event.getItem().is(StressTags.Items.STRESS_GAIN_FOOD)) {
                    playerStress.addStress(random.nextInt(3) + 2);
                    ((Player) event.getEntity()).displayClientMessage(new TextComponent(event.getEntity().getScoreboardName() + " has gained stress").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY), true);
                }
                if(event.getItem().is(StressTags.Items.STRESS_RELIEVE_FOOD)) {
                    playerStress.removeStress(random.nextInt(5) + 6);
                    ((Player) event.getEntity()).displayClientMessage(new TextComponent(event.getEntity().getScoreboardName() + " has lost stress").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY), true);
                }
                if(event.getItem().is(StressTags.Items.STRESS_SLIGHT_RELIEVE_FOOD)) {
                    playerStress.removeStress(random.nextInt(6) + 1);
                    ((Player) event.getEntity()).displayClientMessage(new TextComponent(event.getEntity().getScoreboardName() + " has lost stress").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY), true);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerDamagedByMob(LivingDamageEvent damageEvent) {
        if(damageEvent.getEntity() instanceof Player && damageEvent.getSource() == DamageSource.GENERIC) {
            damageEvent.getEntity().getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(playerStress -> {
                playerStress.addStress(random.nextInt(5) + 1);
                ((Player) damageEvent.getEntity()).displayClientMessage(new TextComponent( ((Player) damageEvent.getEntity()).getScoreboardName() + " has gained stress").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY), true);
            });
        }
    }

    private static void randomizeStress(PlayerStress playerStress, TickEvent.PlayerTickEvent event, float stressModifier) {
        if(playerStress.getStress() < playerStress.max_stress && event.player.getRandom().nextFloat() < 0.0005f && !rolling) {
            int randomizedStress = (int)Math.ceil((random.nextInt(5) + 1) * stressModifier);
            playerStress.addStress(randomizedStress);
            event.player.displayClientMessage(new TextComponent(event.player.getScoreboardName() + " has gained stress").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY), true);
        }
    }

    private static void testAffliction(PlayerStress playerStress, TickEvent.PlayerTickEvent event) {
        if(playerStress.getStress() >= 50 && playerStress.getAfflictionType() == "none") {
            if(!rolling) { event.player.sendMessage(new TextComponent(event.player.getScoreboardName() + " is being tested").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), event.player.getUUID()); }
            rolling = true;
            rollCooldown++;
            if(rollCooldown >= 200) {
                if ((random.nextInt(11) % 2) == 0) {
                    event.player.displayClientMessage(new TextComponent(event.player.getScoreboardName() + " passed").withStyle(ChatFormatting.ITALIC, ChatFormatting.BOLD, ChatFormatting.GOLD), true);
                    playerStress.setAfflictionType(playerStress.positiveAfflictions[random.nextInt(playerStress.positiveAfflictions.length)]);
                    event.player.sendMessage(new TranslatableComponent("stress.affliction.roll." + playerStress.getAfflictionType()).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC), event.player.getUUID());
                    playerStress.setStress(0);
                } else {
                    event.player.displayClientMessage(new TextComponent(event.player.getScoreboardName() + " failed").withStyle(ChatFormatting.ITALIC, ChatFormatting.BOLD, ChatFormatting.DARK_RED), true);
                    playerStress.setAfflictionType(playerStress.negativeAfflictions[random.nextInt(playerStress.negativeAfflictions.length)]);
                    event.player.sendMessage(new TranslatableComponent("stress.affliction.roll." + playerStress.getAfflictionType()).withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC), event.player.getUUID());
                }
                rolling = false;
                rollCooldown = 0;
            }
        }
    }

    private static void checkStress(PlayerStress playerStress, TickEvent.PlayerTickEvent event) {
        if(playerStress.getStress() >= playerStress.max_stress) {
            if(Arrays.stream(playerStress.positiveAfflictions).anyMatch(playerStress.getAfflictionType()::equals)) {
                playerStress.setStress(0);
                playerStress.setAfflictionType("none");
            } else if(Arrays.stream(playerStress.negativeAfflictions).anyMatch(playerStress.getAfflictionType()::equals) && StressCommonConfigs.STRESS_CAUSE_HEART_ATTACK.get()) {
                event.player.addEffect(new MobEffectInstance(EffectInit.HEART_ATTACK.get(), 400));
            } else {
//                event.player.sendSystemMessage(Component.literal("IDK, somehow you broke it"));
            }
        }
    }

    private static void applyAfflictionEffect(PlayerStress playerStress, TickEvent.PlayerTickEvent event) {
        switch(playerStress.getAfflictionType()) {
            case "none" : return;

            // POSITIVE AFFLICTIONS

            case "motivated" :
                event.player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 40));
                break;

            case "fearless" :
                event.player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40));
                break;
            case "resistant" :
                event.player.removeAllEffects();
                break;


            // NEGATIVE AFFLICTIONS

            case "hungry" :
                event.player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 40, 2));
                break;

            case "tired" :
                event.player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 40));
                break;

            case "insomniac" :
                if(event.player.isSleeping()) {
                    event.player.stopSleeping();
                }
                break;

            default: return;
        }

        if(Arrays.stream(playerStress.positiveAfflictions).anyMatch(playerStress.getAfflictionType()::equals)) {
            if(positiveAfflictionCooldown >= 6000) { playerStress.setAfflictionType("none"); }
            positiveAfflictionCooldown++;
        }
    }
}
