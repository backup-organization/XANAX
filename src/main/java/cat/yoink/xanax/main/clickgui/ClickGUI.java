package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.ModuleManager;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends GuiScreen
{
    public static final ClickGUI INSTANCE = new ClickGUI();

    private final List<Window> windows = new ArrayList<>();

    public ClickGUI()
    {
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++)
        {
            windows.add(new Window(categories[i], i * 105, 3, 100, 15));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        windows.forEach(window -> window.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        windows.forEach(window -> window.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        windows.forEach(window -> window.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode)
    {
        windows.forEach(window -> window.keyTyped(typedChar, keyCode));
    }

    @Override
    public void onGuiClosed()
    {
        ModuleManager.INSTANCE.getModule("ClickGUI").disable();

        windows.forEach(Window::onGuiClosed);
    }
}
