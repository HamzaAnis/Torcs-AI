package ai.project;

import java.util.ArrayList;
import java.util.List;


public class SimpleDriver {

	public static int memorySensorLength = 1;
	public static int[]  gearUp={9000,9000,9000,9500,9500,0};
	public static int[]  gearDown={0,3300,6200,7000,7300,7700};

	public static double maxSensorRangeProximity = 180;
	public static List<Double> previousAngles = new ArrayList<Double>();
	public static int maxSizeAnglesTrak = 10;

	public static float [] angles = {-90,-75,-50,-35,-20,-15,-10,-5,-1,0,1,5,10,15,20,35,50,75,90};
		
	public static double [] cosAng = {6.12323399573677e-17,0.258819045102521,0.642787609686539,0.819152044288992,0.939692620785908,0.965925826289068,0.984807753012208,0.996194698091746,0.999847695156391,1,0.999847695156391,0.996194698091746,0.984807753012208,0.965925826289068,0.939692620785908,0.819152044288992,0.642787609686539,0.258819045102521,6.12323399573677e-17};

	public static double [] sinAng = {-1,-0.965925826289068,-0.766044443118978,-0.573576436351046,-0.342020143325669,-0.258819045102521,-0.173648177666930,-0.0871557427476582,-0.0174524064372835,0,0.0174524064372835,0.0871557427476582,0.173648177666930,0.258819045102521,0.342020143325669,0.573576436351046,0.766044443118978,0.965925826289068,1};

	public static double [] sinAngO = {1.22464679914735e-16,0.173648177666930,0.342020143325669,0.500000000000000,0.642787609686540,0.766044443118978,0.866025403784439,0.939692620785908,0.984807753012208,1,0.984807753012208,0.939692620785908,0.866025403784439,0.766044443118978,0.642787609686539,0.500000000000000,0.342020143325669,0.173648177666930,0,-0.173648177666930,-0.342020143325669,-0.500000000000000,-0.642787609686539,-0.766044443118978,-0.866025403784439,-0.939692620785908,-0.984807753012208,-1,-0.984807753012208,-0.939692620785908,-0.866025403784439,-0.766044443118978,-0.642787609686540,-0.500000000000000,-0.342020143325669,-0.173648177666930,-1.22464679914735e-16};
	 
	public static double [] cosAngO = {-1,-0.984807753012208,-0.939692620785908,-0.866025403784439,-0.766044443118978,-0.642787609686539,-0.500000000000000,-0.342020143325669,-0.173648177666930,6.12323399573677e-17,0.173648177666930,0.342020143325669,0.500000000000000,0.642787609686539,0.766044443118978,0.866025403784439,0.939692620785908,0.984807753012208,1,0.984807753012208,0.939692620785908,0.866025403784439,0.766044443118978,0.642787609686539,0.500000000000000,0.342020143325669,0.173648177666930,6.12323399573677e-17,-0.173648177666930,-0.342020143325669,-0.500000000000000,-0.642787609686539,-0.766044443118978,-0.866025403784439,-0.939692620785908,-0.984807753012208,-1};
	
	public static int zeroAngle = 9;

	public static double asrSlip= 1.0;
	public static double asrRange= 1.0;
	public static double asrMinSpeed = 150.0;
	
	/* ABS Filter Constants */
	public static float wheelRadius[]={(float) 0.3179,(float) 0.3179,(float) 0.3276,(float) 0.3276};

	/* Steering constants*/
	public static float steerLock=(float) 0.785398;
	public static float steerSensitivityOffset=(float) 80.0;
	public static float wheelSensitivityCoeff=1;
	public static double steeringMinimumTurn = 0.01;

	/* Clutching Constants */
	public static float clutchMax=(float) 0.5;
	public static float clutchDelta=(float) 0.05;
	public static float clutchRange=(float) 0.82;
	public static float	clutchDeltaTime=(float) 0.02;
	public static float clutchDeltaRaced=10;
	public static float clutchDec=(float) 0.01;
	public static float clutchMaxModifier=(float) 1.3;
	public static float clutchMaxTime=(float) 1.5;
	
	private static double sin1 = Math.sin(Math.PI/180.0);
	private static double cos1 = Math.cos(Math.PI/180.0);
	
	public static int extermumIndexAngle(double [] in, extermumTypes type){
		int indx = 0; 
		for (int i=0;i<in.length;++i){
						
			if(((double)extermumTypes.toInt(type))*in[indx]>((double)extermumTypes.toInt(type))*in[i]){
				indx = i;
			}else{
				if(((double)extermumTypes.toInt(type))*in[indx]==((double)extermumTypes.toInt(type))*in[i]){
					if(Math.abs(angles[indx]) > Math.abs(angles[i]))
						indx = i;
				}
			}
		}
		if(Math.abs(angles[indx]) < 2)
			indx = zeroAngle;
		return indx;
	}

	
	public static double degreeToRadian(double in){
		return in*Math.PI/180.0;
	}
	
