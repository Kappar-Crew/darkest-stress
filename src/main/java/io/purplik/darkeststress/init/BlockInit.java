package io.purplik.darkeststress.init;

import io.purplik.darkeststress.DarkestStress;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, DarkestStress.MOD_ID);
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, DarkestStress.MOD_ID);
/*
    public static <B extends Block> RegistryObject<Item> makeBlockItem(RegistryObject<Block> block) {
        return ITEM.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(ItemInit.CREATIVE_TAB)));
    }
*/
}