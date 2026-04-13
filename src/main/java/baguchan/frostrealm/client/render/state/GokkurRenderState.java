package baguchan.frostrealm.client.render.state;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Crackiness;

public class GokkurRenderState extends LivingEntityRenderState {

    public float snowProgress;
    public boolean grass;
    public Crackiness.Level crackiness;

    public AnimationState rollAnimationState = new AnimationState();
    public AnimationState startRollAnimationState = new AnimationState();
    public final BlockModelRenderState headBlock = new BlockModelRenderState();

}
