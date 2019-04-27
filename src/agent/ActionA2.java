package agent;

import environment.Field2;
import core.*;

//他に使いたいライブラリがあればここに書く. 例 java.util.*;


public class ActionA2 {
	Field2 field;
	Sfmt rnd;
	
	//フィールドはここに書く
	

	public ActionA2(Field2 f, int p[]){
		field = f;
		rnd = new Sfmt(7);
	}
	
	//この関数内に考えたアルゴリズムを記述する
	//乱数を使いたい場合はrnd.NextUnif()で0以上1未満の乱数を得られる(他の範囲の乱数もある。core/Sfmt.java参照) 使用例: if(rnd.NextUnif() < 0.3){30%の確率で発生させたいもの}
	public int act(int pos[], int e_pos[]){
		//位置(x,y)のz方向(0(上),1(右),2(下),3(左))に進めるかどうか: field.getPosStatus(x,y,z)  返り値:{1: 進める, 1以外: 進めない}
		//位置(x,y)の各方向への進行可否の配列([0](上),[1](右),[2](下),[3](左)) field.getPosStatus(x,y)  返り値:{各方向に移動できるかの配列}  例: {1,0,1,0}

		
		
		//返り値別の行動一覧: 0(上移動), 1(右移動), 2(下移動), 3(左移動), 4(上攻撃), 5(右攻撃), 6(下攻撃), 7(左攻撃), それ以外(何もしない)
		return (int)(rnd.NextUnif()*9);
	}

}
