package baguchan.frostrealm.mixin;

import baguchan.frostrealm.message.UpdateMultipartPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerEntity.class)
public class ServerEntityMixin {

    @Shadow
    @Final
    private Entity entity;

    @Inject(method = "sendDirtyEntityData", at = @At("TAIL"))
    private void sendDirtyEntityData(CallbackInfo ci) {
        if (entity.isMultipartEntity())
            PacketDistributor.sendToPlayersTrackingEntity(entity, new UpdateMultipartPacket(entity));

    }
}
