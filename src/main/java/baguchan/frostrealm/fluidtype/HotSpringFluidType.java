package baguchan.frostrealm.fluidtype;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.function.Consumer;

public class HotSpringFluidType extends FluidType {
    public HotSpringFluidType(FluidType.Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation TEXTURE_STILL = new ResourceLocation(FrostRealm.MODID, "block/hot_spring_still");
            private static final ResourceLocation TEXTURE_FLOW = new ResourceLocation(FrostRealm.MODID, "block/hot_spring_flow");
            private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(FrostRealm.MODID, "textures/block/hot_spring_still.png");

            @Override
            public ResourceLocation getStillTexture() {
                return TEXTURE_STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return TEXTURE_FLOW;
            }

            @Override
            public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return TEXTURE_OVERLAY;
            }
        });
    }
}