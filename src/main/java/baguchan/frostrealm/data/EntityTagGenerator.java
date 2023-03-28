package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class EntityTagGenerator extends EntityTypeTagsProvider {
	public EntityTagGenerator(PackOutput p_255941_, CompletableFuture<HolderLookup.Provider> p_256600_, ExistingFileHelper exFileHelper) {
		super(p_255941_, p_256600_, FrostRealm.MODID, exFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider provider) {

		tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(FrostEntities.FROST_WRAITH.get(), FrostEntities.CLUST_WRAITH.get(), FrostEntities.GOKKUDILLO.get()
				, FrostEntities.SNOWPILE_QUAIL.get(), FrostEntities.CRYSTAL_FOX.get(), FrostEntities.FROST_BEASTER.get(), FrostEntities.ASTRA_BALL.get(), FrostEntities.SNOW_MOLE.get(), FrostEntities.FROST_WOLF.get());
		tag(FrostTags.EntityTypes.COLD_WEATHER_IMMUNE).addTag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(FrostEntities.YETI.get(), FrostEntities.MARMOT.get());
		tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(FrostEntities.FROST_WOLF.get(), FrostEntities.SLEDGE.get(), FrostEntities.CHEST_SLEDGE.get());
	}
}