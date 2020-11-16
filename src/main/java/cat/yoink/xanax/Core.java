package cat.yoink.xanax;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@Mod(modid = "loader", name = "Loader", version = Core.VERSION)
public final class Core implements IFMLLoadingPlugin
{
    public static final String VERSION = "1";

    public Core()
    {
//        Loader.INSTANCE.update(VERSION);

//        Loader.INSTANCE.load();

//        Loader.INSTANCE.loadMixin();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event)
    {
        EntryPoint.INSTANCE.initialize();
    }


    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
