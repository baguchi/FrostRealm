package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EntityTagGenerator extends EntityTypeTagsProvider {
	public EntityTagGenerator(DataGenerator generator, ExistingFileHelper exFileHelper) {
		super(generator, FrostRealm.MODID, exFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags() {
		tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(FrostEntities.FROST_WRAITH, FrostEntities.YETI, FrostEntities.CRYSTAL_TORTOISE
				, FrostEntities.MARMOT, FrostEntities.SNOWPILE_QUAIL, FrostEntities.FROST_WOLF, FrostEntities.FROST_BEASTER);
	}
}