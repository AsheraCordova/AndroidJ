package r.android.os;

public interface IMessenger {

	class Stub implements IMessenger {

		static IMessenger asInterface(IBinder target) {
			return null;
		}

		@Override
		public void send(Message message) {
			
		}

		@Override
		public IBinder asBinder() {
			return null;
		}
		
	}

	void send(Message message);

	IBinder asBinder();

}
