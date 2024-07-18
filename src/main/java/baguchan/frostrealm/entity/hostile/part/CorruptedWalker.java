package baguchan.frostrealm.entity.hostile.part;

import baguchan.frostrealm.entity.goal.MoveAttackerAndLookGoal;
import baguchan.frostrealm.entity.path.MultiLegPathNavigation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CorruptedWalker extends Monster {
    public static final int MAX_PART = 4;
    protected int movingPartIndex = -1;
    public final CorruptedWalkerPartContainer[] ec = new CorruptedWalkerPartContainer[4];

    private final CorruptedWalkerPart[] partArray;

    public CorruptedWalker(EntityType<? extends CorruptedWalker> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        List<CorruptedWalkerPart> parts = new ArrayList<>();
        for (int i = 0; i < MAX_PART; i++) {
            CorruptedWalkerPart partParent = new CorruptedWalkerPart<>(this, 1.0F, 0.5F);
            this.ec[i] = new CorruptedWalkerPartContainer(this, i, 6, partParent, EntityDimensions.scalable(0.5F, 0.5F));
            parts.add(this.ec[i].getParentPart());
            Collections.addAll(parts, this.ec[i].getParts());
        }

        this.noCulling = true;
        this.xpReward = 50;
        this.partArray = parts.toArray(new CorruptedWalkerPart[0]);
        this.setId(ENTITY_COUNTER.getAndAdd(this.partArray.length + 1) + 1); // Forge: Fix MC-158205: Make sure part ids are successors of parent mob id
        this.getNavigation().setCanFloat(false);
    }

    @Override
    public void refreshDimensions() {
        super.refreshDimensions();
        for (CorruptedWalkerPartContainer container : this.ec) {
            container.getParentPart().setScale(this.getScale());
            container.getParentPart().setSize(container.getParentPart().getSize());
            for (CorruptedWalkerPart part : container.getParts()) {
                part.setScale(this.getScale());
                part.setSize(part.getSize());
            }
        }
    }

    @Override
    public float sanitizeScale(float p_320290_) {
        return super.sanitizeScale(p_320290_);
    }

    @Override
    protected PathNavigation createNavigation(Level p_21480_) {
        return new MultiLegPathNavigation(this, p_21480_);
    }

    @Override
    public void setZza(float p_21565_) {
    }

    @Override
    public void setXxa(float p_21571_) {
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.partArray.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.partArray[i].setId(id + i + 1);
    }

    public void setMovingPartIndex(int movingPartIndex) {
        this.movingPartIndex = movingPartIndex;
    }

    public static AttributeSupplier.Builder createAttributeMap() {
        return Monster.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.24D).add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.SAFE_FALL_DISTANCE, 8.0D).add(Attributes.STEP_HEIGHT, 3.0F).add(Attributes.FOLLOW_RANGE, 24.0D).add(Attributes.ARMOR, 5.0D).add(Attributes.ATTACK_DAMAGE, 3.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MoveAttackerAndLookGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, true));

    }

    @Override
    public void aiStep() {
        // update all heads

        double xDiff = 0;
        double yDiff = 0;
        double zDiff = 0;
        for (int i = 0; i < MAX_PART; i++) {
            this.ec[i].tick();
            if (this.tickCount > 5) {
                CorruptedWalkerPart part = this.ec[i].getParentPart();

                if (this.getY() > part.getY() + 1.5 && this.getY() + 2F < part.getY()) {
                    yDiff += 0.05F;
                } else if (this.getY() > part.getY() + 2F && this.getY() + 2.5F < part.getY()) {
                    yDiff -= 0.05F;
                }
                xDiff += (part.getX() - this.getX()) * 0.15F;
                yDiff += (part.getY() - this.getY()) * 0.15F;
                zDiff += (part.getZ() - this.getZ()) * 0.15F;
            }
        }
        if (!this.level().isClientSide()) {
            if (!this.getNavigation().isDone()) {
                float speed = 20 * (0.2F / this.getSpeed());
                if (this.tickCount % Mth.floor(speed) == 0) {
                    if (this.movingPartIndex == 3) {
                        this.movingPartIndex = -1;
                    }
                    this.movingPartIndex++;
                }
            } else {
                this.movingPartIndex = -1;
            }
            this.move(MoverType.SELF, new Vec3(xDiff, yDiff, zDiff));
        }

        super.aiStep();
    }

    public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 p_21075_, float p_21076_) {
        this.moveRelative(this.getFrictionInfluencedSpeed(p_21076_), p_21075_);
        this.setDeltaMovement(this.handleOnClimbable(this.getDeltaMovement()));
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 vec3 = this.getDeltaMovement();
        if ((this.horizontalCollision || this.jumping)
                && (this.onClimbable() || this.getInBlockState().is(Blocks.POWDER_SNOW) && PowderSnowBlock.canEntityWalkOnPowderSnow(this))) {
            vec3 = new Vec3(vec3.x, 0.2, vec3.z);
        }

        return vec3;
    }

    private Vec3 handleOnClimbable(Vec3 p_21298_) {
        return p_21298_;
    }

    private float getFrictionInfluencedSpeed(float p_21331_) {
        return this.getFlyingSpeed();
    }

    @Override
    public void travel(Vec3 vec3) {
        if (Arrays.stream(this.ec).noneMatch(corruptedWalkerPartContainer -> corruptedWalkerPartContainer.getParentPart().onGround())) {
            super.travel(vec3);
        } else {
            if (this.isControlledByLocalInstance()) {
                if (this.isInWater()) {
                    this.moveRelative(0.02F, vec3);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
                } else if (this.isInLava()) {
                    this.moveRelative(0.02F, vec3);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
                } else {
                    this.moveRelative(this.getSpeed(), vec3);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.91F));
                }
            }

        }
    }

    @Override
    public void push(Entity p_21294_) {
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public @Nullable PartEntity<?>[] getParts() {
        return partArray;
    }
}
