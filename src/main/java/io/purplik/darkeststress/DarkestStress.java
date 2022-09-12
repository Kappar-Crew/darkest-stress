package io.purplik.darkeststress;


import io.purplik.darkeststress.config.StressCommonConfigs;
import io.purplik.darkeststress.init.BlockInit;
import io.purplik.darkeststress.init.EffectInit;
import io.purplik.darkeststress.init.ItemInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DarkestStress.MOD_ID)
public class DarkestStress
{
    public static final String MOD_ID = "darkeststress";

    public DarkestStress()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ItemInit.ITEM.register(modEventBus);
        BlockInit.BLOCK.register(modEventBus);
        BlockInit.ITEM.register(modEventBus);

        EffectInit.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StressCommonConfigs.SPEC, "darkeststress-common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
}
