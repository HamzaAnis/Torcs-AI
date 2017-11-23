package ahuraDriver;

public enum extermumTypes {
	maximization, minimization;
	public static int toInt(extermumTypes type){
		switch (type) {
		case maximization:
			return -1;
		case minimization:
			return 1;
		default:
			return 1;
		}	
	}
}
