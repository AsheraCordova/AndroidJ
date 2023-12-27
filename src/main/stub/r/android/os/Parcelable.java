package r.android.os;

import r.android.graphics.RectF;

public interface Parcelable {
	public class Creator<T> {

		public T createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return null;
		}

		public T[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}

	}
	int describeContents();
	void writeToParcel(Parcel out, int flags);
}
