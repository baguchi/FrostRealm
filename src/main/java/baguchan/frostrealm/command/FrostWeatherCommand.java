package baguchan.frostrealm.command;

import baguchan.frostrealm.capability.FrostWeatherSavedData;
import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.weather.FrostWeather;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;

public class FrostWeatherCommand {

	private static final DynamicCommandExceptionType ERROR_INVALID_FEATURE = new DynamicCommandExceptionType(
			p_212392_ -> Component.translatable("commands.frostrealm.frost_weather.set.fail")
	);

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

		LiteralArgumentBuilder<CommandSourceStack> frostWeatherCommand = Commands.literal("frost_weather")
				.requires(player -> player.hasPermission(2));

		frostWeatherCommand.then(Commands.literal("clear").executes((p_139190_) -> {
			return setClear(p_139190_.getSource(), 6000);
		}).then(Commands.argument("duration", IntegerArgumentType.integer(0, 1000000)).executes((p_139188_) -> {
			return setClear(p_139188_.getSource(), IntegerArgumentType.getInteger(p_139188_, "duration") * 20);
		}))).then(Commands.argument("set_weather", ResourceKeyArgument.key(FrostWeathers.FROST_WEATHER.getRegistryKey()))
				.executes((ctx) -> {
					return setRain(getFrostWeather(ctx, "set_weather"), ctx.getSource(), 6000);
				}).then(Commands.argument("duration", IntegerArgumentType.integer(0, 1000000)).executes((ctx) -> {
					return setRain(getFrostWeather(ctx, "set_weather"), ctx.getSource(), IntegerArgumentType.getInteger(ctx, "duration") * 20);
				})));

		dispatcher.register(frostWeatherCommand);
	}

	public static Holder.Reference<FrostWeather> getFrostWeather(CommandContext<CommandSourceStack> p_249310_, String p_250729_) throws CommandSyntaxException {
		return resolveKey(p_249310_, p_250729_, FrostWeathers.WEATHER_RESOURCE_KEY, ERROR_INVALID_FEATURE);
	}

	private static <T> Registry<T> getRegistry(CommandContext<CommandSourceStack> p_212379_, ResourceKey<? extends Registry<T>> p_212380_) {
		return p_212379_.getSource().getServer().registryAccess().registryOrThrow(p_212380_);
	}

	private static <T> ResourceKey<T> getRegistryKey(
			CommandContext<CommandSourceStack> p_212374_, String p_212375_, ResourceKey<Registry<T>> p_212376_, DynamicCommandExceptionType p_212377_
	) throws CommandSyntaxException {
		ResourceKey<?> resourcekey = p_212374_.getArgument(p_212375_, ResourceKey.class);
		Optional<ResourceKey<T>> optional = resourcekey.cast(p_212376_);
		return optional.orElseThrow(() -> p_212377_.create(resourcekey));
	}


	private static <T> Holder.Reference<T> resolveKey(
			CommandContext<CommandSourceStack> p_248662_, String p_252172_, ResourceKey<Registry<T>> p_249701_, DynamicCommandExceptionType p_249790_
	) throws CommandSyntaxException {
		ResourceKey<T> resourcekey = getRegistryKey(p_248662_, p_252172_, p_249701_, p_249790_);
		return getRegistry(p_248662_, p_249701_).getHolder(resourcekey).orElseThrow(() -> p_249790_.create(resourcekey.location()));
	}

	private static int setClear(CommandSourceStack p_139173_, int p_139174_) {
		if (p_139173_.getLevel().dimension() != FrostDimensions.FROSTREALM_LEVEL) {
			p_139173_.sendFailure(Component.translatable("commands.frostrealm.frost_weather.clear.fail_dimension"));
			return p_139174_;
		}
		FrostWeatherSavedData cap = FrostWeatherSavedData.get(p_139173_.getLevel());
		if (cap != null) {
			cap.setFrostWeather(FrostWeathers.NOPE.get());
			cap.setWetherTime(p_139174_);
			ChangeWeatherMessage message = new ChangeWeatherMessage(FrostWeathers.NOPE.get());
            PacketDistributor.sendToPlayersInDimension(p_139173_.getLevel(), message);

			p_139173_.sendSuccess(() -> Component.translatable("commands.frostrealm.frost_weather.clear"), true);
		}
		return p_139174_;
	}

	private static int setRain(Holder.Reference<FrostWeather> holder, CommandSourceStack p_139178_, int p_139179_) {
		FrostWeather frostWeather = holder.value();
		if (p_139178_.getLevel().dimension() != FrostDimensions.FROSTREALM_LEVEL) {
			p_139178_.sendFailure(Component.translatable("commands.frostrealm.frost_weather.set.fail_dimension"));
			return p_139179_;
		}
		FrostWeatherSavedData cap = FrostWeatherSavedData.get(p_139178_.getLevel());
				if (cap != null) {
					cap.setFrostWeather(frostWeather);

					cap.setWetherTime(p_139179_);
					ChangeWeatherMessage message = new ChangeWeatherMessage(frostWeather);
                    PacketDistributor.sendToPlayersInDimension(p_139178_.getLevel(), message);
					p_139178_.sendSuccess(() -> Component.translatable("commands.frostrealm.frost_weather.set"), true);
					return p_139179_;
				}

		return p_139179_;
	}
}