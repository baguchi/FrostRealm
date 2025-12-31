package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Supplier;

@Mixin(BlockModelWrapper.class)
public class BlockModelWrapperMixin {

    @Shadow
    @Final
    private ModelRenderProperties properties;

    @Shadow
    @Final
    private List<BakedQuad> quads;

    @Shadow
    @Final
    private Supplier<Vector3fc[]> extents;

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;setFoilType(Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"))
    public void update(
            ItemStackRenderState p_386488_, ItemStack p_386443_, ItemModelResolver p_388726_, ItemDisplayContext p_388231_, ClientLevel p_387522_, ItemOwner p_434975_, int p_388300_, CallbackInfo ci
    ) {
        ItemStackRenderState.LayerRenderState itemstackrenderstate$layerrenderstate = p_386488_.newLayer();
        if (!AuroraPowerUtils.getAuroraPowers(p_386443_).isEmpty()) {
            p_386488_.setAnimated();
            itemstackrenderstate$layerrenderstate.setExtents(this.extents);
            itemstackrenderstate$layerrenderstate.setRenderType(FrostRenderType.AURORA_ENTITY_GLINT);
            this.properties.applyToLayer(itemstackrenderstate$layerrenderstate, p_388231_);
            itemstackrenderstate$layerrenderstate.prepareQuadList().addAll(this.quads);

        }


    }
}