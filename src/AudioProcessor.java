import java.util.Scanner;
public class AudioProcessor {
	public static void main(String[] args) 
	{ 
		Scanner in = new Scanner ( System.in);
		String option = "";
		System.out.print ("Enter wav file name: ");
		double[] sample =StdAudio.read(in.nextLine());
		/* I use while loop here because I want to make other command after the command which is not quit
		 * I start with the value which is not "q" to define the command
		 * I add the line for screening out the sentences during it speeding up, adding noise or scaling volume because it need to appear during the editing process. Not after or before the process.
		 */
		while(!option.equals("q"))
		{
			System.out.print("Select command (p, r, s, n, v, o, q): ");
			option = in.next();
			switch ( option )
			{
			case "p" : 
				System.out.println("Playing sound");
				StdAudio.play(sample); 
				break;
			case "r" : 
				sample = reverse(sample);
				System.out.println("Reversing sound");
				break;
			case "s" : 
				System.out.print("Speed up by how much? ");
				double factor = in.nextDouble();
				System.out.println("Speeding up sound"); 
				sample = increaseSpeed( sample, factor );
				
				break;
			case "n" :
				System.out.print("Add how much noise? ");
				double amount =in.nextDouble();
				System.out.println("Adding noise");
				sample = addNoise( sample, amount);
				
				break;
			case "v" :
				System.out.print("Scale volume by how much? ");
				double scale = in.nextDouble();
				System.out.println("Scaling volume");
				sample = changeVolume( sample, scale);
				System.out.println("Scaling volume");
				break;
			case "o" :
				System.out.print("Save to what file name? ");
				StdAudio.save(in.next(), sample);
				break;
			case "q" : break;
			//if there are no character related to the command so that print out wrong input
			default :
				System.out.println(" Wrong input");
			}
		}


	}
	/*reverse part : 
	 * 1 - add value from the original array to the new array  
	 * 2 - reverse value which stored in the new array 
	 * the last value in original value is equal to length -1 so that subtract i in for loop to make it continuous 
	 */
	public static double[] reverse(double[] sample)
	{
		double[] result = new double[sample.length];
		for(int i = 0 ; i < sample.length ; ++i )
		{
			result[i] = sample[sample.length - i - 1 ];
		}
		return result;
	}
	/*speed up part : 
	 *1 - there is a factor which is the value will time with value in new array 
	 *2 - So that just time each value in new array to factor to get new array 
	 */
	public static double[] increaseSpeed(double[] sample, double factor)
	{
		double[] result = new double[(int)(sample.length / factor)];

		for (int i = 0 ; i < result.length ; ++i)
		{
			result[i] = sample[(int)(i*factor)];
		}
		return result;
	}
	/*add noise : 
	 * 1 - there are number which stay between -amount and amount so that if we want to make that value from (0,1) 
	 * we need to time (0,1) with 2 time amount to make ( 0, 2*amount) and abtract with amount to get ( -amount,amount)
	 * 2 - Every value from new array need to be add with the random value between ( - amount , amount)
	 */
	public static double[] addNoise(double[] sample, double amount )
	{
		double[] result = new double[sample.length];

		for ( int i = 0 ; i < result.length; ++i)
		{
			double noise = Math.random() * 2 * amount - amount;
			result[i] = sample[i] + noise;

			if ( result [i] < -1.0 )
				result[i] = -1.0;
			if ( result [i] > 1.0 )
				result[i] = 1.0;
		}

		return result;
	}
	/*Volume part :
	 * there a number that will increase the volume a number of time
	 * the new array which made by the value equal to the old value time with how much volume that you want to increase or decrease
	 */
	public static double[] changeVolume(double[] sample, double scale)
	{
		for ( int i =0; i < sample.length; ++i)
		{
			sample[i] = sample[i] * scale;
			if ( sample [i] < -1.0 )
				sample[i] = -1.0;
			if ( sample [i] > 1.0 )
				sample[i] = 1.0;
		}
		return sample;
	}
}