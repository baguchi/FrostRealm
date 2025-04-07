package baguchan.frostrealm.item;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class SilverMoonSwordItem extends Item {
    public SilverMoonSwordItem(ToolMaterial toolMaterial, float p_362481_, float p_364182_, Properties p_43272_) {
        super(toolMaterial.applySwordProperties(p_43272_, p_362481_, p_364182_));
    }


    @Override
    public float getAttackDamageBonus(Entity p_344900_, float damage, DamageSource p_344972_) {
        if (p_344972_.getDirectEntity() instanceof LivingEntity livingentity) {
            float f = (1 / Mth.sin(Mth.clamp(p_344900_.distanceTo(livingentity) * 0.5F, 0.1F, 1F)));
            float f1 = Mth.clamp(f * f - 1, 0.0F, 3F);
            return f1 * 2F;
        }
        return 0.0F;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, TooltipDisplay p_399753_, Consumer<Component> p_399884_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_339594_, p_399753_, p_399884_, p_41424_);
        p_399884_.accept(Component.translatable(getDescriptionId() + ".tooltip"));
    }
}
