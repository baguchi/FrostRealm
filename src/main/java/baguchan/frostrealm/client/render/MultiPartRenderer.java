package baguchan.frostrealm.client.render;

import baguchan.frostrealm.entity.hostile.part.CorruptedWalker;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MultiPartRenderer extends MobRenderer<CorruptedWalker, CowModel<CorruptedWalker>> {
    private static final ResourceLocation COW_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/cow/cow.png");

    public MultiPartRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(ModelLayers.COW)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(CorruptedWalker corrupedWalker) {
        return COW_LOCATION;
    }
}
