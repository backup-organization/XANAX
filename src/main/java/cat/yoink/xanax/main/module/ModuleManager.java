package cat.yoink.xanax.main.module;

import cat.yoink.xanax.main.module.modules.client.ClickGUI;
import cat.yoink.xanax.main.module.modules.combat.Criticals;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public enum ModuleManager
{
    INSTANCE;

    private final ArrayList<Module> modules = new ArrayList<>();

    ModuleManager()
    {
        addModules(new Criticals(), new ClickGUI());
    }

    private void addModules(Module... modules)
    {
        this.modules.addAll(Arrays.asList(modules));
    }

    public Setting getSetting(String moduleName, String settingName)
    {
        Setting setting =  Objects.requireNonNull(getModules().stream().filter(m -> m.getName().equalsIgnoreCase(moduleName)).findAny().orElse(null)).getSettings().stream().filter(s -> s.getName().equalsIgnoreCase(settingName)).findAny().orElse(null);

        if (setting == null) return null;

        if (setting instanceof BooleanSetting)
        {
            return (BooleanSetting) setting;
        }
        return setting;
    }

    public ArrayList<Module> getModules()
    {
        return modules;
    }

    public Module getModule(String name)
    {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
