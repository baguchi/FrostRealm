package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FrostAdvancementData extends ForgeAdvancementProvider {
	public FrostAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
		super(output, registries, existingFileHelper, List.of());
	}


	public static class FrostAdvancements implements Consumer<Consumer<Advancement>> {
		@Override
		public void accept(Consumer<Advancement> consumer) {
			Advancement enterFrostrealm = Advancement.Builder.advancement()
					.display(FrostBlocks.FROZEN_GRASS_BLOCK.get(),
							Component.translatable("advancement.frostrealm.enter_frostrealm"),
							Component.translatable("advancement.frostrealm.enter_frostrealm.desc"),
							new ResourceLocation(FrostRealm.MODID, "textures/block/frozen_dirt.png"),
							FrameType.TASK, true, true, false)
					.addCriterion("enter_frostrealm", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(FrostDimensions.FROSTREALM_LEVEL))
					.save(consumer, "frostrealm:enter_frostrealm");
			Advancement getRock = Advancement.Builder.advancement()
					.parent(enterFrostrealm)
					.display(FrostItems.GLIMMERROCK.get(),
							Component.translatable("advancement.frostrealm.hot_source"),
							Component.translatable("advancement.frostrealm.hot_source.desc"),
							null,
							FrameType.TASK, true, true, false)
					.addCriterion("hot_source", InventoryChangeTrigger.TriggerInstance.hasItems(FrostItems.GLIMMERROCK.get()))
					.save(consumer, "frostrealm:get_glimmerrock");
		}
	}
}