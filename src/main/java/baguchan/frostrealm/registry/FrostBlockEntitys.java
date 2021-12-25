package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.blockentity.FrostChestBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostBlockEntitys {
	public static final BlockEntityType<FrostChestBlockEntity> FROST_CHEST = register("frostrealm:frost_chest", BlockEntityType.Builder.of(FrostChestBlockEntity::new, FrostBlocks.FROSTROOT_CHEST));

	private static <T extends BlockEntity> BlockEntityType<T> register(String p_200966_0_, BlockEntityType.Builder<T> p_200966_1_) {
		Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, p_200966_0_);
		return p_200966_1_.build(type);
	}

	@SubscribeEvent
	public static void registerBlockEntityType(RegistryEvent.Register<BlockEntityType<?>> registry) {
		registry.getRegistry().register(FROST_CHEST.setRegistryName("frost_chest"));
	}
}
