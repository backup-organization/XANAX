package cat.yoink.xanax.main.module;

import cat.yoink.xanax.main.module.modules.client.ClickGUI;
import cat.yoink.xanax.main.module.modules.movement.Burrow;
import cat.yoink.xanax.main.module.modules.combat.Criticals;
import cat.yoink.xanax.main.module.modules.combat.Surround;
import cat.yoink.xanax.main.module.modules.misc.Swing;
import cat.yoink.xanax.main.module.modules.movement.FastFall;
import cat.yoink.xanax.main.module.modules.movement.Velocity;
import cat.yoink.xanax.main.module.modules.render.Animations;
import cat.yoink.xanax.main.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public enum ModuleManager
{
    INSTANCE;

    private final ArrayList<Module> modules = new ArrayList<>();

    ModuleManager()
    {
        addModules(new Criticals(),
                new ClickGUI(),
                new Swing(),
                new Animations(),
                new Surround(),
                new Burrow(),
                new FastFall(),
                new Velocity());
    }

    private void addModules(Module... modules)
    {
        this.modules.addAll(Arrays.asList(modules));
        this.modules.sort(Comparator.comparing(Module::getName));
    }

    public Setting getSetting(String moduleName, String settingName)
    {
        return Objects.requireNonNull(getModules().stream().filter(m -> m.getName().equalsIgnoreCase(moduleName)).findAny().orElse(null)).getSettings().stream().filter(s -> s.getName().equalsIgnoreCase(settingName)).findAny().orElse(null);
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
