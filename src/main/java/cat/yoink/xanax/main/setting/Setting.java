package cat.yoink.xanax.main.setting;

public class Setting
{
    private final String name;
    private final Type type;

    public Setting(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public BooleanSetting toBoolean()
    {
        return (BooleanSetting) this;
    }

    public NumberSetting toNumber()
    {
        return (NumberSetting) this;
    }

    public EnumSetting toEnum()
    {
        return (EnumSetting) this;
    }

    public enum Type
    {
        BOOLEAN,
        NUMBER,
        ENUM
    }
}
