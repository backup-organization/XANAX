package cat.yoink.xanax.mixin;

import cat.yoink.xanax.EntryPoint;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftPatch
{
    @Inject(method = "init", at = @At("RETURN"))
    private void init(CallbackInfo ci)
    {
        EntryPoint.INSTANCE.initialize();
    }
}
