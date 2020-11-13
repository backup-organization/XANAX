package cat.yoink.xanax.main.module;

import net.minecraft.util.ResourceLocation;

public enum Category
{
    COMBAT("Combat", new ResourceLocation("clickgui/combat.png")),
    MOVEMENT("Movement", new ResourceLocation("clickgui/move.png")),
    WORLD("World", new ResourceLocation("clickgui/world.png")),
    RENDER("Render", new ResourceLocation("clickgui/render.png")),
    MISC("Misc", new ResourceLocation("clickgui/misc.png")),
    COMPONENT("Component", new ResourceLocation("clickgui/component.png")),
    CLIENT("Client", new ResourceLocation("clickgui/client.png"));

    private final String name;
    private final ResourceLocation image;

    Category(String name, ResourceLocation image)
    {
        this.name = name;
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public ResourceLocation getImage()
    {
        return image;
    }
}
