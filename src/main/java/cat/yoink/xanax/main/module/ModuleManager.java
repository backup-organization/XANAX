package cat.yoink.xanax.main.module;

import cat.yoink.xanax.main.module.modules.client.*;
import cat.yoink.xanax.main.module.modules.combat.*;
import cat.yoink.xanax.main.module.modules.misc.*;
import cat.yoink.xanax.main.module.modules.movement.*;
import cat.yoink.xanax.main.module.modules.render.*;
import cat.yoink.xanax.main.module.modules.world.*;
import cat.yoink.xanax.main.setting.*;

import java.util.*;

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
                new Velocity(),
                new HitMarkers(),
                new FeetHighlight(),
                new PacketMine(),
                new ChatSuffix(),
                new HoleESP(),
                new Replenish(),
                new DiscordRPC(),
                new BlockHighlight(),
                new GhostEntity());
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

    public List<String> getConfig()
    {
        List<String> config = new ArrayList<>();

        modules.forEach(module -> module.getSettings().forEach(setting -> {
            StringBuilder builder = new StringBuilder(module.getName() + ":" + setting.getName() + ":");
            if (setting instanceof BooleanSetting) builder.append(((BooleanSetting) setting).getValue());
            else if (setting instanceof NumberSetting) builder.append(((NumberSetting) setting).getValue());
            else if (setting instanceof EnumSetting) builder.append(((EnumSetting) setting).getIndex());
            config.add(builder.toString());
        }));

        return config;
    }
}
