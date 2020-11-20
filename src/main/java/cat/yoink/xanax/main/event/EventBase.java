package cat.yoink.xanax.main.event;

public abstract class EventBase
{
    public boolean cancelled;

    public boolean isCancelled()
    {
        return cancelled;
    }

    public void setCancelled(final boolean cancelled)
    {
        this.cancelled = cancelled;
    }
}
