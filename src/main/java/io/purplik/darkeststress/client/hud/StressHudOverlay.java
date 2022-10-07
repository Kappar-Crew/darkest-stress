/*
package io.purplik.darkeststress.client.hud;

import io.purplik.darkeststress.DarkestStress;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class StressHudOverlay {

    private static final ResourceLocation NOT_STRESSED = new ResourceLocation(DarkestStress.MOD_ID, "textures/gui/not_stressed.png");
    private static final ResourceLocation STRESSED = new ResourceLocation(DarkestStress.MOD_ID, "textures/gui/stressed.png");

    public static boolean renderOverlay = false;

    public static final IGuiOverlay HUD_STRESS = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        if(renderOverlay) {
            int x = screenWidth;
            int y = screenHeight;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, NOT_STRESSED);
            GuiComponent.blit(poseStack, x/2, y/2, 0, 0, 64, 64, 64, 64);
            RenderSystem.setShaderTexture(0, STRESSED);/*
            if(ClientStressData.getPlayerStress() >= 50) {
                GuiComponent.blit(poseStack, x, y, 0, 0, 64, 64, 64, 64);
            }
        }
    });
}
*/