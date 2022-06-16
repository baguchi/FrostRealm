package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FrostBeasterModel;
import baguchan.frostrealm.entity.FrostBeaster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrostBeasterRenderer<T extends FrostBeaster> extends MobRenderer<T, FrostBeasterModel<T>> {
	private static final ResourceLocation FROST_BEASTER = new ResourceLocation(FrostRealm.MODID, "textures/entity/wolfester/frost_beaster.png");
	private static final RenderType FROST_BEASTER_EYES = RenderType.eyes(new ResourceLocation(FrostRealm.MODID, "textures/entity/wolfester/frost_beaster_eyes.png"));


	public FrostBeasterRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new FrostBeasterModel<>(p_173952_.bakeLayer(FrostModelLayers.WOLFESTER)), 0.5F);
		this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel(p_173952_.bakeLayer(FrostModelLayers.FROST_BEASTER_INNER_ARMOR)), new HumanoidModel(p_173952_.bakeLayer(FrostModelLayers.FROST_BEASTER_OUTER_ARMOR))));
		this.addLayer(new ItemInHandLayer<>(this, Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer()));
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public RenderType renderType() {
				return FROST_BEASTER_EYES;
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return FROST_BEASTER;
	}
}