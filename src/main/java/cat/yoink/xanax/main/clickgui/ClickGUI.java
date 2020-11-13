package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.util.GuiUtil;
import net.minecraft.client.gui.GuiScreen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public final class ClickGUI extends GuiScreen
{
    public static final ClickGUI INSTANCE = new ClickGUI();

    private final List<CategoryButton> buttons = new ArrayList<>();
    private final int x = 150, y = 40, w = 350, h = 250;

    public ClickGUI()
    {
        Category[] values = Category.values();
        for (int i = 0; i < values.length; i++)
        {
            buttons.add(new CategoryButton(values[i], x + 10 + i * 30, y, 30, 30, values[i].getImage()));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GuiUtil.drawSmoothRect(x, y, w, h, 3, new Color(52, 52, 52).getRGB());
        GuiUtil.drawSmoothRect(x + 10, y + 50, w - 20, h - 60, 3, new Color(34, 34, 34).getRGB());

        buttons.forEach(button -> button.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {

    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (keyCode == 1) mc.displayGuiScreen(null);
    }

    @Override
    public void onGuiClosed()
    {

    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
