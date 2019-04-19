package environment;

import core.*;
import java.util.*;

public class Field1 {
	private int field[][][]= {
			{{1,1,0,0},{1,1,1,0},{1,1,1,0},{1,1,1,0},{0,1,1,0}},
			{{1,1,0,1},{1,1,1,1},{1,1,1,1},{1,1,1,1},{0,1,1,1}},
			{{1,1,0,1},{1,1,1,1},{1,1,1,1},{1,1,1,1},{0,1,1,1}},
			{{1,1,0,1},{1,1,1,1},{1,1,1,1},{1,1,1,1},{0,1,1,1}},
			{{1,0,0,1},{1,0,1,1},{1,0,1,1},{1,0,1,1},{0,0,1,1}}
			};
	private int event[][];
	private double p[][];
	
	public Field1(Sfmt rnd){
		event = new int[5][5];
		p = new double[5][5];
		List<Double> p_list = new ArrayList<Double>(25);

		double p_set[] = {0.05, 0.01, 0.001};
		int num_p[] = {3, 12, 10};
		
		for(int i=0;i<3;i++){
			for(int j=0;j<num_p[i];j++){
				p_list.add(p_set[i]);
			}
		}
		Collections.shuffle(p_list);
		
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				event[i][j] = 0;
				p[i][j] = p_list.get(i*5+j);
			}
		}
	}
	
	public void happen_event(Sfmt rnd){
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				if(rnd.NextUnif() < p[i][j]){
					event[i][j] = 1;
				}
			}
		}
	}
	
	public int getPosStatus(int x, int y, int act){
		return field[x][y][act];
	}

	public int[] getPosStatus(int x, int y){
		return field[x][y];
	}
	
	public int[][][] getField(){
		return field;
	}
	
	public int acquire_event(int x, int y){
		int result = event[x][y];
		event[x][y] = 0;
		return result;
	}
	
	public int getEvent(int x, int y){
		return event[x][y];
	}
	
	public double getP(int x, int y){
		return p[x][y];
	}
}
