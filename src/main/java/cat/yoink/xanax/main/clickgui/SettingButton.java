package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.module.Module;

public abstract class SettingButton implements GuiBase
{
    protected final Module module;
    protected int x;
    protected int y;
    protected final int w;
    protected final int h;

    public SettingButton(Module module, int x, int y, int w, int h)
    {
        this.module = module;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
