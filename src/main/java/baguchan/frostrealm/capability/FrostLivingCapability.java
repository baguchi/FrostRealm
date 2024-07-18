package baguchan.frostrealm.capability;

import baguchan.frostrealm.message.ChangedColdMessage;
import baguchan.frostrealm.registry.*;
import baguchan.frostrealm.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

public class FrostLivingCapability implements INBTSerializable<CompoundTag> {

	public boolean isInsidePortal = false;
	public int portalTimer = 0;
	public float portalAnimTime = 0;
	public float prevPortalAnimTime = 0;

	protected int temperature = 20;
	protected float temperatureSaturation = 1.0F;

	private int tickTimer;

	private float exhaustionLevel;
	private int lastTemperate = 20;

	private BlockPos hotSource;

	public void addExhaustion(float exhaution) {
		this.exhaustionLevel = Math.min(this.exhaustionLevel + exhaution, 40.0F);
	}

	public void addHot(int temprature, float saturation) {
		this.temperature = Math.min(temprature + this.temperature, 20);
		this.temperatureSaturation = Math.min(this.temperatureSaturation + temprature * saturation * 2.0F, this.temperature);
	}

	public static FrostLivingCapability get(Entity entity) {
		return entity.getData(FrostAttachs.FROST_LIVING);
	}

