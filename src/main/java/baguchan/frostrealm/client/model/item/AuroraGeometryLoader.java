package baguchan.frostrealm.client.model.item;

import baguchan.frostrealm.FrostRealm;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.ItemLayerModel;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;

public class AuroraGeometryLoader implements IGeometryLoader<AuroraGeometry> {
    // It is highly recommended to use a singleton pattern for geometry loaders, as all models can be loaded through one loader.
    public static final AuroraGeometryLoader INSTANCE = new AuroraGeometryLoader();
    // The id we will use to register this loader. Also used in the loader datagen class.
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "aurora_loader");

    // In accordance with the singleton pattern, make the constructor private.
    private AuroraGeometryLoader() {
    }

    @Override
    public AuroraGeometry read(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
        // Use the given JsonObject and, if needed, the JsonDeserializationContext to get properties from the model JSON.
        // The AuroraGeometry constructor may have constructor parameters (see below).
        jsonObject.remove("loader");
        ItemLayerModel base = context.deserialize(jsonObject, ItemLayerModel.class);
        return new AuroraGeometry(base);
    }
}

