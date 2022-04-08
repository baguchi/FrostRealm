package baguchan.frostrealm.data;

import baguchan.frostrealm.block.BearBerryBushBlock;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends net.minecraft.data.loot.BlockLoot {
	private final Set<Block> knownBlocks = new HashSet<>();
	// [VanillaCopy] super
	private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
	private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

	@Override
	protected void add(Block block, LootTable.Builder builder) {
		super.add(block, builder);
		knownBlocks.add(block);
	}

	@Override
	protected void addTables() {
		registerEmpty(FrostBlocks.FROST_PORTAL.get());
		registerEmpty(FrostBlocks.HOT_AIR.get());

		this.dropSelf(FrostBlocks.FROZEN_DIRT.get());
		this.add(FrostBlocks.FROZEN_GRASS_BLOCK.get(), (p_124193_) -> {
			return createSingleItemTableWithSilkTouch(p_124193_, FrostBlocks.FROZEN_DIRT.get());
		});
		this.dropOther(FrostBlocks.FROZEN_FARMLAND.get(), FrostBlocks.FROZEN_DIRT.get());
		this.dropSelf(FrostBlocks.POINTED_ICE.get());

		this.dropSelf(FrostBlocks.FRIGID_STONE.get());
		this.add(FrostBlocks.FRIGID_STONE_SLAB.get(), BlockLoot::createSlabItemTable);
		this.dropSelf(FrostBlocks.FRIGID_STONE_STAIRS.get());
		this.dropSelf(FrostBlocks.FRIGID_STONE_BRICK.get());
		this.dropSelf(FrostBlocks.FRIGID_STONE_SMOOTH.get());
		this.add(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), BlockLoot::createSlabItemTable);
		this.dropSelf(FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get());

		this.dropSelf(FrostBlocks.FRIGID_STONE_MOSSY.get());
		this.add(FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), BlockLoot::createSlabItemTable);
		this.dropSelf(FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get());

		this.dropSelf(FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());
		this.add(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), BlockLoot::createSlabItemTable);
		this.dropSelf(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get());


		this.dropSelf(FrostBlocks.FROSTROOT_LOG.get());
		this.dropSelf(FrostBlocks.FROSTROOT_SAPLING.get());
		this.add(FrostBlocks.FROSTROOT_LEAVES.get(), (p_124104_) -> {
			return createFrostLeavesDrops(p_124104_, FrostBlocks.FROSTROOT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES);
		});
		this.dropSelf(FrostBlocks.FROSTROOT_PLANKS.get());
		this.add(FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), BlockLoot::createSlabItemTable);
		this.dropSelf(FrostBlocks.FROSTROOT_PLANKS_STAIRS.get());
		this.dropSelf(FrostBlocks.FROSTROOT_FENCE.get());
		this.dropSelf(FrostBlocks.FROSTROOT_FENCE_GATE.get());
		this.add(FrostBlocks.FROSTROOT_DOOR.get(), BlockLoot::createDoorTable);

		this.dropSelf(FrostBlocks.FROZEN_LOG.get());
		this.dropSelf(FrostBlocks.FROZEN_SAPLING.get());
		this.add(FrostBlocks.FROZEN_LEAVES.get(), (p_124104_) -> {
			return createFrostLeavesDrops(p_124104_, FrostBlocks.FROZEN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES);
		});
		this.dropSelf(FrostBlocks.FROZEN_PLANKS.get());
		this.add(FrostBlocks.FROZEN_PLANKS_SLAB.get(), BlockLoot::createSlabItemTable);
		this.dropSelf(FrostBlocks.FROZEN_PLANKS_STAIRS.get());
		this.dropSelf(FrostBlocks.FROZEN_FENCE.get());
		this.dropSelf(FrostBlocks.FROZEN_FENCE_GATE.get());

		this.dropSelf(FrostBlocks.VIGOROSHROOM.get());
		this.dropSelf(FrostBlocks.ARCTIC_POPPY.get());
		this.dropSelf(FrostBlocks.ARCTIC_WILLOW.get());

		this.registerEmpty(FrostBlocks.COLD_GRASS.get());
		this.registerEmpty(FrostBlocks.COLD_TALL_GRASS.get());
		this.add(FrostBlocks.BEARBERRY_BUSH.get(), (p_124096_) -> {
			return applyExplosionDecay(p_124096_, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(FrostBlocks.BEARBERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BearBerryBushBlock.AGE, 3))).add(LootItem.lootTableItem(FrostItems.BEARBERRY.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(FrostBlocks.BEARBERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BearBerryBushBlock.AGE, 2))).add(LootItem.lootTableItem(FrostItems.BEARBERRY.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
		});
		LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(FrostBlocks.SUGARBEET.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BeetrootBlock.AGE, 3));
		this.add(FrostBlocks.SUGARBEET.get(), createCropDrops(FrostBlocks.SUGARBEET.get(), FrostItems.SUGARBEET.get(), FrostItems.SUGARBEET.get(), lootitemcondition$builder));


		this.add(FrostBlocks.FROST_CRYSTAL_ORE.get(), BlockLootTables::createFrostCrystalOreDrops);
		this.add(FrostBlocks.GLIMMERROCK_ORE.get(), BlockLootTables::createGlimmerRockOreDrops);
		this.add(FrostBlocks.ASTRIUM_ORE.get(), BlockLootTables::createAstriumOreDrops);
		this.add(FrostBlocks.STARDUST_CRYSTAL_ORE.get(), BlockLootTables::createStardustCrystalOreDrops);
		this.dropSelf(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get());
		this.dropSelf(FrostBlocks.CORRUPTED_CRYSTAL_CLUSTER.get());
		this.dropSelf(FrostBlocks.WARPED_CRYSTAL_BLOCK.get());
		this.dropSelf(FrostBlocks.FROST_TORCH.get());
		this.dropOther(FrostBlocks.WALL_FROST_TORCH.get(), FrostBlocks.FROST_TORCH.get());
		this.dropSelf(FrostBlocks.FRIGID_STOVE.get());
		this.dropSelf(FrostBlocks.FROSTROOT_CHEST.get());
		this.dropSelf(FrostBlocks.SNOWPILE_QUAIL_EGG.get());
	}


	// [VanillaCopy] super.droppingWithChancesAndSticks, but non-silk touch parameter can be an item instead of a block
	private static LootTable.Builder silkAndStick(Block block, ItemLike nonSilk, float... nonSilkFortune) {
		LootItemCondition.Builder NOT_SILK_TOUCH_OR_SHEARS = ObfuscationReflectionHelper.getPrivateValue(net.minecraft.data.loot.BlockLoot.class, null, "HAS_NO_SHEARS_OR_SILK_TOUCH");
		return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(nonSilk.asItem())).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, nonSilkFortune))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
	}

	protected static LootTable.Builder createFrostLeavesDrops(Block p_124264_, Block p_124265_, float... p_124266_) {
		LootItemCondition.Builder NOT_SILK_TOUCH_OR_SHEARS = ObfuscationReflectionHelper.getPrivateValue(net.minecraft.data.loot.BlockLoot.class, null, "HAS_NO_SHEARS_OR_SILK_TOUCH");
		return createLeavesDrops(p_124264_, p_124265_, p_124266_).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(NOT_SILK_TOUCH_OR_SHEARS).add(applyExplosionCondition(p_124264_, LootItem.lootTableItem(FrostItems.FROZEN_FRUIT.get())).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
	}

	protected static LootTable.Builder createFrostCrystalOreDrops(Block p_176049_) {
		return createSilkTouchDispatchTable(p_176049_, applyExplosionDecay(p_176049_, LootItem.lootTableItem(FrostItems.FROST_CRYSTAL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	protected static LootTable.Builder createGlimmerRockOreDrops(Block p_176049_) {
		return createSilkTouchDispatchTable(p_176049_, applyExplosionDecay(p_176049_, LootItem.lootTableItem(FrostItems.GLIMMERROCK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	protected static LootTable.Builder createAstriumOreDrops(Block p_176049_) {
		return createSilkTouchDispatchTable(p_176049_, applyExplosionDecay(p_176049_, LootItem.lootTableItem(FrostItems.ASTRIUM_RAW.get()).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	protected static LootTable.Builder createStardustCrystalOreDrops(Block p_176049_) {
		return createSilkTouchDispatchTable(p_176049_, applyExplosionDecay(p_176049_, LootItem.lootTableItem(FrostItems.STARDUST_CRYSTAL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}


	private void registerEmpty(Block b) {
		add(b, LootTable.lootTable());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return knownBlocks;
	}
}