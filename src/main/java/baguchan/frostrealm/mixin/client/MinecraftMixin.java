package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

	@Nullable
	@Shadow
	public LocalPlayer player;

	@Inject(method = "getSituationalMusic", at = @At("HEAD"), cancellable = true)
	public void getSituationalMusic(CallbackInfoReturnable<Music> callbackInfo) {
		if (player != null) {
			if (player.level().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
				if (player.level().isNight()) {
					callbackInfo.setReturnValue(new Music(FrostSounds.CALM_NIGHT_BGM.getHolder().orElseThrow(), 12000, 24000, true));
				} else {
					Holder<Biome> holder = player.level().getBiome(player.blockPosition());
					callbackInfo.setReturnValue(holder.value().getBackgroundMusic().orElse(Musics.GAME));

				}
			}
		}
	}
}