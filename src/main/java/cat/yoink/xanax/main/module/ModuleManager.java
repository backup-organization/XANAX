package cat.yoink.xanax.main.module;

import cat.yoink.xanax.main.module.modules.combat.Criticals;

import java.util.ArrayList;
import java.util.Arrays;

public enum ModuleManager
{
    INSTANCE;

    private final ArrayList<Module> modules = new ArrayList<>();

    ModuleManager()
    {
        addModules(new Criticals());
    }

    private void addModules(Module... modules)
    {
        this.modules.addAll(Arrays.asList(modules));
    }

    public ArrayList<Module> getModules()
    {
        return modules;
    }
}
