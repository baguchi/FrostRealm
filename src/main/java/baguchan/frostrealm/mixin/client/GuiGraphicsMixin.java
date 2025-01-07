package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.api.IGlintAurora;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {
    @Shadow
    @Final
    private ItemStackRenderState scratchItemStackRenderState;

    @Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V", at = @At("HEAD"))
    private void renderItem(
            LivingEntity p_282619_, Level p_281754_, ItemStack p_281675_, int p_281271_, int p_282210_, int p_283260_, int p_281995_, CallbackInfo ci
    ) {
        if (this.scratchItemStackRenderState instanceof IGlintAurora glintAurora) {
            glintAurora.frostRealm$setGlint(!AuroraPowerUtils.getAuroraPowers(p_281675_).isEmpty());
        }
    }
}
