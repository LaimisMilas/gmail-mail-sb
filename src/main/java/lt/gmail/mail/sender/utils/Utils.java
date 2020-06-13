package lt.gmail.mail.sender.utils;

import java.util.Date;
import java.util.Random;

public class Utils {
	
	public static long timeDiff(Date begin, Date end) {
		
		long diff = end.getTime() - begin.getTime();
		
		return diff;
	}
	
	public static int randInt(int min, int max) {

		Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static void sleep(long time) {

		sleepThread(time);
	}
	
	public static void sleepThread(long time) {

		try {

			Thread.sleep(time);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
