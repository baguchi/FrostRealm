package baguchan.frostrealm.client.screen;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.aurorapower.AuroraPower;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.data.resource.FrostDimensions;
import baguchan.frostrealm.menu.AuroraInfuserMenu;
import baguchan.frostrealm.registry.AuroraPowers;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AuroraInfuserScreen extends AbstractContainerScreen<AuroraInfuserMenu> {
    private static final Identifier[] ENABLED_LEVEL_SPRITES = new Identifier[]{Identifier.withDefaultNamespace("container/enchanting_table/level_1"), Identifier.withDefaultNamespace("container/enchanting_table/level_2"), Identifier.withDefaultNamespace("container/enchanting_table/level_3")};
    private static final Identifier[] DISABLED_LEVEL_SPRITES = new Identifier[]{Identifier.withDefaultNamespace("container/enchanting_table/level_1_disabled"), Identifier.withDefaultNamespace("container/enchanting_table/level_2_disabled"), Identifier.withDefaultNamespace("container/enchanting_table/level_3_disabled")};
    private static final Identifier ENCHANTMENT_SLOT_DISABLED_SPRITE = Identifier.withDefaultNamespace("container/enchanting_table/enchantment_slot_disabled");
    private static final Identifier ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE = Identifier.withDefaultNamespace("container/enchanting_table/enchantment_slot_highlighted");
    private static final Identifier ENCHANTMENT_SLOT_SPRITE = Identifier.withDefaultNamespace("container/enchanting_table/enchantment_slot");
    private static final Identifier ENCHANTING_TABLE_LOCATION = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/gui/container/aurora_infuser.png");
    private static final FontDescription ALT_FONT = new FontDescription.Resource(Identifier.withDefaultNamespace("alt"));
    private static final Style ROOT_STYLE = Style.EMPTY.withFont(ALT_FONT);

    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    private final ItemStack last;

    public AuroraInfuserScreen(AuroraInfuserMenu p_98754_, Inventory p_98755_, Component p_98756_) {
        super(p_98754_, p_98755_, p_98756_);
        this.last = ItemStack.EMPTY;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent p_446670_, boolean p_434078_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        for (int k = 0; k < 3; k++) {
            double d0 = p_446670_.x() - (i + 60);
            double d1 = p_446670_.y() - (j + 14 + 19 * k);
            if (d0 >= 0.0 && d1 >= 0.0 && d0 < 108.0 && d1 < 19.0 && this.menu.clickMenuButton(this.minecraft.player, k)) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, k);
                return true;
            }
        }

        return super.mouseClicked(p_446670_, p_434078_);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, ENCHANTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        int k = this.menu.getGoldCount();

        for (int l = 0; l < 3; ++l) {
            int i1 = i + 60;
            int j1 = i1 + 20;
            int k1 = this.menu.costs[l];
            if (k1 == 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENCHANTMENT_SLOT_DISABLED_SPRITE, i1, j + 14 + 19 * l, 108, 19);
            } else {
                String s = "" + k1;
                int l1 = 86 - this.font.width(s);
                AuroraPower auroraPower = AuroraPowers.getRegistry().byId(this.menu.auroraClue[l]);

                FormattedText formattedtext;

                if (auroraPower == null) {
                    formattedtext = Component.literal("???");
                } else {
                    formattedtext = auroraPower.getFullnameWithEnglish(this.menu.levelClue[l]).copy().withStyle(ROOT_STYLE);
                }

                int i2 = 6839882;
                if (this.minecraft.player.level().dimension() == FrostDimensions.FROSTREALM_LEVEL && (k >= l + 1 && FrostWeatherManager.getAuroraLevel() >= k1 * 0.01F || this.minecraft.player.getAbilities().instabuild) && this.menu.levelClue[l] != -1) {
                    int j2 = mouseX - (i + 60);
                    int k2 = mouseY - (j + 14 + 19 * l);
                    if (j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                        i2 = 16777088;
                    } else {
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENCHANTMENT_SLOT_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                    }

                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENABLED_LEVEL_SPRITES[l], i1 + 1, j + 15 + 19 * l, 16, 16);
                    graphics.textWithWordWrap(this.font, formattedtext, j1, j + 16 + 19 * l, l1, i2);
                    i2 = 8453920;
                } else {
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENCHANTMENT_SLOT_DISABLED_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, DISABLED_LEVEL_SPRITES[l], i1 + 1, j + 15 + 19 * l, 16, 16);
                    graphics.textWithWordWrap(this.font, formattedtext, j1, j + 16 + 19 * l, l1, (i2 & 16711422) >> 1);
                    i2 = 4226832;
                }

                graphics.text(this.font, s, j1 + 86 - this.font.width(s), j + 16 + 19 * l + 7, i2);
            }
        }

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        a = this.minecraft.getDeltaTracker().getGameTimeDeltaTicks();
        this.extractTooltip(graphics, mouseX, mouseY);
        boolean flag = this.minecraft.player.getAbilities().instabuild;
        int i = this.menu.getGoldCount();

        for (int j = 0; j < 3; ++j) {
            int k = this.menu.costs[j];
            AuroraPower auroraPower = AuroraPowers.getRegistry().byId(this.menu.auroraClue[j]);
            int l = this.menu.levelClue[j];
            int i1 = j + 1;
            if (this.isHovering(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && k > 0) {
                List<Component> list = Lists.newArrayList();
                if (this.minecraft.player.level().dimension() != FrostDimensions.FROSTREALM_LEVEL) {
                    list.add(Component.literal(""));
                    list.add(Component.translatable("container.frostrealm.aurora_infuser.no_aurora").withStyle(ChatFormatting.RED));
                } else {
                    list.add(Component.translatable("container.frostrealm.aurora_infuser.clue", auroraPower == null ? "" : auroraPower.getFullname(l)).withStyle(ChatFormatting.WHITE));
                }
                if (auroraPower == null) {
                    list.add(Component.literal(""));
                    list.add(Component.translatable("container.frostrealm.aurora_infuser.limitedEnchantability").withStyle(ChatFormatting.RED));
                } else if (!flag) {
                    list.add(CommonComponents.EMPTY);
                    if (FrostWeatherManager.getAuroraLevel() < k * 0.01F) {
                        list.add(Component.translatable("container.frostrealm.aurora_infuser.level.requirement_aurora", this.menu.costs[j]).withStyle(ChatFormatting.RED));
                    } else {
                        MutableComponent mutablecomponent;
                        if (i1 == 1) {
                            mutablecomponent = Component.translatable("container.frostrealm.aurora_infuser.stardust_crystal.one");
                        } else {
                            mutablecomponent = Component.translatable("container.frostrealm.aurora_infuser.stardust_crystal.many", i1);
                        }

                        list.add(mutablecomponent.withStyle(i >= i1 ? ChatFormatting.GRAY : ChatFormatting.RED));
                        MutableComponent mutablecomponent1;
                        if (i1 == 1) {
                            mutablecomponent1 = Component.translatable("container.enchant.level.one");
                        } else {
                            mutablecomponent1 = Component.translatable("container.enchant.level.many", i1);
                        }

                        list.add(mutablecomponent1.withStyle(ChatFormatting.GRAY));
                    }
                }

                graphics.setComponentTooltipForNextFrame(this.font, list, mouseX, mouseY);
                break;
            }
        }
    }
}
