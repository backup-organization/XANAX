package cat.yoink.xanax.main.module.modules.world;

import cat.yoink.xanax.main.event.events.EntitiesInAABBEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public final class GhostEntity extends Module
{
    private final BooleanSetting pickaxeOnly = addSetting(new BooleanSetting("PickaxeOnly", true));
    private final BooleanSetting blocksOnly = addSetting(new BooleanSetting("BlocksOnly", true));

    public GhostEntity()
    {
        super("GhostEntity", Category.WORLD);
    }

    @Listener
    private void entitiesEvent(final EntitiesInAABBEvent event)
    {
        if (pickaxeOnly.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe)
            event.setCancelled(true);

        if (blocksOnly.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)
            event.setCancelled(true);

        if (!pickaxeOnly.getValue() && !blocksOnly.getValue()) event.setCancelled(true);
    }
}
