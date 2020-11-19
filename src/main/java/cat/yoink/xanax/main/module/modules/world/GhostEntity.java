package cat.yoink.xanax.main.module.modules.world;

import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;

public final class GhostEntity extends Module
{
    private final BooleanSetting pickaxeOnly = addSetting(new BooleanSetting("PickaxeOnly", true));

    public GhostEntity()
    {
        super("GhostEntity", Category.WORLD);
    }
}
