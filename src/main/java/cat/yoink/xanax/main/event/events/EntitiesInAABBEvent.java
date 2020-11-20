package cat.yoink.xanax.main.event.events;

import cat.yoink.xanax.main.event.EventBase;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.function.Predicate;

public class EntitiesInAABBEvent extends EventBase
{
    private final WorldClient worldClient;
    private final Entity entityIn;
    private final AxisAlignedBB boundingBox;
    private final Predicate<? super Entity> predicate;

    public EntitiesInAABBEvent(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate<? super Entity> predicate)
    {
        this.worldClient = worldClient;
        this.entityIn = entityIn;
        this.boundingBox = boundingBox;
        this.predicate = predicate;
    }

    public WorldClient getWorldClient()
    {
        return worldClient;
    }

    public Entity getEntityIn()
    {
        return entityIn;
    }

    public AxisAlignedBB getBoundingBox()
    {
        return boundingBox;
    }

    public Predicate<? super Entity> getPredicate()
    {
        return predicate;
    }
}
