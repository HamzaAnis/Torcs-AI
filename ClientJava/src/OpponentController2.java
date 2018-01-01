package ahuraDriver;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ahuraDriver.Controller.Stage;

public class OpponentController2 {
	static double minDistOpponentSpeedReviser = 25.0;
	static double minDistOpponentSteerRevisr = 0.0;
	static double minBesideDistanceOvertake = 1.0;
	static double minFrontBackDistanceOvertake = 2.0; 
	static double besideAlter = 0.2;
	static double carWidth = 1.9;
	static double carLength = 2*2;
	static double maxSteerOvertake = 0.5;
	static double minSteerSlowDown = 0.8;
	static double previousDistance = -10;
	static double previousTime = -10;
//	static double relativeSpeedCurrent = 0.0;
//	static double relativeSpeedPrevious = 0.0;
	static double minDistanceInfront = 10000.0;
	static List<Double> minDistances = new ArrayList<Double>();
	static double extrapolatedDist = 10000.0;
	static double timeForExtrapolatedDist = .03;//in seconds
	static double extrapolatedRelativeSpeed = 0.0;//meter per second
	static int timeStepToExtrapolate = 1;
	static List<Double> minDistancesLapTimes = new ArrayList<Double>();
	static int lengthOfInterpolData = 5;
	static double trackWidth = 15.0;
	static double previousS = 0.0;
	static double zigzagAlter = 0.5;
	public static double zigzaggerPos = 0.0;
	
	static OpponentPosition ourPosition = null;
	
	static List<OpponentPosition> opponentPositions = new ArrayList<OpponentPosition>();

	public static void opponentPositionsUpdater(MySensorModel sensors, ParametersContainerE6 myPara){
		double [] opponentInfo = sensors.getOpponentSensors();
		trackWidth = myPara.getTrackWidth();
		ourPosition = new OpponentPosition(sensors.getTrackPosition()*trackWidth/2.0, 0, carWidth);	
		opponentPositions.clear();

		for(int i = 0; i<36; ++i){
			if(opponentInfo[i] > 100.0)
				continue;
			
			OpponentPosition position = new OpponentPosition(opponentInfo[i], ((18 - i)*10.0), i, ourPosition.x, carWidth);
			opponentPositions.add(position);
			
		}
		Collections.sort(opponentPositions, new OpponentSort());
	}
	
	public static void opponentsInfoUpdater(MySensorModel sensors, ParametersContainerE6 myPara){
		opponentPositionsUpdater(sensors, myPara);		

		minDistanceInfront = closestVehicleInfron(sensors);
		
		minDistances.add(minDistanceInfront);
		minDistancesLapTimes.add(sensors.getCurrentLapTime());
		
		if(minDistances.size() > lengthOfInterpolData){
			minDistances.remove(0);
			minDistancesLapTimes.remove(0);
		}
		
		if(sensors.getCurrentLapTime() < 1.0){
			return;
		}
		
		extrapolatedDist = extrapolation(minDistances, minDistancesLapTimes, timeForExtrapolatedDist+sensors.getCurrentLapTime());

		double newExt = (extrapolatedDist-minDistanceInfront)/(timeForExtrapolatedDist);
		if(Math.abs(newExt - extrapolatedRelativeSpeed) > 100){// no big changes, these are noise
			newExt = Math.signum(newExt)*100.0;
		}
						
		extrapolatedRelativeSpeed = newExt;
	}
	
