package cn.d1m.pandora.utils;

import java.util.Random;

public class VerifyUtil {

	public static String get() {
		Random random = new Random();
		String fourRandom = random.nextInt(10000) + "";
		int randLength = fourRandom.length();
		if (randLength < 4) {
			for (int i = 1; i <= 4 - randLength; i++)
				fourRandom = "0" + fourRandom;
		}
		return fourRandom;
	}
}
