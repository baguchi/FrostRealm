package baguchan.frostrealm.mixin.client;

import net.minecraft.client.model.HierarchicalModel;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HierarchicalModel.class)
public interface HierarchicalModelAccessor {
    @Accessor("ANIMATION_VECTOR_CACHE")
    public static Vector3f getAnimationVector() {
        throw new AssertionError();
    }
}