	public static  double[] opponentsDirectionUpdater(MySensorModel sensors, double steer, double s, double correctionSensors, Stage stage, ParametersContainerE6 myPara){
		
		if(stage.equals(Stage.ZIGZAGGER)){			
				correctionSensors = 10;
				
				if(Math.abs(sensors.getTrackPosition()) > 0.9){
					zigzagAlter *= -1.0;
				}else{
				}
				
				s = -(sensors.getTrackPosition() + zigzagAlter);
			return new double [] {zigzaggerPos, correctionSensors};//no overtaking
		}
		
		if(stage.compareTo(Stage.BLOCKER)==0){
			double [] opponentInfo = sensors.getOpponentSensors();

			double alter=0.0;
			for(int i=3; i<11;++i){
				if(opponentInfo[i]<30){//there is someone in 30 meters on our left back
					alter=(sensors.getTrackPosition()+0.3);
					return new double [] {-alter, 10};
				}
					
			}
			
			for(int i=28; i<35;++i){

				if(opponentInfo[i]<30){//there is someone in 30 meters on our right back
					alter=(sensors.getTrackPosition()-0.3);
					return new double [] {-alter, 10};
				}
			}
			
			return new double [] {s, correctionSensors};
		}
		
		double widthOfACar = carWidth;
		
		double xUs=ourPosition.x;
		double leftUs=ourPosition.getLeftWithSafety(minBesideDistanceOvertake);//our left
		double rightUs=ourPosition.getRightWithSafety(minBesideDistanceOvertake);//our left
				
		double omega1 = myPara.getParameterByName("Omega1");
		double omega2 = myPara.getParameterByName("Omega2");
		double omega3 = myPara.getParameterByName("Omega3");
		double omega4 = myPara.getParameterByName("Omega4");
		
		double maximumDistanceToConsiderOpponents = omega1*(-extrapolatedRelativeSpeed)+omega2;//myPara.getParameterByName("distOvertakey");//relate relative speed to the distance to overtake
		
		if(-extrapolatedRelativeSpeed<0.0){
			maximumDistanceToConsiderOpponents = 15.0;
		}

		
		double leftStart = trackWidth/2.0;
		double rightEnd = -trackWidth/2.0;

		for(OpponentPosition position  : opponentPositions){
			if(position.x > xUs && position.y > -(minFrontBackDistanceOvertake+carLength) && position.y < minFrontBackDistanceOvertake+carLength){
				//a left blocker
				leftStart = Math.min(position.rightOfTheCar, leftStart);					
			}
			if(position.x < xUs && position.y > -(minFrontBackDistanceOvertake+carLength) && position.y < minFrontBackDistanceOvertake+carLength){
				//a right blocker
				rightEnd = Math.max(position.leftOfTheCar, rightEnd);					
			}
			
		}
				
		
		List<Point2D.Double> vacancies = createVacancies(widthOfACar,
				maximumDistanceToConsiderOpponents, leftStart, rightEnd);
	
		double closestvacancyToUs = 10000.0;
		if(vacancies.size() == 1 && (vacancies.get(0).x-vacancies.get(0).y)>=trackWidth-0.5){
			previousS = s;
			return new double [] {s, correctionSensors};//We are almost alone in the track
		}
		
		if(vacancies.size() == 0){//no vacant space
			previousS = s;
			return new double [] {s, correctionSensors};
		}
		
		for(Point2D.Double a:vacancies){//check if we are already ok
			if(a.x > leftUs && a.y < rightUs){//we are already in a vacant position, so keep this position. Note that YOU ARE NOT ALONE, otherwise the previous return was already trigerred.
				double pos = positionToS(sensors.getTrackPosition());
				previousS = pos;
				return new double [] {pos, 10};
			}			
		}
		
		double whereToGo = vacanciesEvaluator(xUs, leftUs, rightUs, vacancies,
				closestvacancyToUs);
		
		whereToGo=whereToGo/(trackWidth/2.0);//normalize between -1 to 1, the same as trackPosition sensor
		
		whereToGo = Math.min(0.95, whereToGo);
		whereToGo = Math.max(-0.95, whereToGo);
		
		whereToGo = positionToS(whereToGo);
		double trackPos = positionToS(sensors.getTrackPosition());
		
		double overTakeTurn = (trackPos);
		
		double directionAlt = 0.0;//myPara.getParameterByName("opSteer"); 
		
		double manouver = omega3*(-extrapolatedRelativeSpeed)+omega4;//myPara.getParameterByName("distOvertakey");//relate relative speed to the distance to overtake 3.0;
		
		manouver = Math.min(manouver, 3.0);
		manouver = Math.max(manouver, 1.0);
		if(sensors.getSpeed() < 20){//slow speed can handle large manouvers
			manouver = 5.0;
		}
		
		
		directionAlt = Math.abs(whereToGo - trackPos)*manouver;

		
//		String s1 = "Manouver: " + manouver+", directionAlter: "+directionAlt+", pos: "+trackPos+", where: "+whereToGo;
//		String fileToSave = "Res.txt";
//		File f = new File(fileToSave);
//		FileWriter out = null;
//		try{
//			out=new FileWriter(f, true);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		s1 += "\n";
//	
//		try {
//			out.write(s1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			out.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		System.out.println("Manouver: " + manouver+", directionAlter: "+directionAlt+", pos: "+trackPos+", where: "+whereToGo);
		
		if(whereToGo > trackPos){//if it is on our left
			overTakeTurn += directionAlt; 
		}else{
			overTakeTurn -= directionAlt;
		}

		overTakeTurn = Math.min(0.95, overTakeTurn);
		overTakeTurn = Math.max(-0.95, overTakeTurn);
		previousS = overTakeTurn;
		
		return new double [] {overTakeTurn, 10};
		
	}

