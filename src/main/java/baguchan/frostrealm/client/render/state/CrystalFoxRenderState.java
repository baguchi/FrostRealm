package baguchan.frostrealm.client.render.state;

import baguchan.frostrealm.entity.animal.CrystalFox;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class CrystalFoxRenderState extends LivingEntityRenderState {
    public final AnimationState eatAnimationState = new AnimationState();
    public boolean shearable;
    public CrystalFox.State state;
}
