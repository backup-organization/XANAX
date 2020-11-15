package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.clickgui.settings.BooleanButton;
import cat.yoink.xanax.main.clickgui.settings.EnumButton;
import cat.yoink.xanax.main.clickgui.settings.NumberButton;
import cat.yoink.xanax.main.font.CFontRenderer;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.module.ModuleManager;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.EnumSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import cat.yoink.xanax.main.setting.Setting;
import cat.yoink.xanax.main.util.GuiUtil;
import org.lwjgl.input.Mouse;

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
    private int scroll;

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
            if (selected)
            {
                boolean outline = ModuleManager.INSTANCE.getSetting("ClickGUI", "Outline").toBoolean().getValue();

                float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
                Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

                if (outline) GuiUtil.drawSmoothRect(x - 1, y - 1, w + 2, h + 2, 2, c.getRGB());
                GuiUtil.drawSmoothRect(x, y, w, h + 3, 2, new Color(34, 34, 34).getRGB());
            }

            CFontRenderer.TEXT.drawCenteredString(module.getName(), x + w / 2f, y + 3, module.isEnabled() ? -1 : new Color(150, 150, 150).getRGB());
        }

        if (selected)
        {
            doScroll(windowX, windowY, mouseX, mouseY);

            int setI = 0;
            int setI2 = 0;
            for (int i = 0; i < buttons.size(); i++)
            {
                if (setI2 < scroll)
                {
                    setI2++;
                    continue;
                }

                if (setI >= 8) continue;

                boolean left = i % 2 == 1;

                SettingButton button = buttons.get(i);

                button.x = windowX + 15 + (left ? 175 : 0);
                button.y = windowY + 70 + setI * 20;
                button.drawScreen(mouseX, mouseY, windowX, windowY, true);

                if (left) setI++;
            }
        }
    }

    private void doScroll(int windowX, int windowY, int mouseX, int mouseY)
    {
        if (GuiUtil.isHover(windowX + 15, windowY + 70, 340, 160, mouseX, mouseY))
        {
            int wheel = Mouse.getDWheel();
            if (wheel < 0 && scroll <= buttons.size() - 17) scroll += 2;
            else if (wheel > 0 && scroll > 0) scroll -= 2;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, boolean self)
    {
        if (GuiUtil.isHover(x, y, w, h, mouseX, mouseY) && self)
        {
            if (mouseButton == 0) module.toggle();
            else if (mouseButton == 1)
            {
                parent.getButtons().forEach(button -> button.selected = false);
                selected = true;
            }
        }

        if (selected) buttons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton, true));
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