	private static double vacanciesEvaluator(double xUs, double leftUs,
			double rightUs, List<Point2D.Double> vacancies,
			double closestvacancyToUs) {
		double whereToGo = 0.0;


		double pRealPos = sToPosition(previousS);
		boolean stillValid = false;
		
		for(Point2D.Double a:vacancies){
			if(a.x > (pRealPos + 2.0) && a.y < (pRealPos - 2.0))
				stillValid = true;
			if(a.x > leftUs){//we are in from left, we need to take our right in.		
				if(a.y-rightUs<closestvacancyToUs){//how much we should go to left to make our right in.
					closestvacancyToUs = a.y-rightUs;
					whereToGo = xUs + closestvacancyToUs + 1.6;//go a bit more, let the current position becomes the best, these calculations have errors 
				}
			}else{//we are in from right, we need to take our left in.
				if(leftUs-a.x<closestvacancyToUs){
					closestvacancyToUs = leftUs-a.x;
					whereToGo = xUs - closestvacancyToUs - 1.6;
				}
			}
		}
		
		if(stillValid){//try to keep the previously calculated tentative position if still valid
			whereToGo = pRealPos;
		}
		
		return whereToGo;
	}

	private static List<Point2D.Double> createVacancies(double widthOfACar,
			double maximumDistanceToConsiderOpponents, double leftStart,
			double rightEnd) {
		List<Point2D.Double> vacancies = new ArrayList<Point2D.Double>();//it will contain<x,y> that are left and right of vacancies in the track.
		double pX= leftStart;
		
		for(OpponentPosition position:opponentPositions){			
			if(position.y > maximumDistanceToConsiderOpponents)//do not add the ones that are very far
				continue;
			
			if(position.y < -(minFrontBackDistanceOvertake+carLength))//do not add the ones that are at the back and sufficiently far 
				continue;			
			
			if(position.rightOfTheCar > trackWidth/2.0 || position.leftOfTheCar < -trackWidth/2.0)
				continue;//the opponent is out of the track
			
			if(widthOfACar < pX - position.leftOfTheCar){//if the vacant slot is larger than the car width then add it 			
				vacancies.add(new Point2D.Double(pX, position.leftOfTheCar));
			}
			
			pX = position.rightOfTheCar;			
		}

		if(widthOfACar < pX - rightEnd){//if the vacant slot is larger than the car width then add it 			
			vacancies.add(new Point2D.Double(pX, rightEnd));
		}
		return vacancies;
	}
	
