package agent;

import environment.Field1;
import core.*;

//他に使いたいライブラリがあればここに書く. 例 java.util.*;
import static java.lang.Math.abs;


public class ActionA {
	Field1 field;
	Sfmt rnd;

	//フィールドはここに書く
	private static final int REWARD_THRESHOLD = 5;
	

	public ActionA(Field1 f, int p[]){
		field = f;
		rnd = new Sfmt(7);
	}
	
	//この関数内に考えたアルゴリズムを記述する
	//乱数を使いたい場合はrnd.NextUnif()で0以上1未満の乱数を得られる(他の範囲の乱数もある。core/Sfmt.java参照) 使用例: if(rnd.NextUnif() < 0.3){30%の確率で発生させたいもの}
	public int act(int pos[], int e_pos[]){
		//位置(x,y)のz方向(0(上),1(右),2(下),3(左))に進めるかどうか: field.getPosStatus(x,y,z)  返り値:{1: 進める, 1以外: 進めない}
		//位置(x,y)の各方向への進行可否の配列([0](上),[1](右),[2](下),[3](左)) field.getPosStatus(x,y)  返り値:{各方向に移動できるかの配列}  例: {1,0,1,0}
		if (field.getRewardCount() > REWARD_THRESHOLD) {
			if (rnd.NextUnif() < 0.6) {
				// とりあえず移動する
				// TODO: 報酬の位置を取得してそっちに移動するのが丸い

				return (int)(rnd.NextUnif()*4);
			}
			// とりあえず攻撃する
			return 4 + (int)(rnd.NextUnif()*4);
		} else {
			if (rnd.NextUnif() < 0.6) {
				// とりあえず攻撃する
				return 4 + (int) (rnd.NextUnif() * 4);
			}
			// とりあえず移動する
			return (int) (rnd.NextUnif() * 4);
		}
		
		//返り値別の行動一覧: 0(上移動), 1(右移動), 2(下移動), 3(左移動), 4(上攻撃), 5(右攻撃), 6(下攻撃), 7(左攻撃), それ以外(何もしない)
//		return (int)(rnd.NextUnif()*9);
	}

	private boolean isAttackable(int pos[], int e_pos[]) {
		// 相手の位置が次の移動時に攻撃範囲にくる場合にtrueを返すメソッド
		if (abs(pos[0] - e_pos[0]) >= 2 && abs(pos[1] - e_pos[1]) >= 2) return false;
		return true;
	}
}
