package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;

import java.util.concurrent.CompletableFuture;

public class FrFluidTagGenerator extends FluidTagsProvider {
    public FrFluidTagGenerator(PackOutput p_255941_, CompletableFuture<HolderLookup.Provider> p_256600_) {
        super(p_255941_, p_256600_, FrostRealm.MODID);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider provider) {
	}
}