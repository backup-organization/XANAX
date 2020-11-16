package cat.yoink.xanax.main.module.modules.render;

import cat.yoink.xanax.main.util.Mapping;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public final class Animations extends Module
{
    public Animations()
    {
        super("Animations", Category.RENDER);
    }

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (nullCheck()) return;

        try
        {
            Field prevEquippedProgressMainHand = Mapping.getField(ItemRenderer.class, Mapping.itemRendererPrevEquippedProgressMainHand);
            Field prevEquippedProgressOffHand = Mapping.getField(ItemRenderer.class, Mapping.itemRendererPrevEquippedProgressOffHand);
            Field equippedProgressMainHand = Mapping.getField(ItemRenderer.class, Mapping.itemRendererEquippedProgressMainHand);
            Field equippedProgressOffHand = Mapping.getField(ItemRenderer.class, Mapping.itemRendererEquippedProgressOffHand);
            Field itemStackMainHand = Mapping.getField(ItemRenderer.class, Mapping.itemRendererItemStackMainHand);
            Field itemStackOffHand = Mapping.getField(ItemRenderer.class, Mapping.itemRendererItemStackOffHand);

            prevEquippedProgressMainHand.setAccessible(true);
            prevEquippedProgressOffHand.setAccessible(true);
            equippedProgressMainHand.setAccessible(true);
            equippedProgressOffHand.setAccessible(true);
            itemStackMainHand.setAccessible(true);
            itemStackOffHand.setAccessible(true);

            if (prevEquippedProgressMainHand.getFloat(mc.entityRenderer.itemRenderer) >= 0.9)
            {
                equippedProgressMainHand.set(mc.entityRenderer.itemRenderer, 1);
                itemStackMainHand.set(mc.entityRenderer.itemRenderer, mc.player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if (prevEquippedProgressOffHand.getFloat(mc.entityRenderer.itemRenderer) >= 0.9)
            {
                equippedProgressOffHand.set(mc.entityRenderer.itemRenderer, 1);
                itemStackOffHand.set(mc.entityRenderer.itemRenderer, mc.player.getHeldItem(EnumHand.OFF_HAND));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
