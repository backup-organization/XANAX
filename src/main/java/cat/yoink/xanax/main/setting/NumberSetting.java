package cat.yoink.xanax.main.setting;

public final class NumberSetting extends Setting
{
    private final double minimum;
    private final double maximum;
    private final double increment;
    private double value;

    public NumberSetting(final String name, final double value, final double minimum, final double maximum, final double increment)
    {
        super(name);
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(final double value)
    {
        final double precision = 1 / increment;
        this.value = Math.round(Math.max(minimum, Math.min(maximum, value)) * precision) / precision;
    }

    public void increment()
    {
        setValue(value + increment);
    }

    public void decrement()
    {
        setValue(value - increment);
    }

    public double getMinimum()
    {
        return minimum;
    }

    public double getMaximum()
    {
        return maximum;
    }

    public double getIncrement()
    {
        return increment;
    }
}
