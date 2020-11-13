package cat.yoink.xanax.mixin.mixins;

import cat.yoink.xanax.main.event.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class NetworkManagerPatch
{
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo)
    {
        PacketEvent event = new PacketEvent(packet, PacketEvent.Type.OUTGOING);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) callbackInfo.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo)
    {
        PacketEvent event = new PacketEvent(packet, PacketEvent.Type.INCOMING);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) callbackInfo.cancel();
    }
}