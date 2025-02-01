package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.api.recipe.AttachableCrystal;
import baguchan.frostrealm.data.resource.FrostDimensions;
import baguchan.frostrealm.data.resource.registries.AttachableCrystals;
import baguchan.frostrealm.registry.FrostDataCompnents;
import baguchan.frostrealm.registry.FrostSounds;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.sounds.MusicInfo;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT)
public class ClientEvents {

    public static final Music CALM_NIGHT = createFrostMusic(FrostSounds.CALM_NIGHT_BGM);


    protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    public static final DecimalFormat ATTRIBUTE_MODIFIER_FORMAT = Util.make(new DecimalFormat("#.##"), (p_41704_) -> {
        p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });

    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event) {
        AuroraPowerUtils.getAuroraPowers(event.getItemStack()).addToTooltip(event.getContext(), component -> {
            event.getToolTip().add(component);
        }, TooltipFlag.NORMAL);
        @Nullable Holder<AttachableCrystal> attachableCrystal = event.getItemStack().get(FrostDataCompnents.ATTACH_CRYSTAL);
        int damage = event.getItemStack().getOrDefault(FrostDataCompnents.CRYSTAL_USED, 0);

        if (attachableCrystal != null) {
            int damage2 = (attachableCrystal.value().getUse() - damage);
            if (event.getItemStack().getItem() instanceof ArrowItem) {
                event.getToolTip().add(Component.translatable(Util.makeDescriptionId("attach_crystal", event.getContext().registries().lookup(AttachableCrystals.ATTACHABLE_CRYSTAL_REGISTRY_KEY).get().getOrThrow(attachableCrystal.getKey()).getKey().location())));
            } else {
                event.getToolTip().add(Component.translatable(Util.makeDescriptionId("attach_crystal", event.getContext().registries().lookup(AttachableCrystals.ATTACHABLE_CRYSTAL_REGISTRY_KEY).get().getOrThrow(attachableCrystal.getKey()).getKey().location()))
                        .append(" ").append(damage2 + " / " + attachableCrystal.value().getUse()));
            }
        }
    }

    //handle frostreallam music
    @SubscribeEvent
    public static void onMusicPlayed(SelectMusicEvent event) {

        if (Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) {
            Holder<Biome> biome = Minecraft.getInstance().player.level().getBiome(Minecraft.getInstance().player.blockPosition());
            float volume = biome.value().getBackgroundMusicVolume();
            if (Minecraft.getInstance().level.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
                Optional<SimpleWeightedRandomList<Music>> musicInfo = biome.value().getBackgroundMusic();
                if (!(Minecraft.getInstance().screen instanceof WinScreen)) {
                    long time = Minecraft.getInstance().player.clientLevel.getLevelData().getDayTime() % 24000L;
                    boolean day = time >= 0 && time < 12000;
                    boolean sunset = time >= 12000 && time < 14000;
                    boolean night = time >= 14000 && time < 22000;
                    boolean sunrise = time >= 22000;
                    if (night) {
                        event.setMusic(new MusicInfo(CALM_NIGHT, volume));
                    } else {
                        if (musicInfo.isPresent()) {
                            Optional<Music> music = musicInfo.get().getRandomValue(Minecraft.getInstance().level.random);
                            if (music.isPresent()) {
                                event.setMusic(new MusicInfo(music.get()));
                            }
                        }
                    }
                }
            }
        }
    }

    public static Music createFrostMusic(Holder<SoundEvent> event) {
        return new Music(event, 3600, 10800, false);
    }
}
