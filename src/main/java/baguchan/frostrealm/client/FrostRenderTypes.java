package baguchan.frostrealm.client;

import net.minecraft.client.renderer.RenderStateShard;

public class FrostRenderTypes {
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_AURORA_SHADER = new RenderStateShard.ShaderStateShard(FrostShaders::getRenderTypeAuroraShader);

}
