package r.com.android.internal.util;

public class Preconditions {
    public static float checkArgumentInRange(float value, float lower, float upper,
            String valueName) {
        if (Float.isNaN(value)) {
            throw new IllegalArgumentException(valueName + " must not be NaN");
        } else if (value < lower) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s is out of range of [%f, %f] (too low)", valueName, lower, upper));
        } else if (value > upper) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s is out of range of [%f, %f] (too high)", valueName, lower, upper));
        }
        return value;
    }
    
    public static int checkArgumentInRange(int value, int lower, int upper,
            String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s is out of range of [%d, %d] (too low)", valueName, lower, upper));
        } else if (value > upper) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s is out of range of [%d, %d] (too high)", valueName, lower, upper));
        }
        return value;
    }
    
    public static int checkArgumentNonnegative(final int value,
            final String errorMessage) {
        if (value < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }
    
    public static int checkArgumentNonnegative(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        return value;
    }

}
