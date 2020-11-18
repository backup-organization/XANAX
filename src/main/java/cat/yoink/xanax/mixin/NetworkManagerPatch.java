package cat.yoink.xanax.mixin;

import cat.yoink.xanax.Manager;
import cat.yoink.xanax.main.event.events.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public abstract class NetworkManagerPatch
{
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo)
    {
        PacketEvent event = new PacketEvent(packet, PacketEvent.Type.OUTGOING);
        Manager.EVENT_BUS.dispatchEvent(event);

        if (event.isCancelled()) callbackInfo.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo)
    {
        PacketEvent event = new PacketEvent(packet, PacketEvent.Type.INCOMING);
        Manager.EVENT_BUS.dispatchEvent(event);

        if (event.isCancelled()) callbackInfo.cancel();
    }
}
