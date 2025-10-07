package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FrostEquipmentAssetProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public FrostEquipmentAssetProvider(PackOutput p_387559_) {
        this.pathProvider = p_387559_.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }


    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> p_387865_) {
        p_387865_.accept(FrostEquipmentAssets.ASTRIUM, onlyHumanoidAndWolfflue("astrium"));
        p_387865_.accept(FrostEquipmentAssets.FROST_BOAR_FUR, onlyHumanoidAndWolfflue("frost_boar_fur"));
        p_387865_.accept(FrostEquipmentAssets.YETI_FUR, onlyHumanoid("yeti_fur"));
    }


    private static EquipmentClientInfo onlyHumanoid(String p_371738_) {
        return EquipmentClientInfo.builder().addHumanoidLayers(FrostRealm.prefix(p_371738_)).build();
    }

    private static EquipmentClientInfo onlyHumanoidAndWolfflue(String p_371738_) {
        return EquipmentClientInfo.builder().addHumanoidLayers(FrostRealm.prefix(p_371738_)).addLayers(EquipmentClientInfo.LayerType.valueOf("FROSTREALM_WOLFFLUE"), EquipmentClientInfo.Layer.onlyIfDyed(FrostRealm.prefix(p_371738_), false)).build();
    }

    @Override
    public CompletableFuture<?> run(CachedOutput p_387304_) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> map = new HashMap<>();
        bootstrap((p_386976_, p_388942_) -> {
            if (map.putIfAbsent(p_386976_, p_388942_) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + p_386976_);
            }
        });
        return DataProvider.saveAll(p_387304_, EquipmentClientInfo.CODEC, this.pathProvider::json, map);
    }

    @Override
    public String getName() {
        return "Equipment Asset Definitions";
    }
}