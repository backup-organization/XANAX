package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.MinecraftInstance;

public interface IClickBase extends MinecraftInstance
{
    void drawScreen(int mouseX, int mouseY, float partialTicks);

    void mouseClicked(int mouseX, int mouseY, int mouseButton);

    void mouseReleased(int mouseX, int mouseY, int state);

    void keyTyped(char typedChar, int keyCode);

    void onGuiClosed();

    default boolean isHover(int X, int Y, int W, int H, int mX, int mY)
    {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }
}
