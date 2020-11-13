package cat.yoink.xanax.main.setting;

public final class NumberSetting extends Setting
{
    private double value;
    private final double minimum;
    private final double maximum;
    private final double increment;

    public NumberSetting(String name, double value, double minimum, double maximum, double increment)
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

    public void setValue(double value)
    {
        double precision = 1 / increment;
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
