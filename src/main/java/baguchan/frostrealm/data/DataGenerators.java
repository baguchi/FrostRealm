package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.generator.*;
import baguchan.frostrealm.data.generator.recipe.CraftingGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = FrostRealm.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(packOutput, event.getLookupProvider());

		CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();
		generator.addProvider(true, datapackProvider);
		event.getGenerator().addProvider(true, new FrostAdvancementData(packOutput, lookupProvider, event.getExistingFileHelper()));
		generator.addProvider(true, new FrostEquipmentAssetProvider(packOutput));
		generator.addProvider(true, new FrBlockstateGenerator(packOutput));
		generator.addProvider(true, new FrItemModelGenerator(packOutput));
		event.getGenerator().addProvider(true, LootGenerator.create(packOutput, lookupProvider));

		event.getGenerator().addProvider(true, new Runner(packOutput, lookupProvider));
		generator.addProvider(true, new FrostDataMaps(packOutput, lookupProvider));
		BlockTagsProvider blocktags = new FrBlockTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper());
		event.getGenerator().addProvider(true, blocktags);
		event.getGenerator().addProvider(true, new FrItemTagGenerator(packOutput, lookupProvider, blocktags.contentsGetter(), event.getExistingFileHelper()));
		event.getGenerator().addProvider(true, new FrEntityTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper()));
		event.getGenerator().addProvider(true, new FrFluidTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper()));
		event.getGenerator().addProvider(true, new BiomeTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper()));
		event.getGenerator().addProvider(true, new FrDamageTypeTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper()));
	}

	public static final class Runner extends RecipeProvider.Runner {
		public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(output, lookupProvider);
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
			return new CraftingGenerator(lookupProvider, output);
		}

		@Override
		public String getName() {
			return FrostRealm.MODID + "recipes";
		}
	}
}