package cat.yoink.xanax.main.setting;

import java.util.Arrays;
import java.util.List;

public class EnumSetting extends Setting
{
    private int index;
    private final List<String> values;

    public EnumSetting(String name, String defaultValue, String... values)
    {
        super(name, Type.ENUM);
        this.values = Arrays.asList(values);
        this.index = this.values.indexOf(defaultValue);
    }

    public String getValue()
    {
        return values.get(index);
    }

    public boolean is(String mode)
    {
        return index == mode.indexOf(mode);
    }

    public void cycleForward()
    {
        if (index < values.size() - 1) index++;
        else index = 0;
    }

    public void cycleBackward()
    {
        if (index > 0) index--;
        else index = values.size() - 1;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public List<String> getValues()
    {
        return values;
    }
}
