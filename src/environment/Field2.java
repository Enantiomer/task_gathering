package environment;

import core.*;
import java.util.*;

public class Field2 {
	private int field[][][]= {
			{{2,2,0,0},{2,2,2,0},{2,2,2,0},{2,2,2,0},{0,2,2,0}},
			{{2,2,0,2},{2,2,2,2},{2,2,2,2},{2,2,2,2},{0,2,2,2}},
			{{2,2,0,2},{2,2,2,2},{2,2,2,2},{2,2,2,2},{0,2,2,2}},
			{{2,2,0,2},{2,2,2,2},{2,2,2,2},{2,2,2,2},{0,2,2,2}},
			{{2,0,0,2},{2,0,2,2},{2,0,2,2},{2,0,2,2},{0,0,2,2}}
			};
	private int event[][];
	private double p[][];
	
	public Field2(Sfmt rnd){
		event = new int[5][5];
		p = new double[5][5];
		List<Double> p_list = new ArrayList<Double>(25);
		genField(rnd);
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
	
	public void genField(Sfmt rnd){
		List<Integer> visited_list = new ArrayList<Integer>();
		List<Integer> avoid_list = new ArrayList<Integer>();
		List<Integer> direction = new ArrayList<Integer>();
		for(int i=0;i<4;i++){
			direction.add(i);
		}
		
		int x=2,y=2;
		int id;
		int changed;

		visited_list.add(to_id(x,y));
		while(visited_list.size()+avoid_list.size() < 25){
			id = visited_list.get((int)(rnd.NextUnif()*visited_list.size()));
			x = id % 5;
			y = id / 5;
			changed = 0;
			Collections.shuffle(direction);
			for(int dir: direction){
				if(field[x][y][dir] == 2){
					switch(dir){
					case 0:
						id = to_id(x,y+1);
						break;
					case 1:
						id = to_id(x+1,y);
						break;
					case 2:
						id = to_id(x,y-1);
						break;
					case 3:
						id = to_id(x-1,y);
						break;
					}
					if(!visited_list.contains(id) && !avoid_list.contains(id)){
						visited_list.add(id);
						field[x][y][dir] = 1;
						field[id%5][id/5][(dir+2)%4] = 1;
						changed = 1;
						break;
					}
				}
			}
			if(changed == 0){
				avoid_list.add(to_id(x,y));
				visited_list.remove((Integer)to_id(x,y));
			}
		}
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				for(int k=0;k<4;k++){
					if(field[i][j][k] == 2){
						if(rnd.NextUnif() < 0.3) field[i][j][k] = 1;
					}
				}
			}
		}

	}
	
	public int to_id(int x, int y){
		return x+y*5;
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
