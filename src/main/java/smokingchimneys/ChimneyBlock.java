package smokingchimneys;

import java.util.Random;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = HomeIsWhereTheHearthIs.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ChimneyBlock extends Block {
	
	private static final VoxelShape NORTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
	private static final VoxelShape SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape EAST = Block.makeCuboidShape(14.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D);
	private static final VoxelShape WEST = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 2.0D, 16.0D, 14.0D);
	private static final VoxelShape SHAPE = VoxelShapes.or(NORTH, SOUTH, EAST, WEST);
	
	public static final BooleanProperty EVERSMOKE = BooleanProperty.create("eversmoke");
	
	public ChimneyBlock(Block parent) {
		super(Block.Properties.from(parent));
		this.setDefaultState(this.stateContainer.getBaseState().with(EVERSMOKE, false));
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if(this.canSmoke(world, pos, state)) {
			for(int i = 0; i < 15; i++) {
				world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0, 0.07D, 0);
			}
		}
	}
	
	public boolean canSmoke(World world, BlockPos pos, BlockState state) {
		if(world.getBlockState(pos.up()).isSolid())
			return false;
		
		if(state.get(EVERSMOKE).booleanValue())
			return true;
		
		while(world.getBlockState(pos).getBlock() instanceof ChimneyBlock) {
			for(Direction facing : Direction.values()) {
				if(facing != Direction.UP) {
					if(this.isSmokeSource(world.getBlockState(pos.offset(facing)))) {
						return true;
					}
				}
			}
			if(world.getBlockState(pos).getBlock() instanceof ChimneyBlock) {
				if(world.getBlockState(pos).get(EVERSMOKE).booleanValue()) {
					return true;
				}
			}

			pos = pos.down();
		}
		if(world.isAirBlock(pos) && this.isSmokeSource(world.getBlockState(pos.down())))
			return true;
		
		return false;
	}
	
	public boolean isSmokeSource(BlockState state) {
		return (state.getBlock() == Blocks.FURNACE && state.get(AbstractFurnaceBlock.LIT).booleanValue())
				|| (state.getBlock() == Blocks.BLAST_FURNACE && state.get(AbstractFurnaceBlock.LIT).booleanValue())
				|| (state.getBlock() == Blocks.SMOKER && state.get(AbstractFurnaceBlock.LIT).booleanValue())
				|| state.getBlock() == Blocks.FIRE
				|| state.getBlock() == Blocks.CAMPFIRE
				|| state.getBlock() == Blocks.MAGMA_BLOCK;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult raytraceresult) {
		boolean option = !state.get(EVERSMOKE).booleanValue();
		if(player.isCrouching()) {
			if(!world.isRemote) {
				world.setBlockState(pos, this.getDefaultState().with(EVERSMOKE, option));
			}
			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}
	
	@Override
	public boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos) {
		return false;
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(EVERSMOKE);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		brick_chimney = createChimney(new ChimneyBlock(Blocks.BRICKS), "brick_chimney");
		cobblestone_chimney = createChimney(new ChimneyBlock(Blocks.COBBLESTONE), "cobblestone_chimney");
		mossy_cobblestone_chimney = createChimney(new ChimneyBlock(Blocks.MOSSY_COBBLESTONE), "mossy_cobblestone_chimney");
		stone_chimney = createChimney(new ChimneyBlock(Blocks.STONE), "stone_chimney");
		smooth_stone_chimney = createChimney(new ChimneyBlock(Blocks.SMOOTH_STONE), "smooth_stone_chimney");
		stone_brick_chimney = createChimney(new ChimneyBlock(Blocks.STONE_BRICKS), "stone_brick_chimney");
		cracked_stone_brick_chimney = createChimney(new ChimneyBlock(Blocks.CRACKED_STONE_BRICKS), "cracked_stone_brick_chimney");
		mossy_stone_brick_chimney = createChimney(new ChimneyBlock(Blocks.MOSSY_STONE_BRICKS), "mossy_stone_brick_chimney");
		granite_chimney = createChimney(new ChimneyBlock(Blocks.GRANITE), "granite_chimney");
		andesite_chimney = createChimney(new ChimneyBlock(Blocks.ANDESITE), "andesite_chimney");
		diorite_chimney = createChimney(new ChimneyBlock(Blocks.DIORITE), "diorite_chimney");
		polished_granite_chimney = createChimney(new ChimneyBlock(Blocks.POLISHED_GRANITE), "polished_granite_chimney");
		polished_andesite_chimney = createChimney(new ChimneyBlock(Blocks.POLISHED_ANDESITE), "polished_andesite_chimney");
		polished_diorite_chimney = createChimney(new ChimneyBlock(Blocks.POLISHED_DIORITE), "polished_diorite_chimney");
		nether_brick_chimney = createChimney(new ChimneyBlock(Blocks.NETHER_BRICKS), "nether_brick_chimney");
		red_nether_brick_chimney = createChimney(new ChimneyBlock(Blocks.RED_NETHER_BRICKS), "red_nether_brick_chimney");
		smooth_sandstone_chimney = createChimney(new ChimneyBlock(Blocks.SMOOTH_SANDSTONE), "smooth_sandstone_chimney");
		smooth_red_sandstone_chimney = createChimney(new ChimneyBlock(Blocks.SMOOTH_RED_SANDSTONE), "smooth_red_sandstone_chimney");
	}
	
	public static Block createChimney(Block chimney, String name) {
		chimney.setRegistryName(new ResourceLocation(HomeIsWhereTheHearthIs.MOD_ID, name));
		ForgeRegistries.BLOCKS.register(chimney);
		
		BlockItem chimney_item = new BlockItem(chimney, new Item.Properties().group(HomeIsWhereTheHearthIs.HEARTH_ITEMGROUP));
		chimney_item.setRegistryName(new ResourceLocation(HomeIsWhereTheHearthIs.MOD_ID, name));
		ForgeRegistries.ITEMS.register(chimney_item);
		
		return chimney;
	}
	
	public static Block brick_chimney, cobblestone_chimney, mossy_cobblestone_chimney, stone_chimney, smooth_stone_chimney,
						stone_brick_chimney, cracked_stone_brick_chimney, mossy_stone_brick_chimney, granite_chimney,
						andesite_chimney, diorite_chimney, polished_granite_chimney, polished_andesite_chimney,
						polished_diorite_chimney, nether_brick_chimney, red_nether_brick_chimney, smooth_sandstone_chimney,
						smooth_red_sandstone_chimney;
}
