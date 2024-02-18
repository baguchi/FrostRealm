package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import net.minecraft.Util;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT)
public class ClientEvents {
    protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    public static final DecimalFormat ATTRIBUTE_MODIFIER_FORMAT = Util.make(new DecimalFormat("#.##"), (p_41704_) -> {
        p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });

    @SubscribeEvent
    public static void onAuroraToolTip(ItemTooltipEvent event) {
        AuroraPowerUtils.getAuroraPowers(event.getItemStack()).forEach((auroraPower, integer) -> {

            event.getToolTip().add(AuroraPowerUtils.auroraPowerNameWithLevel(auroraPower, integer));
        });
    }
}
