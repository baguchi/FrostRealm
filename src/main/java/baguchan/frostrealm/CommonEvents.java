package baguchan.frostrealm;

import baguchan.frostrealm.capability.FrostLivingCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
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
}
