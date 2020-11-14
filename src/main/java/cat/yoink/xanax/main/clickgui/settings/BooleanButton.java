package cat.yoink.xanax.main.clickgui.settings;

import cat.yoink.xanax.main.clickgui.SettingButton;
import cat.yoink.xanax.main.font.CFontRenderer;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.util.GuiUtil;

import java.awt.*;

public final class BooleanButton extends SettingButton
{
    private final BooleanSetting setting;

    public BooleanButton(Module module, int x, int y, int w, int h, BooleanSetting setting)
    {
        super(module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, int windowX, int windowY, boolean self)
    {
        GuiUtil.drawSmoothRect(x + 5, y + 5, 10, 10, 1, new Color(20, 20, 20).getRGB());

        if (setting.getValue())
        {
            float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
            Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

            GuiUtil.drawSmoothRect(x + 7, y + 7, 6, 6, 1, c.getRGB());
        }

        CFontRenderer.TEXT.drawString(setting.getName(), x + 20, y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, boolean self)
    {
        if (GuiUtil.isHover(x, y, w, h - 1, mouseX, mouseY)) setting.toggle();
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
