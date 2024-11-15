package baguchan.frostrealm.entity.hostile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class UnderGokkur extends Gokkur {
    public UnderGokkur(EntityType<? extends Gokkur> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ARMOR, 14.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    public void setSnowProgress(float progress) {
    }

    @Override
    public float getSnowProgress() {
        return 0.0F;
    }

    @Override
    public void setGrass(boolean grass) {
    }

    @Override
    public boolean isGrass() {
        return false;
    }
}
