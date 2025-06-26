package baguchan.frostrealm.data.resource;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.Optional;

public class FrostPaintingVariants {

    public static final ResourceKey<PaintingVariant> WOLFFLUE = create("wolfflue");

    public static void bootstrap(BootstrapContext<PaintingVariant> p_345677_) {
        register(p_345677_, WOLFFLUE, 3, 3);
    }

    private static void register(BootstrapContext<PaintingVariant> p_345930_, ResourceKey<PaintingVariant> p_345276_, int p_344851_, int p_345199_) {
        register(p_345930_, p_345276_, p_344851_, p_345199_, true);
    }

    private static void register(
            BootstrapContext<PaintingVariant> p_364757_, ResourceKey<PaintingVariant> p_364083_, int p_362008_, int p_360993_, boolean p_365507_
    ) {
        p_364757_.register(
                p_364083_,
                new PaintingVariant(
                        p_362008_,
                        p_360993_,
                        p_364083_.location(),
                        Optional.of(Component.translatable(p_364083_.location().toLanguageKey("painting", "title")).withStyle(ChatFormatting.YELLOW)),
                        p_365507_
                                ? Optional.of(Component.translatable(p_364083_.location().toLanguageKey("painting", "author")).withStyle(ChatFormatting.GRAY))
                                : Optional.empty()
                )
        );
    }

    private static ResourceKey<PaintingVariant> create(String p_218945_) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, p_218945_));
    }
}