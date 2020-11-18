package cat.yoink.xanax.main.event;

import cat.yoink.xanax.core.Main;
import cat.yoink.xanax.main.MinecraftInstance;
import cat.yoink.xanax.main.event.events.*;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.module.ModuleManager;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import static net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public final class EventHandler implements MinecraftInstance
{
    @SubscribeEvent
    public void onInputKeyInput(InputEvent.KeyInputEvent event)
    {
        Main.EVENT_BUS.dispatchEvent(new KeyboardEvent(Keyboard.getEventKey(), Keyboard.getEventKeyState()));

        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() != Keyboard.KEY_NONE) ModuleManager.INSTANCE.getModules().stream().filter(module -> module.getBind() == Keyboard.getEventKey()).forEach(Module::toggle);
    }

    @SubscribeEvent
    public void onTickClientTick(ClientTickEvent event)
    {
        if (!nullCheck()) Main.EVENT_BUS.dispatchEvent(new TickEvent());
    }

    @SubscribeEvent
    public void onAttackEntity(net.minecraftforge.event.entity.player.AttackEntityEvent event)
    {
        if (!nullCheck() && Main.EVENT_BUS.dispatchEvent(new AttackEntityEvent(event.getEntityPlayer())).isCancelled())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event)
    {
        if (!event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) return;
        if (!nullCheck() && Main.EVENT_BUS.dispatchEvent(new Render2DEvent()).isCancelled())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPlayerSPPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event)
    {
        if (!nullCheck() && Main.EVENT_BUS.dispatchEvent(new BlockPushEvent()).isCancelled())
            event.setCanceled(true);
    }

    protected final boolean nullCheck()
    {
        return mc.player == null || mc.world == null;
    }
}
