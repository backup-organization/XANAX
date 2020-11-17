package cat.yoink.xanax.main.config;

import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.module.ModuleManager;
import cat.yoink.xanax.main.util.FileUtil;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum ConfigManager implements MinecraftInstance
{
    INSTANCE;

    private final File folder = new File(mc.gameDir + File.separator + "xanax");

    private void loadConfiguration()
    {
        if (!folder.exists() && !folder.mkdirs()) return;

        getFile("ToggledModules.txt").forEach(s -> {
            ModuleManager.INSTANCE.getModule(s).setEnabled(true);
            MinecraftForge.EVENT_BUS.register(ModuleManager.INSTANCE.getModule(s));
        });
    }

    private void saveConfiguration()
    {
//        saveFile("ToggledModules.txt", ModuleManager.INSTANCE.getModules().stream().filter(Module::isEnabled).collect(Collectors.toCollection(ArrayList::new)));
    }

    private void saveFile(String name, List<String> lines)
    {
        try { FileUtil.saveFile(new File(folder.getAbsolutePath(), name), lines); }
        catch (IOException e) { e.printStackTrace(); }
    }

    private List<String> getFile(String name)
    {
        try
        {
            return FileUtil.loadFile(new File(folder.getAbsolutePath(), name));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void loadConfig()
    {
        INSTANCE.loadConfiguration();
    }

    public static void saveConfig()
    {
        INSTANCE.saveConfiguration();
    }
}
