package baguchan.frostrealm.item;

import baguchan.frostrealm.client.FrostArmPoses;
import baguchan.frostrealm.registry.FrostEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CryoniteCreamItem extends Item {

    public CryoniteCreamItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_151209_, Level p_151210_, LivingEntity p_151211_) {
        if (p_151211_ instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        p_151211_.addEffect(new MobEffectInstance(FrostEffects.COLD_RESISTANCE.get(), 1200 * 2));
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
    public int getUseDuration(ItemStack p_41454_) {
        return 40;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.CUSTOM;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new ItemRender());
    }

    private static final class ItemRender implements IClientItemExtensions {
        private static final ItemRender INSTANCE = new ItemRender();

        @Override
        public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            return FrostArmPoses.RUB_HAND;
        }

        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
            if (player.isUsingItem()) {
                applyEatTransform(poseStack, player, partialTick, arm, itemInHand);
            }

            applyItemArmTransform(poseStack, arm, equipProcess);
            applyItemArmAttackTransform(poseStack, arm, swingProcess);
            return true;
        }

        private void applyItemArmAttackTransform(PoseStack p_109336_, HumanoidArm p_109337_, float p_109338_) {
            int i = p_109337_ == HumanoidArm.RIGHT ? 1 : -1;
            float f = Mth.sin(p_109338_ * p_109338_ * (float) Math.PI);
            p_109336_.mulPose(Axis.YP.rotationDegrees((float) i * (45.0F + f * -20.0F)));
            float f1 = Mth.sin(Mth.sqrt(p_109338_) * (float) Math.PI);
            p_109336_.mulPose(Axis.ZP.rotationDegrees((float) i * f1 * -20.0F));
            p_109336_.mulPose(Axis.XP.rotationDegrees(f1 * -80.0F));
            p_109336_.mulPose(Axis.YP.rotationDegrees((float) i * -45.0F));
        }

        private void applyEatTransform(PoseStack p_109331_, LocalPlayer player, float p_109332_, HumanoidArm p_109333_, ItemStack p_109334_) {
            float f = (float) player.getUseItemRemainingTicks() - p_109332_ + 1.0F;
            float f1 = f / (float) p_109334_.getUseDuration();
            if (f1 < 0.8F) {
                float f2 = Mth.abs(Mth.cos(f / 4.0F * (float) Math.PI) * 0.1F);
                p_109331_.translate(0.0F, f2, 0.0F);
            }

            float f3 = 1.0F - (float) Math.pow((double) f1, 20.0D);
            int i = p_109333_ == HumanoidArm.RIGHT ? 1 : -1;
            p_109331_.translate(f3 * 0.6F * (float) i, f3 * -0.5F, f3 * 0.0F);
            p_109331_.mulPose(Axis.YP.rotationDegrees((float) i * f3 * 90.0F));
            p_109331_.mulPose(Axis.XP.rotationDegrees(f3 * 10.0F));
            p_109331_.mulPose(Axis.ZP.rotationDegrees((float) i * f3 * 30.0F));
        }

        private void applyItemArmTransform(PoseStack p_109383_, HumanoidArm p_109384_, float p_109385_) {
            int i = p_109384_ == HumanoidArm.RIGHT ? 1 : -1;
            p_109383_.translate((float) i * 0.56F, -0.52F + p_109385_ * -0.6F, -0.72F);
        }
    }
}
