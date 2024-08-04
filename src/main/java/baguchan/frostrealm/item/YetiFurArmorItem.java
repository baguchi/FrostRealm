package baguchan.frostrealm.item;

import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.YetiFurArmorModel;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class YetiFurArmorItem extends ArmorItem {
	public YetiFurArmorItem(Holder<ArmorMaterial> p_40386_, Type p_40387_, Properties p_40388_) {
        super(p_40386_, p_40387_, p_40388_);
    }

	@Override
	public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
		return stack.is(FrostItems.YETI_FUR_BOOTS.get()) || stack.is(FrostItems.FROST_BOAR_FUR_BOOTS.get());
	}

	public static final class ArmorRender implements IClientItemExtensions {
		public static final ArmorRender INSTANCE = new ArmorRender();


		@Override
		public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
			EntityModelSet models = Minecraft.getInstance().getEntityModels();
			ModelPart root = models.bakeLayer(equipmentSlot == EquipmentSlot.LEGS ? FrostModelLayers.YETI_FUR_ARMOR_INNER : FrostModelLayers.YETI_FUR_ARMOR_OUTER);

			YetiFurArmorModel<?> model2 = new YetiFurArmorModel<>(root);
			ClientHooks.copyModelProperties(original, model2);
			return model2;
		}

		protected void setPartVisibility(HumanoidModel p_117126_, EquipmentSlot p_117127_) {
			p_117126_.setAllVisible(false);
			switch (p_117127_) {
				case HEAD:
					p_117126_.head.visible = true;
					p_117126_.hat.visible = true;
					break;
				case CHEST:
					p_117126_.body.visible = true;
					p_117126_.rightArm.visible = true;
					p_117126_.leftArm.visible = true;
					break;
				case LEGS:
					p_117126_.body.visible = true;
					p_117126_.rightLeg.visible = true;
					p_117126_.leftLeg.visible = true;
					break;
				case FEET:
					p_117126_.rightLeg.visible = true;
					p_117126_.leftLeg.visible = true;
			}

		}

	}
}
