package baguchan.frostrealm.client.screen;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.aurorapower.AuroraPower;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.menu.AuroraInfuserMenu;
import baguchan.frostrealm.registry.AuroraPowers;
import baguchan.frostrealm.registry.FrostDimensions;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AuroraInfuserScreen extends AbstractContainerScreen<AuroraInfuserMenu> {
    private static final ResourceLocation[] ENABLED_LEVEL_SPRITES = new ResourceLocation[]{ResourceLocation.withDefaultNamespace("container/enchanting_table/level_1"), ResourceLocation.withDefaultNamespace("container/enchanting_table/level_2"), ResourceLocation.withDefaultNamespace("container/enchanting_table/level_3")};
    private static final ResourceLocation[] DISABLED_LEVEL_SPRITES = new ResourceLocation[]{ResourceLocation.withDefaultNamespace("container/enchanting_table/level_1_disabled"), ResourceLocation.withDefaultNamespace("container/enchanting_table/level_2_disabled"), ResourceLocation.withDefaultNamespace("container/enchanting_table/level_3_disabled")};
    private static final ResourceLocation ENCHANTMENT_SLOT_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/enchanting_table/enchantment_slot_disabled");
    private static final ResourceLocation ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("container/enchanting_table/enchantment_slot_highlighted");
    private static final ResourceLocation ENCHANTMENT_SLOT_SPRITE = ResourceLocation.withDefaultNamespace("container/enchanting_table/enchantment_slot");
    private static final ResourceLocation ENCHANTING_TABLE_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/gui/container/aurora_infuser.png");
    private static final ResourceLocation ALT_FONT = ResourceLocation.fromNamespaceAndPath("minecraft", "alt");
    private static final Style ROOT_STYLE = Style.EMPTY.withFont(ALT_FONT);

    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    private ItemStack last;

    public AuroraInfuserScreen(AuroraInfuserMenu p_98754_, Inventory p_98755_, Component p_98756_) {
        super(p_98754_, p_98755_, p_98756_);
        this.last = ItemStack.EMPTY;
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
            if (d0 >= 0.0 && d1 >= 0.0 && d0 < 108.0 && d1 < 19.0 && ((AuroraInfuserMenu) this.menu).clickMenuButton(this.minecraft.player, k)) {
                this.minecraft.gameMode.handleInventoryButtonClick(((AuroraInfuserMenu) this.menu).containerId, k);
                return true;
            }
        }

        return super.mouseClicked(p_98758_, p_98759_, p_98760_);
    }

    protected void renderBg(GuiGraphics p_282430_, float p_282530_, int p_281621_, int p_283333_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        p_282430_.blit(ENCHANTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int k = ((AuroraInfuserMenu) this.menu).getGoldCount();

        for (int l = 0; l < 3; ++l) {
            int i1 = i + 60;
            int j1 = i1 + 20;
            int k1 = ((AuroraInfuserMenu) this.menu).costs[l];
            if (k1 == 0) {
                p_282430_.blitSprite(ENCHANTMENT_SLOT_DISABLED_SPRITE, i1, j + 14 + 19 * l, 108, 19);
            } else {
                String s = "" + k1;
                int l1 = 86 - this.font.width(s);
                AuroraPower auroraPower = AuroraPowers.getRegistry().byId(((AuroraInfuserMenu) this.menu).auroraClue[l]);

                FormattedText formattedtext;

                if (auroraPower == null) {
                    formattedtext = Component.literal("???");
                } else {
                    formattedtext = auroraPower.getFullname(((AuroraInfuserMenu) this.menu).levelClue[l]).copy().withStyle(ROOT_STYLE);
                }

                int i2 = 6839882;
                if (this.minecraft.player.level().dimension() == FrostDimensions.FROSTREALM_LEVEL && (k >= l + 1 && FrostWeatherManager.getAuroraLevel() >= k1 * 0.01F || this.minecraft.player.getAbilities().instabuild) && ((AuroraInfuserMenu) this.menu).levelClue[l] != -1) {
                    int j2 = p_281621_ - (i + 60);
                    int k2 = p_283333_ - (j + 14 + 19 * l);
                    if (j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
                        p_282430_.blitSprite(ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                        i2 = 16777088;
                    } else {
                        p_282430_.blitSprite(ENCHANTMENT_SLOT_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                    }

                    p_282430_.blitSprite(ENABLED_LEVEL_SPRITES[l], i1 + 1, j + 15 + 19 * l, 16, 16);
                    p_282430_.drawWordWrap(this.font, formattedtext, j1, j + 16 + 19 * l, l1, i2);
                    i2 = 8453920;
                } else {
                    p_282430_.blitSprite(ENCHANTMENT_SLOT_DISABLED_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                    p_282430_.blitSprite(DISABLED_LEVEL_SPRITES[l], i1 + 1, j + 15 + 19 * l, 16, 16);
                    p_282430_.drawWordWrap(this.font, formattedtext, j1, j + 16 + 19 * l, l1, (i2 & 16711422) >> 1);
                    i2 = 4226832;
                }

                p_282430_.drawString(this.font, s, j1 + 86 - this.font.width(s), j + 16 + 19 * l + 7, i2);
            }
        }

    }

    public void render(GuiGraphics p_283462_, int p_282491_, int p_281953_, float p_282182_) {
        p_282182_ = this.minecraft.getTimer().getGameTimeDeltaTicks();
        super.render(p_283462_, p_282491_, p_281953_, p_282182_);
        this.renderTooltip(p_283462_, p_282491_, p_281953_);
        boolean flag = this.minecraft.player.getAbilities().instabuild;
        int i = ((AuroraInfuserMenu) this.menu).getGoldCount();

        for (int j = 0; j < 3; ++j) {
            int k = ((AuroraInfuserMenu) this.menu).costs[j];
            AuroraPower auroraPower = AuroraPowers.getRegistry().byId(((AuroraInfuserMenu) this.menu).auroraClue[j]);
            int l = ((AuroraInfuserMenu) this.menu).levelClue[j];
            int i1 = j + 1;
            if (this.isHovering(60, 14 + 19 * j, 108, 17, (double) p_282491_, (double) p_281953_) && k > 0) {
                List<Component> list = Lists.newArrayList();
                if (this.minecraft.player.level().dimension() != FrostDimensions.FROSTREALM_LEVEL) {
                    list.add(Component.literal(""));
                    list.add(Component.translatable("container.frostrealm.aurora_infuser.no_aurora").withStyle(ChatFormatting.RED));
                } else {
                    list.add(Component.translatable("container.frostrealm.aurora_infuser.clue", new Object[]{auroraPower == null ? "" : auroraPower.getFullname(l)}).withStyle(ChatFormatting.WHITE));
                }
                if (auroraPower == null) {
                    list.add(Component.literal(""));
                    list.add(Component.translatable("container.frostrealm.aurora_infuser.limitedEnchantability").withStyle(ChatFormatting.RED));
                } else if (!flag) {
                    list.add(CommonComponents.EMPTY);
                    if (FrostWeatherManager.getAuroraLevel() < k * 0.01F) {
                        list.add(Component.translatable("container.frostrealm.aurora_infuser.level.requirement_aurora", new Object[]{((AuroraInfuserMenu) this.menu).costs[j]}).withStyle(ChatFormatting.RED));
                    } else {
                        MutableComponent mutablecomponent;
                        if (i1 == 1) {
                            mutablecomponent = Component.translatable("container.frostrealm.aurora_infuser.stardust_crystal.one");
                        } else {
                            mutablecomponent = Component.translatable("container.frostrealm.aurora_infuser.stardust_crystal.many", new Object[]{i1});
                        }

                        list.add(mutablecomponent.withStyle(i >= i1 ? ChatFormatting.GRAY : ChatFormatting.RED));
                        MutableComponent mutablecomponent1;
                        if (i1 == 1) {
                            mutablecomponent1 = Component.translatable("container.enchant.level.one");
                        } else {
                            mutablecomponent1 = Component.translatable("container.enchant.level.many", new Object[]{i1});
                        }

                        list.add(mutablecomponent1.withStyle(ChatFormatting.GRAY));
                    }
                }

                p_283462_.renderComponentTooltip(this.font, list, p_282491_, p_281953_);
                break;
            }
        }

    }
}
