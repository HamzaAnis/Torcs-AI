package ahuraDriver;

public class NeuralNetwork {

	public static double myNeuralNetworkFunction(double []x1){
		double [] x1_step1_xoffset = new double[]{2,7001.93,-12.0666,-5.432427197};

		double [] x1_step1_gain = new double[]{2,0.00203245836000935,0.0758567066810795,0.436093302759573};
		double x1_step1_ymin = -1;

		double [] b1 = new double[]{-0.64673992171340622,-1.5844984387327103,1.5656345272867009,-1.0434730494206232,0.0038644004653753672,-0.5474351335069696,-0.82447154840807335,-0.41623339802250947,2.4313389667830982,2.724020215325003};
		double [][] IW1_1 = new double [][] {{4.904774936061278,-0.095436949330665127,-0.16723889828795957,-4.1375425370397325},{2.5526562020090888,-0.53176918277800456,-0.66765312647136177,-0.8363240232773117},{-0.9539200996764482,0.81218356971463501,-2.1016758368440951,-0.19170117665269892},{2.094281839677806,-1.258811820025876,0.4041773271077978,-0.0094142826807153655},{-1.3148918199229198,-0.73555631705941293,-1.8219655985042527,1.6462614964358129},{-1.1588312153720368,1.2712546410035384,-0.7963667591893212,-1.8514828880074925},{-0.98292373812739575,1.0626809051230779,-1.6317163745795622,-0.89048771290915252},{-3.0832792827793725,-0.037463015795962007,-0.34732008038932882,-2.7782973686396781},{1.3794010411704365,0.18519402857066464,-1.3577450310113328,-0.51486758605718563},{1.0073216987066302,1.7868003613619583,-0.78722946605968125,-0.54860811111432428}};

		double b2 = -1.4352602465723323;
		double [] LW2_1 = new double[]{-2.2812826849327199,0.090418205192221823,0.22142405709283969,0.081088910253513072,0.0073249859515589211,-0.30444328946053711,0.29803396157160877,-2.1175739593634018,0.48583450060358596,0.031879706162296143};

		double y1_step1_ymin = -1;
		double y1_step1_gain = 3.63636363636364;
		double y1_step1_xoffset = 0.85;


		double []xp1 = mapminmax_apply(x1,x1_step1_gain,x1_step1_xoffset,x1_step1_ymin);

		double [] a1 = tansig_apply(plus(b1 , matrixTimesVector(IW1_1,xp1)));

		double a2 = (b2+ timesAdd(LW2_1,a1));

		double y1 = mapminmax_reverse(a2,y1_step1_gain,y1_step1_xoffset,y1_step1_ymin);

		return y1;

	}
	
	static double timesAdd(double [] a, double [] b){
		double res=0;
		for (int i=0;i<a.length;++i){
			res+=a[i]*b[i];
		}
		return res;
	}
	
	static double [] tansig_apply(double [] in){
		double[]res=new double[in.length];
		
		for(int i=0;i<in.length;++i){
			res[i]=2.0/ (1.0 + Math.exp(-2.0*in[i])) - 1.0;
		}
		
		return res;
	}
	
	static double [] mapminmax_apply(double [] x, double [] settings_gain, double [] settings_xoffset,double settings_ymin){
		double [] y=minus(x, settings_xoffset);
		y=elementwiseTimes(y, settings_gain);
		y=plus(y, settings_ymin);
		return y;
	}
	
	static double mapminmax_reverse(double y,double settings_gain,double settings_xoffset,double settings_ymin){
		double x= (y- settings_ymin);
		x=(x/settings_gain);
		x=(x+settings_xoffset);
		return x;
	}
	
	static double[] minus(double []x, double [] y){
		double[] res=new double[x.length];
		
		for(int i=0;i<x.length;++i){
			res[i]=x[i]-y[i];
		}
		return res;
		
	}
	
	static double[] minus(double []x, double y){
		double[] res=new double[x.length];
		
		for(int i=0;i<x.length;++i){
			res[i]=x[i]-y;
		}
		return res;
		
	}
	
	static double[] elementwiseTimes(double []x, double [] y){
		double[] res=new double[x.length];
		
		for(int i=0;i<x.length;++i){
			res[i]=x[i]*y[i];
		}
		
		return res;
		
	}
	static double[] elementwiseTimes(double []x, double y){
		double[] res=new double[x.length];
		
		for(int i=0;i<x.length;++i){
			res[i]=x[i]*y;
		}
		
		return res;
		
	}
	static double[] plus(double []x, double [] y){
		double[] res=new double[x.length];
		
		for(int i=0;i<x.length;++i){
			res[i]=x[i]+y[i];
		}
		return res;
		
	}
	static double[] plus(double []x, double y){
		double[] res=new double[x.length];
		
		for(int i=0;i<x.length;++i){
			res[i]=x[i]+y;
		}
		return res;
		
	}
	static double[] divide(double []x, double [] y){
		double[] res=new double[x.length];
		
		for(int i=0;i<x.length;++i){
			res[i]=x[i]/y[i];
		}
		return res;
		
	}
	static double[] divide(double []x, double y){
		double[] res=new double[x.length];
		
		for(int i=0;i<x.length;++i){
			res[i]=x[i]/y;
		}
		return res;
		
	}
	
	static double [] matrixTimesVector(double [][]matrix, double [] vector){
		double [] res= new double[matrix.length];
		
		for(int i = 0; i<matrix.length;++i){
			double toRes = 0.0;
			for (int j=0;j<vector.length;++j){
				toRes += vector[j]*matrix[i][j];
			}
			res[i]=toRes;
		}
		
		return res;
		
	}
	
}
