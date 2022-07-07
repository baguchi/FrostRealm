package baguchan.frostrealm.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class CrystalFoxAnimations {
	public static final AnimationDefinition CRYSTAL_FOX_EATING = AnimationDefinition.Builder.withLength(1.5F)
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION
					, new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
					, new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
					, new Keyframe(0.5F, KeyframeAnimations.degreeVec(35.0F, 0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
					, new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
					, new Keyframe(1.0F, KeyframeAnimations.degreeVec(35.0F, 0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
					, new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
}
