package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.module.Module;

public class ModuleButton implements IClickBase
{
    private final Module module;
    private int x;
    private int y;
    private final int w;
    private final int h;

    public ModuleButton(Module module, int x, int y, int w, int h)
    {
        this.module = module;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state)
    {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {

    }

    @Override
    public void onGuiClosed()
    {

    }
}
