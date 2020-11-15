package cat.yoink.xanax.main.handling;

import net.minecraft.client.Minecraft;

public enum Mapping
{
    INSTANCE;

    public String cPacketAnimationHand = isObfuscated() ? "field_187019_a" : "hand";

    @SuppressWarnings("ALL")
    private boolean isObfuscated()
    {
        try { return Minecraft.class.getDeclaredField("instance") == null; }
        catch (Exception e) { return true; }
    }
}
