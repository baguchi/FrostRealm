package baguchan.frostrealm.client.screen;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.frost_archive.FrostArchive;
import baguchan.frostrealm.registry.FrostArchives;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public class FrostArchiveScreen extends Screen {
    public static final int PAGE_INDICATOR_TEXT_Y_OFFSET = 16;
    public static final int PAGE_TEXT_X_OFFSET = 36;
    public static final int PAGE_TEXT_Y_OFFSET = 30;
    public static final ResourceLocation BOOK_LOCATION = new ResourceLocation(FrostRealm.MODID, "textures/gui/frost_archive.png");
    protected static final int TEXT_WIDTH = 114;
    protected static final int TEXT_HEIGHT = 128;
    protected static final int IMAGE_WIDTH = 256;
    protected static final int IMAGE_HEIGHT = 192;
    private int currentPage;
    private List<FormattedCharSequence> cachedPageComponents = Collections.emptyList();
    private int cachedPage = -1;

    private int currentCategoryPage;
    private Set<ResourceLocation> cachedCategoryPageId = Set.of();

    private Component pageMsg = CommonComponents.EMPTY;
    private PageButton forwardButton;
    private PageButton backButton;
    private PageButton forwardCategoryButton;
    private PageButton backCategoryButton;
    private FrostArchive book;

    public FrostArchiveScreen(FrostArchive p_98266_) {
        super(GameNarrator.NO_TITLE);
        this.cachedCategoryPageId = FrostArchives.getRegistry().get().getKeys();
        this.currentCategoryPage = Mth.clamp(this.currentCategoryPage, 0, this.cachedCategoryPageId.size());
        this.book = p_98266_;
        this.currentPage = Mth.clamp(this.currentPage, 0, p_98266_.getPageCount());
    }

    public void setBookArchive(FrostArchive p_98289_) {
        this.book = p_98289_;
        this.currentPage = Mth.clamp(this.currentPage, 0, p_98289_.getPageCount());
        this.updateButtonVisibility();
        this.cachedPage = -1;
    }

    public boolean setPage(int p_98276_) {
        int i = Mth.clamp(p_98276_, 0, this.book.getPageCount() - 1);
        if (i != this.currentPage) {
            this.currentPage = i;
            this.updateButtonVisibility();
            this.cachedPage = -1;
            return true;
        } else {
            return false;
        }
    }

    protected boolean forcePage(int p_98295_) {
        return this.setPage(p_98295_);
    }

    protected void init() {
        this.createMenuControls();
        this.createPageControlButtons();
    }

    protected void createMenuControls() {
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (p_275866_) -> {
            this.onClose();
        }).bounds(this.width / 2 - 100, 196, 200, 20).build());
    }

    protected void createPageControlButtons() {
        int i = (this.width / 2);
        int j = 2;
        this.forwardButton = this.addRenderableWidget(new PageButton((int) (i + (IMAGE_WIDTH / 3.5)) + 12, IMAGE_HEIGHT - 16, true, (p_98297_) -> {
            this.pageForward();
        }, true));
        this.backButton = this.addRenderableWidget(new PageButton((int) (i - (IMAGE_WIDTH / 3.5)) - 12, IMAGE_HEIGHT - 16, false, (p_98287_) -> {
            this.pageBack();
        }, true));
        this.forwardCategoryButton = this.addRenderableWidget(new PageButton((int) (i + (IMAGE_WIDTH / 2.5F)) + 12, IMAGE_HEIGHT - 16, true, (p_98297_) -> {
            this.pageCategoryForward();
        }, true));
        this.backCategoryButton = this.addRenderableWidget(new PageButton((int) (i - (IMAGE_WIDTH / 2.5)) - 12, IMAGE_HEIGHT - 16, false, (p_98287_) -> {
            this.pageCategoryBack();
        }, true));

        this.updateButtonVisibility();
    }

    private int getNumPages() {
        return this.book.getPageCount();
    }

    private int getNumCategoryPages() {
        return this.cachedCategoryPageId.size();
    }

    protected void pageCategoryBack() {
        if (this.currentCategoryPage > 0) {
            --this.currentCategoryPage;
        }
        this.updateCategoryPage();
        this.updateButtonVisibility();

    }

    protected void pageCategoryForward() {
        if (this.currentCategoryPage < this.getNumCategoryPages() - 1) {
            ++this.currentCategoryPage;
        }
        this.updateCategoryPage();
        this.updateButtonVisibility();
    }

    protected void pageBack() {
        if (this.currentPage > 0) {
            --this.currentPage;
        }

        this.updateButtonVisibility();
    }

    protected void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) {
            ++this.currentPage;
        }
        this.updateButtonVisibility();
    }

    private void updateCategoryPage() {
        this.currentPage = 0;
        FrostArchive archive = FrostArchives.getRegistry().get().getValues().stream().toList().get(this.currentCategoryPage);
        if (archive != null) {
            this.setBookArchive(archive);
        }
    }

    private void updateButtonVisibility() {
        this.forwardButton.visible = this.currentPage < this.getNumPages() - 1;
        this.backButton.visible = this.currentPage > 0;
        this.forwardCategoryButton.visible = this.currentCategoryPage < this.getNumCategoryPages() - 1;
        this.backCategoryButton.visible = this.currentCategoryPage > 0;
    }

    public boolean keyPressed(int p_98278_, int p_98279_, int p_98280_) {
        if (super.keyPressed(p_98278_, p_98279_, p_98280_)) {
            return true;
        } else {
            switch (p_98278_) {
                case 266:
                    this.backButton.onPress();
                    return true;
                case 267:
                    this.forwardButton.onPress();
                    return true;
                default:
                    return false;
            }
        }
    }

    public void render(PoseStack p_98282_, int p_98283_, int p_98284_, float p_98285_) {
        this.renderBackground(p_98282_);
        RenderSystem.setShaderTexture(0, BOOK_LOCATION);
        int i = (this.width - IMAGE_WIDTH) / 2;
        int j = 2;
        blit(p_98282_, i, 2, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);


        if (this.cachedPage != this.currentPage) {
            FormattedText formattedtext = Component.translatable(this.book.getPageId() + ".page." + this.currentPage);
            this.cachedPageComponents = this.font.split(formattedtext, 114);
            this.pageMsg = Component.translatable("book.pageIndicator", this.currentPage + 1, Math.max(this.getNumPages(), 1));
        }

        this.cachedPage = this.currentPage;
        int i1 = this.font.width(this.pageMsg);
        this.font.draw(p_98282_, this.pageMsg, (float) (i - i1 + IMAGE_WIDTH - 44), 18.0F, 0);
        int k = Math.min(128 / 9, this.cachedPageComponents.size());

        for (int l = 0; l < k; ++l) {
            FormattedCharSequence formattedcharsequence = this.cachedPageComponents.get(l);
            this.font.draw(p_98282_, formattedcharsequence, (float) (i + (IMAGE_WIDTH / 2) + 8), (float) (32 + l * 9), 0);
        }

        Style style = this.getClickedComponentStyleAt((double) p_98283_, (double) p_98284_);
        if (style != null) {
            this.renderComponentHoverEffect(p_98282_, style, p_98283_, p_98284_);
        }

        Component formattedtext = Component.translatable(this.book.getPageId());

        this.font.draw(p_98282_, formattedtext, (float) (i + 8), (float) 32, 0);


        if (this.book != null) {
            this.book.renderPage(this.book.getPageCount(), p_98282_, (float) (i), 32);
        }

        super.render(p_98282_, p_98283_, p_98284_, p_98285_);
    }

    public boolean mouseClicked(double p_98272_, double p_98273_, int p_98274_) {
        if (p_98274_ == 0) {
            Style style = this.getClickedComponentStyleAt(p_98272_, p_98273_);
            if (style != null && this.handleComponentClicked(style)) {
                return true;
            }
        }

        return super.mouseClicked(p_98272_, p_98273_, p_98274_);
    }

    public boolean handleComponentClicked(Style p_98293_) {
        ClickEvent clickevent = p_98293_.getClickEvent();
        if (clickevent == null) {
            return false;
        } else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String s = clickevent.getValue();

            try {
                int i = Integer.parseInt(s) - 1;
                return this.forcePage(i);
            } catch (Exception exception) {
                return false;
            }
        } else {
            boolean flag = super.handleComponentClicked(p_98293_);
            if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.closeScreen();
            }

            return flag;
        }
    }

    protected void closeScreen() {
        this.minecraft.setScreen((Screen) null);
    }

    @Nullable
    public Style getClickedComponentStyleAt(double p_98269_, double p_98270_) {
        if (this.cachedPageComponents.isEmpty()) {
            return null;
        } else {
            int i = Mth.floor(p_98269_ - (double) ((this.width - IMAGE_WIDTH) / 2) - 36.0D);
            int j = Mth.floor(p_98270_ - 2.0D - 30.0D);
            if (i >= 0 && j >= 0) {
                int k = Math.min(128 / 9, this.cachedPageComponents.size());
                if (i <= 114 && j < 9 * k + k) {
                    int l = j / 9;
                    if (l >= 0 && l < this.cachedPageComponents.size()) {
                        FormattedCharSequence formattedcharsequence = this.cachedPageComponents.get(l);
                        return this.minecraft.font.getSplitter().componentStyleAtWidth(formattedcharsequence, i);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    static List<String> loadPages(CompoundTag p_169695_) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        loadPages(p_169695_, builder::add);
        return builder.build();
    }

    public static void loadPages(CompoundTag p_169697_, Consumer<String> p_169698_) {
        ListTag listtag = p_169697_.getList("pages", 8).copy();
        IntFunction<String> intfunction;
        if (Minecraft.getInstance().isTextFilteringEnabled() && p_169697_.contains("filtered_pages", 10)) {
            CompoundTag compoundtag = p_169697_.getCompound("filtered_pages");
            intfunction = (p_169702_) -> {
                String s = String.valueOf(p_169702_);
                return compoundtag.contains(s) ? compoundtag.getString(s) : listtag.getString(p_169702_);
            };
        } else {
            intfunction = listtag::getString;
        }

        for (int i = 0; i < listtag.size(); ++i) {
            p_169698_.accept(intfunction.apply(i));
        }

    }
}