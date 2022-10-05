package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.model.OctorolgaModel;
import baguchan.frostrealm.entity.Octorolga;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class OctorolgaMagmaLayer<T extends Octorolga, M extends OctorolgaModel<T>> extends RenderLayer<T, M> {
	public static final ResourceLocation LOCATION = new ResourceLocation("textures/block/lava_still.png");

	protected static final RenderStateShard.LightmapStateShard LIGHTMAP = new RenderStateShard.LightmapStateShard(true);
	protected static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("additive_transparency", () -> {
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
	}, () -> {
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
	});
	protected static final RenderStateShard.CullStateShard NO_CULL = new RenderStateShard.CullStateShard(false);
	protected static final RenderStateShard.TexturingStateShard ENTITY_GLINT_TEXTURING = new RenderStateShard.TexturingStateShard("entity_glint_texturing", () -> {
		setupGlintTexturing();
	}, () -> {
		RenderSystem.resetTextureMatrix();
	});
	protected static final RenderStateShard.ShaderStateShard RENDERTYPE_ENERGY_SWIRL_SHADER = new RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeEnergySwirlShader);


	public OctorolgaMagmaLayer(RenderLayerParent<T, M> p_116981_) {
		super(p_116981_);
	}

	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

		if (!entitylivingbaseIn.isInvisible()) {
			float f = (float) entitylivingbaseIn.tickCount + partialTicks;
			M entitymodel = this.getParentModel();
			entitymodel.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
			this.getParentModel().copyPropertiesTo(entitymodel);
			VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.lavaSwirl());
			entitymodel.rock_eye_R.visible = false;
			entitymodel.rock_eye_L.visible = false;
			entitymodel.rocks.visible = false;
			entitymodel.rock_tube.visible = false;
			entitymodel.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			entitymodel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			entitymodel.rock_eye_R.visible = true;
			entitymodel.rock_eye_L.visible = true;
			entitymodel.rocks.visible = true;
			entitymodel.rock_tube.visible = true;
		}
	}

	public RenderType lavaSwirl() {
		return RenderType.create("entity_magma", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER).setTextureState(new RenderStateShard.TextureStateShard(LOCATION, false, false)).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setTexturingState(ENTITY_GLINT_TEXTURING).createCompositeState(false));
	}

	private static void setupGlintTexturing() {
		long var1 = Util.getMillis();
		float var3 = (float) (var1 % 10000L) / 10000F;
		float var4 = (float) (var1 % 10000L) / 10000F;
		Matrix4f var5 = Matrix4f.createTranslateMatrix(0, var4, 0.0F);
		//var5.multiply(Vector3f.ZP.rotationDegrees(10.0F));
		var5.multiply(Matrix4f.createScaleMatrix(1F, 16 / 360F, 1F));
		RenderSystem.setTextureMatrix(var5);
	}
}