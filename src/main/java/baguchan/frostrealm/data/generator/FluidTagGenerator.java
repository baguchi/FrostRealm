package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class FluidTagGenerator extends FluidTagsProvider {
	public FluidTagGenerator(PackOutput p_255941_, CompletableFuture<HolderLookup.Provider> p_256600_, ExistingFileHelper exFileHelper) {
		super(p_255941_, p_256600_, FrostRealm.MODID, exFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider provider) {
	}
}