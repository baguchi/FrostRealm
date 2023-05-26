package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.brain.sensor.EnemySensor;
import baguchan.frostrealm.entity.brain.sensor.FrostBoarSensor;
import baguchan.frostrealm.entity.brain.sensor.YetiSensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostSensors {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, FrostRealm.MODID);

    public static final RegistryObject<SensorType<FrostBoarSensor>> FROST_BOAR_SENSOR = SENSOR_TYPES.register("frost_boar_sensor",
            () -> new SensorType<>(FrostBoarSensor::new));
    public static final RegistryObject<SensorType<YetiSensor>> YETI_SENSOR = SENSOR_TYPES.register("yeti_sensor",
            () -> new SensorType<>(YetiSensor::new));
    public static final RegistryObject<SensorType<EnemySensor>> ENEMY_SENSOR = SENSOR_TYPES.register("enemy_sensor",
            () -> new SensorType<>(EnemySensor::new));
}