package core;

import simulation.*;

public class Launcher {

	public static void main(String[] args) {
		for(int i=0;i<1;i++){
			Simulation1.start(Seed._seeds[i], i ,"a");
		}
	}

}
