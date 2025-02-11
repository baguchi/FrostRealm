package baguchan.frostrealm.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;

public class FrostCrawlerRenderState extends LivingEntityRenderState {
    public Direction attachFace = Direction.DOWN;
    public float walkProgress;
}
