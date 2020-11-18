package cat.yoink.xanax.mixin;

import cat.yoink.xanax.Manager;
import cat.yoink.xanax.main.event.events.CollisionEvent;
import cat.yoink.xanax.main.event.events.WaterPushEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
        Manager.EVENT_BUS.dispatchEvent(event);

        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "isPushedByWater", at = @At("HEAD"), cancellable = true)
    public void isPushedByWater(CallbackInfoReturnable<Boolean> cir)
    {
        WaterPushEvent event = new WaterPushEvent();
        Manager.EVENT_BUS.dispatchEvent(event);

        if (event.isCancelled()) cir.setReturnValue(false);
    }
}
