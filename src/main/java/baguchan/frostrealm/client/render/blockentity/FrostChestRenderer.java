package baguchan.frostrealm.client.render.blockentity;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;

public class FrostChestRenderer<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T> {
	public static final Material CHEST_LOCATION = chestMaterial("frostroot");
	public static final Material CHEST_LOCATION_LEFT = chestMaterial("frostroot_left");
	public static final Material CHEST_LOCATION_RIGHT = chestMaterial("frostroot_right");

	public FrostChestRenderer(BlockEntityRendererProvider.Context p_173607_) {
		super(p_173607_);
	}

	private static Material chestMaterial(String p_110779_) {
		return new Material(Sheets.CHEST_SHEET, new ResourceLocation(FrostRealm.MODID, "entity/chest/" + p_110779_));
	}

	protected net.minecraft.client.resources.model.Material getMaterial(T blockEntity, ChestType chestType) {
		return chooseMaterial(chestType, CHEST_LOCATION, CHEST_LOCATION_LEFT, CHEST_LOCATION_RIGHT);
	}

	private static Material chooseMaterial(ChestType p_110772_, Material p_110773_, Material p_110774_, Material p_110775_) {
		switch (p_110772_) {
			case LEFT:
				return p_110774_;
			case RIGHT:
				return p_110775_;
			case SINGLE:
			default:
				return p_110773_;
		}
	}
}