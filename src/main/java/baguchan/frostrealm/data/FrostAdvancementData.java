package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
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

			AdvancementHolder root = Advancement.Builder.advancement()
					.display(FrostItems.STRAY_NECKLACE_PART.get(),
							Component.translatable("advancement.frostrealm.warriors_lost_item"),
							Component.translatable("advancement.frostrealm.warriors_lost_item.desc"),
							ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/block/frigid_stone.png"),
							AdvancementType.TASK, false, false, false)
					.addCriterion("warriors_lost_item", InventoryChangeTrigger.TriggerInstance.hasItems(FrostItems.STRAY_NECKLACE_PART.get()))
					.save(consumer, "frostrealm:root");
            AdvancementHolder enterFrostrealm = Advancement.Builder.advancement()
					.parent(root)
					.display(FrostBlocks.FROZEN_GRASS_BLOCK.get(),
							Component.translatable("advancement.frostrealm.enter_frostrealm"),
							Component.translatable("advancement.frostrealm.enter_frostrealm.desc"),
							ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/block/frozen_dirt.png"),
							AdvancementType.TASK, true, true, false)
					.addCriterion("enter_frostrealm", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(FrostDimensions.FROSTREALM_LEVEL))
					.save(consumer, "frostrealm:enter_frostrealm");
			AdvancementHolder tiny_source = Advancement.Builder.advancement()
					.parent(enterFrostrealm)
					.display(FrostBlocks.FROST_CAMPFIRE.get(),
							Component.translatable("advancement.frostrealm.tiny_source"),
							Component.translatable("advancement.frostrealm.tiny_source.desc"),
							null,
							AdvancementType.TASK, true, true, false)
					.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(FrostBlocks.FROST_CAMPFIRE.get()))
					.save(consumer, "frostrealm:tiny_source");
            AdvancementHolder getRock = Advancement.Builder.advancement()
					.parent(enterFrostrealm)
					.display(FrostItems.GLIMMERROCK.get(),
							Component.translatable("advancement.frostrealm.hot_source"),
							Component.translatable("advancement.frostrealm.hot_source.desc"),
							null,
							AdvancementType.TASK, true, true, false)
					.addCriterion("hot_source", InventoryChangeTrigger.TriggerInstance.hasItems(FrostItems.GLIMMERROCK.get()))
					.save(consumer, "frostrealm:get_glimmerrock");
			AdvancementHolder alternatives = Advancement.Builder.advancement()
					.parent(getRock)
					.display(FrostItems.CRYONITE_CREAM.get(),
							Component.translatable("advancement.frostrealm.alternatives"),
							Component.translatable("advancement.frostrealm.alternatives.desc"),
							null,
							AdvancementType.GOAL, true, true, false)
					.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(FrostItems.CRYONITE_CREAM.get()))
					.save(consumer, "frostrealm:alternatives");

			AdvancementHolder astrium_age = Advancement.Builder.advancement()
					.parent(enterFrostrealm)
					.display(FrostItems.ASTRIUM_INGOT.get(),
							Component.translatable("advancement.frostrealm.astrium_age"),
							Component.translatable("advancement.frostrealm.astrium_age.desc"),
							null,
							AdvancementType.TASK, true, true, false)
					.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(FrostItems.ASTRIUM_INGOT.get()))
					.save(consumer, "frostrealm:astrium_age");
			AdvancementHolder combined_strength = Advancement.Builder.advancement()
					.parent(astrium_age)
					.display(FrostItems.GLACINIUM_INGOT.get(),
							Component.translatable("advancement.frostrealm.combined_strength"),
							Component.translatable("advancement.frostrealm.combined_strength.desc"),
							null,
							AdvancementType.TASK, true, true, false)
					.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(FrostItems.GLACINIUM_INGOT.get()))
					.save(consumer, "frostrealm:combined_strength");

			AdvancementHolder tame_wolfflue = Advancement.Builder.advancement()
					.parent(enterFrostrealm)
					.display(FrostItems.COOKED_SNOWPILE_QUAIL_MEAT.get(),
							Component.translatable("advancement.frostrealm.tame_wolfflue"),
							Component.translatable("advancement.frostrealm.tame_wolfflue.desc"),
							null,
							AdvancementType.TASK, true, true, false)
					.addCriterion("has_item", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(FrostEntities.WOLFFLUE.get())))
					.save(consumer, "frostrealm:tame_wolfflue");
		}
	}
}