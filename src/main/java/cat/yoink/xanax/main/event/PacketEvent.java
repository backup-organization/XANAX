package cat.yoink.xanax.main.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public final class PacketEvent extends Event
{
    private final Packet<?> packet;
    private final Type type;

    public PacketEvent(Packet<?> packet, Type type)
    {
        this.packet = packet;
        this.type = type;
    }

    public Packet<?> getPacket()
    {
        return packet;
    }

    public Type getType()
    {
        return type;
    }

    public enum Type
    {
        INCOMING,
        OUTGOING
    }
}
