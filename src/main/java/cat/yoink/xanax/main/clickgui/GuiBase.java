package cat.yoink.xanax.main.clickgui;

public interface GuiBase
{
    void drawScreen(int mouseX, int mouseY, int windowX, int windowY);

    void mouseClicked(int mouseX, int mouseY, int mouseButton);

    void mouseReleased(int mouseX, int mouseY, int state);

    void keyTyped(char typedChar, int keyCode);

    void onGuiClosed();
}
