package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.api.entity.WolfflueVariant;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FrostEntityDatas {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITIE_DATAS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, FrostRealm.MODID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<WolfflueVariant>>> WOLFFLUE_VARIANT = ENTITIE_DATAS.register("wlfflue_data", () -> EntityDataSerializer.forValueType(WolfflueVariant.STREAM_CODEC));

}
