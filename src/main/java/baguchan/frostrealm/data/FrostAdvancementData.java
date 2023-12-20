package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FrostAdvancementData extends AdvancementProvider {
	public FrostAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
		super(output, registries, existingFileHelper, List.of(new FrostAdvancements()));
	}


	public static class FrostAdvancements implements AdvancementGenerator {

		@SuppressWarnings("unused")
		@Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {

            AdvancementHolder warriors_lost_item = Advancement.Builder.advancement()
					.display(FrostItems.STRAY_NECKLACE_PART.get(),
							Component.translatable("advancement.frostrealm.warriors_lost_item"),
							Component.translatable("advancement.frostrealm.warriors_lost_item.desc"),
							null,
							AdvancementType.TASK, true, true, false)
					.addCriterion("warriors_lost_item", InventoryChangeTrigger.TriggerInstance.hasItems(FrostItems.STRAY_NECKLACE_PART.get()))
					.save(consumer, "frostrealm:warriors_lost_item");
            AdvancementHolder enterFrostrealm = Advancement.Builder.advancement()
					.parent(warriors_lost_item)
					.display(FrostBlocks.FROZEN_GRASS_BLOCK.get(),
							Component.translatable("advancement.frostrealm.enter_frostrealm"),
							Component.translatable("advancement.frostrealm.enter_frostrealm.desc"),
							new ResourceLocation(FrostRealm.MODID, "textures/block/frozen_dirt.png"),
							AdvancementType.TASK, true, true, false)
					.addCriterion("enter_frostrealm", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(FrostDimensions.FROSTREALM_LEVEL))
					.save(consumer, "frostrealm:enter_frostrealm");
            AdvancementHolder getRock = Advancement.Builder.advancement()
					.parent(enterFrostrealm)
					.display(FrostItems.GLIMMERROCK.get(),
							Component.translatable("advancement.frostrealm.hot_source"),
							Component.translatable("advancement.frostrealm.hot_source.desc"),
							null,
							AdvancementType.TASK, true, true, false)
					.addCriterion("hot_source", InventoryChangeTrigger.TriggerInstance.hasItems(FrostItems.GLIMMERROCK.get()))
					.save(consumer, "frostrealm:get_glimmerrock");
		}
	}
}