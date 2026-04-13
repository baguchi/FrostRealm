package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.FrostConfiguredFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.tags.FeatureTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.concurrent.CompletableFuture;

public class CustomTagGenerator {


    public static class ConfiguredFeatureTagGenerator extends KeyTagProvider<ConfiguredFeature<?, ?>> {

        public ConfiguredFeatureTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, Registries.CONFIGURED_FEATURE, provider, FrostRealm.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider p_256380_) {
            tag(FeatureTags.CAN_SPAWN_FROM_BONE_MEAL).add(FrostConfiguredFeatures.ARCTIC_POPPY).add(FrostConfiguredFeatures.ARCTIC_WILLOW)
                    .add(FrostConfiguredFeatures.PATCH_BEARBERRY).add(FrostConfiguredFeatures.PATCH_VIGOROSHROOM);
        }
    }

}