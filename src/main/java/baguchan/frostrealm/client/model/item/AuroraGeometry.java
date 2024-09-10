package baguchan.frostrealm.client.model.item;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.ItemLayerModel;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;

import java.util.function.Function;

public class AuroraGeometry implements IUnbakedGeometry<AuroraGeometry> {
    private final ItemLayerModel base;

    // Store the block model for usage below.
    public AuroraGeometry(ItemLayerModel base) {
        this.base = base;
    }

    // Method responsible for model baking, returning our dynamic model. Parameters in this method are:
    // - The geometry baking context. Contains many properties that we will pass into the model, e.g. light and ao values.
    // - The model baker. Can be used for baking sub-models.
    // - The sprite getter. Maps materials (= texture variables) to TextureAtlasSprites. Materials can be obtained from the context.
    //   For example, to get a model's particle texture, call spriteGetter.apply(context.getMaterial("particle"));
    // - The model state. This holds the properties from the blockstate file, e.g. rotations and the uvlock boolean.
    // - The item overrides. This is the code representation of an "overrides" block in an item model.
    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
        // See info on the parameters below.
        BakedModel bakedBase = base.bake(context, baker, spriteGetter, modelState, overrides);
        return new AuroraModel(bakedBase);
    }

    // Method responsible for correctly resolving parent properties. Required if this model loads any nested models or reuses the vanilla loader on itself (see below).
    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
        base.resolveParents(modelGetter, context);
    }
}