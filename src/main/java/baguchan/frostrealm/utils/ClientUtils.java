package baguchan.frostrealm.utils;

import baguchan.frostrealm.client.FrostRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class ClientUtils {

    @OnlyIn(Dist.CLIENT)
    public static void playPortalSound(Player localPlayer) {
        Minecraft.getInstance()
                .getSoundManager()
                .play(SimpleSoundInstance.forLocalAmbience(SoundEvents.PORTAL_TRIGGER, localPlayer.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));

    }

    @OnlyIn(Dist.CLIENT)
    public static void renderItemAurora(PoseStack p_360423_, MultiBufferSource p_360415_, int p_361265_, int p_364771_, int[] p_386517_, BakedModel p_363970_, RenderType p_388877_) {
        VertexConsumer vertexconsumer = FrostRenderType.getAurora(p_360415_, p_388877_);

        renderModelLists(p_363970_, p_386517_, p_361265_, p_364771_, p_360423_, vertexconsumer);
    }

    @OnlyIn(Dist.CLIENT)
    private static void renderModelLists(BakedModel p_115190_, int[] p_387364_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_) {
        RandomSource randomsource = RandomSource.create();
        long i = 42L;

        for (Direction direction : Direction.values()) {
            randomsource.setSeed(42L);
            renderQuadList(p_115194_, p_115195_, p_115190_.getQuads((BlockState) null, direction, randomsource), p_387364_, p_115192_, p_115193_);
        }

        randomsource.setSeed(42L);
        renderQuadList(p_115194_, p_115195_, p_115190_.getQuads((BlockState) null, (Direction) null, randomsource), p_387364_, p_115192_, p_115193_);
    }

    @OnlyIn(Dist.CLIENT)
    private static int getLayerColorSafe(int[] p_387884_, int p_388524_) {
        return p_388524_ >= p_387884_.length ? -1 : p_387884_[p_388524_];
    }

    @OnlyIn(Dist.CLIENT)
    private static void renderQuadList(PoseStack p_115163_, VertexConsumer p_115164_, List<BakedQuad> p_115165_, int[] p_387305_, int p_115167_, int p_115168_) {
        PoseStack.Pose posestack$pose = p_115163_.last();

        for (BakedQuad bakedquad : p_115165_) {
            float f;
            float f1;
            float f2;
            float f3;
            if (bakedquad.isTinted()) {
                int i = getLayerColorSafe(p_387305_, bakedquad.getTintIndex());
                f = (float) ARGB.alpha(i) / 255.0F;
                f1 = (float) ARGB.red(i) / 255.0F;
                f2 = (float) ARGB.green(i) / 255.0F;
                f3 = (float) ARGB.blue(i) / 255.0F;
            } else {
                f = 1.0F;
                f1 = 1.0F;
                f2 = 1.0F;
                f3 = 1.0F;
            }

            p_115164_.putBulkData(posestack$pose, bakedquad, f1, f2, f3, f, p_115167_, p_115168_, true);
        }

    }
}