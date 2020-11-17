package cat.yoink.xanax;

import cat.yoink.xanax.main.EventHandler;
import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.config.ConfigManager;
import net.minecraftforge.common.MinecraftForge;

public enum EntryPoint implements MinecraftInstance
{
    INSTANCE;

    public void initialize()
    {
        ConfigManager.loadConfig();

        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
