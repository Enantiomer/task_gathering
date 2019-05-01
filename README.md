# 課題1の方針
## 基本方針
- 攻撃可能な場合以外 (次のステップで相手がどのマスに移動してもこちらの攻撃が当たらない場合) には移動する。
- 報酬の数が多い (具体的に何個以上が多いとするのかは調整しながら決める) ときは攻撃よりも移動を優先する。 (確率0.6~0.7くらい)
- 反対に、報酬の数が少ないときは攻撃を優先する。 (確率0.6~0.7くらい)
- 試合がn (40000 < n < 60000 くらい?) ステップ進んだとき、自分より相手がより多くの報酬を得ていた場合には攻撃を優先する。 (確率0.7~0.8くらい)

## 実装方法
エージェントの行動 (move/attack) を確率的に決定するためのメソッドを用意し、フィールド上の報酬の数、相手のスコアに応じて動的に確率を変化させる。

## 必要なモジュール及び期限
- 攻撃可能かどうかを判定するメソッド (isAttackable) (4/26まで)
```java
public boolean isAttackable(int pos[], int e_pos[]) { /* 処理 */ }
```
- 確率的に (move/attack) を 決定するメソッド (determineAct) (4/29まで)
```java
public void determineAct(int act, int e_pos[], int rewardCount) { if (hoge) { attack(act, epos) } else { move(act) }}
```
- フィールド上の報酬の数を取得するメソッド (getRewardCount / getAppleCount) (5/1まで)
```java
public int getRewardCount() { return Arrays.stream(event).flatMapToInt(x -> Arrays.stream(x)).sum() }
```
# 課題2の方針
より攻撃を優先する。
