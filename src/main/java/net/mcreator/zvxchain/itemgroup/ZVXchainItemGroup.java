package net.mcreator.zvxchain.itemgroup;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ZVXchainItemGroup {
    public static CreativeTabs tab;

    public static void init() {
        tab = new CreativeTabs("tabzv_xchain") {
            @SideOnly(Side.CLIENT)
            @Override
            public ItemStack getTabIconItem() {
                return new ItemStack(Blocks.CHAIN);
            }

            @SideOnly(Side.CLIENT)
            @Override
            public boolean hasSearchBar() {
                return false;
            }
        };
    }
}

