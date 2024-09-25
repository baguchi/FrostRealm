package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class EntityTagGenerator extends EntityTypeTagsProvider {
	public EntityTagGenerator(PackOutput p_255941_, CompletableFuture<HolderLookup.Provider> p_256600_, ExistingFileHelper exFileHelper) {
		super(p_255941_, p_256600_, FrostRealm.MODID, exFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider provider) {

		tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(FrostEntities.FROST_WRAITH.get()
						, FrostEntities.SNOWPILE_QUAIL.get(), FrostEntities.CRYSTAL_FOX.get(), FrostEntities.ASTRA_BALL.get(), FrostEntities.SNOW_MOLE.get(), FrostEntities.YETI.get(), FrostEntities.WOLFFLUE.get(), FrostEntities.SEAL.get())
                .add(FrostEntities.SEEKER.get()).add(FrostEntities.CORRUPTED_WALKER.get());
		tag(FrostTags.EntityTypes.COLD_WEATHER_IMMUNE).addTag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(FrostEntities.MARMOT.get(), FrostEntities.FROST_BOAR.get(), FrostEntities.MIND_VINE.get());
        tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS);
		tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(FrostEntities.ASTRA_BALL.get()).add(FrostEntities.SNOWPILE_QUAIL.get()).add(FrostEntities.CORRUPTED_WALKER.get());
        this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER).add(FrostEntities.CORRUPTED_WALKER.get());
	}
}