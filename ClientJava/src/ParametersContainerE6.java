package ahuraDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ahuraDriver.Controller.BlockerLevel;
import ahuraDriver.Controller.Stage;


public class ParametersContainerE6 {
//	private boolean evolutionaryMode = false;
	
//	String param = "d: 107.9075418536763, lambda2: 4.753901789093406, -absSlip: 1.0, x2: 95.30302118020252, c: 114.1158700288616, b: 90.47991440926471, -absMinSpeed: 3.0, a: 50.95124925780534, lambda1: 0.01956501913690144, e2: 5.382771708803534, y2: 6.230728885167587, x1: -0.2300585760015009, -absRange: 3.0, y1: 2.011735390446281";//wheel2
//	String param = "maxv: 107.9075418536763, maxAggV: 6.153901789093406, -absSlip: 1.0, MaximumSoar: 95.30302118020252, midv2: 114.1158700288616, midv1: 90.47991440926471, -absMinSpeed: 3.0, minv: 50.95124925780534, minAggV: 0.01956501913690144, maxAggTurn: 5.382771708803534, Maxsensors: 6.230728885167587, MinimumSoar: -0.2300585760015009, -absRange: 3.0, Minsensors: 2.011735390446281";//Brondehatch
//	String param = "maxv: 107.9075418536763, maxAggV: 5.683901789093406, -absSlip: 1.0, MaximumSoar: 95.30302118020252, midv2: 114.1158700288616, midv1: 90.47991440926471, -absMinSpeed: 3.0, minv: 50.95124925780534, minAggV: 0.01956501913690144, maxAggTurn: 5.382771708803534, Maxsensors: 6.230728885167587, MinimumSoar: -0.2300585760015009, -absRange: 3.0, Minsensors: 2.011735390446281";//Etrack-3
//	String param = "maxv: 107.9075418536763, maxAggV: 4.983901789093406, -absSlip: 1.0, MaximumSoar: 95.30302118020252, midv2: 114.1158700288616, midv1: 90.47991440926471, -absMinSpeed: 3.0, minv: 50.95124925780534, minAggV: 0.01956501913690144, maxAggTurn: 5.382771708803534, Maxsensors: 6.230728885167587, MinimumSoar: -0.2300585760015009, -absRange: 3.0, Minsensors: 2.011735390446281";//Eroad
//	String param = "maxv: 107.9075418536763, maxAggV: 5.513901789093406, -absSlip: 1.0, MaximumSoar: 95.30302118020252, midv2: 114.1158700288616, midv1: 90.47991440926471, -absMinSpeed: 3.0, minv: 50.95124925780534, minAggV: 0.01956501913690144, maxAggTurn: 5.382771708803534, Maxsensors: 6.230728885167587, MinimumSoar: -0.2300585760015009, -absRange: 3.0, Minsensors: 2.011735390446281";//Street1
//	String param = "maxv: 107.9075418536763, maxAggV: 5.513901789093406, -absSlip: 1.0, MaximumSoar: 95.30302118020252, midv2: 114.1158700288616, midv1: 90.47991440926471, -absMinSpeed: 3.0, minv: 50.95124925780534, minAggV: 0.01956501913690144, maxAggTurn: 5.382771708803534, Maxsensors: 6.230728885167587, MinimumSoar: -0.2300585760015009, -absRange: 3.0, Minsensors: 2.011735390446281";//Wheel1
//	String param = "maxv: 107.9075418536763, maxAggV: 6.913901789093406, -absSlip: 1.0, MaximumSoar: 95.30302118020252, midv2: 114.1158700288616, midv1: 90.47991440926471, -absMinSpeed: 3.0, minv: 50.95124925780534, minAggV: 0.01956501913690144, maxAggTurn: 5.382771708803534, Maxsensors: 6.230728885167587, MinimumSoar: -0.2300585760015009, -absRange: 3.0, Minsensors: 2.011735390446281";//Alpine2
//	String param = "d: 548.5510497961806, -absSlip: 1.0, b: 226.58876971975494, c: 29.244997998960944, lambda2: 4.825515039965515, lambda1: 0.038964770592202214, a: 15.782483161958048, y1: 3.2756651001846664, y2: 2.320791447366081, x2: -53.97800754060265, -absMinSpeed: 3.0, x1: 24.164074463994343, e2: 10.46954217357637, -absRange: 3.0";
//	String param = "maxv: 107.9075418536763, maxAggV: 3.242568600336371, -absSlip: 1.0, MaximumSoar: 95.30302118020252, midv2: 114.1158700288616, midv1: 90.47991440926471, -absMinSpeed: 3.0, minv: 50.95124925780534, minAggV: 0.01478384777891481, maxAggTurn: 3.169069285437615, Maxsensors: 6.230728885167587, MinimumSoar: -0.2300585760015009, -absRange: 3.0, Minsensors: 2.011735390446281";//Brondehatch
//	String param = "maxv: 107.9075418536763, maxAggV: 8.351090668086998, -absSlip: 1.0, MaximumSoar: 95.30302118020252, midv2: 114.1158700288616, midv1: 90.47991440926471, -absMinSpeed: 3.0, minv: 50.95124925780534, minAggV: 0.02459410715535047, maxAggTurn: 16.95975738979463, Maxsensors: 6.230728885167587, MinimumSoar: -0.2300585760015009, -absRange: 3.0, Minsensors: 2.011735390446281";//etrack2
//	String param = "maxv: 107.9075418536763, maxAggV: 5.332868446716203, -absSlip: 1.0, MaximumSoar: 95.30302118020252, midv2: 114.1158700288616, midv1: 90.47991440926471, -absMinSpeed: 3.0, minv: 50.95124925780534, minAggV: 0.01971582329352539, maxAggTurn: 5.145771032002575, Minsensors: 2.011735390446281, MinimumSoar: -0.2300585760015009, -absRange: 3.0, Maxsensors: 6.230728885167587";//etrack3
//	String param = "distOvertakex: 0.287, distOvertakey: 7.0, opSteer: 0.4, d: 187.9075418536763, lambda2: 6.9, -absSlip: 1.0, x2: 95.30302118020252, c: 164.1158700288616, b: 90.47991440926471, -absMinSpeed: 3.0, a: 50.95124925780534, lambda1: 0.01956501913690144, e2: 5.382771708803534, y2: 6.230728885167587, x1: -0.2300585760015009, -absRange: 3.0, y1: 2.011735390446281";//default-slowest
	String param = "Omega1: 0.583432442, Omega2: 15.00001987, Omega3: 0.033898305084746, Omega4: 1.00010291102, Omega5: 10.0754, d: 187.9075418536763, lambda2: 7.032868446716203, -absSlip: 1.0, x2: 95.30302118020252, c: 164.1158700288616, b: 90.47991440926471, -absMinSpeed: 3.0, a: 50.95124925780534, lambda1: 0.01956501913690144, e2: 5.382771708803534, y2: 6.230728885167587, x1: -0.2300585760015009, -absRange: 3.0, y1: 2.011735390446281";//default-slowest
	private Map<String, Double> parameters = new HashMap<String, Double>();
	private List<String> parametersNames = new ArrayList<String>();

