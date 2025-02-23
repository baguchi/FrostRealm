package baguchan.frostrealm.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import org.joml.Quaternionf;

public class FrostCrawlerRenderState extends LivingEntityRenderState {
    public Direction attachFace = Direction.DOWN;
    public Quaternionf rotations = new Quaternionf();
    public Quaternionf prevRotations = new Quaternionf();
    public float attachChangeProgress;

}
