package utility;

public class Clamp {

    private Clamp(){}

    /**
     * Clamps a value between an upper and lower bound
     * 
     * 
     * @param value the value to clamp
     * @param min the minimum value is the smallest (most negative) value. This is the lower bound in the range of allowed values
     * @param max The maximum value is the largest (most positive) expression value to which the value of the property will be assigned
     * @return
     */
    public static int clamp(int value, int min, int max) {
        if(min > max) throw new Error("min: " + min + " was greater than max: " + max);
        return Math.min(Math.max(value, min), max);
    }
}
