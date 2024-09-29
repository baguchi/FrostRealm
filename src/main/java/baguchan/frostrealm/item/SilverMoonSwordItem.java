package baguchan.frostrealm.item;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SilverMoonSwordItem extends SwordItem {
    public SilverMoonSwordItem(Tier p_43269_, Properties p_43272_) {
        super(p_43269_, p_43272_);
    }


    @Override
    public float getAttackDamageBonus(Entity p_344900_, float damage, DamageSource p_344972_) {
        if (p_344972_.getDirectEntity() instanceof LivingEntity livingentity) {
            float f = (float) (damage / (p_344900_.distanceTo(livingentity) * 0.25F));
            float f1 = Mth.clamp(f, damage, damage * 3F);
            return f1;
        }
        return damage;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, tooltipContext, components, flag);
        components.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }
}