	static public Random rand = new Random();
	public double zigzaggerposition = 0.0;
	
	private Stage stage;
	private BlockerLevel level;
	
	//	outputs
	private double totalTime = 0.0;
	private double damage = 0.0;
	private int numberOfParameters = 0;
	private double penaltyCoef = 1.0;
	boolean pIsOut = false;
	
	private double friction = -1.08;
	double slipSampler = 0.0;
	double slipSamplerNumber = 0.0;
	double pRPM = 0.0;
	double lastTimeOut = 0.0;
	int lastLapOut = 1;
	double lastdamage = 0.0;
	private double trackWidth = 15.0;
	List<Double> frictionList = new ArrayList<Double>();
	
	List<DangerZoneInfo> dangerousZonesList = new ArrayList<DangerZoneInfo>();
	
	public double minSpeed = 25;
	public double maxNormalSpeed = 360;
	
	public ParametersContainerE6(){	
		setStage(Stage.UNKNOWN);
		readInitialization();		
	}
	
	public ParametersContainerE6(Stage stage, BlockerLevel level){
		
		setStage(stage);
		setLevel(level);		
		readInitialization();		
	}
	public void readInitialization(){
		zigzaggerposition = rand.nextDouble()*2.0-1.0;
		String [] parameters = param.split(", ");
		for(String s : parameters){
			String [] currentLine = s.split(": ");
			numberOfParameters++;

			parametersNames.add(currentLine[0]);
			System.out.println(currentLine[0] + " " + currentLine[1]);
			getParameters().put(currentLine[0], Double.parseDouble(currentLine[1]));
		}
		
		System.out.println("******** Note **********");
		System.out.println("Ahura is running in " + getStage().toString() + " mode. " + stage.getModeDescription() + (getStage().compareTo(Stage.BLOCKER) == 0 ? level.getModeDescription() : ""));
		
		System.out.println("************************");
		if(getStage().compareTo(Stage.BLOCKER)==0){//limit the maximum speed
			maxNormalSpeed = level.ordinal()*30+240;
			System.out.println(maxNormalSpeed);
		}
	}
	
