package cat.yoink.xanax.mixin;

import cat.yoink.xanax.main.module.ModuleManager;
import cat.yoink.xanax.main.setting.BooleanSetting;
import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(EntityRenderer.class)
public class EntityRendererPatch
{
    @Redirect(method = "getMouseOver", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate<? super Entity> predicate)
    {
        return ModuleManager.INSTANCE.getModule("GhostEntity").isEnabled() ? ((BooleanSetting) ModuleManager.INSTANCE.getSetting("GhostEntity", "PickaxeOnly")).getValue() ? Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemPickaxe ? new ArrayList<>() : worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate) : new ArrayList<>() : worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }
}
