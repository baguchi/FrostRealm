package baguchan.frostrealm.client.sounds;

import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.weather.FrostWeather;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class FrostAmbientSoundsHandler implements AmbientSoundHandler {
    private static final int LOOP_SOUND_CROSS_FADE_TIME = 100;
    private static final float SKY_MOOD_RECOVERY_RATE = 0.001F;
    private final SoundManager soundManager;
    private final Object2ObjectArrayMap<FrostWeather, FrostAmbientSoundsHandler.LoopSoundInstance> loopSounds = new Object2ObjectArrayMap<>();
    @Nullable
    private FrostWeather previousWeather;

    public FrostAmbientSoundsHandler(SoundManager p_1196100_) {
        this.soundManager = p_1196100_;
    }

    @Override
    public void tick() {
        this.loopSounds.values().removeIf(AbstractTickableSoundInstance::isStopped);
        FrostWeather frostWeather = FrostWeatherManager.getFrostWeather();
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            BlockPos blockpos = Minecraft.getInstance().player.blockPosition();
            BlockPos blockpos1 = null;
            BlockPos blockpos2 = Minecraft.getInstance().level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos);
            if (blockpos2.getY() > Minecraft.getInstance().level.getMinBuildHeight() && blockpos2.getY() <= blockpos.getY() + 10 && blockpos2.getY() >= blockpos.getY() - 10) {
                blockpos1 = blockpos2.below();
            }
            if (blockpos1 != null) {
                if (blockpos1.getY() > blockpos.getY() + 1 && Minecraft.getInstance().level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos).getY() > Mth.floor((float) blockpos.getY())) {
                    this.loopSounds.values().forEach(FrostAmbientSoundsHandler.LoopSoundInstance::toneDown);
                } else {
                    this.loopSounds.values().forEach(FrostAmbientSoundsHandler.LoopSoundInstance::toneUp);
                }
            }
        }
        BlockPos blockpos = Minecraft.getInstance().player.blockPosition();
        if (frostWeather.getNonAffectableBiome().isPresent() && Minecraft.getInstance().level.getBiome(blockpos).is(frostWeather.getNonAffectableBiome().get())) {
            this.previousWeather = frostWeather;
            this.loopSounds.values().forEach(FrostAmbientSoundsHandler.LoopSoundInstance::fadeOut);
        } else {
            if (frostWeather != this.previousWeather || this.loopSounds.values().isEmpty()) {
                this.previousWeather = frostWeather;
                this.loopSounds.values().forEach(FrostAmbientSoundsHandler.LoopSoundInstance::fadeOut);
                frostWeather.getSoundEvents().ifPresent(soundEvent -> {
                    this.loopSounds.compute(frostWeather, (p_174924_, p_174925_) -> {
                        if (p_174925_ == null) {
                            p_174925_ = new FrostAmbientSoundsHandler.LoopSoundInstance((SoundEvent) soundEvent);
                            this.soundManager.play(p_174925_);
                        }

                        p_174925_.fadeIn();
                        return p_174925_;
                    });
                });

            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class LoopSoundInstance extends AbstractTickableSoundInstance {
        private int fadeDirection;
        private int fade;

        private float tone;

        private int toneDirection;

        public LoopSoundInstance(SoundEvent p_119658_) {
            super(p_119658_, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
            this.looping = true;
            this.delay = 0;
            this.volume = 1.0F;
            this.relative = true;
        }

        @Override
        public void tick() {
            if (this.fade < 0) {
                this.stop();
            }

            this.fade += this.fadeDirection;
            this.tone += this.toneDirection;
            this.volume = Mth.clamp((float) this.fade / 100.0F, 0.0F, Mth.clamp(0.5F + tone, 0F, 3F));
        }


        public void fadeOut() {
            this.fade = Math.min(this.fade, 100);
            this.fadeDirection = -1;
        }

        public void toneDown() {
            this.tone = Math.min(this.fade, 2.5F);
            this.toneDirection = -1;
        }

        public void toneUp() {
            this.tone = Math.max(0, this.tone);
            this.toneDirection = 1;
        }

        public void fadeIn() {
            this.fade = Math.max(0, this.fade);
            this.fadeDirection = 1;
        }
    }
}
