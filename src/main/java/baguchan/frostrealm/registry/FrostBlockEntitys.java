package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.blockentity.MagmaCoreBlockEntity;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class FrostBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FrostRealm.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends MagmaCoreBlockEntity>> MAGMA_CORE = register("magma_core", MagmaCoreBlockEntity::new, FrostBlocks.MAGMA_CORE);

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends T>> register(
            String p_58957_, BlockEntityType.BlockEntitySupplier<? extends T> p_362578_, Holder<Block>... p_364748_
    ) {

        Util.fetchChoiceType(References.BLOCK_ENTITY, Identifier.fromNamespaceAndPath(FrostRealm.MODID, p_58957_).toString());
        return BLOCK_ENTITIES.register(p_58957_, () -> new BlockEntityType<>(p_362578_, Set.copyOf(Arrays.stream(Objects.requireNonNull(p_364748_)).map(Holder::value).toList())));
    }
}
