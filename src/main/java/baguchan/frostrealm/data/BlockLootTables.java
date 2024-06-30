package baguchan.frostrealm.data;

import baguchan.frostrealm.block.crop.BearBerryBushBlock;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
	private final Set<Block> knownBlocks = new HashSet<>();
	// [VanillaCopy] super
	private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
	private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

	private static final Set<Item> EXPLOSION_RESISTANT = Set.of();


	protected BlockLootTables(HolderLookup.Provider p_344943_) {
		super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), p_344943_);
	}

	@Override
	protected void add(Block block, LootTable.Builder builder) {
		super.add(block, builder);
		knownBlocks.add(block);
	}

	@Override
	protected void generate() {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		registerEmpty(FrostBlocks.FROST_PORTAL.get());

		this.dropSelf(FrostBlocks.FROZEN_DIRT.get());
		this.add(FrostBlocks.FROZEN_GRASS_BLOCK.get(), (p_124193_) -> {
			return createSingleItemTableWithSilkTouch(p_124193_, FrostBlocks.FROZEN_DIRT.get());
		});
		this.add(FrostBlocks.FRIGID_GRASS_BLOCK.get(), (p_124193_) -> {
			return createSingleItemTableWithSilkTouch(p_124193_, FrostBlocks.FRIGID_STONE.get());
		});

		this.dropOther(FrostBlocks.FROZEN_FARMLAND.get(), FrostBlocks.FROZEN_DIRT.get());
		this.dropSelf(FrostBlocks.POINTED_ICE.get());

		this.dropSelf(FrostBlocks.PERMA_SLATE.get());
		this.dropSelf(FrostBlocks.PERMA_SLATE_BRICK.get());
		this.dropSelf(FrostBlocks.PERMA_SLATE_SMOOTH.get());
		this.add(FrostBlocks.PERMA_SLATE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(FrostBlocks.PERMA_SLATE_BRICK_STAIRS.get());

		this.dropSelf(FrostBlocks.FRIGID_STONE.get());

		this.add(FrostBlocks.FRIGID_STONE_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(FrostBlocks.FRIGID_STONE_STAIRS.get());
		this.dropSelf(FrostBlocks.FRIGID_STONE_BRICK.get());
		this.dropSelf(FrostBlocks.FRIGID_STONE_SMOOTH.get());
        this.dropSelf(FrostBlocks.CHISELED_FRIGID_STONE_BRICK.get());
        this.add(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get());

		this.dropSelf(FrostBlocks.FRIGID_STONE_MOSSY.get());
		this.add(FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get());

		this.dropSelf(FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());
		this.add(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get());

		this.dropSelf(FrostBlocks.SHERBET_SAND.get());
		this.dropSelf(FrostBlocks.SHERBET_SANDSTONE.get());
		this.add(FrostBlocks.SHERBET_SANDSTONE_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(FrostBlocks.SHERBET_SANDSTONE_STAIRS.get());

		this.dropSelf(FrostBlocks.FROSTROOT_LOG.get());
		this.dropSelf(FrostBlocks.STRIPPED_FROSTROOT_LOG.get());
		this.dropSelf(FrostBlocks.FROSTROOT_SAPLING.get());
		this.add(FrostBlocks.FROSTROOT_LEAVES.get(), (p_124104_) -> {
            return createFrostLeavesDrops(p_124104_, FrostBlocks.FROSTROOT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES);
        });
        this.dropSelf(FrostBlocks.FROSTROOT_PLANKS.get());
        this.dropSelf(FrostBlocks.FROSTROOT_CRAFTING_TABLE.get());
        this.add(FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(FrostBlocks.FROSTROOT_PLANKS_STAIRS.get());
        this.dropSelf(FrostBlocks.FROSTROOT_FENCE.get());
        this.dropSelf(FrostBlocks.FROSTROOT_FENCE_GATE.get());
        this.add(FrostBlocks.FROSTROOT_DOOR.get(), this::createDoorTable);

        this.dropSelf(FrostBlocks.VIGOROSHROOM.get());
        this.dropSelf(FrostBlocks.ARCTIC_POPPY.get());
        this.dropSelf(FrostBlocks.ARCTIC_WILLOW.get());

		this.add(FrostBlocks.COLD_GRASS.get(), this.createFrostGrassDrops(FrostBlocks.COLD_GRASS.get()));
		this.add(FrostBlocks.COLD_TALL_GRASS.get(), this.createDoublePlantWithFrostSeedDrops(FrostBlocks.COLD_TALL_GRASS.get(), FrostBlocks.COLD_GRASS.get()));
        this.add(FrostBlocks.BEARBERRY_BUSH.get(), (p_124096_) -> {
			return applyExplosionDecay(p_124096_, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(FrostBlocks.BEARBERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BearBerryBushBlock.AGE, 3))).add(LootItem.lootTableItem(FrostItems.BEARBERRY.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(FrostBlocks.BEARBERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BearBerryBushBlock.AGE, 2))).add(LootItem.lootTableItem(FrostItems.BEARBERRY.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
        });
		LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(FrostBlocks.SUGARBEET.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BeetrootBlock.AGE, 3));
		this.add(FrostBlocks.SUGARBEET.get(), createCropDrops(FrostBlocks.SUGARBEET.get(), FrostItems.SUGARBEET.get(), FrostItems.SUGARBEET.get(), lootitemcondition$builder));
        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(FrostBlocks.RYE.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BeetrootBlock.AGE, 7));
        this.add(FrostBlocks.RYE.get(), createCropDrops(FrostBlocks.RYE.get(), FrostItems.RYE_SEEDS.get(), FrostItems.RYE.get(), lootitemcondition$builder2));


		this.add(FrostBlocks.FROST_CRYSTAL_ORE.get(), this::createFrostCrystalOreDrops);
		this.add(FrostBlocks.GLIMMERROCK_ORE.get(), this::createGlimmerRockOreDrops);
		this.add(FrostBlocks.ASTRIUM_ORE.get(), this::createAstriumOreDrops);

		this.add(FrostBlocks.FROST_CRYSTAL_SLATE_ORE.get(), this::createFrostCrystalOreDrops);
		this.add(FrostBlocks.GLIMMERROCK_SLATE_ORE.get(), this::createGlimmerRockOreDrops);
		this.add(FrostBlocks.ASTRIUM_SLATE_ORE.get(), this::createAstriumOreDrops);

		this.dropSelf(FrostBlocks.ASTRIUM_BLOCK.get());
		this.add(FrostBlocks.STARDUST_CRYSTAL_ORE.get(), this::createStardustCrystalOreDrops);
		this.dropSelf(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get());
		this.dropSelf(FrostBlocks.WARPED_CRYSTAL_BLOCK.get());
		this.dropSelf(FrostBlocks.FROST_TORCH.get());
		this.dropOther(FrostBlocks.WALL_FROST_TORCH.get(), FrostBlocks.FROST_TORCH.get());
		this.add(FrostBlocks.FROST_CAMPFIRE.get(), (p_236259_) -> {
			return createSilkTouchDispatchTable(p_236259_, applyExplosionCondition(p_236259_, LootItem.lootTableItem(FrostItems.FROST_CRYSTAL.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))));
		});
		this.dropSelf(FrostBlocks.FROSTROOT_CHEST.get());
        this.dropSelf(FrostBlocks.AURORA_INFUSER.get());
		this.dropSelf(FrostBlocks.SNOWPILE_QUAIL_EGG.get());
	}

	protected LootTable.Builder createFrostGrassDrops(Block p_252139_) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		return createShearsDispatchTable(
				p_252139_,
				(LootPoolEntryContainer.Builder<?>) this.applyExplosionDecay(
						p_252139_,
						LootItem.lootTableItem(FrostItems.RYE_SEEDS.get())
								.when(LootItemRandomChanceCondition.randomChance(0.125F))
								.apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE), 2))
				)
		);
	}

	protected LootTable.Builder createDoublePlantWithFrostSeedDrops(Block p_248590_, Block p_248735_) {
		LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(p_248735_)
				.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
				.when(HAS_SHEARS)
				.otherwise(
						((LootPoolSingletonContainer.Builder) this.applyExplosionCondition(p_248590_, LootItem.lootTableItem(FrostItems.RYE_SEEDS.get())))
								.when(LootItemRandomChanceCondition.randomChance(0.125F))
				);
		return LootTable.lootTable()
				.withPool(
						LootPool.lootPool()
								.add(builder)
								.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_248590_)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
								)
								.when(
										LocationCheck.checkLocation(
												LocationPredicate.Builder.location()
														.setBlock(
																BlockPredicate.Builder.block()
																		.of(p_248590_)
																		.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))
														),
												new BlockPos(0, 1, 0)
										)
								)
				)
				.withPool(
						LootPool.lootPool()
								.add(builder)
								.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_248590_)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))
								)
								.when(
										LocationCheck.checkLocation(
												LocationPredicate.Builder.location()
														.setBlock(
																BlockPredicate.Builder.block()
																		.of(p_248590_)
																		.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
														),
												new BlockPos(0, -1, 0)
										)
								)
				);
	}


	private void registerSlab(Block b) {
		add(b, createSlabItemTable(b));
	}


	// [VanillaCopy] super.droppingWithChancesAndSticks, but non-silk touch parameter can be an item instead of a block
	private LootTable.Builder silkAndStick(Block block, ItemLike nonSilk, float... nonSilkFortune) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);


        return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(nonSilk.asItem())).when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), nonSilkFortune))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(HAS_SHEARS).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
	}

	protected LootTable.Builder createFrostLeavesDrops(Block p_124264_, Block p_124265_, float... p_124266_) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);


        return createLeavesDrops(p_124264_, p_124265_, p_124266_).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_SHEARS).add(applyExplosionCondition(p_124264_, LootItem.lootTableItem(FrostItems.FROZEN_FRUIT.get())).when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
	}

	protected LootTable.Builder createFrostCrystalOreDrops(Block p_176049_) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		return createSilkTouchDispatchTable(p_176049_, applyExplosionCondition(p_176049_, LootItem.lootTableItem(FrostItems.FROST_CRYSTAL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
	}

	protected LootTable.Builder createGlimmerRockOreDrops(Block p_176049_) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		return createSilkTouchDispatchTable(p_176049_, applyExplosionDecay(p_176049_, LootItem.lootTableItem(FrostItems.GLIMMERROCK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))).apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
	}

	protected LootTable.Builder createAstriumOreDrops(Block p_176049_) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		return createSilkTouchDispatchTable(p_176049_, applyExplosionDecay(p_176049_, LootItem.lootTableItem(FrostItems.ASTRIUM_RAW.get()).apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
	}

	protected LootTable.Builder createStardustCrystalOreDrops(Block p_176049_) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		return createSilkTouchDispatchTable(p_176049_, applyExplosionDecay(p_176049_, LootItem.lootTableItem(FrostItems.STARDUST_CRYSTAL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
	}


	private void registerEmpty(Block b) {
		add(b, LootTable.lootTable());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return knownBlocks;
	}
}