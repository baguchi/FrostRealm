package baguchan.frostrealm.client.render;

import bagu_chan.bagus_lib.client.layer.CustomArmorLayer;
import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.YetiModel;
import baguchan.frostrealm.entity.Yeti;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YetiRenderer<T extends Yeti> extends MobRenderer<T, YetiModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/yeti/yeti.png");
    private static final ResourceLocation JAKT_TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/yeti/jakt_yeti.png");
    private static final RenderType YETI_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/yeti/yeti_glow.png"));

	public YetiRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new YetiModel<>(p_173952_.bakeLayer(FrostModelLayers.YETI)), 0.75F);
        this.addLayer(new ItemInHandLayer<>(this, Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer()));
        this.addLayer(new CustomArmorLayer<>(this, p_173952_));
        this.addLayer(new EyesLayer<T, YetiModel<T>>(this) {
            @Override
            public RenderType renderType() {
                return YETI_GLOW;
            }
        });
	}

	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
        p_115315_.scale(p_115314_.getAgeScale(), p_115314_.getAgeScale(), p_115314_.getAgeScale());
		super.scale(p_115314_, p_115315_, p_115316_);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
        return p_110775_1_.isHuntLeader() ? JAKT_TEXTURE : TEXTURE;
	}
}