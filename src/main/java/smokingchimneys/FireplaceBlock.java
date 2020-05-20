package smokingchimneys;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FireplaceBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE; //don't need to reinvent the wheel
	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	
	public FireplaceBlock(Block parent) {
		super(Block.Properties.from(parent));
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(TYPE, ChestType.SINGLE).with(LIT, false));
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if(state.get(LIT)) {
			for(int i = 0; i < 10; i++) {
				double posX = pos.getX() + 0.5D + (state.get(FACING).getZOffset() * 0.5 * (rand.nextDouble() - rand.nextDouble()));
				double posZ = pos.getZ() + 0.5D + (state.get(FACING).getXOffset() * 0.5 * (rand.nextDouble() - rand.nextDouble()));
				world.addParticle(ParticleTypes.SMOKE, posX, pos.getY() + 0.5D, posZ, 0, 0, 0);
			}	
		}
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.get(LIT) ? 15 : 0;
	}
	
	//yeah I stole all this from ChestBlock.class
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (facingState.getBlock() == this && facing.getAxis().isHorizontal()) {
			ChestType chesttype = facingState.get(TYPE);
			if (stateIn.get(TYPE) == ChestType.SINGLE && chesttype != ChestType.SINGLE
					&& stateIn.get(FACING) == facingState.get(FACING)
					&& getDirectionToAttached(facingState) == facing.getOpposite()) {
				return stateIn.with(TYPE, chesttype.opposite());
			}
		} else if (getDirectionToAttached(stateIn) == facing) {
			return stateIn.with(TYPE, ChestType.SINGLE);
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	//to do: stop fire from appearing and disappearing
	//light/extinguish fireplace for both, if connected
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult raytraceresult) {
		if(!world.isRemote) {
			Direction dir = getDirectionToAttached(state);
			if(!state.get(LIT)) {
				if(player.getHeldItem(hand).getItem() == Items.FLINT_AND_STEEL || player.getHeldItem(hand).getItem() == Items.FIRE_CHARGE) {
					world.setBlockState(pos, state.with(LIT, true));
					if (state.get(TYPE) != ChestType.SINGLE) {
						world.setBlockState(pos.offset(dir), world.getBlockState(pos.offset(dir)).with(LIT, true));
					}
					return ActionResultType.SUCCESS;
				}
			} else {
				world.setBlockState(pos, state.with(LIT, false));
				if (state.get(TYPE) != ChestType.SINGLE) {
					world.setBlockState(pos.offset(dir), world.getBlockState(pos.offset(dir)).with(LIT, false));
				}
				return ActionResultType.SUCCESS;
			}
		}

		return super.onBlockActivated(state, world, pos, player, hand, raytraceresult);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		ChestType chesttype = ChestType.SINGLE;
		Direction direction = context.getPlacementHorizontalFacing().getOpposite();
		boolean flag = context.func_225518_g_();
		Direction direction1 = context.getFace();
		if (direction1.getAxis().isHorizontal() && flag) {
			Direction direction2 = this.getDirectionToAttach(context, direction1.getOpposite());
			if (direction2 != null && direction2.getAxis() != direction1.getAxis()) {
				direction = direction2;
				chesttype = direction2.rotateYCCW() == direction1.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
			}
		}

		if (chesttype == ChestType.SINGLE && !flag) {
			if (direction == this.getDirectionToAttach(context, direction.rotateY())) {
				chesttype = ChestType.LEFT;
			} else if (direction == this.getDirectionToAttach(context, direction.rotateYCCW())) {
				chesttype = ChestType.RIGHT;
			}
		}

		return this.getDefaultState().with(FACING, direction).with(TYPE, chesttype);
	}
	
	public static Direction getDirectionToAttached(BlockState state) {
		Direction direction = state.get(FACING);
		return state.get(TYPE) == ChestType.LEFT ? direction.rotateY() : direction.rotateYCCW();
	}

	@Nullable
	private Direction getDirectionToAttach(BlockItemUseContext p_196312_1_, Direction p_196312_2_) {
		BlockState blockstate = p_196312_1_.getWorld().getBlockState(p_196312_1_.getPos().offset(p_196312_2_));
		return blockstate.getBlock() == this && blockstate.get(TYPE) == ChestType.SINGLE ? blockstate.get(FACING)
				: null;
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, TYPE, LIT);
	}
}
