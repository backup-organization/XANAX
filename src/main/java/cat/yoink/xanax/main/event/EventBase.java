package cat.yoink.xanax.main.event;

public abstract class EventBase
{
    public boolean cancelled;

    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public boolean isCancelled()
    {
        return cancelled;
    }
}
