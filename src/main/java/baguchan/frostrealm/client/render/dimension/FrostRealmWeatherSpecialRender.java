package baguchan.frostrealm.client.render.dimension;

import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.client.FrostRealmTextureManager;
import baguchan.frostrealm.registry.FrostParticleTypes;
import baguchan.frostrealm.registry.FrostWeathers;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.CustomWeatherEffectRenderer;

public class FrostRealmWeatherSpecialRender implements CustomWeatherEffectRenderer {
    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        FrostRealmRenderer frostrealmRenderer = FrostRealmTextureManager.INSTANCE.getFrostrealmRenderer();
        if(frostrealmRenderer != null) {
            frostrealmRenderer.getSoundsHandler().tick();
            float f = FrostWeatherManager.getWeatherLevel(1.0F);
            if (!(f <= 0.0F) && FrostWeatherManager.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
                for (int i = 0; i < 2; i++) {
                    if (level.random.nextInt(2) != 0) {
                        float x = level.getRandom().nextFloat() * 0.5F - level.getRandom().nextFloat();
                        float y = level.getRandom().nextFloat();
                        float z = level.getRandom().nextFloat() * 0.5F - level.getRandom().nextFloat();
                        if (level.canSeeSky(BlockPos.containing(new Vec3((float) (camera.position().x - x * 36F), (float) (camera.position().y + 8 + y * 16), (float) (camera.position().z - z * 36))))) {
                            level.addParticle(FrostParticleTypes.SNOW.get(), camera.position().x - x * 36F, camera.position().y + 8 + y * 16, camera.position().z - z * 36, -0.2F, -0.5F, -0.2F);
                        }
                    }
                }
            }
        }
        return true;
    }
}
