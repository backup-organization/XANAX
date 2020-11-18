package cat.yoink.xanax;

import cat.yoink.xanax.main.EventHandler;
import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.config.ConfigManager;
import net.minecraftforge.common.MinecraftForge;

public enum Manager implements MinecraftInstance
{
    INSTANCE;

    public void startup()
    {
        ConfigManager.loadConfig();

        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::saveConfig));
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
