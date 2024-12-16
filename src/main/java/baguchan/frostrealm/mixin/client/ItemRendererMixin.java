package baguchan.frostrealm.mixin.client;

import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
/*

    @Shadow
    private static void renderQuadList(PoseStack p_115163_, VertexConsumer p_115164_, List<BakedQuad> p_115165_, int[] p_387305_, int p_115167_, int p_115168_) {
    }

    @Shadow @Final private ItemModelResolver resolver;

    @Shadow @Final private ItemStackRenderState scratchItemStackRenderState;

    @Inject(method = "renderStatic(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;IILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V", shift = At.Shift.AFTER))
    private void renderStatic(ItemStack p_270761_, ItemDisplayContext p_270648_, int p_270410_, int p_270894_, PoseStack p_270430_, MultiBufferSource p_270457_, Level p_270149_, int p_270509_, CallbackInfo ci) {
        if (!AuroraPowerUtils.getAuroraPowers(p_270761_).isEmpty()) {
            frostRealm$renderAfterModelLists(this.scratchItemStackRenderState.render();, new int[]{1}, p_270410_, p_270894_, p_270430_, p_270457_.getBuffer(FrostRenderType.AURORA_GLINT));
        }
    }

    @Unique
    public void frostRealm$renderAfterModelLists(BakedModel p_115190_, int[] p_387364_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_) {
            RandomSource randomsource = RandomSource.create();
            long i = 42L;

            for (Direction direction : Direction.values()) {
                randomsource.setSeed(42L);
                renderQuadList(p_115194_, p_115195_, p_115190_.getQuads(null, direction, randomsource), p_387364_, p_115192_, p_115193_);
            }

            randomsource.setSeed(42L);
            renderQuadList(p_115194_, p_115195_, p_115190_.getQuads(null, null, randomsource), p_387364_, p_115192_, p_115193_);
        }

    }*/
}