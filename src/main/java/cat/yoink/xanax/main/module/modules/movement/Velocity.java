package cat.yoink.xanax.main.module.modules.movement;

import cat.yoink.xanax.main.event.events.CollisionEvent;
import cat.yoink.xanax.main.event.events.PacketEvent;
import cat.yoink.xanax.main.event.events.WaterPushEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public final class Velocity extends Module
{
    private final NumberSetting horizontal = addSetting(new NumberSetting("Horizontal", 0, 0, 100, 1));
    private final BooleanSetting velocity = addSetting(new BooleanSetting("Velocity", true));
    private final NumberSetting vertical = addSetting(new NumberSetting("Vertical", 0, 0, 100, 1));
    private final BooleanSetting explosions = addSetting(new BooleanSetting("Explosions", true));
    private final BooleanSetting fishable = addSetting(new BooleanSetting("Fishable", false));
    private final BooleanSetting noPush = addSetting(new BooleanSetting("NoPush", true));

    public Velocity()
    {
        super("Velocity", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPlayerSPPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event)
    {
        if (!nullCheck() && noPush.getValue() && event.getEntity().equals(mc.player)) event.setCanceled(true);
    }

    @Listener
    public void onWaterPush(WaterPushEvent event)
    {
        if (!nullCheck() && noPush.getValue()) event.setCancelled(true);
    }

    @Listener
    public void onCollision(CollisionEvent event)
    {
        if (!nullCheck() && noPush.getValue()) event.setCancelled(true);
    }

    @Listener
    public void onPacket(PacketEvent event)
    {
        if (nullCheck()) return;

        if (event.getPacket() instanceof SPacketEntityStatus && !fishable.getValue() && ((SPacketEntityStatus) event.getPacket()).getOpCode() == 31 && ((SPacketEntityStatus) event.getPacket()).getEntity(mc.world) instanceof EntityFishHook && ((EntityFishHook) ((SPacketEntityStatus) event.getPacket()).getEntity(mc.world)).caughtEntity.equals(mc.player))
        {
            event.setCancelled(true);
        }

        if (event.getPacket() instanceof SPacketEntityVelocity && velocity.getValue() && ((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
        {
            SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();

            packet.motionX = packet.motionX / 100 * (int) horizontal.getValue();
            packet.motionY = packet.motionY / 100 * (int) vertical.getValue();
            packet.motionZ = packet.motionZ / 100 * (int) horizontal.getValue();
        }

        if (event.getPacket() instanceof SPacketExplosion && explosions.getValue())
        {
            SPacketExplosion packet = ((SPacketExplosion) event.getPacket());

            packet.motionX = packet.motionX / 100 * (int) horizontal.getValue();
            packet.motionY = packet.motionY / 100 * (int) vertical.getValue();
            packet.motionZ = packet.motionZ / 100 * (int) horizontal.getValue();
        }
    }
}
