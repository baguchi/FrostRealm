package baguchan.frostrealm.command;

import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.message.ChangedColdMessage;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;

public class TemperatureCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        LiteralArgumentBuilder<CommandSourceStack> temperatureCommand = Commands.literal("temperature")
                .requires(player -> player.hasPermission(2));
        temperatureCommand.then(Commands.argument("target", EntityArgument.entity()).then(Commands.argument("temperature", IntegerArgumentType.integer()).then(Commands.argument("saturation", FloatArgumentType.floatArg()).executes((ctx) -> {
            return setTemperature(ctx.getSource(), EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "temperature"), FloatArgumentType.getFloat(ctx, "saturation"));
        }))));
        dispatcher.register(temperatureCommand);
    }

    private static int setTemperature(CommandSourceStack commandStack, Entity entity, int temperature, float saturation) {

        if (entity != null) {
            if (entity instanceof LivingEntity) {
                FrostLivingCapability frostLivingCapability = FrostLivingCapability.get(entity);
                    frostLivingCapability.setTemperatureLevel(temperature);
                    frostLivingCapability.setSaturation(saturation);

                ChangedColdMessage message = new ChangedColdMessage(entity, temperature, saturation);
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, message);


                commandStack.sendSuccess(() -> Component.translatable("commands.frostrelam.temperature.set", entity.getDisplayName()), true);
                return 1;
            } else {
                commandStack.sendFailure(Component.translatable("commands.frostrelam.temperature.set.fail.no_living_entity", entity.getDisplayName()));

                return 0;
            }
        } else {
            commandStack.sendFailure(Component.translatable("commands.frostrelam.temperature.set.fail.no_entity"));

            return 0;
        }
    }
}