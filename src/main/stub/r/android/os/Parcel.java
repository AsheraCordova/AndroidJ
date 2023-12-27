package r.android.os;

import java.util.List;

public interface Parcel {

	float readFloat();
	void writeFloat(float value);
	void readIntArray(int[] mGapPerSpan);
	List readArrayList(ClassLoader classLoader);
	void writeList(List mFullSpanItems);
	int readInt();
	void writeInt(int x);

}
