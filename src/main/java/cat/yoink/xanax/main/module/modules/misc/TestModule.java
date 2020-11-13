package cat.yoink.xanax.main.module.modules.misc;

import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class TestModule extends Module
{
    public TestModule()
    {
        super("Test", Category.MISC);
        setBind(Keyboard.KEY_G);
    }

    @Override
    protected void onEnable()
    {
        System.out.println("Enabled");
    }

    @Override
    protected void onDisable()
    {
        System.out.println("Disable");
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        if (nullCheck()) return;

        System.out.println(event.getEntity().getName() + " died");
    }
}
