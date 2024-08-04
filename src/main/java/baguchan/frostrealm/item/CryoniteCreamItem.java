package baguchan.frostrealm.item;

import baguchan.frostrealm.registry.FrostEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CryoniteCreamItem extends Item {

    public CryoniteCreamItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_151209_, Level p_151210_, LivingEntity p_151211_) {
        if (p_151211_ instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        p_151211_.addEffect(new MobEffectInstance(FrostEffects.COLD_RESISTANCE, 1200 * 2));
        p_151209_.shrink(1);
        return p_151209_;
    }

    @Override
    public void onUseTick(Level p_41428_, LivingEntity livingEntity, ItemStack p_41430_, int count) {
        super.onUseTick(p_41428_, livingEntity, p_41430_, count);
        if (count % 6 == 0) {
            livingEntity.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 0.3F, 1.0F);
        }
    }

    public InteractionResultHolder<ItemStack> use(Level p_42927_, Player p_42928_, InteractionHand p_42929_) {
        return ItemUtils.startUsingInstantly(p_42927_, p_42928_, p_42929_);
    }

    @Override
    public int getUseDuration(ItemStack p_41454_, LivingEntity p_344979_) {
        return 40;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.CUSTOM;
    }
}
