package cat.yoink.xanax;

import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.handling.EventSubscriber;
import net.minecraftforge.common.MinecraftForge;

public enum EntryPoint implements MinecraftInstance
{
    INSTANCE;

    public void initialize()
    {
        MinecraftForge.EVENT_BUS.register(new EventSubscriber());
    }
}
