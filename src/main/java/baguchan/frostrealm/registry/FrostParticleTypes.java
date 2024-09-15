package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.particle.FrostPortalParticle;
import baguchan.frostrealm.client.particle.VenomBubbleParticle;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.client.particle.WhiteSmokeParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = FrostRealm.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FrostParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, FrostRealm.MODID);

    public static final Supplier<SimpleParticleType> FROST_PORTAL = PARTICLE_TYPES.register("frost_portal", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> CRYSTAL_SPORE = PARTICLE_TYPES.register("crystal_spore", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> VENOM_BUBBLE = PARTICLE_TYPES.register("venom_bubble", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> VENOM_CLOUD = PARTICLE_TYPES.register("venom_cloud", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FrostParticleTypes.FROST_PORTAL.get(), FrostPortalParticle.Provider::new);
        event.registerSpriteSet(FrostParticleTypes.CRYSTAL_SPORE.get(), SuspendedParticle.SporeBlossomAirProvider::new);
        event.registerSpriteSet(FrostParticleTypes.VENOM_BUBBLE.get(), VenomBubbleParticle.Provider::new);
        event.registerSpriteSet(FrostParticleTypes.VENOM_CLOUD.get(), WhiteSmokeParticle.Provider::new);
    }
}