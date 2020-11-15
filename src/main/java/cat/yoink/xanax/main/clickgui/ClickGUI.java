package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.font.CFontRenderer;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.ModuleManager;
import cat.yoink.xanax.main.util.GuiUtil;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class ClickGUI extends GuiScreen
{
    public static final ClickGUI INSTANCE = new ClickGUI();

    private final List<CategoryButton> buttons = new ArrayList<>();
    private int x = 150;
    private int y = 40;
    private final int w = 380;
    private final int h = 245;
    private int dragX, dragY;
    private boolean dragging;

    public ClickGUI()
    {
        Category[] values = Category.values();
        for (int i = 0; i < values.length; i++)
        {
            buttons.add(new CategoryButton(values[i], x + 10 + i * 60, y + 34, 60, 15, x, y));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (dragging)
        {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }

        boolean outline = ModuleManager.INSTANCE.getSetting("ClickGUI", "Outline").toBoolean().getValue();

        float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
        Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

        GuiUtil.drawSmoothRect(x, y, w, h, 3, new Color(52, 52, 52).getRGB(), outline, c.getRGB());
        GuiUtil.drawRect(x + 10, y + 50, w - 20, h - 60, new Color(43, 43, 43).getRGB(), outline, c.getRGB());
        GuiUtil.drawRect(x + 15, y + 70, w - 30, h - 85, new Color(34, 34, 34).getRGB(), outline, c.getRGB());

        CategoryButton selected = buttons.stream().filter(CategoryButton::isSelected).findAny().orElse(null);

        if (selected != null)
        {
            if (selected.getTab() > 0)
            {
                GuiUtil.drawSmoothRect(x + 15, y + 57, 10, 10, 3, new Color(34, 34, 34).getRGB(), outline, c.getRGB());
                CFontRenderer.TEXT.drawString("<", x + 16, y + 57.5f, -1);
            }

            if (selected.getTab() < selected.getButtons().size() - 5)
            {
                GuiUtil.drawSmoothRect(x + 355, y + 57, 10, 10, 3, new Color(34, 34, 34).getRGB(), outline, c.getRGB());
                CFontRenderer.TEXT.drawString(">", x + 356.5f, y + 57.5f, -1);
            }
        }

        CFontRenderer.TITLE.drawCenteredString("XANAX", x + w / 2f, y + 5, c.getRGB());

        if (dragging)
        {
            for (int i = 0; i < buttons.size(); i++)
            {
                CategoryButton button = buttons.get(i);
                button.setX(x + 10 + i * 60);
                button.setY(y + 34);
            }
        }

        buttons.forEach(button -> button.drawScreen(mouseX, mouseY, x, y, true));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (GuiUtil.isHover(x, y, w, 30, mouseX, mouseY) && mouseButton == 0)
        {
            dragging = true;
            dragX = x - mouseX;
            dragY = y - mouseY;
        }

        buttons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton, true));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        dragging = false;

        buttons.forEach(buttons -> buttons.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (keyCode == 1) mc.displayGuiScreen(null);

        buttons.forEach(button -> button.keyTyped(typedChar, keyCode));
    }

    @Override
    public void onGuiClosed()
    {
        ModuleManager.INSTANCE.getModule("ClickGUI").disable();

        buttons.forEach(CategoryButton::onGuiClosed);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public List<CategoryButton> getButtons()
    {
        return buttons;
    }
}
