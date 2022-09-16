package io.purplik.darkeststress.client;

import io.purplik.darkeststress.common.item.StressMeter;
import io.purplik.darkeststress.init.ItemInit;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModelHandler {

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ItemInit.STRESS_METER.get(), new ResourceLocation("stress"), StressMeter.playerStressLevelGetter());
        });
    }

}
