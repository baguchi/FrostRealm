package baguchan.frostrealm.client.render;

import bagu_chan.bagus_lib.client.layer.CustomArmorLayer;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.StrayWarriorModel;
import baguchan.frostrealm.entity.StrayWarrior;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StrayWarriorRenderer<T extends StrayWarrior> extends MobRenderer<T, StrayWarriorModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/skeleton/stray.png");

    public StrayWarriorRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new StrayWarriorModel<>(p_173952_.bakeLayer(FrostModelLayers.STRAY_WARRIOR)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer()));
        this.addLayer(new CustomArmorLayer<>(this, p_173952_));
    }


    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}