package ahuraDriver;

public class OpponentPosition {
	public double x;
	public double y;
	public double leftOfTheCar;
	public double rightOfTheCar;
	public double r;
	public double tetha;
	public int angleIndex;
	
	double carHalfLength = 1.0;
	
	public OpponentPosition(double r, double tetha, int angleIndex, double relativeX, double carWidth){
		carHalfLength = carWidth/2.0;
		this.r=r;
		this.tetha=tetha;
		this.angleIndex = angleIndex;
		x=relativeX + DriverControllerHelperE6.sinAngO[angleIndex]*r;
		y=DriverControllerHelperE6.cosAngO[angleIndex]*r;
		leftOfTheCar = x + carHalfLength;
		rightOfTheCar = x - carHalfLength;
	}

	public OpponentPosition(double x, double y, double carWidth){
		carHalfLength = carWidth/2.0;
		this.r=0;
		this.tetha=0;
		this.angleIndex = -1;
		this.x=x;
		this.y=y;
		leftOfTheCar = x + carHalfLength;
		rightOfTheCar = x - carHalfLength;
	}
	
	public double getLeftWithSafety(double safetyDist){
		return leftOfTheCar+safetyDist;
	}
	
	public double getRightWithSafety(double safetyDist){
		return rightOfTheCar-safetyDist;
	}
	
	
	public String toString(){
		return String.format("x: %2.2f, y: %2.2f, tetha: %2.2f, r: %2.2f, left: %2.2f, right: %2.2f", x, y, tetha, r, leftOfTheCar, rightOfTheCar);
	}
}
