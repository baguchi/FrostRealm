package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class WarpedIceSoul extends FrozenMonster {
	public WarpedIceSoul(EntityType<? extends WarpedIceSoul> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
		this.moveControl = new FlyingMoveControl(this, 20, true);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25F, false));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 0.95D));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	protected PathNavigation createNavigation(Level p_29417_) {
		FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_29417_);
		flyingpathnavigation.setCanOpenDoors(false);
		flyingpathnavigation.setCanFloat(true);
		flyingpathnavigation.setCanPassDoors(true);
		return flyingpathnavigation;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 18.0D).add(Attributes.ATTACK_DAMAGE, 2.0F).add(Attributes.ARMOR, 12.0F).add(Attributes.FLYING_SPEED, 0.24F).add(Attributes.MOVEMENT_SPEED, 0.21F).add(Attributes.FOLLOW_RANGE, 24.0F);
	}

	@Override
	public void travel(Vec3 p_218382_) {
		if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
			if (this.isInWater()) {
				this.moveRelative(0.02F, p_218382_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
			} else if (this.isInLava()) {
				this.moveRelative(0.02F, p_218382_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
			} else {
				this.moveRelative(this.getSpeed(), p_218382_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.91F));
			}
		}

		this.calculateEntityAnimation(false);
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
		this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(FrostItems.ASTRIUM_SWORD.get()));
	}

	public boolean causeFallDamage(float p_148989_, float p_148990_, DamageSource p_148991_) {
		return false;
	}

	protected void checkFallDamage(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
	}
}
