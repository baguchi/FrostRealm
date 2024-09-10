package baguchan.frostrealm.data;

import baguchan.frostrealm.client.model.item.AuroraGeometryLoader;
import com.google.gson.JsonObject;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AuroraLoaderBuilder extends CustomLoaderBuilder<ItemModelBuilder> {
    public AuroraLoaderBuilder(ItemModelBuilder parent, ExistingFileHelper existingFileHelper) {
        super(
                // Your model loader's id.
                AuroraGeometryLoader.ID,
                // The parent builder we use. This is always the first constructor parameter.
                parent,
                // The existing file helper we use. This is always the second constructor parameter.
                existingFileHelper,
                // Whether the loader allows inline vanilla elements as a fallback if the loader is absent.
                true
        );
    }

    // Add fields and setters for the fields here. The fields can then be used below.

    // Serialize the model to JSON.
    @Override
    public JsonObject toJson(JsonObject json) {
        // Add your fields to the given JsonObject.
        // Then call super, which adds the loader property and some other things.
        return super.toJson(json);
    }
}