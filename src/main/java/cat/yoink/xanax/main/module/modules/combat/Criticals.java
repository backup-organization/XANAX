package cat.yoink.xanax.main.module.modules.combat;

import cat.yoink.xanax.main.event.PacketEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Criticals extends Module
{
    public Criticals()
    {
        super("Test", Category.COMBAT);
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
