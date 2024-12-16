package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FrostClientItemsProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public FrostClientItemsProvider(PackOutput p_387559_) {
        this.pathProvider = p_387559_.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
    }


    private static void bootstrap(BiConsumer<ResourceLocation, ClientItem> p_387865_) {
        FrostItems.ITEMS.getEntries().forEach(p_388426_ -> {
            ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(p_388426_.value());
            p_387865_.accept(getLocation(p_388426_.value()), new ClientItem(ItemModelUtils.plainModel(resourcelocation), ClientItem.Properties.DEFAULT));
        });
    }

    public static ResourceLocation getLocation(Block p_387471_) {
        ResourceLocation resourcelocation = BuiltInRegistries.BLOCK.getKey(p_387471_);
        return resourcelocation;
    }

    public static ResourceLocation getLocation(Item p_388071_) {
        ResourceLocation resourcelocation = BuiltInRegistries.ITEM.getKey(p_388071_);
        return resourcelocation;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput p_387304_) {
        Map<ResourceLocation, ClientItem> map = new HashMap<>();
        bootstrap((p_386976_, p_388942_) -> {
            if (map.putIfAbsent(p_386976_, p_388942_) != null) {
                //throw new IllegalStateException("Tried to register equipment asset twice for id: " + p_386976_);
            }
        });
        return DataProvider.saveAll(p_387304_, ClientItem.CODEC, this.pathProvider::json, map);
    }

    @Override
    public String getName() {
        return "Client Item Asset Definitions";
    }
}