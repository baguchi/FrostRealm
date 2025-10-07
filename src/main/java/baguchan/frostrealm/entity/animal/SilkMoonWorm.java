package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.registry.FrostBlocks;
import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SilkMoonWorm extends PathfinderMob {
    @VisibleForTesting
    public static int ticksToBeBig = Math.abs(-24000);
    private int age;

    public SilkMoonWorm(EntityType<? extends SilkMoonWorm> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MOVEMENT_SPEED, 0.125F).add(Attributes.MAX_HEALTH, 4.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            this.setAge(this.age + 1);
        }
    }

    @Override
    public void addAdditionalSaveData(ValueOutput p_218709_) {
        super.addAdditionalSaveData(p_218709_);
        p_218709_.putInt("Age", this.age);
    }

    @Override
    public void readAdditionalSaveData(ValueInput p_218698_) {
        super.readAdditionalSaveData(p_218698_);
        this.setAge(p_218698_.getIntOr("Age", 0));
    }

    @Override
    public InteractionResult mobInteract(Player p_218703_, InteractionHand p_218704_) {
        ItemStack itemstack = p_218703_.getItemInHand(p_218704_);
        if (this.isFood(itemstack)) {
            this.feed(p_218703_, itemstack);
            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(p_218703_, p_218704_);
        }
    }

    private boolean isFood(ItemStack p_218727_) {
        return p_218727_.is(ItemTags.LEAVES);
    }

    private void feed(Player p_218691_, ItemStack p_218692_) {
        this.usePlayerItem(p_218691_, p_218692_);
        this.ageUp(AgeableMob.getSpeedUpSecondsWhenFeeding(this.getTicksLeftUntilAdult()));
        this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
    }

    private void usePlayerItem(Player p_218706_, ItemStack p_218707_) {
        p_218707_.consume(1, p_218706_);
    }

    private int getAge() {
        return this.age;
    }

    private void ageUp(int p_218701_) {
        this.setAge(this.age + p_218701_ * 20);
    }

    private void setAge(int p_218711_) {
        this.age = p_218711_;
        if (this.age >= ticksToBeBig) {
            this.ageUp();
        }
    }

    private void ageUp() {
        if (this.level() instanceof ServerLevel serverlevel && serverlevel.getBlockState(this.blockPosition()).isAir()) {
            this.playSound(SoundEvents.WOOL_PLACE, 0.5F, 1.0F);
            serverlevel.setBlock(this.blockPosition(), FrostBlocks.SILK_MOON_COCOON.get().defaultBlockState(), 3);
            this.discard();
        }
    }

    private int getTicksLeftUntilAdult() {
        return Math.max(0, ticksToBeBig - this.age);
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

}
