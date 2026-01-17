package net.mcreator.zvxchain;

import net.mcreator.zvxchain.block.ChainBlock;
import net.mcreator.zvxchain.block.Chain2Block;
import net.mcreator.zvxchain.itemgroup.ZVXchainItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = ZvxchainMod.MODID, name = ZvxchainMod.NAME, version = ZvxchainMod.VERSION)
public class ZvxchainMod {
    public static final String MODID = "zvxchain";
    public static final String NAME = "ZVXchain";
    public static final String VERSION = "2.1";

    @Mod.Instance(MODID)
    public static ZvxchainMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ZVXchainItemGroup.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventBusSubscriber
    public static class RegistryHandler {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(ChainBlock.block);
            event.getRegistry().register(Chain2Block.block);
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(ChainBlock.itemBlock);
            event.getRegistry().register(Chain2Block.itemBlock);
        }

        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ChainBlock.registerModel();
            Chain2Block.registerModel();
        }
    }
}

