package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.tree.decorator.DripHangingLeavesDecorator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FrostTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, FrostRealm.MODID);
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<DripHangingLeavesDecorator>> DRIP_TREE_HANGING_LEAVES = TREE_DECORATORS.register("drip_wood_hanging_leaves",
            () -> new TreeDecoratorType<>(DripHangingLeavesDecorator.CODEC));

}
