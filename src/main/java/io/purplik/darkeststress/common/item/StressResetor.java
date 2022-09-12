package io.purplik.darkeststress.common.item;

import io.purplik.darkeststress.DarkestStress;
import io.purplik.darkeststress.common.stress.PlayerStressProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StressResetor extends Item {
    public StressResetor(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide()) {
            player.getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(playerStress -> {
                playerStress.setStress(0);
                playerStress.setAfflictionType("none");
                player.sendSystemMessage(Component.literal("Your stress has been reset").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            });
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.translatable(DarkestStress.MOD_ID + ".tooltip.item.creative").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }
}
