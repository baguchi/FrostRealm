package baguchan.frostrealm.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;

public class FrostRenderType extends RenderType {

    private static final BiFunction<ResourceLocation, Boolean, RenderType> CRYSTAL_ENTITY = Util.memoize(
            (p_286156_, p_286157_) -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(new ShaderStateShard(FrostShaders::getRenderTypeCrystalEntityShader))
                        .setTextureState(new RenderStateShard.TextureStateShard(p_286156_, false, false))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .createCompositeState(p_286157_);
                return create("frostrealm:crystal_entity", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, rendertype$compositestate);
            }
    );

    public FrostRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static RenderType crystalEntity(ResourceLocation p_110455_, boolean p_110456_) {
        return CRYSTAL_ENTITY.apply(p_110455_, p_110456_);
    }

    public static RenderType crystalEntity(ResourceLocation p_110474_) {
        return crystalEntity(p_110474_, true);
    }

    public static void init() {

    }
}
