package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class FrostModelTemplates {
    public static final ModelTemplate GRASS_BLOCK = createDefault(
            "grass_block", TextureSlot.PARTICLE, TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE, FrostTextureMappings.OVERLAY
    );
    public static final ModelTemplate EMPTY = ModelTemplates.create("block/block", TextureSlot.PARTICLE);

    public static final ModelTemplate GLOW_CUBE = create("block/glow_cube", TextureSlot.ALL, FrostTextureMappings.GLOW_ALL);
    public static final ModelTemplate CHAIN = create("block/chain", TextureSlot.ALL);
    public static final ModelTemplate LADDER = create("block/ladder", TextureSlot.ALL);
    public static final ModelTemplate TRANSLUCENT_CUBE = create("block/translucent_cube", TextureSlot.ALL);
    public static final ModelTemplate BIG_HANDHELD = create("item/big_handheld", TextureSlot.LAYER0);
    public static ModelTemplate create(String p_386521_, TextureSlot... p_388561_) {
        return new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(FrostRealm.MODID, p_386521_)), Optional.empty(), p_388561_);
    }

    public static ModelTemplate createDefault(String p_386521_, TextureSlot... p_388561_) {
        return new ModelTemplate(Optional.of(Identifier.withDefaultNamespace(p_386521_).withPrefix("block/")), Optional.empty(), p_388561_);
    }
}
