package homelet.utils;

public final class ToolBox{
	
	private ToolBox(){}
	
	public static double max(double[] doubles){
		if(doubles.length == 0)
			return 0.0;
		double max = doubles[0];
		for(int index = 1; index < doubles.length; index++)
			max = Math.max(max, doubles[index]);
		return max;
	}
	
	public static float max(float[] floats){
		if(floats.length == 0)
			return 0.0f;
		float max = floats[0];
		for(int index = 1; index < floats.length; index++)
			max = Math.max(max, floats[index]);
		return max;
	}
}
