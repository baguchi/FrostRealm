package baguchan.frostrealm.utils;

import baguchan.frostrealm.client.FrostRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ClientUtils {


    public static void playPortalSound(Player localPlayer) {
        Minecraft.getInstance()
                .getSoundManager()
                .play(SimpleSoundInstance.forLocalAmbience(SoundEvents.PORTAL_TRIGGER, localPlayer.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));

    }

    
    private static int getLayerColorSafe(int[] p_387884_, int p_388524_) {
        return p_388524_ >= p_387884_.length ? -1 : p_387884_[p_388524_];
    }


    private static void renderQuadList(PoseStack p_115163_, VertexConsumer p_115164_, List<BakedQuad> p_115165_, int[] p_387305_, int p_115167_, int p_115168_) {
        PoseStack.Pose posestack$pose = p_115163_.last();

        for (BakedQuad bakedquad : p_115165_) {
            float f;
            float f1;
            float f2;
            float f3;
            if (bakedquad.isTinted()) {
                int i = getLayerColorSafe(p_387305_, bakedquad.tintIndex());
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