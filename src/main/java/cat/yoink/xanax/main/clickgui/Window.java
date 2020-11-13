package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.module.ModuleManager;

import java.util.ArrayList;
import java.util.List;

public final class Window implements IClickBase
{
    private final List<ModuleButton> buttons = new ArrayList<>();
    private final Category category;
    private int x;
    private int y;
    private final int w;
    private final int h;

    public Window(Category category, int x, int y, int w, int h)
    {
        this.category = category;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        List<Module> modules = ModuleManager.INSTANCE.getModules();
        for (int i = 0; i < modules.size(); i++)
        {
            buttons.add(new ModuleButton(modules.get(i), x, h + i * 15, w, h));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        buttons.forEach(button -> button.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        buttons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state)
    {
        buttons.forEach(button -> button.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
        buttons.forEach(button -> button.keyTyped(typedChar, keyCode));
    }

    @Override
    public void onGuiClosed()
    {
        buttons.forEach(IClickBase::onGuiClosed);
    }
}
