package baguchan.frostrealm.client;

import net.minecraft.client.renderer.ShaderInstance;
import org.jetbrains.annotations.Nullable;

public class FrostShaders {
    private static ShaderInstance renderTypeAuroraShader;

    @Nullable
    public static ShaderInstance getRenderTypeAuroraShader() {
        return renderTypeAuroraShader;
    }

    public static void setRenderTypeAuroraShader(ShaderInstance instance) {
        renderTypeAuroraShader = instance;
    }
}
