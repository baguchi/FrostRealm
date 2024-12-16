package baguchan.frostrealm.client.render.state;

import baguchan.frostrealm.entity.animal.CrystalFox;
import net.minecraft.client.renderer.entity.state.HoldingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class CrystalFoxRenderState extends HoldingEntityRenderState {
    public final AnimationState eatAnimationState = new AnimationState();
    public boolean shearable;
    public CrystalFox.State state;
}
