package io.purplik.darkeststress.init;

import io.purplik.darkeststress.DarkestStress;
import io.purplik.darkeststress.common.item.StressBall;
import io.purplik.darkeststress.common.item.StressInflictor;
import io.purplik.darkeststress.common.item.StressMeter;
import io.purplik.darkeststress.common.item.StressResetor;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {

    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, DarkestStress.MOD_ID);

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(DarkestStress.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(STRESS_INFLICTOR.get());
        }
    };


    public static final RegistryObject<Item> STRESS_INFLICTOR = ITEM.register("stress_inflictor", () -> new StressInflictor(new Item.Properties().tab(CREATIVE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> STRESS_RESETOR = ITEM.register("stress_resetor", () -> new StressResetor(new Item.Properties().tab(CREATIVE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> STRESS_BALL = ITEM.register("stress_ball", () -> new StressBall(new Item.Properties().tab(CREATIVE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> STRESS_METER = ITEM.register("stress_meter", () -> new StressMeter(new Item.Properties().tab(CREATIVE_TAB).stacksTo(1)));
}
