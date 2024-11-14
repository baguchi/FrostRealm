package baguchan.frostrealm.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Crackiness;

public class RootDeerRenderState extends LivingEntityRenderState {
    public Direction direction;
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState summonAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    public Crackiness.Level crackiness;
}
