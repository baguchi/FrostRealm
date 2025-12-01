package baguchan.frostrealm.utils;

import baguchan.frostrealm.registry.FrostTags;
import baguchi.bagus_lib.util.CombatUtils;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class AttackUtils {
    public static void sickleAttack(Player player, Entity target, ItemStack itemstack) {

        if (itemstack.is(FrostTags.Items.SICKLE) && player.onGround()) {
            DamageSource damagesource = Optional.ofNullable(itemstack.getItem().getItemDamageSource(player)).orElse(player.damageSources().playerAttack(player));

            float f = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);

            float f2 = player.getAttackStrengthScale(0.5F);
            boolean flag3 = f2 > 0.9F;
            if (flag3) {
                double entityReachSq = Mth.square(player.entityInteractionRange() + 0.5F); // Use entity reach instead of constant 9.0. Vanilla uses bottom center-to-center checks here, so don't update player to use canReach, since it uses closest-corner checks.

                for (LivingEntity livingentity2 : player.level()
                        .getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(entityReachSq / 2 - 0.5F, 0.25, entityReachSq / 2 - 0.5F))) {
                    if (livingentity2 != player
                            && livingentity2 != target
                            && !player.isAlliedTo(livingentity2)
                            && (!(livingentity2 instanceof ArmorStand) || !((ArmorStand) livingentity2).isMarker())
                            && player.distanceToSqr(livingentity2) < entityReachSq) {
                        Vec3 vec3 = livingentity2.position();
                        double d0;
                        Vec3 vec31 = player.calculateViewVector(0.0F, player.getYHeadRot());
                        Vec3 vec32 = vec3.subtract(player.position());
                        vec32 = new Vec3(vec32.x, 0.0, vec32.z).normalize();
                        d0 = Math.acos(vec32.dot(vec31));
                        if (resolveRange(d0)) {

                            //attack bonus
                            f += itemstack.getItem().getAttackDamageBonus(target, f, damagesource);
                            //enchant
                            float f1 = (player.level() instanceof ServerLevel serverLevel) ? EnchantmentHelper.modifyDamage(serverLevel, itemstack, livingentity2, damagesource, f) : f;
                            f += f1;
                            f *= 0.2F + f2 * f2 * 0.8F;

                            livingentity2.knockback(
                                    0.4F,
                                    (double) Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)),
                                    (double) (-Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)))
                            );
                            f /= (float) Math.max(1F, player.distanceTo(livingentity2) / (player.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) + 1));
                            livingentity2.hurt(damagesource, f);
                            if (player.level() instanceof ServerLevel serverlevel) {
                                EnchantmentHelper.doPostAttackEffects(serverlevel, livingentity2, damagesource);
                            }
                            itemstack.hurtEnemy(livingentity2, player);
                        }
                    }

                  if(player.level() instanceof ServerLevel serverLevel) {
                      double d0 = -Mth.sin(player.getYRot() * (float) (Math.PI / 180.0));
                      double d1 = Mth.cos(player.getYRot() * (float) (Math.PI / 180.0));
                      serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
                  }
                }
            }
        }
    }

    public static boolean resolveRange(double p_401381_) {
        if (p_401381_ > (float) (Math.PI / 180.0) * 80) {
            return false;
        } else {
            return true;
        }
    }


    public static void damageParticle(Player player, Entity target, float health) {
        if (target instanceof LivingEntity) {
            float f8 = health - ((LivingEntity) target).getHealth();
            //this.awardStat(Stats.DAMAGE_DEALT, Math.round(f8 * 10.0F));
            if (player.level() instanceof ServerLevel && f8 > 2.0F) {
                int i = (int) ((double) f8 * 0.5);
                ((ServerLevel) player.level())
                        .sendParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getY(0.5), target.getZ(), i, 0.1, 0.0, 0.1, 0.2);
            }
        }
    }

}