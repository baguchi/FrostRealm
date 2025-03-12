package baguchan.frostrealm.utils;

import baguchan.frostrealm.registry.FrostTags;
import baguchi.bagus_lib.util.CombatUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Optional;

public class AttackUtils {
    public static void sickleAttack(Player player, Entity target, ItemStack itemstack) {

        if (itemstack.is(FrostTags.Items.SICKLE) && player.onGround()) {
            DamageSource damagesource = Optional.ofNullable(itemstack.getItem().getDamageSource(player)).orElse(player.damageSources().playerAttack(player));

            float f = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float f2 = player.getAttackStrengthScale(0.5F);
            boolean flag3 = f2 > 0.9F;

            f *= 0.2F + f2 * f2 * 0.8F;
            float f7 = 0.8F + (float) player.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) * 0.25F * f;
            if (flag3) {
                for (LivingEntity livingentity2 : player.level()
                        .getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1.5, 0.25, 1.5))) {
                    double entityReachSq = Mth.square(player.entityInteractionRange() + 0.5F); // Use entity reach instead of constant 9.0. Vanilla uses bottom center-to-center checks here, so don't update player to use canReach, since it uses closest-corner checks.
                    if (livingentity2 != player
                            && livingentity2 != target
                            && !player.isAlliedTo(livingentity2)
                            && (!(livingentity2 instanceof ArmorStand) || !((ArmorStand) livingentity2).isMarker())
                            && player.distanceToSqr(livingentity2) < entityReachSq) {
                        float f5 = player.level() instanceof ServerLevel serverLevel ? EnchantmentHelper.modifyDamage(serverLevel, player.getWeaponItem(), livingentity2, damagesource, f7) : f7;
                        livingentity2.knockback(
                                0.4F,
                                (double) Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)),
                                (double) (-Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)))
                        );
                        f5 = (float) (f5 / Mth.clamp(player.distanceToSqr(livingentity2) * 0.75F, 1, 2));

                        livingentity2.hurt(damagesource, f5);
                        if (player.level() instanceof ServerLevel serverlevel) {
                            EnchantmentHelper.doPostAttackEffects(serverlevel, livingentity2, damagesource);
                        }
                        itemstack.hurtEnemy(livingentity2, player);
                    }
                }
            }

            player.level()
                    .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
            player.sweepAttack();
        }
    }

    public static void spearAttack(Player player, Entity target, ItemStack itemstack) {

        if (itemstack.is(FrostTags.Items.SPEAR) && player.onGround()) {
            DamageSource damagesource = Optional.ofNullable(itemstack.getItem().getDamageSource(player)).orElse(player.damageSources().playerAttack(player));

            float f = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float f2 = player.getAttackStrengthScale(0.5F);
            boolean flag3 = f2 > 0.9F;

            f *= 0.2F + f2 * f2 * 0.8F;
            float f7 = f; //+ (float) player.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) * 0.25F * f;
            if (flag3) {
                for (Entity entity : CombatUtils.getTotalEntityHitResult(player.level(), player, player.getEyePosition(), player.getViewVector(0.0F).scale(player.entityInteractionRange() + 0.5F), target.getBoundingBox().inflate(1.5, 0.25, 1.5), (predicate) -> true, 0.1F)) {
                    if (entity instanceof LivingEntity livingentity2) {
                        double entityReachSq = Mth.square(player.entityInteractionRange() + 0.5F); // Use entity reach instead of constant 9.0. Vanilla uses bottom center-to-center checks here, so don't update player to use canReach, since it uses closest-corner checks.
                        if (livingentity2 != player
                                && livingentity2 != target
                                && !player.isAlliedTo(livingentity2)
                                && (!(livingentity2 instanceof ArmorStand) || !((ArmorStand) livingentity2).isMarker())
                                && player.distanceToSqr(livingentity2) < entityReachSq) {
                            float f5 = player.level() instanceof ServerLevel serverLevel ? EnchantmentHelper.modifyDamage(serverLevel, player.getWeaponItem(), livingentity2, damagesource, f7) : f7;

                            float f6 = ((float) player.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + 0.2F) * 0.5F;

                            livingentity2.knockback(
                                    f6,
                                    (double) Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)),
                                    (double) (-Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)))
                            );
                            f5 = (float) (f5 / Mth.clamp(player.distanceToSqr(livingentity2) * 0.5F, 1F, f));

                            livingentity2.hurt(damagesource, f5);
                            if (player.level() instanceof ServerLevel serverlevel) {
                                EnchantmentHelper.doPostAttackEffects(serverlevel, livingentity2, damagesource);
                            }
                            itemstack.hurtEnemy(livingentity2, player);
                        }

                    }
                }
            }

            player.level()
                    .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
            player.sweepAttack();
        }
    }

}