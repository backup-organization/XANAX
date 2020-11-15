package cat.yoink.xanax.main.handling;

import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;

public enum Mapping
{
    INSTANCE;

    public final String cPacketAnimationHand = isObfuscated() ? "field_187019_a" : "hand";
    public final String itemRendererPrevEquippedProgressMainHand = isObfuscated() ? "field_187470_g" : "prevEquippedProgressMainHand";
    public final String itemRendererPrevEquippedProgressOffHand = isObfuscated() ? "field_187472_i" : "prevEquippedProgressOffHand";
    public final String itemRendererEquippedProgressMainHand = isObfuscated() ? "field_187469_f" : "equippedProgressMainHand";
    public final String itemRendererEquippedProgressOffHand = isObfuscated() ? "field_187471_h" : "equippedProgressOffHand";
    public final String itemRendererItemStackMainHand = isObfuscated() ? "field_187467_d" : "itemStackMainHand";
    public final String itemRendererItemStackOffHand = isObfuscated() ? "field_187468_e" : "itemStackOffHand";

    public Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException
    {
        return clazz.getDeclaredField(fieldName);
    }

    @SuppressWarnings("ALL")
    private boolean isObfuscated()
    {
        try { return Minecraft.class.getDeclaredField("instance") == null; }
        catch (Exception e) { return true; }
    }
}
