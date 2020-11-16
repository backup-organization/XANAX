package cat.yoink.xanax.main.util;

import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;

public final class Mapping
{ // TODO: 11/16/2020 replace with access transformer 
    public static final String cPacketAnimationHand = isObfuscated() ? "field_187019_a" : "hand";
    public static final String itemRendererPrevEquippedProgressMainHand = isObfuscated() ? "field_187470_g" : "prevEquippedProgressMainHand";
    public static final String itemRendererPrevEquippedProgressOffHand = isObfuscated() ? "field_187472_i" : "prevEquippedProgressOffHand";
    public static final String itemRendererEquippedProgressMainHand = isObfuscated() ? "field_187469_f" : "equippedProgressMainHand";
    public static final String itemRendererEquippedProgressOffHand = isObfuscated() ? "field_187471_h" : "equippedProgressOffHand";
    public static final String itemRendererItemStackMainHand = isObfuscated() ? "field_187467_d" : "itemStackMainHand";
    public static final String itemRendererItemStackOffHand = isObfuscated() ? "field_187468_e" : "itemStackOffHand";

    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException
    {
        return clazz.getDeclaredField(fieldName);
    }

    @SuppressWarnings("ALL")
    private static boolean isObfuscated()
    {
        try { return Minecraft.class.getDeclaredField("instance") == null; }
        catch (Exception e) { return true; }
    }
}
