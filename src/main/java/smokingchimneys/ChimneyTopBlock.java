package smokingchimneys;

import java.util.Random;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChimneyTopBlock extends Block {
	
	private static final VoxelShape BASE_NORTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 2.0D);
	private static final VoxelShape BASE_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 6.0D, 16.0D);
	private static final VoxelShape BASE_EAST = Block.makeCuboidShape(14.0D, 0.0D, 2.0D, 16.0D, 6.0D, 14.0D);
	private static final VoxelShape BASE_WEST = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 2.0D, 6.0D, 14.0D);
	private static final VoxelShape MID = Block.makeCuboidShape(2.0D, 6.0D, 2.0D, 14.0D, 8.0D, 14.0D);
	private static final VoxelShape TOP = Block.makeCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	private static final VoxelShape SHAPE = VoxelShapes.or(BASE_NORTH , BASE_SOUTH, BASE_EAST, BASE_WEST, MID, TOP);
	
	public static final BooleanProperty EVERSMOKE = BooleanProperty.create("eversmoke");
	
	public ChimneyTopBlock(Block parent) {
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
				world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + 0.5D, pos.getY() + 1.15D, pos.getZ() + 0.5D, 0, 0.07D, 0);
			}
		}
	}
	
	public boolean canSmoke(World world, BlockPos pos, BlockState state) {
		if(world.getBlockState(pos.up()).isSolid())
			return false;
		
		if(state.get(EVERSMOKE).booleanValue())
			return true;
		
		pos = pos.down();
		
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
				|| (state.getBlock() instanceof FireplaceBlock && state.get(FireplaceBlock.LIT).booleanValue())
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
}
