package br.com.ftec.ads.poo.util;

import java.util.Calendar;
import java.util.Random;

public class Utilitarios {

	public static Long nextLong() {
		Random random = new Random(Calendar.getInstance().getTimeInMillis());
		return random.nextLong();
	}

}