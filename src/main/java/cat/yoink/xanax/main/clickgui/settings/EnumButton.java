package cat.yoink.xanax.main.clickgui.settings;

import cat.yoink.xanax.main.clickgui.SettingButton;
import cat.yoink.xanax.main.font.CFontRenderer;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.module.ModuleManager;
import cat.yoink.xanax.main.setting.EnumSetting;
import cat.yoink.xanax.main.util.GuiUtil;

import java.awt.*;

public final class EnumButton extends SettingButton
{
    private final EnumSetting setting;

    public EnumButton(Module module, int x, int y, int w, int h, EnumSetting setting)
    {
        super(module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, int windowX, int windowY, boolean self)
    {
        boolean outline = ModuleManager.INSTANCE.getSetting("ClickGUI", "Outline").toBoolean().getValue();

        float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
        Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

        GuiUtil.drawSmoothRect(x + 3, y + 4, 50, 12, 1, new Color(20, 20, 20).getRGB(), outline, c.getRGB());
        CFontRenderer.SMALLTEXT.drawString(setting.getValue(), x + 8, y + 6.5f, -1);

        CFontRenderer.TEXT.drawString(setting.getName(), x + 57, y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, boolean self)
    {
        if (GuiUtil.isHover(x, y, w, h -1, mouseX, mouseY))
        {
            if (mouseButton == 0) setting.cycleForward();
            else if (mouseButton == 1) setting.cycleBackward();
        }
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
