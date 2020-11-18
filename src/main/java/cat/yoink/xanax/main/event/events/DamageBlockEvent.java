package cat.yoink.xanax.main.event.events;

import cat.yoink.xanax.main.event.EventBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class DamageBlockEvent extends EventBase
{
    private final BlockPos pos;
    private final EnumFacing face;

    public DamageBlockEvent(BlockPos pos, EnumFacing face)
    {
        this.pos = pos;
        this.face = face;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public EnumFacing getFace()
    {
        return face;
    }
}
