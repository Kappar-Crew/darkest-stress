package io.purplik.darkeststress.common.item;

import io.purplik.darkeststress.DarkestStress;
import io.purplik.darkeststress.common.stress.PlayerStressProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StressBall extends Item {
    public StressBall(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide()) {
            player.getCapability(PlayerStressProvider.PLAYER_STRESS).ifPresent(playerStress -> {
                playerStress.removeStress(5);
                String debugMessage = player.getScoreboardName() + " has relieved 5 stress";
                String message = "You have " + playerStress.getStress() + " stress";
                player.sendMessage(new TextComponent(debugMessage).withStyle(ChatFormatting.GRAY), player.getUUID());
                player.sendMessage(new TextComponent(message).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), player.getUUID());
            });
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(new TranslatableComponent(DarkestStress.MOD_ID + ".tooltip.item.creative").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }
}