	public void ParametersContainerInitializer(List<Double> parametersValues){
		int i = 0;
		for(String s : parametersNames){
			if(i >= parametersValues.size())
				break;
			
			getParameters().put(s, parametersValues.get(i));
			++i;
		}		
	}
	
	public void writeToResultsFile(double dist){
		String s = printOutResults(dist);
		String fileToSave = "Res.txt";
		File f = new File(fileToSave);
		FileWriter out = null;
		try{
			out=new FileWriter(f, true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		s += "\n";
	
		try {
			out.write(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String printOutResults(double dist) {
		
		String s = "Track name: F-speedway"  + ", Total Time: " + totalTime + ", Damage: " + damage + ", dist: " + dist + " ";
		
//		for(Map.Entry<String, Double> i : parameters.entrySet()){
//			s += (", " + i.getKey() + ": " + i.getValue());
//		}
		
//		System.out.println(s);
		return s;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public Map<String, Double> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Double> parameters) {
		this.parameters = parameters;
	}
		
	public double getParameterByName(String name){
		Double res = parameters.get(name);
		if(res == null){
			String s = "-" + name;
			res = parameters.get(s);
		}
		return res;		
	}

	public int getNumberOfParameters() {
		return numberOfParameters;
	}

	public void setNumberOfParameters(int numberOfParameters) {
		this.numberOfParameters = numberOfParameters;
	}

	public String getParametersNames(int i) {
		String s = parametersNames.get(i);
		if(s.charAt(0) == '-')
			return s.substring(1);
		
		return s;
	}
	
	public boolean isEvolvable(int i){
		String s = parametersNames.get(i);
		if(s.charAt(0) == '-')
			return false;
		
		return true;
	}

//	public void updatePenalty(boolean isOut, double damage, int lap, int pos, double location){
//		if(pIsOut != isOut){
//			if(isOut){
//				
//				lastLapOut = lap;
////				setPenaltyCoef(getPenaltyCoef() / 1.03);
////				setPenaltyCoef(Math.max(getPenaltyCoef(), 0.6));
//			}
//		}
//		
//		if(lastLapOut < lap){
//			if(lastdamage >= damage){				
//				if(pos != 1){
////					setPenaltyCoef(getPenaltyCoef() * 1.03);
////					setPenaltyCoef(Math.min(getPenaltyCoef(), 1.4));
//				}
//			}
//			lastLapOut = lap;
//			lastdamage = damage;
//		}
//		pIsOut = isOut;
//		
//	}

	public void dangerZoneUpdater(SensorModel sensor) {
		boolean isOut=false;
		
		if(Math.abs(sensor.getTrackPosition()) > 0.98)
			isOut = true;
		
		if((pIsOut != isOut && isOut)  || sensor.getZ() > 1.0){
			DangerzoneCause cause = DangerzoneCause.OutOfTrack; 

			if(isOut){//stucking
				cause = DangerzoneCause.OutOfTrack; 
			}
			if(sensor.getZ() > 1.0){//jumping badly
				cause = DangerzoneCause.Jump;				
			}
			
			double location = sensor.getDistanceFromStartLine();
			boolean incSeverity=false;
			
			for (DangerZoneInfo i:dangerousZonesList){
				incSeverity = i.severityUpdate(location);			
			}
			
			if(!incSeverity){
				dangerousZonesList.add(new DangerZoneInfo(location,1, cause));
			}

		}else{
			
		}
		
		pIsOut = isOut;

	}

	public double dangZoneSpeedUpdate(double location, double speed) {
		if(speed < 25){
			return 25;
		}
		for (DangerZoneInfo i : dangerousZonesList){
			if(location>i.location-160 && location<i.location+20){
//				System.out.println("Entering a danger zone with severity " + i.severity + " location " + i.location + " caused by " + i.cause);							
				return i.speedFactor*speed;
			}else{
				
			}
		}		
		return speed;
	}


	public double severityOfdangZone(double location) {
		for (DangerZoneInfo i : dangerousZonesList){
			if(location>i.location-250 && location<i.location+20){
//				System.out.println("Entering danger zone with severity " + i.y);
				return i.severity;//severity
			}
		}		
		return 0.0;
	}

	
	public double getPenaltyCoef() {
		return penaltyCoef;
	}

	public void setPenaltyCoef(double penaltyCoef) {
		this.penaltyCoef = penaltyCoef;
	}
	
	public void frictionUpdater(int gear, MySensorModel sensors, double steer, double accel){
		double RPM = sensors.getRPM();
//		if(RPM < 7100.0 && RPM > 7050.0 && RPM > pRPM && Math.abs(steer) < 0.06 && (gear==2 || gear == 3) && accel >0.99){
//			double slip = 0.0;
//			slip=(sensors.getWheelSpinVelocity()[3] * DriverControllerHelperE6.wheelRadius[3] + sensors.getWheelSpinVelocity()[2] * DriverControllerHelperE6.wheelRadius[2])/2.0;
//	    	slip = (sensors.getSpeed()/3.6)-slip;
//	    	if(gear==2)
//	    		slip*=0.67;
//
////	    	double fr = slip*1.04;
//	    	setFriction(slip);
//	    	double lambdaNew=(getFriction())*4.99;
//	    	lambdaNew = Math.min(lambdaNew, 7.0);
//	    	lambdaNew = Math.max(lambdaNew, 4.5);
//	    	getParameters().put("lambda2", lambdaNew);
//	    	
//			updateAggrByTrackWidthAndDammage(sensors.getDamage()); 
//			
//		}
//		
//		if(RPM > 7000.0 && RPM < 8000.0){
//			if(gear == 3 || gear == 2){// || gear == 4){
//				if(RPM > pRPM && Math.abs(steer) < 0.06 && accel > 0.99){//friction calculation is invalid at the turns and ramps
//			    	double slip = 0.0;
//			    	slip=(sensors.getWheelSpinVelocity()[3] * DriverControllerHelperE6.wheelRadius[3] + sensors.getWheelSpinVelocity()[2] * DriverControllerHelperE6.wheelRadius[2])/2.0;
//			    	slip = (sensors.getSpeed()/3.6)-slip;
//					slipSampler += slip;
//					slipSamplerNumber++;
////					System.out.println(gear + ", " + RPM + ", " + accel + ", " + sensors.getZSpeed() + ", " +slip);
////					System.out.println("NN estimation: " + NeuralNetwork.myNeuralNetworkFunction(new double[]{gear,RPM,sensors.getZSpeed(),slip}));
////					saveMe(gear + ", " + RPM + ", " + accel + ", " + sensors.getZSpeed() + ", " +slip);
//				}else{
//					slipSampler = 0.0;
//					slipSamplerNumber = 0.0;	
//				}				
//			}
//		}else{
//			slipSampler = 0.0;
//			slipSamplerNumber = 0.0;					
//		}
//		
//		if(slipSampler != 0.0 && slipSamplerNumber > 15){
//			double fr=slipSampler/slipSamplerNumber;
//			if(gear==2){
//				fr*=(3.4/4.4);
//			}
//			
//			setFriction(fr);
//			
//			slipSampler = 0.0;
//			slipSamplerNumber = 0.0;
//			double lambdaNew = getFriction()*4.4;
//	    	lambdaNew = Math.min(lambdaNew, 7.0);
//	    	lambdaNew = Math.max(lambdaNew, 4.5);
//	    	
//			getParameters().put("lambda2", lambdaNew);
//			
//			System.out.println("Friction: " + fr + ", " + lambdaNew);
//			
//			updateAggrByTrackWidthAndDammage(sensors.getDamage()); 
//		}
//    	System.out.println(sensors.getZSpeed());
		
		if(RPM > 7000.0 && RPM < 8000.0){
			if(gear == 3 || gear == 2){// || gear == 4){
				if(RPM > pRPM && Math.abs(steer) < 0.06 && accel > 0.99){//friction calculation is invalid at the turns and ramps
			    	double slip = 0.0;
			    	slip=(sensors.getWheelSpinVelocity()[3] * DriverControllerHelperE6.wheelRadius[3] + sensors.getWheelSpinVelocity()[2] * DriverControllerHelperE6.wheelRadius[2])/2.0;
			    	slip = (sensors.getSpeed()/3.6)-slip;
			    	double estimatedFric = NeuralNetwork.myNeuralNetworkFunction(new double[]{gear,RPM,sensors.getZSpeed(),slip});
			    	
			    	estimatedFric = Math.max(estimatedFric, 0.7);
			    	estimatedFric = Math.min(estimatedFric, 1.6);
			    	
			    	frictionList.add(estimatedFric);
			    	if(frictionList.size()>5){
			    		frictionList.remove(0);
			    	}
				}				
			}
		}
		
		double fric = 0.0;
		if(frictionList.size()==0){
			fric=0.8;
		}else{
		
			for(int i=0;i<frictionList.size();++i){
				fric+=frictionList.get(i);
			}
			
			fric/=frictionList.size();
		}
		
		setFriction(fric);
		
		
		
//		double lambda2Fric = -10.0*(fric-1.0)+7.0;
		double lambda2Fric = 6.9/(fric*fric);
		lambda2Fric = Math.max(lambda2Fric, 4.5);
		lambda2Fric = Math.min(lambda2Fric, 7.1);
		
//		System.out.println("Friction: " + fric + ", " + lambda2Fric + ", "+sensors.getZ());

		getParameters().put("lambda2", lambda2Fric);
		
		
		updateAggrByTrackWidthAndDammage(sensors.getDamage());
		
    	pRPM = RPM;
	}

	public void saveMe(String s1){
//		String s1 = "Manouver: " + manouver+", directionAlter: "+directionAlt+", pos: "+trackPos+", where: "+whereToGo;
		String fileToSave = "Res.txt";
		File f = new File(fileToSave);
		FileWriter out = null;
		try{
			out=new FileWriter(f, true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		s1 += "\n";
	
		try {
			out.write(s1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println("Manouver: " + manouver+", directionAlter: "+directionAlt+", pos: "+trackPos+", where: "+whereToGo);
	}
	
	
	public void updateAggrByTrackWidthAndDammage(double currentDamage) {
		double widthCoef =  ((2.0-1.0)/(30.0-16.0)) * (getTrackWidth()-16.0) + 1.0;
		widthCoef = Math.max(widthCoef, 1.0);
		double lambdaNew = getParameterByName("lambda2");
		lambdaNew /= widthCoef;
//		lambdaNew = Math.max(lambdaNew, 4.5);
		
		if(currentDamage > 7000 && widthCoef <= 1){
			lambdaNew = Math.max(lambdaNew, 7.0);
		}
		
    	lambdaNew = Math.min(lambdaNew, 7.1);
    	lambdaNew = Math.max(lambdaNew, 4.5);
		
		getParameters().put("lambda2", lambdaNew);
	}

	public void trackWidthUpdater(MySensorModel sensors, double steer, boolean isOut){
		if(Math.abs(steer) < 0.06 && !isOut){
			trackWidth = (sensors.getTrackEdgeSensors()[0] + sensors.getTrackEdgeSensors()[sensors.getTrackEdgeSensors().length - 1]);
			
		}
	}
	
	public double getFriction() {
		return Math.abs(friction);
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}

	public double getTrackWidth() {
		return trackWidth;
	}

	public void setTrackWidth(double trackWidth) {
		this.trackWidth = trackWidth;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public BlockerLevel getLevel(){
		return level;
	}
	
	public void setLevel(BlockerLevel level){
		this.level = level;
	}
}
