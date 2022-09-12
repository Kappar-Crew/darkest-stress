package io.purplik.darkeststress.common.item;

import io.purplik.darkeststress.common.stress.PlayerStressProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StressMeter extends Item {

    public StressMeter(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide()) {
            player.getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(playerStress -> {
                player.sendSystemMessage(Component.literal("You have " + playerStress.getStress() + " stress").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            });
        }

        return super.use(level, player, interactionHand);
    }
}
