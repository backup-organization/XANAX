package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.font.CFontRenderer;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.module.ModuleManager;
import cat.yoink.xanax.main.util.GuiUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CategoryButton implements GuiBase, MinecraftInstance
{
    private final List<ModuleButton> buttons = new ArrayList<>();
    private final Category category;
    private int x, y, w, h;
    private boolean selected;
    private int windowX, windowY;
    private int tab;

    public CategoryButton(Category category, int x, int y, int w, int h, int windowX, int windowY)
    {
        this.category = category;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        List<Module> modules = ModuleManager.INSTANCE.getModules().stream().filter(m -> m.getCategory().equals(category)).collect(Collectors.toList());
        for (int i = 0; i < modules.size(); i++)
        {
            buttons.add(new ModuleButton(modules.get(i), windowX + 30 + i * 65, y + 20, 60, 15, this, windowX, windowY));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, int windowX, int windowY, boolean self)
    {
        this.windowX = windowX;
        this.windowY = windowY;

        if (selected)
        {
            GuiUtil.drawSmoothRect(x, y, w, h + 3, 2, new Color(43, 43, 43).getRGB());
        }

        CFontRenderer.TEXT.drawString(category.getName(), x + (w / 2f) - (CFontRenderer.TEXT.getStringWidth(category.getName()) / 2f), y + 3, -1);

        if (selected)
        {
            int modX = 0;
            for (int i = 0; i < buttons.size(); i++)
            {
                if (i < tab || i > tab + 4)
                {
                    buttons.get(i).drawScreen(mouseX, mouseY, windowX, windowY, false);
                    continue;
                }

                ModuleButton button = buttons.get(i);

                button.setX(windowX + 30 + modX * 65);
                button.setY(y + 20);
                buttons.get(i).drawScreen(mouseX, mouseY, windowX, windowY, true);

                modX++;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (GuiUtil.isHover(x, y, w, h, mouseX, mouseY) && mouseButton == 0)
        {
            ClickGUI.INSTANCE.getButtons().forEach(b -> b.selected = false);
            selected = true;
        }

        if (mouseButton == 0 && selected)
        {
            if (GuiUtil.isHover(windowX + 15, windowY + 57, 10, 10, mouseX, mouseY) && tab > 0) tab--;
            if (GuiUtil.isHover(windowX + 355, windowY + 57, 10, 10, mouseX, mouseY) && tab < buttons.size() - 5) tab++;
        }

        if (selected)
        {
            for (int i = 0; i < buttons.size(); i++)
            {
                if (i < tab || i > tab + 4) continue;

                buttons.get(i).mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (selected) buttons.forEach(button -> button.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
        if (selected) buttons.forEach(button -> button.keyTyped(typedChar, keyCode));
    }

    @Override
    public void onGuiClosed()
    {
        if (selected) buttons.forEach(ModuleButton::onGuiClosed);
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public List<ModuleButton> getButtons()
    {
        return buttons;
    }
}
