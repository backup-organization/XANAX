package cat.yoink.xanax.main.handling;

import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.module.ModuleManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public final class EventSubscriber
{
    @SubscribeEvent
    public void onInputKeyInput(InputEvent.KeyInputEvent event)
    {
        if (!Keyboard.getEventKeyState() || Keyboard.getEventKey() == Keyboard.KEY_NONE) return;

        ModuleManager.INSTANCE.getModules().stream().filter(module -> module.getBind() == Keyboard.getEventKey()).forEach(Module::toggle);
    }
}