	public static double radianToDegree(double in){
		return in*180.0/Math.PI;
	}
	
	public static double maximumDistanceInfront(double[] proximities){
		return proximities[zeroAngle];
	}
	
	public static void addToAngles(double angle){
		if(previousAngles.size() > maxSizeAnglesTrak){
			previousAngles.remove(0);
		}
		previousAngles.add(angle);
	}

	/**
	 * @param minimumY: minimum value that the curve can converge to
	 * @param maximumY: maximum value that the curve can converge to
	 * @param minimumXvalue: the value of x that has the minimumY
	 * @param maximumXvalue: the value of x that has the maximumY
	 * @param x: the current x that it y is needed
	 * @return
	 */
	public static double logSig(double minY,  double maxY, double minX, double maxX, double percent, double x){
		if(minY>maxY){
			double t = maxY;
			maxY=minY;
			minY=t;
			t=maxX;
			maxX=minX;
			minX=t;
		}
		double c = Math.log(((maxY-minY/percent)*(percent*maxY-minY))/((-minY+minY/percent)*(maxY-percent*maxY)))/(minX-maxX);
		double d = -maxX+Math.log(((maxY-minY)/(percent*maxY-minY))-1.0)/c;
		double res= logSig(maxY, minY, c, d, x); 
		return res;
	}
	
	public static double logSig(double maxY,  double minY, double c, double d, double x){
		double a= maxY-minY;
		double b = 1.0;
		double res = ((a)/(b+Math.exp(c*(x+d)))+minY);
		res = Math.round(res*1000.0)/1000.0;
		return res;
	}
	
	public static double angleToDistanceInterpolate(double angle, double[] dist){
		int i = 0;
		for(i=0;i<angles.length; ++i){
			if(angle<=angles[i]){
				break;
			}
		}
		
				
		if(angle == angles[i] || i <= 0 || i >= angles.length - 1)
			return dist[i];
		
		double al = dist[i-1];
		double ar = dist[i+1];
		double ac = dist[i];
		double a = (al+ar)/50.0;
		double c = ac;
		double b = (a*25.0-al)/5.0;
		
		return (a*angle*angle+b*angle+c);
		
	}
	
	public static double trapasoide(double a, double b, double c, double d, double inp){
		if(inp < a || inp > d){
			return 0.0;
		}
		
		if(inp > b && inp < c){
			return 1.0;
		}
		
		if(inp > a && inp < b){
			return (((1.0-0.0)/(b-a))*(inp-a));
		}
		
		return ((0.0-1.0)/(d-c))*(inp-c)+1.0;
	}
	
	public static double bell(double a, double b, double c, double inp){
		return ((1.0)/(1.0+(Math.abs(Math.pow((inp-c)/a, 2.0*b)))));
	}
	
	public static double turnDirectionCalculator(MySensorModel sensors,
			int maxDistanceSensorIndx) {
		
		double distLeft = sensors.getTrackEdgeSensors()[DriverControllerHelperE6.zeroAngle - 1];//DriverControllerHelperE4.angleToDistanceInterpolate(leftAngl, sensors.getTrackEdgeSensors());
		double distRight = sensors.getTrackEdgeSensors()[DriverControllerHelperE6.zeroAngle + 1];//DriverControllerHelperE4.angleToDistanceInterpolate(rightAngl, sensors.getTrackEdgeSensors());
		double distBase = sensors.getTrackEdgeSensors()[DriverControllerHelperE6.zeroAngle];//DriverControllerHelperE4.angleToDistanceInterpolate(baseAngl, sensors.getTrackEdgeSensors());
		double sinAngle = 0.0;
		double k = 0.0;
		
		if(distRight > distLeft){
			k = distRight*sin1/(distBase-distRight*cos1);
		}else{
			k = distBase*sin1/(distLeft-distBase*cos1);
	    }
		sinAngle = Math.atan(k);
		
		return sinAngle;
	
	}
	
	public static boolean isTowardsInsideTheTrack(MySensorModel sensors){
		return sensors.getTrackPosition()*sensors.getAngleToTrackAxis() > 0;
	}
	public static boolean isOnTheLeftHandSide(MySensorModel sensors){
		return Math.abs(sensors.getTrackPosition()) > 0.0 ? sensors.getTrackPosition() > 0 : false;
	}
	public static boolean isInTheCorrectDirection(MySensorModel sensor){
		return Math.abs(sensor.getAngleToTrackAxis()) <= Math.PI/2; 
	}
}
