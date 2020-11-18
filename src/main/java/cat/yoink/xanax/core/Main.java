package cat.yoink.xanax.core;

import cat.yoink.xanax.main.event.EventHandler;
import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.config.ConfigManager;
import net.minecraftforge.common.MinecraftForge;
import team.stiff.pomelo.EventManager;
import team.stiff.pomelo.impl.annotated.AnnotatedEventManager;

public enum Main implements MinecraftInstance
{
    INSTANCE;

    public static final EventManager EVENT_BUS = new AnnotatedEventManager();

    public void startup()
    {
        ConfigManager.loadConfig();

        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::saveConfig));
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
