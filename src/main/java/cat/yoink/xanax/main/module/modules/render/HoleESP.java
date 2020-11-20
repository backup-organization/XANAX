package cat.yoink.xanax.main.module.modules.render;

import cat.yoink.xanax.main.event.events.Render3DEvent;
import cat.yoink.xanax.main.event.events.TickEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import cat.yoink.xanax.main.util.RenderUtil;
import cat.yoink.xanax.main.util.WorldUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class HoleESP extends Module
{
    private final BooleanSetting box = addSetting(new BooleanSetting("Box", true));
    private final BooleanSetting outline = addSetting(new BooleanSetting("Outline", true));
    private final NumberSetting range = addSetting(new NumberSetting("Range", 8, 2, 20, 1));
    private final BooleanSetting wide = addSetting(new BooleanSetting("Wide", false));
    private final NumberSetting bedrockRed = addSetting(new NumberSetting("BedrockRed", 10, 0, 255, 1));
    private final NumberSetting obsidianRed = addSetting(new NumberSetting("ObsidianRed", 10, 0, 255, 1));
    private final NumberSetting bedrockGreen = addSetting(new NumberSetting("BedrockGreen", 10, 0, 255, 1));
    private final NumberSetting obsidianGreen = addSetting(new NumberSetting("ObsidianGreen", 10, 0, 255, 1));
    private final NumberSetting bedrockBlue = addSetting(new NumberSetting("BedrockBlue", 10, 0, 255, 1));
    private final NumberSetting obsidianBlue = addSetting(new NumberSetting("ObsidianBlue", 10, 0, 255, 1));
    private final NumberSetting bedrockAlpha = addSetting(new NumberSetting("BedrockAlpha", 150, 0, 255, 1));
    private final NumberSetting obsidianAlpha = addSetting(new NumberSetting("ObsidianAlpha", 150, 0, 255, 1));

    private final List<BlockPos> bedrockHoles = new ArrayList<>();
    private final List<BlockPos> obsidianHoles = new ArrayList<>();
    private final List<BlockPos> doubleBedrockHolesX = new ArrayList<>();
    private final List<BlockPos> doubleBedrockHolesZ = new ArrayList<>();
    private final List<BlockPos> doubleObsidianHolesX = new ArrayList<>();
    private final List<BlockPos> doubleObsidianHolesZ = new ArrayList<>();

    public HoleESP()
    {
        super("HoleESP", Category.RENDER);
    }

    @Listener
    public void tickEvent(final TickEvent event)
    {
        bedrockHoles.clear();
        obsidianHoles.clear();
        doubleBedrockHolesX.clear();
        doubleBedrockHolesZ.clear();
        doubleObsidianHolesX.clear();
        doubleObsidianHolesZ.clear();

        assert mc.renderViewEntity != null;
        final Vec3i vec3i = new Vec3i(mc.renderViewEntity.posX, mc.renderViewEntity.posY, mc.renderViewEntity.posZ);

        for (int i = vec3i.getX() - (int) range.getValue(); i < vec3i.getX() + (int) range.getValue(); ++i)
        {
            for (int j = vec3i.getZ() - (int) range.getValue(); j < vec3i.getZ() + (int) range.getValue(); ++j)
            {
                for (int k = vec3i.getY() + (int) range.getValue(); k > vec3i.getY() - (int) range.getValue(); --k)
                {
                    final BlockPos blockPos = new BlockPos(i, k, j);

                    if (WorldUtil.isBedrockHole(blockPos)) bedrockHoles.add(blockPos);
                    else if (WorldUtil.isHole(blockPos)) obsidianHoles.add(blockPos);
                    else if (wide.getValue())
                    {
                        if (WorldUtil.isDoubleBedrockHoleX(blockPos)) doubleBedrockHolesX.add(blockPos);
                        else if (WorldUtil.isDoubleBedrockHoleZ(blockPos)) doubleBedrockHolesZ.add(blockPos);
                        else if (WorldUtil.isDoubleHoleX(blockPos)) doubleObsidianHolesX.add(blockPos);
                        else if (WorldUtil.isDoubleHoleZ(blockPos)) doubleObsidianHolesZ.add(blockPos);
                    }
                }
            }
        }
    }

    @Listener
    public void render3D(final Render3DEvent event)
    {
        final int bedrockR = (int) bedrockRed.getValue();
        final int bedrockG = (int) bedrockGreen.getValue();
        final int bedrockB = (int) bedrockBlue.getValue();
        final int bedrockA = (int) bedrockAlpha.getValue();
        final int obsidianR = (int) obsidianRed.getValue();
        final int obsidianG = (int) obsidianGreen.getValue();
        final int obsidianB = (int) obsidianBlue.getValue();
        final int obsidianA = (int) obsidianAlpha.getValue();

        bedrockHoles.forEach(hole -> RenderUtil.drawBox(hole, new Color(bedrockR, bedrockG, bedrockB, bedrockA), box.getValue(), outline.getValue()));
        obsidianHoles.forEach(hole -> RenderUtil.drawBox(hole, new Color(obsidianR, obsidianG, obsidianB, obsidianA), box.getValue(), outline.getValue()));
        if (!wide.getValue()) return;
        doubleBedrockHolesX.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 2 - mc.getRenderManager().viewerPosX, hole.getY() + 1 - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, bedrockR, bedrockG, bedrockB, bedrockA, box.getValue(), outline.getValue()));
        doubleBedrockHolesZ.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + 1 - mc.getRenderManager().viewerPosY, hole.getZ() + 2 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, bedrockR, bedrockG, bedrockB, bedrockA, box.getValue(), outline.getValue()));
        doubleObsidianHolesX.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 2 - mc.getRenderManager().viewerPosX, hole.getY() + 1 - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, obsidianR, obsidianG, obsidianB, obsidianA, box.getValue(), outline.getValue()));
        doubleObsidianHolesZ.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + 1 - mc.getRenderManager().viewerPosY, hole.getZ() + 2 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, obsidianR, obsidianG, obsidianB, obsidianA, box.getValue(), outline.getValue()));
    }
}
