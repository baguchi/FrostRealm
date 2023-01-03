package baguchan.frostrealm.block;

import baguchan.frostrealm.menu.AuroraInfuserMenu;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class AuroraInfuserBlock extends Block {
	private static final Component CONTAINER_TITLE = Component.translatable("container.frostrealm.aurora_infuser");
	public static final List<BlockPos> OFFSETS = BlockPos.betweenClosedStream(-2, 0, -2, 2, 1, 2).filter((p_207914_) -> {
		return Math.abs(p_207914_.getX()) == 2 || Math.abs(p_207914_.getZ()) == 2;
	}).map(BlockPos::immutable).toList();

	public AuroraInfuserBlock(BlockBehaviour.Properties p_52953_) {
		super(p_52953_);
	}

	public static boolean isValidWarpedCrystal(Level p_207910_, BlockPos p_207911_, BlockPos p_207912_) {
		return p_207910_.getBlockState(p_207911_.offset(p_207912_)).getBlock() == FrostBlocks.WARPED_CRYSTAL_BLOCK.get() && p_207910_.isEmptyBlock(p_207911_.offset(p_207912_.getX() / 2, p_207912_.getY(), p_207912_.getZ() / 2));
	}

	public boolean useShapeForLightOcclusion(BlockState p_52997_) {
		return true;
	}

	public void animateTick(BlockState p_221092_, Level p_221093_, BlockPos p_221094_, RandomSource p_221095_) {
		super.animateTick(p_221092_, p_221093_, p_221094_, p_221095_);

		for (BlockPos blockpos : OFFSETS) {
			if (p_221095_.nextInt(16) == 0 && isValidWarpedCrystal(p_221093_, p_221094_, blockpos)) {
				p_221093_.addParticle(ParticleTypes.ENCHANT, (double) p_221094_.getX() + 0.5D, (double) p_221094_.getY() + 2.0D, (double) p_221094_.getZ() + 0.5D, (double) ((float) blockpos.getX() + p_221095_.nextFloat()) - 0.5D, (double) ((float) blockpos.getY() - p_221095_.nextFloat() - 1.0F), (double) ((float) blockpos.getZ() + p_221095_.nextFloat()) - 0.5D);
			}
		}

	}

	public RenderShape getRenderShape(BlockState p_52986_) {
		return RenderShape.MODEL;
	}

	public InteractionResult use(BlockState p_52974_, Level p_52975_, BlockPos p_52976_, Player p_52977_, InteractionHand p_52978_, BlockHitResult p_52979_) {
		if (p_52975_.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			p_52977_.openMenu(p_52974_.getMenuProvider(p_52975_, p_52976_));
			return InteractionResult.CONSUME;
		}
	}

	public MenuProvider getMenuProvider(BlockState p_52240_, Level p_52241_, BlockPos p_52242_) {
		return new SimpleMenuProvider((p_52229_, p_52230_, p_52231_) -> {
			return new AuroraInfuserMenu(p_52229_, p_52230_, ContainerLevelAccess.create(p_52241_, p_52242_));
		}, CONTAINER_TITLE);
	}

	public boolean isPathfindable(BlockState p_52969_, BlockGetter p_52970_, BlockPos p_52971_, PathComputationType p_52972_) {
		return false;
	}
}
