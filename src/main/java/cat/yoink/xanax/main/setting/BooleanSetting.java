package cat.yoink.xanax.main.setting;

public final class BooleanSetting extends Setting
{
    private boolean value;

    public BooleanSetting(final String name, final boolean value)
    {
        super(name);
        this.value = value;
    }

    public boolean getValue()
    {
        return value;
    }

    public void setValue(final boolean value)
    {
        this.value = value;
    }

    public void enable()
    {
        value = true;
    }

    public void disable()
    {
        value = false;
    }

    public void toggle()
    {
        value = !value;
    }
}
