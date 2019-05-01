package agent;

import environment.Field1;
import core.*;

//他に使いたいライブラリがあればここに書く. 例 java.util.*;
import static java.lang.Math.abs;
import java.util.Arrays;


public class ActionA {
	Field1 field;
	Sfmt rnd;

	//フィールドはここに書く
	private static final int REWARD_THRESHOLD = 7; // 報酬数の閾値
	private static final int STEP_THRESHOLD = 60000; // ステップ数の閾値
	private static int score1 = 0; // スコア
	private static int score2 = 0;
	private static int numOfsteps = 0; // ステップ数

	public ActionA(Field1 f, int p[]){
		field = f;
		rnd = new Sfmt(7);
	}
	
	//この関数内に考えたアルゴリズムを記述する
	//乱数を使いたい場合はrnd.NextUnif()で0以上1未満の乱数を得られる(他の範囲の乱数もある。core/Sfmt.java参照) 使用例: if(rnd.NextUnif() < 0.3){30%の確率で発生させたいもの}
	public int act(int pos[], int e_pos[]){
		//位置(x,y)のz方向(0(上),1(右),2(下),3(左))に進めるかどうか: field.getPosStatus(x,y,z)  返り値:{1: 進める, 1以外: 進めない}
		//位置(x,y)の各方向への進行可否の配列([0](上),[1](右),[2](下),[3](左)) field.getPosStatus(x,y)  返り値:{各方向に移動できるかの配列}  例: {1,0,1,0}

		// scoreの概算値を計算する
		calcScore(pos, e_pos);
		// ステップ数
		numOfsteps++;
//		System.out.println("score1: " + score1 + ", score2: " + score2);
//		System.out.println("numofSteps: " + numOfsteps);
		if (numOfsteps > STEP_THRESHOLD) {
			// 試合がSTEP_THRESHOLDステップ進んだとき、自分より相手がより多くの報酬を得ていた場合には攻撃を優先する。
			if (score1 > score2 && isAttackable(pos, e_pos) && rnd.NextUnif() < 0.8) {
				return determineAttackDirection(pos, e_pos);
			}
			return determineMoveDirection(pos);
		}
		if (field.getRewardCount() > REWARD_THRESHOLD) { // 報酬の数が閾値より多かったら
			// 移動を優先
			if (rnd.NextUnif() < 0.6) return determineMoveDirection(pos);
			if (isAttackable(pos, e_pos)) return determineAttackDirection(pos, e_pos);
			return (int)(rnd.NextUnif()*4);
		} else {
			// 攻撃を優先
			if (rnd.NextUnif() < 0.6 && isAttackable(pos, e_pos)) return determineAttackDirection(pos, e_pos);
			return determineMoveDirection(pos);
		}
		
		//返り値別の行動一覧: 0(上移動), 1(右移動), 2(下移動), 3(左移動), 4(上攻撃), 5(右攻撃), 6(下攻撃), 7(左攻撃), それ以外(何もしない)
	}

	private boolean isAttackable(int pos[], int e_pos[]) {
		// 相手の位置が次の移動時に攻撃範囲にくる場合にtrueを返すメソッド
		if (abs(pos[0] - e_pos[0]) >= 2 && abs(pos[1] - e_pos[1]) >= 2) return false;
		return true;
	}

	private void calcScore(int pos[], int e_pos[]) {
		if (pos[0] == -1 || pos[1] == -1 || e_pos[0] == -1 || e_pos[1] == -1) return;
		score1 += field.getEvent(pos[0], pos[1]);
		score2 += field.getEvent(e_pos[0], e_pos[1]);
	}

	private int determineMoveDirection(int pos[]) {
		for (int y = pos[1] - 1; y <= pos[1] + 1; y++) {
			for (int x = pos[0] - 1; x <= pos[0] + 1; x++) {
				if (x > -1 && x < 5 && y > -1 && y < 5 && field.getEvent(x, y) == 1) {
					if (x == pos[0] && y == pos[1] + 1 && field.getPosStatus(pos[0], pos[1], 0) == 1) return 0; //上
					if (x == pos[0] + 1 && y == pos[1] && field.getPosStatus(pos[0], pos[1], 1) == 1) return 1; //右
					if (x == pos[0] && y == pos[1] - 1 && field.getPosStatus(pos[0], pos[1], 2) == 1) return 2; //下
					if (x == pos[0] - 1 && y == pos[1] && field.getPosStatus(pos[0], pos[1], 3) == 1) return 3; //左
				}
			}
		}
		return randomDirection(pos);
	}

	private int randomDirection(int pos[]) {
		if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{0,1,1,0})) {
			// 上と左に行けない
			int[] movableDirections = {1,2};
			return movableDirections[(int)(rnd.NextUnif() * 2)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{0,0,1,1})) {
			// 上と右に行けない
			int[] movableDirections = {2,3};
			return movableDirections[(int)(rnd.NextUnif() * 2)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,0,0,1})) {
			// 右と下に行けない
			int[] movableDirections = {0,3};
			return movableDirections[(int)(rnd.NextUnif() * 2)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,1,0,0})) {
			// 下と左に行けない
			int[] movableDirections = {0,1};
			return movableDirections[(int)(rnd.NextUnif() * 2)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,1,1,0})) {
			int[] movableDirections = {0,1,2};
			return movableDirections[(int)(rnd.NextUnif() * 3)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,1,0,1})) {
			int[] movableDirections = {0,1,3};
			return movableDirections[(int)(rnd.NextUnif() * 3)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,0,1,1})) {
			int[] movableDirections = {0,2,3};
			return movableDirections[(int)(rnd.NextUnif() * 3)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{0,1,1,1})) {
			int[] movableDirections = {1,2,3};
			return movableDirections[(int)(rnd.NextUnif() * 3)];
		} else {
			return (int)(rnd.NextUnif() * 4);
		}
	}

	private int determineAttackDirection(int pos[], int e_pos[]) {
		// 相手が右側
		if (e_pos[0] == pos[0] + 1) {
			if (e_pos[1] > pos[1] + 1) return 4; // 上攻撃
			if (e_pos[1] == pos[1] + 1) {
				int[] attackDirections = {4, 5};
				return attackDirections[(int)(rnd.NextUnif() * 2)]; //右か上に攻撃
			}
			if (e_pos[1] == pos[1]) return 5; // 右攻撃
			if (e_pos[1] == pos[1] - 1) {
				int[] attackDirections = {5, 6};
				return attackDirections[(int)(rnd.NextUnif() * 2)]; // 右か下に攻撃
			}
			return 6; //下攻撃
		}
		// 相手が左側
		if (e_pos[0] == pos[0] - 1) {
			if (e_pos[1] > pos[1] + 1) return 4; // 上攻撃
			if (e_pos[1] == pos[1] + 1) {
				int[] attackDirections = {4, 7};
				return attackDirections[(int)(rnd.NextUnif() * 2)]; // 左か上に攻撃
			}
			if (e_pos[1] == pos[1]) return 7; // 左攻撃
			if (e_pos[1] == pos[1] - 1) {
				int[] attackDirections = {6, 7};
				return attackDirections[(int)(rnd.NextUnif() * 2)]; // 左か下に攻撃
			}
			return 6; // 下攻撃
		}
		// 相手が真上か真下
		if (e_pos[1] > pos[1]) return 4;
		if (e_pos[1] == pos[1]) {
			return 4 + (int)(rnd.NextUnif()*4);
		}
		return 6;
	}
}
