package cat.yoink.xanax.main.module.modules.client;

import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.EnumSetting;
import org.lwjgl.input.Keyboard;

public final class ClickGUI extends Module {
    private final BooleanSetting outline = addSetting(new BooleanSetting("Outline", false));
    private final EnumSetting closing = addSetting(new EnumSetting("Closing", "Keyboard", "Keyboard", "Button", "Both"));

    public ClickGUI() {
        super("ClickGUI", Category.CLIENT);
        setBind(Keyboard.KEY_RSHIFT);
    }

    @Override
    protected void onEnable() {
        mc.displayGuiScreen(cat.yoink.xanax.main.clickgui.ClickGUI.INSTANCE);
    }
}
