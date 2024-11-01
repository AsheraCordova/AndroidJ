package r.android.graphics;

public class Color {

	public static final int BLACK = 0xFF000000;
	public static final int RED = 0xFFFF0000;
	public static final int WHITE = 0xFFFFFFFF;
	public static final int TRANSPARENT = 0;

	public static int HSVToColor(float[] value) {
		return 0;
	}

	public static int alpha(int i) {
		return 0;
	}

    public static int parseColor(String colorString) {
        if (colorString.charAt(0) == '#') {
        	if (colorString.length() == 4) {
        		colorString = "#" + colorString.charAt(1) + "" + colorString.charAt(1) + "" +
        				colorString.charAt(2) + "" + colorString.charAt(2) + "" +
        				colorString.charAt(3) + "" + colorString.charAt(3) + "";
			}
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
    	String hexColor = String.format("#%08X", (0xFFFFFFFF & intColor));
    	return hexColor;
    }
}
