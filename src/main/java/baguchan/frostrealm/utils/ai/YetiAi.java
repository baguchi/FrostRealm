package baguchan.frostrealm.utils.ai;

import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.registry.FrostLoots;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.List;

public class YetiAi {
    public static void stopHoldingMainHandItem(Yeti yeti, boolean thrown) {
        ItemStack itemstack = yeti.getItemInHand(InteractionHand.MAIN_HAND);

        if (!yeti.isBaby()) {
            boolean flag = itemstack.is(FrostTags.Items.YETI_CURRENCY);
            boolean flag2 = itemstack.is(FrostTags.Items.YETI_BIG_CURRENCY);
            if (thrown && flag) {
                itemstack.shrink(1);
                throwItems(yeti, getBarterResponseItems(yeti));
                yeti.setHoldTime(40);
                if (itemstack.getCount() <= 0) {
                    yeti.seeTradeState.setActive(yeti, false);
                }
            } else if (thrown && flag2) {
                itemstack.shrink(1);
                throwItems(yeti, getBigBarterResponseItems(yeti));
                yeti.setHoldTime(40);
                if (itemstack.getCount() <= 0) {
                    yeti.seeTradeState.setActive(yeti, false);
                }
            } else if (!flag) {
                yeti.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                boolean flag1 = !yeti.equipItemIfPossible(itemstack).isEmpty();
                if (!flag1) {
                    putInInventory(yeti, itemstack);
                }
            }
        } else {
            boolean flag2 = !yeti.equipItemIfPossible(itemstack).isEmpty();
            if (!flag2) {
                ItemStack itemstack1 = yeti.getMainHandItem();
                if (isLovedItem(itemstack1)) {
                    putInInventory(yeti, itemstack1);
                } else {
                    throwItems(yeti, Collections.singletonList(itemstack1));
                }

                yeti.holdInMainHand(itemstack);
            }
            yeti.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            yeti.seeTradeState.setActive(yeti, false);
        }

    }

    protected static boolean isLovedItem(ItemStack p_149966_) {
        return p_149966_.is(FrostTags.Items.YETI_LOVED);
    }

    private static List<ItemStack> getBarterResponseItems(Yeti p_34997_) {
        LootTable loottable = p_34997_.level.getServer().getLootTables().get(FrostLoots.YETI_BARTERING);
        List<ItemStack> list = loottable.getRandomItems((new LootContext.Builder((ServerLevel) p_34997_.level)).withParameter(LootContextParams.THIS_ENTITY, p_34997_).withRandom(p_34997_.level.random).create(LootContextParamSets.PIGLIN_BARTER));
        return list;
    }

    private static List<ItemStack> getBigBarterResponseItems(Yeti p_34997_) {
        LootTable loottable = p_34997_.level.getServer().getLootTables().get(FrostLoots.YETI_BIG_BARTERING);
        List<ItemStack> list = loottable.getRandomItems((new LootContext.Builder((ServerLevel) p_34997_.level)).withParameter(LootContextParams.THIS_ENTITY, p_34997_).withRandom(p_34997_.level.random).create(LootContextParamSets.PIGLIN_BARTER));
        return list;
    }

    private static void putInInventory(Yeti p_34953_, ItemStack p_34954_) {
        ItemStack itemstack = p_34953_.addToInventory(p_34954_);
        throwItemsTowardRandomPos(p_34953_, Collections.singletonList(itemstack));
    }

    private static void throwItems(Yeti p_34861_, List<ItemStack> p_34862_) {
        Player player = p_34861_.getLevel().getNearestPlayer(p_34861_, 10.0D);
        if (player != null) {
            throwItemsTowardPlayer(p_34861_, player, p_34862_);
        } else {
            throwItemsTowardRandomPos(p_34861_, p_34862_);
        }

    }

    private static void throwItemsTowardRandomPos(Yeti p_34913_, List<ItemStack> p_34914_) {
        throwItemsTowardPos(p_34913_, p_34914_, getRandomNearbyPos(p_34913_));
    }

    private static void throwItemsTowardPlayer(Yeti p_34851_, Player p_34852_, List<ItemStack> p_34853_) {
        throwItemsTowardPos(p_34851_, p_34853_, p_34852_.position());
    }

    private static void throwItemsTowardPos(Yeti p_34864_, List<ItemStack> p_34865_, Vec3 p_34866_) {
        if (!p_34865_.isEmpty()) {
            p_34864_.swing(InteractionHand.MAIN_HAND);

            for (ItemStack itemstack : p_34865_) {
                BehaviorUtils.throwItem(p_34864_, itemstack, p_34866_.add(0.0D, 1.0D, 0.0D));
            }
        }

    }

    public static void holdInMainHand(Yeti p_34933_, ItemStack p_34934_) {
        if (isHoldingItemInMainHand(p_34933_)) {
            p_34933_.spawnAtLocation(p_34933_.getItemInHand(InteractionHand.MAIN_HAND));
        }

        p_34933_.holdInMainHand(p_34934_);
    }


    private static boolean isHoldingItemInMainHand(Yeti p_35027_) {
        return !p_35027_.getOffhandItem().isEmpty();
    }

    private static Vec3 getRandomNearbyPos(Yeti p_35017_) {
        Vec3 vec3 = LandRandomPos.getPos(p_35017_, 4, 2);
        return vec3 == null ? p_35017_.position() : vec3;
    }
}
