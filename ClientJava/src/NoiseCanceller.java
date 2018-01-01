package ahuraDriver;

import java.util.ArrayList;
import java.util.List;




public class NoiseCanceller {
	static MySensorModel noiseCancelled = new MySensorModel();
	static double [] differentInfo = new double[DriverControllerHelperE6.memorySensorLength];
	
	public NoiseCanceller(){
		
	}
	
	public static MySensorModel cancelNoise(List<SensorModel> sensorsList){
		
		SensorModel lastSensor = sensorsList.get(sensorsList.size() - 1);
		
		//Except focus, opponent sensors, track edge sensor which may have noises
		noiseCancelled.setAngleToTrackAxis(lastSensor.getAngleToTrackAxis());
		noiseCancelled.setCurrentLapTime(lastSensor.getCurrentLapTime());
		noiseCancelled.setDamage(lastSensor.getDamage());
		noiseCancelled.setDistanceFromStartLine(lastSensor.getDistanceFromStartLine());
		noiseCancelled.setDistanceRaced(lastSensor.getDistanceRaced());
		noiseCancelled.setFuelLevel(lastSensor.getFuelLevel());
		noiseCancelled.setGear(lastSensor.getGear());
		noiseCancelled.setLastLapTime(lastSensor.getLastLapTime());
		noiseCancelled.setLateralSpeed(lastSensor.getLateralSpeed());
		noiseCancelled.setMessage(lastSensor.getMessage());
		noiseCancelled.setRacePosition(lastSensor.getRacePosition());
		noiseCancelled.setRPM(lastSensor.getRPM());
		noiseCancelled.setSpeed(lastSensor.getSpeed());
		noiseCancelled.setTrackPosition(lastSensor.getTrackPosition());
		noiseCancelled.setWheelSpinVelocity(lastSensor.getWheelSpinVelocity());
		noiseCancelled.setZ(lastSensor.getZ());
		noiseCancelled.setZSpeed(lastSensor.getZSpeed());
		
		
		double [] differentSensors = new double[lastSensor.getFocusSensors().length];
		
		for(int i = 0; i < lastSensor.getFocusSensors().length; ++i){
			int j = 0;
			for(SensorModel sensor : sensorsList){
				differentInfo[j] = sensor.getFocusSensors()[i];
				++j;
			}
			//cancel the noise for differentInfo and put it in the following double variable 
			double noiseCancelledValue = cancelArrayNoise(differentInfo);//lastSensor.getFocusSensors()[i];
			differentSensors[i] = noiseCancelledValue; 
		}
		
		noiseCancelled.setFocusSensors(differentSensors);
		
		
		differentSensors = new double[lastSensor.getOpponentSensors().length];
		for(int i = 0; i < lastSensor.getOpponentSensors().length; ++i){
			int j = 0;
			for(SensorModel sensor : sensorsList){
				differentInfo[j] = sensor.getOpponentSensors()[i];
				++j;
			}
			//cancel the noise for differentInfo and put it in the following double variable 
			double noiseCancelledValue = cancelArrayNoise(differentInfo);//lastSensor.getOpponentSensors()[i];
			differentSensors[i] = noiseCancelledValue; 
		}
				
		noiseCancelled.setOpponentSensors(differentSensors);
		
		
		differentSensors = new double[lastSensor.getTrackEdgeSensors().length];
		for(int i = 0; i < lastSensor.getTrackEdgeSensors().length; ++i){
			int j = 0;
			for(SensorModel sensor : sensorsList){
				differentInfo[j] = sensor.getTrackEdgeSensors()[i];
				++j;
			}
			//cancel the noise for differentInfo and put it in the following double variable 
			double noiseCancelledValue = cancelArrayNoise(differentInfo);//lastSensor.getTrackEdgeSensors()[i];
			differentSensors[i] = noiseCancelledValue; 
		}
		
		noiseCancelled.setTrackEdgeSensors(differentSensors);

		
		return noiseCancelled;
	}
	
	/** RC low-pass filter
	 * @param input
	 * @return denosised last element
	 */
	public static double cancelArrayNoise(double [] input){

		float ALPHA = 0.15f;
	    if ( input == null ) return 0.0;
	    double [] output = new double [input.length]; 
	    output[0] = input[0];
	    for ( int i=1; i<input.length; i++ ) {
	        output[i] = output[i - 1] + ALPHA * (input[i] - output[i - 1]);
	    }
	    return output[output.length - 1];

	}
	
	/** RC low-pass filter
	 * @param input
	 * @return denosised last element
	 */
	public static double cancelArrayNoise(List<Double>  input){

		float ALPHA = 0.15f;
	    if ( input == null ) return 0.0;
	    List<Double> output = new ArrayList<Double>();
	    output.addAll(input);
	    for (int i=1; i<input.size(); i++ ) {
	    	double e = output.get(i - 1) + ALPHA * (input.get(i) - output.get(i - 1));
	        output.set(i, e); 
	    }
	    return output.get(output.size() - 1);

	}

	
	
//	public static double arraySmoother(List<Double> list, double angle){
//		
//		for(int i = 0; i<list.size(); ++i){
//			
//		}
//		
//	}
	
	public static double averageArray(List<Double>  input){
		double res = 0;
		for(int i = 0; i < input.size(); ++i){
			res += input.get(i); 
		}
		return res/input.size();
	}
	
}
