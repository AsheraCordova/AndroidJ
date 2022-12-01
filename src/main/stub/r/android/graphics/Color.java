package r.android.graphics;

public class Color {

	public static final int BLACK = 0;
	public static final int RED = 0xFFFF0000;

	public static int HSVToColor(float[] value) {
		return 0;
	}

	public static int alpha(int i) {
		return 0;
	}

    public static int parseColor(String colorString) {
        if (colorString.charAt(0) == '#') {
            // Use a long to avoid rollovers on #ffXXXXXX
            long color = Long.parseLong(colorString.substring(1), 16);
            if (colorString.length() == 7) {
                // Set the alpha value
                color |= 0x00000000ff000000;
            } else if (colorString.length() != 9) {
                throw new IllegalArgumentException("Unknown color");
            }
            return (int)color;
        }
        throw new IllegalArgumentException("Unknown color");
    }
    
    public static String formatColor(int intColor) {
    	String hexColor = String.format("#%06X", (0xFFFFFF & intColor));
    	return hexColor;
    }
}
