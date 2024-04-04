package baguchan.frostrealm.client;

import net.minecraft.client.renderer.ShaderInstance;
import org.jetbrains.annotations.Nullable;

public class FrostShaders {
    private static ShaderInstance renderTypeAuroraShader;
    private static ShaderInstance renderTypeGhostShader;

    @Nullable
    public static ShaderInstance getRenderTypeAuroraShader() {
        return renderTypeAuroraShader;
    }

    public static void setRenderTypeAuroraShader(ShaderInstance instance) {
        renderTypeAuroraShader = instance;
    }

    @Nullable
    public static ShaderInstance getRenderTypeGhostShader() {
        return renderTypeGhostShader;
    }

    public static void setRenderTypeGhostShader(ShaderInstance instance) {
        renderTypeGhostShader = instance;
    }

}
