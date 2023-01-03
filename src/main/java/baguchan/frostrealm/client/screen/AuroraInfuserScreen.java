package baguchan.frostrealm.client.screen;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.aurorapower.AuroraPower;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.client.AuroraPowerClientHandler;
import baguchan.frostrealm.menu.AuroraInfuserMenu;
import baguchan.frostrealm.registry.AuroraPowers;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AuroraInfuserScreen extends AbstractContainerScreen<AuroraInfuserMenu> {
	private static final ResourceLocation ENCHANTING_TABLE_LOCATION = new ResourceLocation(FrostRealm.MODID, "textures/gui/container/aurora_infuser.png");
	private final RandomSource random = RandomSource.create();

	private ItemStack last = ItemStack.EMPTY;

	public AuroraInfuserScreen(AuroraInfuserMenu p_98754_, Inventory p_98755_, Component p_98756_) {
		super(p_98754_, p_98755_, p_98756_);
	}

	protected void init() {
		super.init();
	}

	public void containerTick() {
		super.containerTick();
	}

	public boolean mouseClicked(double p_98758_, double p_98759_, int p_98760_) {
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;

		for (int k = 0; k < 3; ++k) {
			double d0 = p_98758_ - (double) (i + 60);
			double d1 = p_98759_ - (double) (j + 14 + 19 * k);
			if (d0 >= 0.0D && d1 >= 0.0D && d0 < 108.0D && d1 < 19.0D && this.menu.clickMenuButton(this.minecraft.player, k)) {
				this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, k);
				return true;
			}
		}

		return super.mouseClicked(p_98758_, p_98759_, p_98760_);
	}

	protected void renderBg(PoseStack p_98762_, float p_98763_, int p_98764_, int p_98765_) {
		Lighting.setupForFlatItems();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, ENCHANTING_TABLE_LOCATION);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(p_98762_, i, j, 0, 0, this.imageWidth, this.imageHeight);
		int k = (int) this.minecraft.getWindow().getGuiScale();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		EnchantmentNames.getInstance().initSeed((long) this.menu.getEnchantmentSeed());
		int l = this.menu.getGoldCount();

		for (int i1 = 0; i1 < 3; ++i1) {
			int j1 = i + 60;
			int k1 = j1 + 20;
			this.setBlitOffset(0);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, ENCHANTING_TABLE_LOCATION);
			int l1 = (this.menu).costs[i1];
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			if (l1 == 0) {
				this.blit(p_98762_, j1, j + 14 + 19 * i1, 0, 185, 108, 19);
			} else {
				String s = "" + l1;
				int i2 = 86 - this.font.width(s);
				FormattedText formattedtext = EnchantmentNames.getInstance().getRandomName(this.font, i2);
				int j2 = 6839882;
				if (((l < i1 + 1 || FrostWeatherCapability.getAuroraLevel(this.minecraft.player.level) < l1 * 0.01F) && !this.minecraft.player.getAbilities().instabuild) || AuroraPowerClientHandler.auroraIDs[i1] == null) { // Forge: render buttons as disabled when enchantable but enchantability not met on lower levels
					this.blit(p_98762_, j1, j + 14 + 19 * i1, 0, 185, 108, 19);
					this.blit(p_98762_, j1 + 1, j + 15 + 19 * i1, 16 * i1, 239, 16, 16);
					this.font.drawWordWrap(formattedtext, k1, j + 16 + 19 * i1, i2, (j2 & 16711422) >> 1);
					j2 = 4226832;
				} else {
					int k2 = p_98764_ - (i + 60);
					int l2 = p_98765_ - (j + 14 + 19 * i1);
					if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
						this.blit(p_98762_, j1, j + 14 + 19 * i1, 0, 204, 108, 19);
						j2 = 16777088;
					} else {
						this.blit(p_98762_, j1, j + 14 + 19 * i1, 0, 166, 108, 19);
					}

					this.blit(p_98762_, j1 + 1, j + 15 + 19 * i1, 16 * i1, 223, 16, 16);
					this.font.drawWordWrap(formattedtext, k1, j + 16 + 19 * i1, i2, j2);
					j2 = 8453920;
				}

				this.font.drawShadow(p_98762_, s, (float) (k1 + 86 - this.font.width(s)), (float) (j + 16 + 19 * i1 + 7), j2);
			}
		}

	}

	public void render(PoseStack p_98767_, int p_98768_, int p_98769_, float p_98770_) {
		p_98770_ = this.minecraft.getFrameTime();
		this.renderBackground(p_98767_);
		super.render(p_98767_, p_98768_, p_98769_, p_98770_);
		this.renderTooltip(p_98767_, p_98768_, p_98769_);
		boolean flag = this.minecraft.player.getAbilities().instabuild;
		int i = this.menu.getGoldCount();

		for (int j = 0; j < 3; ++j) {
			int k = (this.menu).costs[j];
			AuroraPower auroraPower = AuroraPowerClientHandler.auroraIDs[j] == null ? null : AuroraPowers.getRegistry().get().getValue(AuroraPowerClientHandler.auroraIDs[j]);
			int l = (this.menu).levelClue[j];
			int i1 = j + 1;
			if (this.isHovering(60, 14 + 19 * j, 108, 17, (double) p_98768_, (double) p_98769_) && k > 0) {
				List<Component> list = Lists.newArrayList();
				list.add((Component.translatable("container.frostrealm.aurora_infuser.clue", auroraPower == null ? "" : AuroraPowerUtils.auroraPowerName(auroraPower))).withStyle(ChatFormatting.WHITE));
				if (FrostWeatherCapability.getAuroraLevel(this.minecraft.player.level) < k * 0.01F) {
					list.add(Component.translatable("container.frostrealm.aurora_infuser.level.requirement_aurora", (this.menu).costs[j]).withStyle(ChatFormatting.RED));
				} else if (auroraPower == null) {
					list.add(Component.literal(""));
					list.add(Component.translatable("container.frostrealm.aurora_infuser.limitedEnchantability").withStyle(ChatFormatting.RED));
				} else if (!flag) {
					list.add(CommonComponents.EMPTY);

					MutableComponent mutablecomponent;
					if (i1 == 1) {
						mutablecomponent = Component.translatable("container.frostrealm.aurora_infuser.stardust_crystal.one");
					} else {
						mutablecomponent = Component.translatable("container.frostrealm.aurora_infuser.stardust_crystal.many", i1);
					}

					list.add(mutablecomponent.withStyle(i >= i1 ? ChatFormatting.GRAY : ChatFormatting.RED));
				}

				this.renderComponentTooltip(p_98767_, list, p_98768_, p_98769_);
				break;
			}
		}

	}
}
