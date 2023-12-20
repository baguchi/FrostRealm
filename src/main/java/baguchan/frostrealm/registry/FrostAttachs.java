package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostLivingCapability;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FrostAttachs {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, FrostRealm.MODID);

    public static final Supplier<AttachmentType<FrostLivingCapability>> FROST_LIVING = ATTACHMENT_TYPES.register(
            "frost_living", () -> AttachmentType.serializable(FrostLivingCapability::new).build());
}