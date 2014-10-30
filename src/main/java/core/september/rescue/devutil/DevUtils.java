package core.september.rescue.devutil;

import java.util.Map;

public class DevUtils {
	
	
	public static void printEnvironmetValue() {
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
		    System.out.format("%s=%s%n", envName, env.get(envName));
		}
	}
	
//	public static void main(String[] args) {
//		printEnvironmetValue();
//	}
}
