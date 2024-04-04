package baguchan.frostrealm.client;

import net.minecraft.client.renderer.ShaderInstance;
import org.jetbrains.annotations.Nullable;

public class FrostShaders {
    private static ShaderInstance renderTypeAuroraShader;
    private static ShaderInstance renderTypeCrystalEntityShader;

    @Nullable
    public static ShaderInstance getRenderTypeAuroraShader() {
        return renderTypeAuroraShader;
    }

    public static void setRenderTypeAuroraShader(ShaderInstance instance) {
        renderTypeAuroraShader = instance;
    }

    @Nullable
    public static ShaderInstance getRenderTypeCrystalEntityShader() {
        return renderTypeCrystalEntityShader;
    }

    public static void setRenderTypeCrystalEntityShader(ShaderInstance instance) {
        renderTypeCrystalEntityShader = instance;
    }

}
