package baguchan.frostrealm.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.AnimationState;
import org.joml.Quaternionf;

public class VenochemRenderState extends LivingEntityRenderState {
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState shootAnimationState = new AnimationState();

    public Direction attachFace = Direction.DOWN;
    public Quaternionf rotations = new Quaternionf();
    public Quaternionf prevRotations = new Quaternionf();
    public float attachChangeProgress;

}
