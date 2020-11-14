package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.clickgui.settings.BooleanButton;
import cat.yoink.xanax.main.clickgui.settings.EnumButton;
import cat.yoink.xanax.main.clickgui.settings.NumberButton;
import cat.yoink.xanax.main.font.CFontRenderer;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.EnumSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import cat.yoink.xanax.main.setting.Setting;
import cat.yoink.xanax.main.util.GuiUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class ModuleButton implements GuiBase
{
    private final List<SettingButton> buttons = new ArrayList<>();
    private final Module module;
    private final CategoryButton parent;
    private int x;
    private int y;
    private final int w;
    private final int h;
    private boolean selected;

    public ModuleButton(Module module, int x, int y, int w, int h, CategoryButton parent, int windowX, int windowY)
    {
        this.module = module;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.parent = parent;

        int setI = 0;
        List<Setting> settings = module.getSettings();
        for (int i = 0; i < settings.size(); i++)
        {
            Setting setting = settings.get(i);
            boolean left = i % 2 == 1;

            if (setting instanceof BooleanSetting) buttons.add(new BooleanButton(module, windowX + 15 + (left ? 175 : 0), windowY + 70 + setI * 20, 175, 20, setting.toBoolean()));
            else if (setting instanceof NumberSetting) buttons.add(new NumberButton(module, windowX + 15 + (left ? 175 : 0), windowY + 70 + setI * 20, 175, 20, setting.toNumber()));
            else if (setting instanceof EnumSetting) buttons.add(new EnumButton(module, windowX + 15 + (left ? 175 : 0), windowY + 70 + setI * 20, 175, 20, setting.toEnum()));

            if (left) setI++;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, int windowX, int windowY, boolean self)
    {
        if (self)
        {
            if (selected) GuiUtil.drawSmoothRect(x, y, w, h + 3, 2, new Color(34, 34, 34).getRGB());

            CFontRenderer.TEXT.drawCenteredString(module.getName(), x + w / 2f, y + 3, -1);
        }

        if (selected)
        {
            int setI = 0;
            for (int i = 0; i < buttons.size(); i++)
            {
                boolean left = i % 2 == 1;

                SettingButton button = buttons.get(i);

                button.x = windowX + 15 + (left ? 175 : 0);
                button.y = windowY + 70 + setI * 20;
                button.drawScreen(mouseX, mouseY, windowX, windowY, true);

                if (left) setI++;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (GuiUtil.isHover(x, y, w, h, mouseX, mouseY))
        {
            parent.getButtons().forEach(button -> button.selected = false);
            selected = true;
        }

        if (selected) buttons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
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
        if (selected) buttons.forEach(GuiBase::onGuiClosed);
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
