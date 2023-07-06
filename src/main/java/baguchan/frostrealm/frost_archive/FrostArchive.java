package baguchan.frostrealm.frost_archive;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class FrostArchive {
    public final String pageId;
    public final int page;
    public final Optional<ResourceLocation> pageImage;

    public FrostArchive(String pageId, int page, Optional<ResourceLocation> pageImage) {
        this.pageId = pageId;
        this.page = page;
        this.pageImage = pageImage;
    }

    public String getPageId() {
        return pageId;
    }

    public int getPageCount() {
        return page;
    }

    public Optional<ResourceLocation> getPageImage() {
        return pageImage;
    }

    public void renderPage(int pageCount, GuiGraphics poseStack, float x, int y) {
    }
}