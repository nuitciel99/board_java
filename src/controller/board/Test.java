package controller.board;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Test {
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
//		System.out.println(uuid);
//		System.out.println(uuid.toString() + System.currentTimeMillis());
		System.out.println(uuid.toString() + ".txt");
		
		Set<String> set = new HashSet<String>();
		
//		for(int i = 0; i<10000000; i++) {
//			set.add(UUID.randomUUID().toString());
//		}
		System.out.println(set.size());
	}
}
