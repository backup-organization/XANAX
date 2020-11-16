package cat.yoink.xanax.main.module.modules.misc;

import cat.yoink.xanax.main.event.PacketEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.EnumSetting;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class Swing extends Module
{
    private final EnumSetting mode = addSetting(new EnumSetting("Hand", "None", "MainHand", "Offhand", "None"));

    public Swing()
    {
        super("Swing", Category.MISC);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event)
    {
        if (nullCheck() || event.getType().equals(PacketEvent.Type.INCOMING) || !(event.getPacket() instanceof CPacketAnimation)) return;

        if (mode.getValue().equalsIgnoreCase("None")) event.setCanceled(true);

        ((CPacketAnimation) event.getPacket()).hand = mode.is("MainHand") ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
    }
}
