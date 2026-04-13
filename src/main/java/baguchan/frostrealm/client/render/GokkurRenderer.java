package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.GokkurModel;
import baguchan.frostrealm.client.render.layer.CrackingGokkurLayer;
import baguchan.frostrealm.client.render.layer.SnowGokkurLayer;
import baguchan.frostrealm.client.render.state.GokkurRenderState;
import baguchan.frostrealm.entity.hostile.Gokkur;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;

public class GokkurRenderer<T extends Gokkur> extends MobRenderer<T, GokkurRenderState, GokkurModel<GokkurRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur.png");
    private static final Identifier GRASS_TEXTURE = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_grass.png");
    private static final RenderType GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/gokkur/gokkur_eye.png"));
    private final BlockModelResolver blockModelResolver;

    public static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();

    public GokkurRenderer(EntityRendererProvider.Context context) {
        super(context, new GokkurModel<>(context.bakeLayer(FrostModelLayers.GOKKUR)), 0.5F);
        this.blockModelResolver = context.getBlockModelResolver();


        this.addLayer(new SnowGokkurLayer<>(this));
        this.addLayer(new CrackingGokkurLayer<>(this));
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return GLOW;
            }
        });
    }

    @Override
    public void extractRenderState(T entity, GokkurRenderState renderState, float p_361157_) {
        super.extractRenderState(entity, renderState, p_361157_);
        renderState.grass = entity.isGrass();
        renderState.snowProgress = entity.getSnowProgress();
        renderState.rollAnimationState.copyFrom(entity.rollAnimationState);
        renderState.startRollAnimationState.copyFrom(entity.startRollAnimationState);
        renderState.crackiness = entity.getCrackiness();
        if (entity.getSnowProgress() > 0) {
            this.blockModelResolver.update(renderState.headBlock, Blocks.SNOW_BLOCK.defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
        } else {
            renderState.headBlock.clear();
        }
    }

    @Override
    public GokkurRenderState createRenderState() {
        return new GokkurRenderState();
    }

    @Override
    public Identifier getTextureLocation(GokkurRenderState entity) {
        if (entity.grass) {
            return GRASS_TEXTURE;
        }
        return TEXTURE;
    }
}