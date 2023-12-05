package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.brain.sensor.EnemySensor;
import baguchan.frostrealm.entity.brain.sensor.FrostBoarSensor;
import baguchan.frostrealm.entity.brain.sensor.YetiSensor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostSensors {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(BuiltInRegistries.SENSOR_TYPE, FrostRealm.MODID);

    public static final Supplier<SensorType<FrostBoarSensor>> FROST_BOAR_SENSOR = SENSOR_TYPES.register("frost_boar_sensor",
            () -> new SensorType<>(FrostBoarSensor::new));
    public static final Supplier<SensorType<YetiSensor>> YETI_SENSOR = SENSOR_TYPES.register("yeti_sensor",
            () -> new SensorType<>(YetiSensor::new));
    public static final Supplier<SensorType<EnemySensor>> ENEMY_SENSOR = SENSOR_TYPES.register("enemy_sensor",
            () -> new SensorType<>(EnemySensor::new));
}