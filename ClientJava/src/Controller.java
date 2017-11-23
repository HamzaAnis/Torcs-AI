package ahuraDriver;


public abstract class Controller {
	
	public enum Stage {
		
		WARMUP,QUALIFYING,RACE,COMPLEXITYMEASURER,BLOCKER,ZIGZAGGER,UNKNOWN;
		
		static Stage fromInt(int value)
		{
			switch (value) {
			case 0:
				return WARMUP;
			case 1:
				return QUALIFYING;
			case 2:
				return RACE;
			case 3:
				return COMPLEXITYMEASURER;
			case 4:
				return BLOCKER;
			case 5:
				return ZIGZAGGER;
			default:
				return UNKNOWN;
			}			
		}
		
		public String getModeDescription(){
			String s = "No description is available for this mode!";
			if(this.compareTo(COMPLEXITYMEASURER)==0)
				s= " In this mode Ahura moves slow and gathers information about the maximum distance in front every 1 second and save the results in \"\\bin\\Res.txt\"";
			if(this.compareTo(BLOCKER)==0)
				s= " In this mode Ahura moves slightly slower than its standard version but tries to block the vehicle behind it. Use the setting \"blockerlevel:0\" to make it easier to overtake or \"blockerlevel:4\" to make it very hard to overtake.";

			return s;
		}
	};
	
	public enum BlockerLevel {
		
		VERYEASY, EASY, NORMAL, HARD, VERYHARD;
		
		static BlockerLevel fromInt(int value)
		{
			switch (value) {
			case 4:
				return VERYHARD;
			case 3:
				return HARD;
			case 2:
				return NORMAL;
			case 1:
				return EASY;
			case 0:
				return VERYEASY;
			default:
				return VERYEASY;
			}			
		}
		
		public String getModeDescription(){
			String s = "No description is available for this mode!";
			if(this.compareTo(VERYHARD)==0)
				s= " This vehicle is very hard to overtake.";
			if(this.compareTo(HARD)==0)
				s= " This vehicle is hard to overtake.";
			if(this.compareTo(NORMAL)==0)
				s= " This vehicle is not that hard to overtake.";
			if(this.compareTo(EASY)==0)
				s= " This vehicle is easy to overtake.";
			if(this.compareTo(VERYEASY)==0)
				s= " This vehicle is very easy to overtake.";			
			return s;
		}

	};
	
	private String trackName;
	public ParametersContainerE6 myPara;
	
	public void initializePara(ParametersContainerE6 para){
		myPara = para;
	}
	
	
	public float[] initAngles()	{
		float[] angles = new float[19];
		for (int i = 0; i < 19; ++i)
			angles[i]=-90+i*10;
		return angles;
	}
	
	
	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

    public abstract Action control(SensorModel sensors);

    public abstract void reset(); // called at the beginning of each new trial
    
    public abstract void shutdown();

}