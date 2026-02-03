package baguchan.frostrealm.client.render.state;

import baguchan.frostrealm.client.render.GroundedWeaponRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class GroundedWeaponRenderState extends EntityRenderState {
    public final ItemStackRenderState item = new ItemStackRenderState();
    public float xRot;
    public float yRot;
    public Direction direction;
    public float animationScale;
    public float glowScale;
    public GroundedWeaponRenderer.GlowPiece glowPiece;
}