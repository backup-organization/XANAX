package cat.yoink.xanax;

import cat.yoink.xanax.main.EventHandler;
import cat.yoink.xanax.main.MinecraftInstance;
import net.minecraftforge.common.MinecraftForge;

public enum EntryPoint implements MinecraftInstance
{
    INSTANCE;

    public void initialize()
    {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
