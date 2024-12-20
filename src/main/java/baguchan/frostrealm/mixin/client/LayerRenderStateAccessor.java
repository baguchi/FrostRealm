package baguchan.frostrealm.mixin.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public interface LayerRenderStateAccessor {

    @Accessor(value = "renderType")
    public RenderType getRenderType();

    @Accessor(value = "model")
    public BakedModel getBakedModel();

    @Accessor(value = "tintLayers")
    public int[] getTintLayers();


    @Accessor(value = "foilType")
    public ItemStackRenderState.FoilType getFoilType();
}
