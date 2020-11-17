package cat.yoink.xanax.main.module.modules.movement;

import cat.yoink.xanax.main.event.PacketEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class Velocity extends Module
{
    private final BooleanSetting velocity = addSetting(new BooleanSetting("Velocity", true));
    private final BooleanSetting explosions = addSetting(new BooleanSetting("Explosions", true));

    public Velocity()
    {
        super("Velocity", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event)
    {
        if (nullCheck()) return;

        if (event.getPacket() instanceof SPacketEntityVelocity && velocity.getValue() && ((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
        {
            ((SPacketEntityVelocity) event.getPacket()).motionX = 0;
            ((SPacketEntityVelocity) event.getPacket()).motionY = 0;
            ((SPacketEntityVelocity) event.getPacket()).motionZ = 0;
        }

        if (event.getPacket() instanceof SPacketExplosion && explosions.getValue())
        {
            ((SPacketExplosion) event.getPacket()).motionX = 0;
            ((SPacketExplosion) event.getPacket()).motionY = 0;
            ((SPacketExplosion) event.getPacket()).motionZ = 0;
        }
    }
}
