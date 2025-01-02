package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostDamageType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class FrDamageTypeTagGenerator extends TagsProvider<DamageType> {

    public FrDamageTypeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, Registries.DAMAGE_TYPE, future, FrostRealm.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.NO_KNOCKBACK).add(FrostDamageType.VENOM).add(FrostDamageType.VENOM_BALL);
        this.tag(DamageTypeTags.WITCH_RESISTANT_TO).add(FrostDamageType.VENOM).add(FrostDamageType.VENOM_BALL);
    }
}