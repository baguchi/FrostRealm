package baguchan.frostrealm.entity;

import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.entity.goal.BeasterAngryGoal;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostWeathers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class FrostBeaster extends FrozenMonster {

	private static final UniformInt TIME_BETWEEN_ANGRY = UniformInt.of(300, 600);
	private static final UniformInt TIME_BETWEEN_ANGRY_COOLDOWN = UniformInt.of(100, 400);

	public FrostBeaster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new BeasterAngryGoal(this, TIME_BETWEEN_ANGRY_COOLDOWN, TIME_BETWEEN_ANGRY));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1F, true));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.85D));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 4.0F).add(Attributes.MAX_HEALTH, 26.0D).add(Attributes.FOLLOW_RANGE, 26.0D).add(Attributes.ARMOR, 2.0D).add(Attributes.MOVEMENT_SPEED, 0.275D);
	}

	public static boolean checkFrostBeasterSpawnRules(EntityType<? extends Monster> p_27578_, ServerLevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		FrostWeatherCapability capability = FrostWeatherCapability.get(p_27579_.getLevel()).orElse(null);
		if (capability != null && capability.isWeatherActive() && capability.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
			return Monster.checkMonsterSpawnRules(p_27578_, p_27579_, p_27580_, p_27581_, p_27582_);
		}
		return false;
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	protected int calculateFallDamage(float p_21237_, float p_21238_) {
		return super.calculateFallDamage(p_21237_, p_21238_) - 8;
	}


	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
		SpawnGroupData flag = super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);

		this.populateDefaultEquipmentSlots(p_21435_);
		this.populateDefaultEquipmentEnchantments(this.random, p_21435_);

		return flag;
	}

	protected void populateDefaultEquipmentSlots(DifficultyInstance p_34286_) {
		if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.05F) + (0.5F * p_34286_.getSpecialMultiplier())) {
			int i = this.random.nextInt(3);
			if (i == 0) {
				this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(FrostItems.YETI_FUR_HELMET.get()));
				this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(FrostItems.YETI_FUR_CHESTPLATE.get()));
			} else {
				this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(FrostItems.YETI_FUR_HELMET.get()));
			}
		}

		if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.05F)) {
			int i = this.random.nextInt(3);
			if (i == 0) {
				this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
			} else {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
			}
		}

	}
}
