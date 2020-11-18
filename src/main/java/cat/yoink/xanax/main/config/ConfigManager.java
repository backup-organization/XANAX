package cat.yoink.xanax.main.config;

import cat.yoink.xanax.Manager;
import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.module.ModuleManager;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.EnumSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import cat.yoink.xanax.main.setting.Setting;
import cat.yoink.xanax.main.util.FileUtil;

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
            Manager.EVENT_BUS.addEventListener(ModuleManager.INSTANCE.getModule(s));
        });

        getFile("Binds.txt").forEach(s -> {
            Module module = ModuleManager.INSTANCE.getModule(s.split(":")[0]);
            if (module != null) module.setBind(Integer.parseInt(s.split(":")[1]));
        });

        getFile("Settings.txt").forEach(s -> {
            Module module = ModuleManager.INSTANCE.getModule(s.split(":")[0]);
            if (module == null) return;
            Setting setting = module.getSetting(s.split(":")[1]);
            if (setting == null) return;
            if (setting instanceof BooleanSetting) ((BooleanSetting) setting).setValue(Boolean.parseBoolean(s.split(":")[2]));
            if (setting instanceof NumberSetting) ((NumberSetting) setting).setValue(Double.parseDouble(s.split(":")[2]));
            if (setting instanceof EnumSetting) ((EnumSetting) setting).setIndex(Integer.parseInt(s.split(":")[2]));
        });
    }

    private void saveConfiguration()
    {
        saveFile("ToggledModules.txt", ModuleManager.INSTANCE.getModules().stream().filter(Module::isEnabled).filter(module -> !module.getName().equalsIgnoreCase("ClickGUI")).map(Module::getName).collect(Collectors.toList()));
        saveFile("Binds.txt", ModuleManager.INSTANCE.getModules().stream().map(module -> module.getName() + ":" + module.getBind()).collect(Collectors.toList()));
        saveFile("Settings.txt", ModuleManager.INSTANCE.getConfig());
    }

    private void saveFile(String name, List<String> lines)
    {
        try { FileUtil.saveFile(new File(folder.getAbsolutePath(), name), lines); }
        catch (IOException e) { e.printStackTrace(); }
    }

    private List<String> getFile(String name)
    {
        try { return FileUtil.loadFile(new File(folder.getAbsolutePath(), name)); }
        catch (IOException e) { e.printStackTrace(); }
        return new ArrayList<>();
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
