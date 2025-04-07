package baguchan.frostrealm.mixin.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public interface LayerRenderStateAccessor {

    @Accessor(value = "renderType")
    public RenderType getRenderType();

    @Accessor(value = "quads")
    public List<BakedQuad> getQuads();

    @Accessor(value = "tintLayers")
    public int[] getTintLayers();


    @Accessor(value = "foilType")
    public ItemStackRenderState.FoilType getFoilType();
}
