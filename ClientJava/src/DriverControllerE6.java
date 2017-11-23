package ahuraDriver;

import java.util.ArrayList;
import java.util.List;


public class DriverControllerE6 extends Controller{
//	private float clutch = 0;
	
	SpeedControllerE6 speedController = new SpeedControllerE6();
	DirectionControllerE6 directionController = new DirectionControllerE6();
	List<SensorModel> sensorList = new ArrayList<SensorModel>();  
	double totalDistance = 0.0;
	double prevLapTime = 0.0;
	double totalTime = 0.0;
	double damage = 0.0;
	float clutch = DriverControllerHelperE6.clutchMax;
	int lapCounter = 1;
	boolean lastLapTimeCounted= false;
	MySensorModel noiseCan = new MySensorModel();
	Action action = new Action();
	
	double tempTime = 0.0;
	
	@Override
	public Action control(SensorModel sensors) {
		if(myPara.getStage().compareTo(Stage.COMPLEXITYMEASURER)==0){
			if(tempTime+1.0 < sensors.getCurrentLapTime()){
				myPara.setTotalTime(tempTime+1.0);
				tempTime++;
				myPara.writeToResultsFile((float) DriverControllerHelperE6.maximumDistanceInfront(sensors.getTrackEdgeSensors()));
			}
		}
//		System.out.println(sensors.getZ() + " "+ sensors.getZSpeed());
		
//		totalDistance += sensors.getDistanceRaced();
		totalDistance = sensors.getDistanceFromStartLine();
		if(sensors.getDistanceFromStartLine() < 10.0f && sensors.getDistanceFromStartLine() > 0.0f && !lastLapTimeCounted){
			lastLapTimeCounted = true;
			prevLapTime += sensors.getLastLapTime();
			lapCounter++;
		}
		
		OpponentController2.zigzaggerPos = myPara.zigzaggerposition;
		
		if(sensors.getDistanceFromStartLine() > 10.0f)
			lastLapTimeCounted = false;
		
		totalTime = prevLapTime + sensors.getCurrentLapTime();
		
		StuckTypes isStuck = StuckHandler.isStuck(sensors);
		boolean isOut = StuckHandler.isOutTrack(sensors);
//		myPara.updatePenalty(isOut, sensors.getDamage(), lapCounter, sensors.getRacePosition(), sensors.getDistanceFromStartLine());
						
		speedController.setMyPara(myPara);
		directionController.setMyPara(myPara);
		
		sensorList.add(sensors);
		damage = sensors.getDamage();
		if(sensorList.size() > DriverControllerHelperE6.memorySensorLength){
			sensorList.remove(0);
		}
		
		noiseCan = NoiseCanceller.cancelNoise(sensorList);
				
		double estimatedTurn = DriverControllerHelperE6.turnDirectionCalculator(noiseCan, 9);
		speedController.setEstimatedTurn(estimatedTurn);
		directionController.setEstimatedTurn(estimatedTurn);
//		OpponentController1.relativeSpeedUpdater(noiseCan);
		OpponentController2.opponentsInfoUpdater(noiseCan, myPara);
		
		
		int gear = speedController.calculateGear(noiseCan, isStuck);
		double steer = directionController.calcSteer(noiseCan, isStuck, isOut);

		myPara.frictionUpdater(gear, noiseCan, steer, action.accelerate);
		myPara.dangerZoneUpdater(sensors);
		myPara.trackWidthUpdater(noiseCan, steer, isOut);
		
		float [] accelBrake = speedController.calcBrakeAndAccelPedals(noiseCan, steer, isStuck, isOut);
		if(gear == 1)
			clutch = DriverControllerHelperE6.clutchMax;
		if(clutch > 0.0)
			clutch = speedController.clutching(noiseCan, this.clutch);
				
        action.gear = gear;
        action.steering = steer;
        action.accelerate = accelBrake[1];
        action.brake = accelBrake[0];
        action.clutch = clutch;

		return action;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		System.out.println("Restarting the race!");
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		myPara.setDamage(damage);
		myPara.setTotalTime(totalTime);
//		System.out.println(totalDistance);
	}
	
	public float[] initAngles()	{
		
		float[] angles = DriverControllerHelperE6.angles;
		
		/* set angles as {-90,-75,-60,-45,-30,-20,-15,-10,-5,0,5,10,15,20,30,45,60,75,90} */
		return angles;
	}

}
