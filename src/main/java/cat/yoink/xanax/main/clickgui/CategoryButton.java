package cat.yoink.xanax.main.clickgui;

import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.module.Category;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public final class CategoryButton implements GuiBase, MinecraftInstance
{
    private final ResourceLocation image;
    private final Category category;
    private int x, y, w, h;

    public CategoryButton(Category category, int x, int y, int w, int h, ResourceLocation image)
    {
        this.category = category;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.image = image;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.enableBlend();
        mc.getTextureManager().bindTexture(image);
//        if (selected) GlStateManager.color(1, 1, 1, 1);
        /*else */GlStateManager.color(0.7f, 0.7f, 0.7f, 1);
        Gui.drawModalRectWithCustomSizedTexture(x + 5, y + 5, 0, 0, w - 5, h - 5, w - 5, h - 5);
        GlStateManager.disableBlend();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {

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
