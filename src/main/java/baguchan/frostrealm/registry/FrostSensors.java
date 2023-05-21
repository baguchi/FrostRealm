package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.brain.sensor.FrostBoarSensor;
import baguchan.frostrealm.entity.brain.sensor.UncomfortableSensor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostSensors {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, FrostRealm.MODID);

    public static final RegistryObject<SensorType<UncomfortableSensor<Mob>>> UNCOMFORTABLE_SENSOR = SENSOR_TYPES.register("uncomfortable_sensor",
            () -> new SensorType<>(UncomfortableSensor::new));
    public static final RegistryObject<SensorType<FrostBoarSensor>> FROST_BOAR_SENSOR = SENSOR_TYPES.register("frost_boar_sensor",
            () -> new SensorType<>(FrostBoarSensor::new));
}