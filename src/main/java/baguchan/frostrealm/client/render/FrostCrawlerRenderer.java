package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.FrostCrawlerModel;
import baguchan.frostrealm.client.render.state.FrostCrawlerRenderState;
import baguchan.frostrealm.entity.hostile.FrostCrawler;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FrostCrawlerRenderer<T extends FrostCrawler> extends MobRenderer<T, FrostCrawlerRenderState, FrostCrawlerModel<FrostCrawlerRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/frost_crawler.png");

    public FrostCrawlerRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new FrostCrawlerModel<>(p_173952_.bakeLayer(FrostModelLayers.FROST_CRAWLER)), 0.5F);
    }

    @Override
    public FrostCrawlerRenderState createRenderState() {
        return new FrostCrawlerRenderState();
    }

    @Override
    public void extractRenderState(T p_362733_, FrostCrawlerRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
    }

    @Override
    public ResourceLocation getTextureLocation(FrostCrawlerRenderState p_110775_1_) {
        return TEXTURE;
    }
}