package cat.yoink.xanax.main.setting;

import java.util.Arrays;
import java.util.List;

public final class EnumSetting extends Setting {
    private final List<String> values;
    private int index;

    public EnumSetting(final String name, final String defaultValue, final String... values) {
        super(name);
        this.values = Arrays.asList(values);
        this.index = this.values.indexOf(defaultValue);
    }

    public String getValue() {
        return values.get(index);
    }

    public boolean is(final String mode) {
        return getValue().equalsIgnoreCase(mode);
    }

    public void cycleForward() {
        if (index < values.size() - 1) index++;
        else index = 0;
    }

    public void cycleBackward() {
        if (index > 0) index--;
        else index = values.size() - 1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public List<String> getValues() {
        return values;
    }
}
