package io.purplik.darkeststress.common.item;

import io.purplik.darkeststress.common.stress.PlayerStressProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StressMeter extends Item {

    public StressMeter(Properties p_41383_) {
        super(p_41383_);
    }

    private static int playerStressLevel;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide()) {
            player.getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(playerStress -> {
                player.sendSystemMessage(Component.literal("You have " + playerStress.getStress() + " stress").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            });
        }

        return super.use(level, player, interactionHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);
        if(!level.isClientSide()) {
            entity.getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(playerStress -> {
                if (playerStress.getStress() <= 25) {
                    playerStressLevel = 0;
                } else if (25 < playerStress.getStress() && playerStress.getStress() <= 50) {
                    playerStressLevel = 1;
                } else if (50 < playerStress.getStress() && playerStress.getStress() <= 75) {
                    playerStressLevel = 2;
                } else if (playerStress.getStress() > 75) {
                    playerStressLevel = 3;
                }
            });
        }
    }

    public static ItemPropertyFunction playerStressLevelGetter() {
        return (stack, world, entity, unused) -> {
            if(playerStressLevel != 0) {
                return playerStressLevel;
            }
            return 0;
        };
    }
}
