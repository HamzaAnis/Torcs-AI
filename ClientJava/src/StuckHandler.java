package ahuraDriver;

public class StuckHandler {
	
	private static double stuckAngle = Math.PI/2;
	private static int angularStuck = 0;
	private static int wallStuck = 0;
	private static int angularStuckTime = 25;
	private static int wallStuckTime = 60;
	private static boolean wallStuckCont = false;
	
	private static int outTrack = 0;
	private static int outTrackTime = 20;
	private static boolean weAretOut = false;
	
	public static StuckTypes isStuck(SensorModel sensors){
		if (Math.abs(sensors.getAngleToTrackAxis()) > stuckAngle )
	    {
			// update stuck counter
	        angularStuck++;
	    }
	    else
	    {
	    	// if not stuck reset stuck counter
	        angularStuck = 0;
	    }
//		System.out.println(sensors.getDistanceRaced());
//		System.out.println("ang: " + angularStuck + ", wall: "+ wallStuck + ", out: " + weAretOut + " " + sensors.getZSpeed());
		if(Math.abs(sensors.getSpeed()) < 5.0 && sensors.getDistanceRaced() > 10.0){
			wallStuck++;
		}else{
			wallStuck--;
			wallStuck = Math.max(wallStuck, 0);
			if(wallStuck == 0){
				wallStuckCont = false;
			}
		}
		
//		System.out.println(wallStuck);
		
		if(wallStuck > 200){
			wallStuck = -200;
			wallStuckCont = false;

			return StuckTypes.NoStuck;
		}
		
		if(wallStuck > wallStuckTime || wallStuckCont){
			wallStuckCont = true;
			return StuckTypes.WallStuck;
		}
		
		if(angularStuck > angularStuckTime){
			return StuckTypes.AngularStuck;
		}
		
		return StuckTypes.NoStuck;

		// after car is stuck for a while apply recovering policy

	}
	
	public static boolean isOutTrack(SensorModel sensors){
		if (Math.abs(sensors.getTrackPosition()) > 1.0)
	    {
			// update stuck counter
			outTrack=0;
			weAretOut = true;
	    }
	    else
	    {
	    	if(weAretOut){
		    	outTrack++;		    	
	    	}
	    	if(outTrack > outTrackTime){
	    		weAretOut = false;
	    		outTrack = 0;
	    	}
	    }
	
		// after car is stuck for a while apply recovering policy
	    return weAretOut;

	}
	
	public static boolean isStuck(MySensorModel sensors){
		if (Math.abs(sensors.getAngleToTrackAxis()) > stuckAngle )
	    {
			// update stuck counter
	        angularStuck++;
	    }
	    else
	    {
	    	// if not stuck reset stuck counter
	        angularStuck = 0;
	    }

		// after car is stuck for a while apply recovering policy
	    return (angularStuck > angularStuckTime);

	}
	
	public static boolean isOutTrack(MySensorModel sensors){
		if (Math.abs(sensors.getTrackPosition()) > 1.0 )
	    {
//			System.out.println("Angles: " + sensors.getAngleToTrackAxis() + " stuck: "+stuck);
			// update stuck counter
			outTrack=0;
			weAretOut = true;
	    }
	    else
	    {
	    	if(weAretOut){
		    	outTrack++;		    	
	    	}
	    	if(outTrack > outTrackTime){
	    		weAretOut = false;
	    		outTrack = 0;
	    	}
	    }
	
		// after car is stuck for a while apply recovering policy
	    return weAretOut;

	}
}
