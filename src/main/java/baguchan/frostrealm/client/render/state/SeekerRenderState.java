package baguchan.frostrealm.client.render.state;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.AnimationState;

public class SeekerRenderState extends HumanoidRenderState {
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState preAttackAnimationState = new AnimationState();
    public final AnimationState stopAttackAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public boolean isAgressive;
}
