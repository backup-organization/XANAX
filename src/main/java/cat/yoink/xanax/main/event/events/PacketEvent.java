package cat.yoink.xanax.main.event.events;

import cat.yoink.xanax.main.event.EventBase;
import net.minecraft.network.Packet;

public final class PacketEvent extends EventBase
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