	public void tick(LivingEntity entity) {
		if (entity instanceof Player player) {
			this.handlePortal(player);
		}

		/*
		 *  Body temperature stuff
		 */
		if (entity.level().dimension() == FrostDimensions.FROSTREALM_LEVEL && (!entity.getType().is(FrostTags.EntityTypes.COLD_WEATHER_IMMUNE) && !((entity instanceof Player && ((Player) entity).isCreative()) || entity.isSpectator())) && !entity.hasEffect(FrostEffects.COLD_RESISTANCE)) {
			Difficulty difficulty = entity.level().getDifficulty();
			this.lastTemperate = this.temperature;
			hotSourceTick(entity);
			float tempAffect = 1.0F;
			if (!entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty())
				tempAffect *= 0.85F;
			if (!entity.getItemBySlot(EquipmentSlot.CHEST).isEmpty())
				tempAffect *= 0.65F;
			if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemTags.FREEZE_IMMUNE_WEARABLES))
				tempAffect *= 0.5F;
			if (!entity.getItemBySlot(EquipmentSlot.LEGS).isEmpty())
				tempAffect *= 0.75F;
			if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.FREEZE_IMMUNE_WEARABLES))
				tempAffect *= 0.55F;
			if (!entity.getItemBySlot(EquipmentSlot.FEET).isEmpty())
				tempAffect *= 0.8F;
			if (entity.isInWaterOrRain())
				tempAffect *= 2.0F;
			if (entity.isInFluidType(FrostFluidTypes.HOT_SPRING.get())) {
				tempAffect *= 0.1F;
				if (entity.tickCount % 80 == 0) {
					this.temperatureSaturation = Math.min(this.temperatureSaturation + 0.05F, 1.0F);
					this.temperature = Math.min(this.temperature + 1, 20);
				}
			}

			if (this.hotSource == null) {
				FrostWeatherSavedData cap = FrostWeatherSavedData.get(entity.level());
				if (cap != null) {
					if (isAffectRain(entity) && cap.isWeatherActive() && cap.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
						addExhaustion(0.001F * (entity.canFreeze() ? 1.0F : 0.25F));
					}
				}
			}
			Biome biome = entity.level().getBiome(entity.blockPosition()).value();

			if (biome.getModifiedClimateSettings().temperature() < 0.4F) {
				if (this.hotSource == null) {
					addExhaustion(tempAffect * 0.002F);
					if (this.exhaustionLevel > 4.0F) {
						this.exhaustionLevel -= 4.0F;
						if (this.temperatureSaturation > 0.0F) {
							this.temperatureSaturation = Math.max(this.temperatureSaturation - 0.1F, 0.0F);
						} else if (difficulty != Difficulty.PEACEFUL) {
							this.temperature = Math.max(this.temperature - 1, 0);
						}
					}
				}
				if (entity.getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) {
					this.temperature = Math.max(this.temperature - 1, 0);
					this.temperatureSaturation = 0.0F;
					entity.setTicksFrozen(Mth.clamp(entity.getTicksFrozen() + 8, 0, 200));
				} else if (this.temperature <= 0) {
					entity.setTicksFrozen(Mth.clamp(entity.getTicksFrozen() + 8, 0, 200));
					this.tickTimer = 0;
				} else {
					this.tickTimer = 0;
				}
			} else {
				if (entity.tickCount % 80 == 0) {
					this.temperatureSaturation = Math.min(this.temperatureSaturation + 0.05F, 1.0F);
					this.temperature = Math.min(this.temperature + 1, 20);
				}
				this.exhaustionLevel = 0.0F;
			}
		} else {
			if (entity.tickCount % 20 == 0) {
				this.temperatureSaturation = Math.min(this.temperatureSaturation + 0.1F, 1.0F);
				this.temperature = Math.min(this.temperature + 1, 20);
			}
			this.exhaustionLevel = 0.0F;
		}
		if (entity.tickCount % 20 == 0 && !entity.level().isClientSide()) {
			ChangedColdMessage message = new ChangedColdMessage(entity, this.temperature, this.temperatureSaturation);
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, message);
		}
	}

	private void hotSourceTick(LivingEntity entity) {
		if (this.hotSource != null && (!this.hotSource.closerThan(entity.blockPosition(), 3.46D) || !entity.level().getBlockState(this.hotSource).is(FrostTags.Blocks.HOT_SOURCE) || (entity.level().getBlockState(this.hotSource).hasProperty(BlockStateProperties.LIT) && entity.level().getBlockState(this.hotSource).getValue(BlockStateProperties.LIT)))) {
			this.hotSource = null;
		}
		if (this.hotSource == null) {
			int heatRange = 2;
			int entityPosX = (int) entity.getX();
			int entityPosY = (int) entity.getY();
			int entityPosZ = (int) entity.getZ();
			if (entity.tickCount % 20 == 0)
				for (int hX = entityPosX - heatRange; hX <= entityPosX + heatRange; hX++) {
					for (int hY = entityPosY - 2; hY <= entityPosY; hY++) {
						for (int hZ = entityPosZ - heatRange; hZ <= entityPosZ + heatRange; hZ++) {
							if (entity.level().getBlockState(new BlockPos(hX, hY, hZ)).is(FrostTags.Blocks.HOT_SOURCE))
								this.hotSource = new BlockPos(hX, hY, hZ);
						}
					}
				}
		}
		if (this.hotSource != null &&
				this.hotSource.closerThan(entity.blockPosition(), 3.46D) &&
				entity.tickCount % 20 == 0) {
			this.temperatureSaturation = Math.min(this.temperatureSaturation + 0.1F, 1.0F);
			this.temperature = Math.min(this.temperature + 1, 20);
		}
	}

	private boolean isAffectRain(LivingEntity entity) {
		BlockPos blockpos = entity.blockPosition();
		return entity.level().canSeeSky(blockpos) && entity.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos).getY() <= blockpos.getY();
	}

	public int getTemperatureLevel() {
		return this.temperature;
	}

	public float getSaturationLevel() {
		return this.temperatureSaturation;
	}

	public void setTemperatureLevel(int temperature) {
		this.temperature = temperature;
	}

	public void setSaturation(float saturation) {
		this.temperatureSaturation = saturation;
	}


	@OnlyIn(Dist.CLIENT)
	private void playPortalSound(Minecraft mc) {
		if (mc.player != null) {
			mc.getSoundManager().play(SimpleSoundInstance.forLocalAmbience(SoundEvents.PORTAL_TRIGGER, mc.player.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
		}
	}

	public void setInPortal(boolean inPortal) {
		this.isInsidePortal = inPortal;
	}

	public boolean isInsidePortal() {
		return this.isInsidePortal;
	}

	public void setPortalTimer(int timer) {
		this.portalTimer = timer;
	}

	public int getPortalTimer() {
		return this.portalTimer;
	}

	public float getPortalAnimTime() {
		return this.portalAnimTime;
	}

	public float getPrevPortalAnimTime() {
		return this.prevPortalAnimTime;
	}

	public void handlePortal(Player player) {
		if (player.level().isClientSide()) {
			this.prevPortalAnimTime = this.portalAnimTime;
			if (this.isInsidePortal()) {
				if (Minecraft.getInstance().screen != null
						&& !Minecraft.getInstance().screen.isPauseScreen()
						&& !(Minecraft.getInstance().screen instanceof DeathScreen)
						&& !(Minecraft.getInstance().screen instanceof WinScreen)) {
					if (Minecraft.getInstance().screen instanceof AbstractContainerScreen) {
						player.closeContainer();
					}

					Minecraft.getInstance().setScreen(null);
				}

				if (this.portalAnimTime == 0.0F) {
					ClientUtils.playPortalSound(player);
				}
			}
		}

		if (this.isInsidePortal()) {
			++this.portalTimer;
			if (player.level().isClientSide()) {
				this.portalAnimTime += 0.0125F;
				if (this.getPortalAnimTime() > 1.0F) {
					this.portalAnimTime = 1.0F;
				}
			}
			this.isInsidePortal = false;
		} else {
			if (player.level().isClientSide()) {
				if (this.getPortalAnimTime() > 0.0F) {
					this.portalAnimTime -= 0.05F;
				}

				if (this.getPortalAnimTime() < 0.0F) {
					this.portalAnimTime = 0.0F;
				}
			}
			if (this.getPortalTimer() > 0) {
				this.portalTimer -= 4;
			}
		}
	}

	public boolean isColdBody() {
		return this.temperature < 12;
	}

	public CompoundTag serializeNBT(HolderLookup.Provider provider) {
		CompoundTag nbt = new CompoundTag();

		nbt.putInt("Temperature", this.temperature);
		nbt.putFloat("TemperatureSaturation", this.temperatureSaturation);
		nbt.putFloat("TemperatureExhaustion", this.exhaustionLevel);

		return nbt;
	}

	public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
		this.temperature = nbt.getInt("Temperature");
		this.temperatureSaturation = nbt.getInt("TemperatureSaturation");
		this.exhaustionLevel = nbt.getInt("TemperatureExhaustion");
	}
}