	public static double opponentSpeedReviser(MySensorModel sensors, double targetSpeed, double estimatedTurn, double distInfront, double steer, ParametersContainerE6 myPara){
		if(myPara.getStage().equals(Stage.ZIGZAGGER)){
			return 100;
		}
		
		if(sensors.getDamage() > 7000){
			minBesideDistanceOvertake = 2;
		}
		
		if(Math.abs(steer) > maxSteerOvertake)//do not over take when you are turning intensely. it is dangerous to slow down when you are turning, we are close to a turn
			return Math.min(sensors.getSpeed()*1.0, targetSpeed);
				
		double minDist = minDistanceInfront;
		double rv = -extrapolatedRelativeSpeed;		
		
		if(minDist < 5.5){//someone is very close to us, we didnt detect that for some reasons
			return Math.min(sensors.getSpeed()*.9, targetSpeed);
		}
		
		double Omega5 = myPara.getParameterByName("Omega5");
//		double Omega6 = myPara.getParameterByName("Omega6");
		
		double distToStartbraking = Omega5;//relate relative speed to the distance to brake
		
		if(minDist < distToStartbraking){
			if((estimatedTurn > minSteerSlowDown && distInfront < 20.0) || steer > minSteerSlowDown)//it is dangerous to slow down when you are turning, we are close to a turn
				return Math.min(sensors.getSpeed()*1.0, targetSpeed);		
			
			if(sensors.getDamage() > 7000)
				rv*=1.5;
			
			return Math.min(sensors.getSpeed()-rv, targetSpeed);
		}
		
		int angBeside = maxSomeoneBeside(sensors, 4*minBesideDistanceOvertake + carWidth/2.0);
		if(angBeside != 18){//some one is beside us, go faster
			targetSpeed = 1.1*targetSpeed;
		}
		
		return targetSpeed;
	}
	
	private static double closestVehicleInfron(MySensorModel sensors){
		double minDist = 100000.0;
//		
		for(OpponentPosition position : opponentPositions){
			
			if(position.y > 0){
				if(!((position.rightOfTheCar > ourPosition.getLeftWithSafety(minBesideDistanceOvertake/2)) || (position.leftOfTheCar < ourPosition.getRightWithSafety(minBesideDistanceOvertake/2)))){
					minDist = Math.min(position.y, minDist);
				}
				
			}
		}

		return minDist;
	}
	
	private static int maxSomeoneBeside(MySensorModel sensors, double distance){
		double [] opponentInfo = sensors.getOpponentSensors();
		int ang = 18;
		int finalAng = 18;
		
		for(int i = 7; i<=33; ++i){
			int j = (int) Math.ceil((double) i/2.0);
			j *= ((i%2)==0 ? 1 : -1);
			double avr = (opponentInfo[j + ang]);
			
			double width = Math.abs(avr);
			if(width < distance){
				finalAng = ang + j;
				break;
			}
			
			
		}
		return finalAng;

	}
	
	static private double extrapolation(List<Double> ys, List<Double> xs, double x){
		
		double y = 0;
		if(ys.size() < 2){
			return ys.get(0);
		}
		for(int i = 0; i < ys.size(); ++i){
			double denom = 1;
			double nom = 1;
			for(int j = 0; j < ys.size(); ++j){
				if(j==i)
					continue;
				denom*=(xs.get(i)-xs.get(j));
			}
			
			for(int j = 0; j < ys.size(); ++j){
				if(j==i)
					continue;
				nom*=(x-xs.get(j));
			}
			
			y+=((nom/denom)*ys.get(i));
		}
		return y;
	}
	
	static double positionToS(double p){
		return Math.log((2.0/(p+1))-1)/Math.log(16.0);
	}
	
	static double sToPosition(double s){
		return (2.0/(Math.pow(16, s)+1))-1;
	}
	
}

class OpponentSort implements Comparator<OpponentPosition> {
    @Override
    public int compare(OpponentPosition a, OpponentPosition b) {
        return a.x < b.x ? 1 : a.x == b.x ? 0 : -1;
    }
}
