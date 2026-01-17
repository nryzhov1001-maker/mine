package net.mcreator.zvxchain.block;

import net.mcreator.zvxchain.ZvxchainMod;
import net.mcreator.zvxchain.itemgroup.ZVXchainItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChainBlock {
    public static final PropertyDirection FACING = BlockDirectional.FACING;
    public static final Block block = new CustomBlock();
    public static final Item itemBlock = new ItemBlock(block).setRegistryName(block.getRegistryName());

    public static class CustomBlock extends Block {
        private static final AxisAlignedBB AABB_VERTICAL = new AxisAlignedBB(0.4, 0.0, 0.4, 0.6, 1.0, 0.6);
        private static final AxisAlignedBB AABB_HORIZONTAL_NS = new AxisAlignedBB(0.4, 0.4, 0.0, 0.6, 0.6, 1.0);
        private static final AxisAlignedBB AABB_HORIZONTAL_EW = new AxisAlignedBB(0.0, 0.4, 0.4, 1.0, 0.6, 0.6);

        public CustomBlock() {
            super(Material.IRON);
            setUnlocalizedName("chain");
            setRegistryName("chain");
            setSoundType(SoundType.METAL);
            setHardness(1.0F);
            setResistance(10.0F);
            setLightOpacity(0);
            setCreativeTab(ZVXchainItemGroup.tab);
            setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
        }

        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
            EnumFacing facing = state.getValue(FACING);
            switch (facing) {
                case EAST:
                case WEST:
                    return AABB_HORIZONTAL_EW;
                case UP:
                case DOWN:
                    return AABB_HORIZONTAL_NS;
                default:
                    return AABB_VERTICAL;
            }
        }

        @Override
        @SideOnly(Side.CLIENT)
        public BlockRenderLayer getBlockLayer() {
            return BlockRenderLayer.CUTOUT;
        }

        @Override
        public boolean isOpaqueCube(IBlockState state) {
            return false;
        }

        @Override
        public boolean isFullCube(IBlockState state) {
            return false;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
            IBlockState adjacentState = blockAccess.getBlockState(pos.offset(side));
            if (adjacentState.getBlock() == this) {
                return false;
            }
            return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }

        @Override
        public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
            return BlockFaceShape.UNDEFINED;
        }

        @Override
        protected BlockStateContainer createBlockState() {
            return new BlockStateContainer(this, FACING);
        }

        @Override
        public IBlockState getStateFromMeta(int meta) {
            return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
        }

        @Override
        public int getMetaFromState(IBlockState state) {
            return state.getValue(FACING).getIndex();
        }

        @Override
        public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
            EnumFacing placementFacing = facing;
            if (placementFacing == EnumFacing.WEST || placementFacing == EnumFacing.EAST) {
                placementFacing = EnumFacing.UP;
            } else if (placementFacing == EnumFacing.NORTH || placementFacing == EnumFacing.SOUTH) {
                placementFacing = EnumFacing.EAST;
            } else {
                placementFacing = EnumFacing.SOUTH;
            }
            return getDefaultState().withProperty(FACING, placementFacing);
        }

        @Override
        public IBlockState withRotation(IBlockState state, Rotation rot) {
            EnumFacing facing = state.getValue(FACING);
            if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) {
                if (facing == EnumFacing.WEST || facing == EnumFacing.EAST) {
                    return state.withProperty(FACING, EnumFacing.UP);
                } else if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
                    return state.withProperty(FACING, EnumFacing.WEST);
                }
            }
            return state;
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel() {
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(ZvxchainMod.MODID + ":chain", "inventory"));
    }
}

