package ahuraDriver;

import ahuraDriver.Controller.Stage;


public class OpponentController1 {
	static double minDistOpponentSpeedReviser = 25.0;
	static double minDistOpponentSteerRevisr = 0.0;
	static double minBesideDistanceOvertake = 1.5;
	
	
	static double besideAlter = 0.2;
	
	static double carWidth = 1.9;
	static double carLength = 2.3*2*2;
	
	static double minSteerOvertake = 0.5;
	static double minSteerSlowDown = 0.8;
	
	static double previousDistance = -10;
	static double previousTime = -10;

	
	static double relativeSpeed = 0.0;
	static double minDistanceInfront = 10000.0;
	
	public static void relativeSpeedUpdater(MySensorModel sensors){
		
		minDistanceInfront = closestVehicleInfron(sensors);
		
		double rv = 0.0;
		if(previousDistance < 0){//the first time we come here the value is negative (initialized to -10)
			previousDistance = minDistanceInfront;
			previousTime = sensors.getCurrentLapTime();
		}else{
			//calculate relative speed
			double currentTime = sensors.getCurrentLapTime();
			double currentDist = minDistanceInfront;
			rv = (currentDist - previousDistance)/(currentTime - previousTime);
			rv=-rv;
			
			previousTime = sensors.getCurrentLapTime();
			previousDistance = minDistanceInfront;
			
		}
		
		relativeSpeed = rv>100 ? 100.0 : rv;
		
	}
	
	public static double opponentSpeedReviser(MySensorModel sensors, double targetSpeed, double estimatedTurn, double distInfront, double steer){
		if(sensors.getDamage() > 7000){
			minBesideDistanceOvertake = 2;
		}
		
		double minDist = minDistanceInfront;
		double rv = relativeSpeed;
		
//		double distToBrakeIn50 = 50.0;
//		if(sensors.getDamage() > 7000)
//			distToBrakeIn50 = 80.0;
		
		double minDistToBr = 7.0;
		double ac = 0.287;
		
//		double ac = ((distToBrakeIn50-minDistToBr)/(150.0 - 0.0));
		
		double distToStartbraking = ac*(rv)+minDistToBr;//relate relative speed to the distance to brake
//		distToStartbraking = 0;	
		if(minDist < distToStartbraking){
			if((estimatedTurn > minSteerSlowDown && distInfront < 20.0) || steer > minSteerSlowDown)//it is dangerous to slow down when you are turning, we are close to a turn
				return Math.min(sensors.getSpeed()*1.0, targetSpeed);		
			
			double coef = 0.95;
			if(sensors.getDamage() > 7000)
				coef = 0.5;

//			return targetSpeed;
			
			return Math.min(sensors.getSpeed()*coef, targetSpeed);			
		}
		
		int angBeside = maxSomeoneBeside(sensors, 4*minBesideDistanceOvertake + carWidth/2.0);
//		System.out.println(angBeside);
		if(angBeside != 18){//some one is beside us, go faster
//			System.out.println("beside detected, revising speed from " + targetSpeed + " to " + 1.1*targetSpeed);
			targetSpeed = 1.1*targetSpeed;
		}
		
		return targetSpeed;
	}
	
	public static double[] opponentSteerReviser(MySensorModel sensors, double s, double steer, double correctionSensors, Stage stage){
		if(stage.compareTo(Stage.BLOCKER)==0){
			double [] opponentInfo = sensors.getOpponentSensors();
			double alter=0.0;
			for(int i=3; i<11;++i){
				if(opponentInfo[i]<30){//there is someone in 20 meters on our left back
					alter=(sensors.getTrackPosition()+0.3);
					return new double [] {-alter, 10};
				}
					
			}
			for(int i=28; i<35;++i){
				if(opponentInfo[i]<30){//there is someone in 20 meters on our right back
					alter=(sensors.getTrackPosition()-0.3);
					return new double [] {-alter, 10};
				}
			}
			
			return new double [] {s, correctionSensors};
		}
		
		int angBeside = maxSomeoneBeside(sensors, minBesideDistanceOvertake + carWidth/2.0);
		
		double pos = -sensors.getTrackPosition();
	
		if(angBeside > 18){//someone is on the left side, forget overtaking, 
//			System.out.println("Someone beside");
			return new double [] {Math.max(pos-besideAlter, -0.95), 10};
		}else{
			if(angBeside < 18){
//				System.out.println("Someone beside");
				return new double [] {Math.min(pos+besideAlter, 0.95), 10};
			}
		}
		
		if(Math.abs(steer) > minSteerOvertake){//do not over take when you are turning intensely. it is dangerous to slow down when you are turning, we are close to a turn
			return new double [] {s, correctionSensors};
		}
		
		double minDistInf = minDistanceInfront;		
		double rv = relativeSpeed;	
		
		double distToStartOvertaking = (rv/2.0)+30.0;//when to start overtaking
		
		if(minDistInf > distToStartOvertaking){//too soon to prepare for overtaking
			return new double [] {s, correctionSensors};
		}
		
		int bestAngleToOvertake = bestVacantAngInfron(sensors);//the best angle that has a lot of empty space in front
//		System.out.println("Best angle: "+bestAngleToOvertake);
		double direction = Math.abs((18 - bestAngleToOvertake)*10.0);//convert the sensor index to angle
		double directionAlt = direction/(((100.0-1000.0)/(20.0 - 0.0))*(rv)+1000.0);//how much we should alter the direction?
		
		if(minDistInf < 15.0){//if we are very close it is better to alter a lot to overtake safely 
			directionAlt = Math.max(0.4, directionAlt);
		}
		
		double overTakeTurn = (sensors.getTrackPosition());
		
	
		if(bestAngleToOvertake > 18){//the best position is at our right
			overTakeTurn -= directionAlt; 
		}else{
			if(bestAngleToOvertake < 18)
				overTakeTurn += directionAlt;
		}

		overTakeTurn = Math.min(0.95, overTakeTurn);
		overTakeTurn = Math.max(-0.95, overTakeTurn);
		
//		System.out.println("Best postition: " + overTakeTurn + ", current: " + sensors.getTrackPosition());

		
		return new double [] {-overTakeTurn, 10};
	}
	
