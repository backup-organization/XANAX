package cat.yoink.xanax.main.module.modules.combat;

import cat.yoink.xanax.main.event.PacketEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.EnumSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public final class Criticals extends Module
{
    private final BooleanSetting setting1 = addSetting(new BooleanSetting("Test!", true));
    private final BooleanSetting setting2 = addSetting(new BooleanSetting("Test2", true));
    private final BooleanSetting setting3 = addSetting(new BooleanSetting("Test3", true));
    private final BooleanSetting setting4 = addSetting(new BooleanSetting("Test4", true));
    private final BooleanSetting setting5 = addSetting(new BooleanSetting("5", true));
    private final BooleanSetting setting6 = addSetting(new BooleanSetting("Test6", true));
    private final NumberSetting setting62 = addSetting(new NumberSetting("Test!", 5, 2, 8, 0.5));
    private final EnumSetting setting43 = addSetting(new EnumSetting("Test!", "Two", "One", "Two", "Three"));
    private final NumberSetting newds = addSetting(new NumberSetting("number", 3.4, 1.1, 9.4, 0.1));

    public Criticals()
    {
        super("Criticals", Category.COMBAT);
        setBind(Keyboard.KEY_G);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event)
    {
        if (nullCheck() || event.getType() == PacketEvent.Type.INCOMING || !(event.getPacket() instanceof CPacketUseEntity) || ((CPacketUseEntity) event.getPacket()).getAction() != CPacketUseEntity.Action.ATTACK || !mc.player.onGround) return;

        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
    }
}
