package baguchan.frostrealm.command;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.message.ChangeWeatherEvent;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.weather.FrostWeather;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.PacketDistributor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FrostWeatherCommand {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

		LiteralArgumentBuilder<CommandSourceStack> frostWeatherCommand = Commands.literal("frost_weather")
				.requires(player -> player.hasPermission(2));

		frostWeatherCommand.then(Commands.literal("clear").executes((p_139190_) -> {
			return setClear(p_139190_.getSource(), 6000);
		}).then(Commands.argument("duration", IntegerArgumentType.integer(0, 1000000)).executes((p_139188_) -> {
			return setClear(p_139188_.getSource(), IntegerArgumentType.getInteger(p_139188_, "duration") * 20);
		}))).then(Commands.argument("set_weather", StringArgumentType.string())
				.suggests((ctx, builder) -> SharedSuggestionProvider.suggest(FrostWeathers.getRegistry().get().getKeys().stream().map(ResourceLocation::toString).map(StringArgumentType::escapeIfRequired), builder))
				.executes((ctx) -> {
					return setRain(StringArgumentType.getString(ctx, "set_weather"), ctx.getSource(), 6000);
				}).then(Commands.argument("duration", IntegerArgumentType.integer(0, 1000000)).executes((ctx) -> {
					return setRain(StringArgumentType.getString(ctx, "set_weather"), ctx.getSource(), IntegerArgumentType.getInteger(ctx, "duration") * 20);
				})));

		dispatcher.register(frostWeatherCommand);
	}

	private static int setClear(CommandSourceStack p_139173_, int p_139174_) {
		if (p_139173_.getLevel().dimension() != FrostDimensions.FROSTREALM_LEVEL) {
			p_139173_.sendFailure(Component.translatable("commands.frostrealm.frost_weather.clear.fail_dimension"));
			return p_139174_;
		}
		p_139173_.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
			cap.setWetherTime(0);
			cap.setWeatherCooldown(p_139174_);
			cap.needWeatherCooldownChanged = true;
		});
		p_139173_.sendSuccess(Component.translatable("commands.frostrealm.frost_weather.clear"), true);

		return p_139174_;
	}

	private static int setRain(String filter, CommandSourceStack p_139178_, int p_139179_) {
		Set<ResourceLocation> names = FrostWeathers.getRegistry().get().getKeys().stream().filter(n -> n.toString().matches(filter)).collect(Collectors.toSet());

		if (p_139178_.getLevel().dimension() != FrostDimensions.FROSTREALM_LEVEL) {
			p_139178_.sendFailure(Component.translatable("commands.frostrealm.frost_weather.set.fail_dimension"));
			return p_139179_;
		}

		if (names.isEmpty()) {
			p_139178_.sendFailure(Component.translatable("commands.frostrealm.frost_weather.set.fail"));
			return p_139179_;
		}


		if (names.size() == 1) {
			ResourceLocation name = names.iterator().next();
			Optional<FrostWeather> frostWeather = FrostWeathers.getRegistry().get().getValues().stream().filter(weather -> {
				return FrostWeathers.getRegistry().get().getKey(weather).equals(name);
			}).findFirst();

			if (frostWeather.isPresent()) {
				p_139178_.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
					cap.setFrostWeather(frostWeather.get());

					ChangeWeatherEvent message = new ChangeWeatherEvent(frostWeather.get());
					FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);

					cap.setWetherTime(p_139179_);
					cap.setWeatherCooldown(0);
					cap.needWeatherChanged = true;
				});
				p_139178_.sendSuccess(Component.translatable("commands.frostrealm.frost_weather.set"), true);
				return p_139179_;
			} else {
				p_139178_.sendFailure(Component.translatable("commands.frostrealm.frost_weather.set.fail"));
				return p_139179_;
			}
		}

		return p_139179_;
	}
}