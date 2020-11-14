package cat.yoink.xanax.main.clickgui.settings;

import cat.yoink.xanax.main.clickgui.SettingButton;
import cat.yoink.xanax.main.font.CFontRenderer;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.EnumSetting;
import cat.yoink.xanax.main.util.GuiUtil;

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
        CFontRenderer.TEXT.drawString(setting.getName() + " -> " + setting.getValue(), x + 5, y + 5.5f, -1);
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
