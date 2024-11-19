package baguchan.frostrealm.fluidtype;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;

public class HotSpringFluidType extends FluidType {
    public HotSpringFluidType(FluidType.Properties properties) {
        super(properties);
    }

    @Override
    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        /*boolean flag = entity.getDeltaMovement().y <= 0.0;
        float f = entity.isSprinting() ? 0.9F : 0.8F;
        float f1 = 0.02F;
        float f2 = (float)entity.getAttributeValue(Attributes.WATER_MOVEMENT_EFFICIENCY);
        if (!entity.onGround()) {
            f2 *= 0.5F;
        }

        if (f2 > 0.0F) {
            f += (0.54600006F - f) * f2;
            f1 += (entity.getSpeed() - f1) * f2;
        }

        if (entity.hasEffect(MobEffects.DOLPHINS_GRACE)) {
            f = 0.96F;
        }

        f1 *= (float)entity.getAttributeValue(net.neoforged.neoforge.common.NeoForgeMod.SWIM_SPEED);
        double d8 = entity.getY();

        entity.moveRelative(f1, movementVector);
        entity.move(MoverType.SELF, entity.getDeltaMovement());

        if (entity.getFluidTypeHeight(this) <= entity.getFluidJumpThreshold()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(f, (double) 0.8F, f));
            Vec3 vec33 = entity.getFluidFallingAdjustedMovement(gravity, flag, entity.getDeltaMovement());
            entity.setDeltaMovement(vec33);
        } else {
            entity.setDeltaMovement(entity.getDeltaMovement().scale(0.85D));
        }

        *//*if (!entity.isNoGravity()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, -gravity / 4.0D, 0.0D));
        }*//*

        Vec3 vec34 = entity.getDeltaMovement();
        if (entity.horizontalCollision && entity.isFree(vec34.x, vec34.y + (double) 0.6F - entity.getY() + d8, vec34.z)) {
            entity.setDeltaMovement(vec34.x, (double) 0.3F, vec34.z);
        }*/
        entity.addDeltaMovement(new Vec3(0, gravity / 6F, 0));

        return true;
    }

    protected static double getEffectiveGravity(LivingEntity entity) {
        boolean flag = entity.getDeltaMovement().y <= 0.0;
        return flag && entity.hasEffect(MobEffects.SLOW_FALLING) ? Math.min(entity.getGravity(), 0.01) : entity.getGravity();
    }
}