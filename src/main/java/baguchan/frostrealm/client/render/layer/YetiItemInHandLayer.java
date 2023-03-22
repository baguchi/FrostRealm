package baguchan.frostrealm.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YetiItemInHandLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends ItemInHandLayer<T, M> {
	public YetiItemInHandLayer(RenderLayerParent<T, M> p_117183_, ItemInHandRenderer itemInHandRenderer) {
		super(p_117183_, itemInHandRenderer);
	}

	@Override
	protected void renderArmWithItem(LivingEntity p_117185_, ItemStack p_117186_, ItemDisplayContext p_117187_, HumanoidArm p_117188_, PoseStack p_117189_, MultiBufferSource p_117190_, int p_117191_) {
		if (!p_117186_.isEmpty()) {
			p_117189_.pushPose();
			this.getParentModel().translateToHand(p_117188_, p_117189_);
			p_117189_.mulPose(Axis.XP.rotationDegrees(-90.0F));
			p_117189_.mulPose(Axis.YP.rotationDegrees(180.0F));
			boolean flag = p_117188_ == HumanoidArm.LEFT;
			p_117189_.translate((float) (flag ? -1 : 1) / 16.0F, 0.125D, -1.45D);
			Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(p_117185_, p_117186_, p_117187_, flag, p_117189_, p_117190_, p_117191_);
			p_117189_.popPose();
		}
	}
}
