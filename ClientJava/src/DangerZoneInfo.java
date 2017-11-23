package ahuraDriver;
class DangerZoneInfo{
	double location;
	int severity;
	DangerzoneCause cause;
	double speedFactor = 1.0;
	
	public DangerZoneInfo(double x, int y, DangerzoneCause cause) {
		// TODO Auto-generated constructor stub
		this.location = x;
		this.cause = cause;
		this.severity = y;
		double coef=((0.70-1.0)/(3))*((double)severity)+1.0;
		speedFactor=Math.min(1.0, Math.max(coef, 0.70));
//		System.out.println("Added danger zone at " + location + " caused by " + cause);

	}
	public boolean severityUpdate(double location){
		if(location>this.location-20 && location<this.location+20){
			this.severity++;
			this.location=Math.min(this.location, location);//the one that is smaller (closer to the start line) is better to be cautious about.
//			System.out.println("Updated danger zone at " + location + " caused by " + cause);
			
			double coef=((0.70-1.0)/(3))*((double)severity)+1.0;
			speedFactor=Math.min(1.0, Math.max(coef, 0.70));
			
			return true;
		}
		return false;
	}
}