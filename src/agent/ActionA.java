package agent;

import environment.Field1;
import core.*;

//他に使いたいライブラリがあればここに書く. 例 java.util.*;


public class ActionA {
	Field1 field;
	Sfmt rnd;
	
	//フィールドはここに書く
	

	public ActionA(Field1 f, int p[]){
		field = f;
		rnd = new Sfmt(7);
	}
	
	//この関数内に考えたアルゴリズムを記述する
	//乱数を使いたい場合はrnd.NextUnif()で0以上1未満の乱数を得られる(他の範囲の乱数もある。core/Sfmt.java参照)
	public int act(int pos[], int e_pos[]){

		
		
		//返り値別の行動一覧: 0(上移動), 1(右移動), 2(下移動), 3(左移動), 4(上攻撃), 5(右攻撃), 6(下攻撃), 7(左攻撃), それ以外(何もしない)
		return (int)(rnd.NextUnif()*9);
	}

}
