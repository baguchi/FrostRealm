package baguchan.frostrealm.client;

import net.minecraft.resources.ResourceLocation;

public class AuroraPowerClientHandler {
	public static ResourceLocation[] auroraIDs = new ResourceLocation[]{null, null, null};

	public static void setAuroraIDs(ResourceLocation[] currentsAuroraIDs) {
		auroraIDs = currentsAuroraIDs;
	}

	public static ResourceLocation[] getAuroraIDs() {
		return auroraIDs;
	}
}