	private static double closestVehicleInfron(MySensorModel sensors){
		double [] opponentInfo = sensors.getOpponentSensors();
		double minDist = 100000.0;
		for(int i = 14; i<=22; ++i){//sensors in front
			double width = Math.abs(DriverControllerHelperE6.sinAngO[i]*opponentInfo[i]);

			if(width < 1.5*minBesideDistanceOvertake+carWidth/2.0){//it is infront of us				
				double distance = Math.abs(DriverControllerHelperE6.cosAngO[i]*opponentInfo[i]);
				if(distance < minDist)
					minDist = distance;
			}
		}
		return minDist;
	}
	
	private static int bestVacantAngInfron(MySensorModel sensors){
		double [] opponentInfo = sensors.getOpponentSensors();
//		System.out.format("0: %2.2f, 1: %2.2f, 2: %2.2f, 33: %2.2f, 34: %2.2f, 35: %2.2f " + opponentInfo[0], opponentInfo[1],opponentInfo[2],opponentInfo[33],opponentInfo[34],opponentInfo[35]);
//		System.out.println("16: "+opponentInfo[16] + ", 17: " + opponentInfo[17]+ ", 18: " +opponentInfo[18]+ ", 19: " +opponentInfo[19]+ ", 20: " +opponentInfo[20]);
//		System.out.println();
		
		int maxDistAng = 18;
		int baseAngle = 18;
		
		double avr = (opponentInfo[maxDistAng] + opponentInfo[maxDistAng + 1] + opponentInfo[maxDistAng - 1] + opponentInfo[maxDistAng + 2] + opponentInfo[maxDistAng - 2])/5.0; 
		double width = Math.abs(avr);
		
		for(int i = 0; i<=10; ++i){//sensors in front
			int j = (int) Math.ceil((double) i/2.0);
			j *= ((i%2)==0 ? 1 : -1);
			avr = (opponentInfo[j + baseAngle] + opponentInfo[j + baseAngle + 1] + opponentInfo[j + baseAngle - 1] + opponentInfo[j + baseAngle + 2] + opponentInfo[j + baseAngle - 2])/5.0; 

			if(width < Math.abs(avr)){
				maxDistAng = baseAngle + j;
				width = Math.abs(avr);
			}
		}
		return maxDistAng;
	}
	
	
//	private static int maxSomeoneInfron(MySensorModel sensors){
//		double [] opponentInfo = sensors.getOpponentSensors();
//		int ang = 18;
//		int finalAng = 18;
//		
//		double avr = (opponentInfo[ang] + opponentInfo[ang + 1] + opponentInfo[ang - 1])/3.0; 
//		double width = Math.abs(avr);
//		
//		for(int i = 0; i<=8; ++i){
//			int j = (int) Math.ceil((double) i/2.0);
//			j *= ((i%2)==0 ? 1 : -1);
//			
//			avr = (opponentInfo[j + ang] + opponentInfo[j + ang + 1] + opponentInfo[j + ang - 1])/3.0; 
//
//			if(width < Math.abs(avr)){
//				finalAng = ang + j;
//				width = Math.abs(avr);
//			}
//		}
//		return finalAng;
//	}
	
	private static int maxSomeoneBeside(MySensorModel sensors, double distance){
		double [] opponentInfo = sensors.getOpponentSensors();
		int ang = 18;
		int finalAng = 18;
		
		for(int i = 7; i<=33; ++i){
			int j = (int) Math.ceil((double) i/2.0);
			j *= ((i%2)==0 ? 1 : -1);
			double avr = (opponentInfo[j + ang]);
			
			double width = Math.abs(avr);
//			System.out.print(" " + width);
			if(width < distance){
				finalAng = ang + j;
				break;
			}
			
			
		}
//		System.out.println();
		
		return finalAng;
	}
		
}
