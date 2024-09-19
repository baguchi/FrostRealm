package baguchan.frostrealm.data;

import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class FrostDataMaps extends DataMapProvider {

    public FrostDataMaps(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void gather() {
        var compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        this.addCompostable(compostables, FrostItems.RYE_BREAD.get(), 0.6F);
        this.addCompostable(compostables, FrostItems.RYE.get(), 0.5F);
        this.addCompostable(compostables, FrostItems.RYE_SEEDS.get(), 0.3F);
        this.addCompostable(compostables, FrostItems.BEARBERRY.get(), 0.5F);
        this.addCompostable(compostables, FrostItems.SUGARBEET.get(), 0.5F);
        this.addCompostable(compostables, FrostItems.SUGARBEET_SEEDS.get(), 0.3F);
    }

    private void addCompostable(DataMapProvider.Builder<Compostable, Item> compostableBuilder, ItemLike item, float chance) {
        compostableBuilder.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false);
    }
}