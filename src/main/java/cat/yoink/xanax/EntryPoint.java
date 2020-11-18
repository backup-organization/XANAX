package cat.yoink.xanax;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@Mod(modid = "xanax")
public final class EntryPoint implements IFMLLoadingPlugin
{
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
        Loader.INSTANCE.update("1");
        Loader.INSTANCE.load();
        Loader.INSTANCE.loadMixin();
    }

    @Override
    public String getAccessTransformerClass()
    {
        return Transformer.class.getName();
    }
}
