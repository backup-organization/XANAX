package cat.yoink.xanax.main.module;

import cat.yoink.xanax.Manager;
import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.setting.Setting;

import java.util.ArrayList;
import java.util.List;

public abstract class Module implements MinecraftInstance
{
    protected final String name;
    protected final Category category;
    protected int bind;
    protected boolean enabled;
    protected final List<Setting> settings = new ArrayList<>();

    protected Module(String name, Category category)
    {
        this.name = name;
        this.category = category;
    }

    protected void onEnable() { }
    protected void onDisable() { }

    public final void enable()
    {
        enabled = true;
        onEnable();
        Manager.EVENT_BUS.addEventListener(this);
    }

    public final void disable()
    {
        enabled = false;
        onDisable();
        Manager.EVENT_BUS.removeEventListener(this);
    }

    public final void toggle()
    {
        if (enabled) disable();
        else enable();
    }

    protected <E extends Setting> E addSetting(E setting)
    {
        settings.add(setting);
        return setting;
    }

    public Setting getSetting(String name)
    {
        return settings.stream().filter(setting -> setting.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    protected final boolean nullCheck()
    {
        return mc.player == null || mc.world == null;
    }

    public final String getName()
    {
        return name;
    }

    public final Category getCategory()
    {
        return category;
    }

    public final int getBind()
    {
        return bind;
    }

    public final void setBind(int bind)
    {
        this.bind = bind;
    }

    public final boolean isEnabled()
    {
        return enabled;
    }

    public final void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public List<Setting> getSettings()
    {
        return settings;
    }
}
