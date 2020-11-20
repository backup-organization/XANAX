package cat.yoink.xanax.main.clickgui.settings;

import cat.yoink.xanax.main.clickgui.SettingButton;
import cat.yoink.xanax.main.font.CFontRenderer;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.module.ModuleManager;
import cat.yoink.xanax.main.setting.NumberSetting;
import cat.yoink.xanax.main.util.GuiUtil;

import java.awt.*;

public final class NumberButton extends SettingButton
{
    private final NumberSetting setting;
    private int sliderWidth;
    private boolean dragging;

    public NumberButton(final Module module, final int x, final int y, final int w, final int h, final NumberSetting setting)
    {
        super(module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final int windowX, final int windowY, final boolean self)
    {
        updateSlider(mouseX);

        final boolean outline = ModuleManager.INSTANCE.getSetting("ClickGUI", "Outline").toBoolean().getValue();

        final float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
        final Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

        GuiUtil.drawRect(x + 5, y + 5, 100, 10, new Color(20, 20, 20).getRGB(), outline, c.getRGB());
        GuiUtil.drawRect(x + sliderWidth - 2 + 8, y + 6, 4, 8, c.getRGB());

        CFontRenderer.SMALLTEXT.drawCenteredString(String.valueOf(setting.getValue()), x + 55, y + 6.5f, -1);
        CFontRenderer.TEXT.drawString(setting.getName(), x + 109, y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final boolean self)
    {
        if (GuiUtil.isHover(x, y, w, h - 1, mouseX, mouseY)) dragging = true;
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state)
    {
        dragging = false;
    }

    @Override
    public void keyTyped(final char typedChar, final int keyCode)
    {

    }

    @Override
    public void onGuiClosed()
    {
        dragging = false;
    }

    private void updateSlider(final int mouseX)
    {
        final double diff = Math.min(94, Math.max(0, mouseX - x - 8));

        final double minimum = setting.getMinimum();
        final double maximum = setting.getMaximum();

        sliderWidth = (int) (94f * (setting.getValue() - minimum) / (maximum - minimum));

        if (dragging)
        {
            if (diff == 0) setting.setValue(minimum);
            else if (diff == 94) setting.setValue(maximum);
            else setting.setValue(diff / 96f * (maximum - minimum) + minimum);
        }
    }
}
