package ahuraDriver;
public enum DangerzoneCause {
	
	CarCrash,OutOfTrack,Jump;
	
	static DangerzoneCause fromInt(int value)
	{
		switch (value) {
		case 0:
			return CarCrash;
		case 1:
			return OutOfTrack;
		case 2:
			return Jump;
		default:
			return OutOfTrack;
		}			
	}
};