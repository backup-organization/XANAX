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

    public EnumButton(final Module module, final int x, final int y, final int w, final int h, final EnumSetting setting)
    {
        super(module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final int windowX, final int windowY, final boolean self)
    {
        final boolean outline = ModuleManager.INSTANCE.getSetting("ClickGUI", "Outline").toBoolean().getValue();

        final float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
        final Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

        GuiUtil.drawRect(x + 5, y + 5, 50, 10, new Color(20, 20, 20).getRGB(), outline, c.getRGB());
        CFontRenderer.SMALLTEXT.drawString(setting.getValue(), x + 8, y + 6.5f, -1);

        CFontRenderer.TEXT.drawString(setting.getName(), x + 59, y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final boolean self)
    {
        if (GuiUtil.isHover(x, y, w, h - 1, mouseX, mouseY))
        {
            if (mouseButton == 0) setting.cycleForward();
            else if (mouseButton == 1) setting.cycleBackward();
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state)
    {

    }

    @Override
    public void keyTyped(final char typedChar, final int keyCode)
    {

    }

    @Override
    public void onGuiClosed()
    {

    }
}
