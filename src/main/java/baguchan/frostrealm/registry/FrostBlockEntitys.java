package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.blockentity.FrostChestBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FrostRealm.MODID);

    public static final Supplier<BlockEntityType<FrostChestBlockEntity>> FROST_CHEST = BLOCK_ENTITIES.register("frost_chest", () -> register("frostrealm:frost_chest", BlockEntityType.Builder.of(FrostChestBlockEntity::new, FrostBlocks.FROSTROOT_CHEST.get())));

	private static <T extends BlockEntity> BlockEntityType<T> register(String p_200966_0_, BlockEntityType.Builder<T> p_200966_1_) {
		Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, p_200966_0_);
		return p_200966_1_.build(type);
	}
}
