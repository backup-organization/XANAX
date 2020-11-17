package cat.yoink.xanax.mixin;

import cat.yoink.xanax.main.event.CollisionEvent;
import cat.yoink.xanax.main.event.WaterPushEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerPatch
{
    @Inject(method = "applyEntityCollision", at = @At("HEAD"), cancellable = true)
    public void applyEntityCollision(Entity entity, CallbackInfo ci)
    {
        CollisionEvent event = new CollisionEvent(entity);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) ci.cancel();
    }

    @Inject(method = "isPushedByWater", at = @At("HEAD"), cancellable = true)
    public void isPushedByWater(CallbackInfoReturnable<Boolean> cir)
    {
        WaterPushEvent event = new WaterPushEvent();
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) cir.setReturnValue(false);
    }
}
