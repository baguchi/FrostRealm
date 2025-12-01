package baguchan.frostrealm.client;

import baguchan.frostrealm.client.render.dimension.FrostRealmRenderer;
import baguchan.frostrealm.client.render.dimension.FrostRealmWeatherSpecialRender;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import javax.annotation.Nullable;

public class FrostRealmTextureManager implements ResourceManagerReloadListener {
    public static final FrostRealmTextureManager INSTANCE = new FrostRealmTextureManager();


    private @Nullable FrostRealmRenderer frostrealmRenderer;
    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        frostrealmRenderer = new FrostRealmRenderer(Minecraft.getInstance().getAtlasManager());
    }

    public @Nullable FrostRealmRenderer getFrostrealmRenderer() {
        return frostrealmRenderer;
    }
}