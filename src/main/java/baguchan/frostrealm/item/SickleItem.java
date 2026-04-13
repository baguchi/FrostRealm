package baguchan.frostrealm.item;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SickleItem extends Item {
    public static final Identifier BASE_ENTITY_RANGE = Identifier.withDefaultNamespace("base_entity_range");
    public static final Identifier BASE_BLOCK_RANGE = Identifier.withDefaultNamespace("base_block_range");


    public static final Identifier SWEEP_SPEED_ID = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "sweep_speed");

    public SickleItem(ToolMaterial tier, float damage, float speed, Item.Properties properties) {
        super(properties.durability(tier.durability()).enchantable(tier.enchantmentValue()).repairable(tier.repairItems()).attributes(createAttributes(tier, damage, speed)).component(DataComponents.TOOL, createToolProperties()).component(DataComponents.WEAPON, new Weapon(1)));
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(), 1.0F, 2, false);
    }

    public static ItemAttributeModifiers createAttributes(ToolMaterial tier, float damage, float speed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage + tier.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.SWEEPING_DAMAGE_RATIO, new AttributeModifier(SWEEP_SPEED_ID, 0.1F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_RANGE, 0.5F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public AABB getSweepHitBox(ItemStack stack, Player player, Entity target) {
        return target.getBoundingBox().inflate(1D, 0.25D, 1D);
    }


    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.SWEEPING_EDGE);
    }
}
