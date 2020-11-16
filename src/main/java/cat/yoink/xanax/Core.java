package cat.yoink.xanax;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public final class Core implements IFMLLoadingPlugin
{
    public Core()
    {
//        Loader.INSTANCE.update("1");
//        Loader.INSTANCE.load();
        Loader.INSTANCE.loadMixin();
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
        return Transformer.class.getName();
    }
}
