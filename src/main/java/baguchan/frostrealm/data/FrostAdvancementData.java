package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class FrostAdvancementData extends AdvancementProvider {
	public final List<Consumer<Consumer<Advancement>>> advancements = ImmutableList.of(new FrostAdvancements());

	public FrostAdvancementData(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
		super(generatorIn, existingFileHelper);
	}

	@Nonnull
	@Override
	public String getName() {
		return "Frostrealm Advancements";
	}

	@Override
	protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
		for (Consumer<Consumer<Advancement>> consumer1 : this.advancements) {
			consumer1.accept(consumer);
		}
	}

	public static class FrostAdvancements implements Consumer<Consumer<Advancement>> {
		@Override
		public void accept(Consumer<Advancement> consumer) {
			Advancement enterAether = Advancement.Builder.advancement()
					.display(FrostBlocks.FROZEN_GRASS_BLOCK.get(),
							Component.translatable("advancement.frostrealm.enter_frostrealm"),
							Component.translatable("advancement.frostrealm.enter_frostrealm.desc"),
							new ResourceLocation(FrostRealm.MODID, "textures/block/frozen_dirt.png"),
							FrameType.TASK, true, true, false)
					.addCriterion("enter_frostrealm", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(FrostDimensions.FROSTREALM_LEVEL))
					.save(consumer, "frostrealm:enter_frostrealm");
			Advancement craftAltar = Advancement.Builder.advancement()
					.parent(enterAether)
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