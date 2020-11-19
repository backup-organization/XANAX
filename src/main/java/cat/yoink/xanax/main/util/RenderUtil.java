package cat.yoink.xanax.main.util;

import cat.yoink.xanax.main.MinecraftInstance;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public final class RenderUtil implements MinecraftInstance
{
    public static void drawBox(AxisAlignedBB bb, int red, int green, int blue, int alpha, boolean box, boolean outline)
    {
        try
        {
            glSetup();
            if (box) RenderGlobal.renderFilledBox(bb, red / 255f, green / 255f, blue / 255f, alpha / 255f);
            if (outline) RenderGlobal.drawSelectionBoundingBox(bb, red / 255f, green / 255f, blue / 255f, (alpha / 255f) * 1.5F);
            glCleanup();
        }
        catch (Exception ignored)
        {
        }
    }

    public static void drawBox(BlockPos blockPos, Color color, boolean box, boolean outline)
    {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos.getX() - mc.getRenderManager().viewerPosX, blockPos.getY() - mc.getRenderManager().viewerPosY, blockPos.getZ() - mc.getRenderManager().viewerPosZ, blockPos.getX() + 1 - mc.getRenderManager().viewerPosX, blockPos.getY() + 1 - mc.getRenderManager().viewerPosY, blockPos.getZ() + 1 - mc.getRenderManager().viewerPosZ);
        drawBox(axisAlignedBB, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), box, outline);
    }

    public static void glSetup()
    {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(1.5f);
    }

    public static void glCleanup()
    {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static AxisAlignedBB convertBox(AxisAlignedBB box)
    {
        return new AxisAlignedBB(box.minX - mc.getRenderManager().viewerPosX, box.minY - mc.getRenderManager().viewerPosY, box.minZ - mc.getRenderManager().viewerPosZ, box.maxX - mc.getRenderManager().viewerPosX, box.maxY - mc.getRenderManager().viewerPosY, box.maxZ - mc.getRenderManager().viewerPosZ);
    }
}
