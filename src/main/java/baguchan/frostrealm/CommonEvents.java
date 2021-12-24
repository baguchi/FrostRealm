package baguchan.frostrealm;

import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID)
public class CommonEvents {
	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(FrostLivingCapability.class);
	}

	@SubscribeEvent
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof LivingEntity) {
			event.addCapability(new ResourceLocation(FrostRealm.MODID, "frost_living"), new FrostLivingCapability());
		}
	}

	@SubscribeEvent
	public static void onUpdate(LivingEvent.LivingUpdateEvent event) {
		LivingEntity livingEntity = event.getEntityLiving();
		livingEntity.getCapability(FrostRealm.FROST_LIVING_CAPABILITY).ifPresent(tofuLivingCapability -> {
			tofuLivingCapability.tick(livingEntity);
		});
	}

	@SubscribeEvent
	public static void onHoeRightClick(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();

		if (stack.getItem() instanceof HoeItem) {
			if (event.getWorld().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_DIRT || event.getWorld().getBlockState(event.getPos()).getBlock() == FrostBlocks.FROZEN_GRASS_BLOCK) {
				event.getWorld().setBlock(event.getPos(), FrostBlocks.FROZEN_FARMLAND.defaultBlockState(), 2);
				stack.hurtAndBreak(1, event.getPlayer(), (p_147232_) -> {
					p_147232_.broadcastBreakEvent(event.getHand());
				});
				event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
			}
		}
	}
}
