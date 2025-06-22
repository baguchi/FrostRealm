package baguchan.frostrealm.registry;

import net.minecraft.world.level.block.state.properties.WoodType;

public class FrostWoodTypes {
    public static final WoodType FROSTROOT = WoodType.register(new WoodType("frostrealm:frostroot", FrostBlockSetTypes.FROSTROOT));
    public static final WoodType FROSTBITE = WoodType.register(new WoodType("frostrealm:frostbite", FrostBlockSetTypes.FROSTBITE));
    public static final WoodType DRIP = WoodType.register(new WoodType("frostrealm:drip", FrostBlockSetTypes.DRIP));
    public static final WoodType ROCK_WOOD = WoodType.register(new WoodType("frostrealm:rock_wood", FrostBlockSetTypes.FROSTBITE));

    public static void init() {

    }
}