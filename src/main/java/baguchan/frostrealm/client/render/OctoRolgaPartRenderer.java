package baguchan.frostrealm.client.render;

import baguchan.frostrealm.entity.OctorolgaPart;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class OctoRolgaPartRenderer<T extends OctorolgaPart> extends EntityRenderer<T> {
	public OctoRolgaPartRenderer(EntityRendererProvider.Context p_174008_) {
		super(p_174008_);
	}

	@Override
	public void render(T p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
		BlockState blockstate = Blocks.MAGMA_BLOCK.defaultBlockState();
		if (blockstate.getRenderShape() == RenderShape.MODEL) {
			Level level = p_114485_.getLevel();
			if (blockstate != level.getBlockState(p_114485_.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
				p_114488_.pushPose();
				BlockPos blockpos = new BlockPos(p_114485_.getX(), p_114485_.getBoundingBox().maxY, p_114485_.getZ());
				p_114488_.translate(-0.5D, 0.0D, -0.5D);
				var model = Minecraft.getInstance().getBlockRenderer().getBlockModel(blockstate);
				for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(p_114485_.blockPosition())), net.minecraftforge.client.model.data.ModelData.EMPTY))
					Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, p_114488_, p_114489_.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(p_114485_.blockPosition()), OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.ModelData.EMPTY, renderType);
				p_114488_.popPose();
			}
		}
		super.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_114482_) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
