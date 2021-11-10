package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FluidTagGenerator extends FluidTagsProvider {
	public FluidTagGenerator(DataGenerator generator, ExistingFileHelper exFileHelper) {
		super(generator, FrostRealm.MODID, exFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags() {
	}
}