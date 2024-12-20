package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.api.IGlintAurora;
import baguchan.frostrealm.utils.ClientUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderState.class)
public class ItemStackRenderStateMixin implements IGlintAurora {
    @Shadow
    private int activeLayerCount;
    @Shadow
    private ItemStackRenderState.LayerRenderState[] layers;
    @Shadow
    private ItemDisplayContext displayContext;
    @Shadow
    private boolean isLeftHand;
    @Unique
    private boolean frostRealm$glint;

    @Inject(method = "render", at = @At("HEAD"))
    public void render(PoseStack p_388193_, MultiBufferSource p_388719_, int p_386913_, int p_387272_, CallbackInfo ci) {
        for (int i = 0; i < this.activeLayerCount; ++i) {
            if (this.frostRealm$glint) {
                p_388193_.pushPose();
                if (((LayerRenderStateAccessor) this.layers[i]).getBakedModel() != null)
                    ((LayerRenderStateAccessor) this.layers[i]).getBakedModel().applyTransform(displayContext, p_388193_, this.isLeftHand);
                else
                    this.frostRealm$transform(((LayerRenderStateAccessor) this.layers[i]).getBakedModel()).apply(this.isLeftHand, p_388193_);
                p_388193_.translate(-0.5F, -0.5F, -0.5F);
                ClientUtils.renderItemAurora(p_388193_, p_388719_, p_386913_, p_387272_, ((LayerRenderStateAccessor) this.layers[i]).getTintLayers(), ((LayerRenderStateAccessor) this.layers[i]).getBakedModel(), ((LayerRenderStateAccessor) this.layers[i]).getRenderType());

                p_388193_.popPose();
            }
        }
    }

    @Unique
    ItemTransform frostRealm$transform(BakedModel bakedModel) {
        return bakedModel != null ? bakedModel.getTransforms().getTransform(this.displayContext) : ItemTransform.NO_TRANSFORM;
    }

    @Override
    public void frostRealm$setGlint(boolean glint) {
        this.frostRealm$glint = glint;
    }

    @Override
    public boolean frostRealm$hasGlint() {
        return this.frostRealm$glint;
    }
}
