package baguchan.frostrealm.client.model.item;

import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.google.common.base.Preconditions;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AuroraModel implements BakedModel {
    private static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();

    private final ModelBakery bakery;
    private final BakedModel originalModel;

    public AuroraModel(ModelBakery bakery, BakedModel originalModel) {
        this.bakery = bakery;
        this.originalModel = Preconditions.checkNotNull(originalModel);
    }

    private final ItemOverrides itemOverrides = new ItemOverrides() {
        @Nonnull
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entityIn, int seed) {
            if (!AuroraPowerUtils.getAuroraPowers(stack).isEmpty()) {
                return (BakedModel) AuroraModel.this.getAuroraModel();
            }

            return originalModel;
        }
    };

    @Nonnull
    @Override
    public ItemOverrides getOverrides() {
        return itemOverrides;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand) {
        return originalModel.getQuads(state, side, rand);
    }

    @Nonnull
    @Override
    public ItemTransforms getTransforms() {
        return originalModel.getTransforms();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return originalModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return originalModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return originalModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return originalModel.isCustomRenderer();
    }

    @Nonnull
    @Override
    public TextureAtlasSprite getParticleIcon() {
        return originalModel.getParticleIcon();
    }

    @Override
    public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        return originalModel.getModelData(level, pos, state, modelData);
    }


    private CompositeBakedModel getAuroraModel() {
        return new CompositeBakedModel(bakery);
    }

    private class CompositeBakedModel extends WrappedItemModel<BakedModel> {
        public CompositeBakedModel(ModelBakery bakery) {
            super(AuroraModel.this.originalModel);

        }

        @Override
        public boolean isCustomRenderer() {
            return originalModel.isCustomRenderer();
        }

        @Nonnull
        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, @Nonnull RandomSource rand) {
            List<BakedQuad> list = Lists.newArrayList();

            list.addAll(AuroraModel.this.originalModel.getQuads(state, face, rand));
            return list;
        }

        @Override
        public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {

            List<RenderType> renderTypes = new ArrayList<>(originalModel.getRenderTypes(itemStack, fabulous));
            renderTypes.add(FrostRenderType.AURORA);
            return renderTypes;
        }

        @Override
        public BakedModel applyTransform(@Nonnull ItemDisplayContext cameraTransformType, PoseStack stack, boolean leftHand) {
            super.applyTransform(cameraTransformType, stack, leftHand);
            return this;
        }
    }
}