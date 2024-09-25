package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.generator.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = FrostRealm.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(packOutput, event.getLookupProvider());

		CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();
		generator.addProvider(event.includeServer(), datapackProvider);
		event.getGenerator().addProvider(event.includeServer(), new FrostAdvancementData(packOutput, lookupProvider, event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeClient(), new BlockstateGenerator(packOutput, event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeClient(), new ItemModelGenerator(packOutput, event.getExistingFileHelper()));

		event.getGenerator().addProvider(event.includeServer(), LootGenerator.create(packOutput, lookupProvider));

		event.getGenerator().addProvider(event.includeServer(), new CraftingGenerator(packOutput, lookupProvider));
		generator.addProvider(event.includeServer(), new FrostDataMaps(packOutput, lookupProvider));
		BlockTagsProvider blocktags = new BlockTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeServer(), blocktags);
		event.getGenerator().addProvider(event.includeServer(), new ItemTagGenerator(packOutput, lookupProvider, blocktags.contentsGetter(), event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeServer(), new EntityTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeServer(), new FluidTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeServer(), new DamageTypeTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper()));

	}
}