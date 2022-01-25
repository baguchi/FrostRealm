package baguchan.frostrealm.mixin;

import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostMusics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

	@Shadow
	public LocalPlayer player;

	@Inject(method = "getSituationalMusic", at = @At("HEAD"), cancellable = true)
	public void getSituationalMusic(CallbackInfoReturnable<Music> callbackInfoReturnable) {
		if (this.player != null) {
			if (this.player.level.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
				callbackInfoReturnable.setReturnValue(this.player.level.isNight() ? FrostMusics.CALM_NIGHT : FrostMusics.FRSOT_MOON);
			}
		}
	}
}
