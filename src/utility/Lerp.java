package utility;

/**
 * Linear Interpolation - also known as "lerp" or "mix".
 */
public class Lerp {

    private Lerp() {}

    public static float calculate(float start, float end, float t){
        return start * (1-t) + end * t;
    }
}
