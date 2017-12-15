package game;

import java.math.BigDecimal;
import framework.game2D.Map2D;

/**
 * 迷路ゲームのステージのクラス
 * @author T.Kuno
 *
 */
public class MapStage extends Map2D {
	// コンストラクタ
	public MapStage() {
		super(new String[]{
				"data\\tile\\tile1.png",//0
				"data\\MapChip\\kabe1.png",//1
				"data\\MapChip\\kabe2.png",//2
				"data\\MapChip\\bed1.png",//3
				"data\\MapChip\\bed2.png",//4
				"data\\MapChip\\bed3.png",//5
				"data\\MapChip\\bed4.png",//6
				"data\\MapChip\\bed5.png",//7
				"data\\MapChip\\hondana0101.png",//8
				"data\\MapChip\\hondana0102.png",//9
				"data\\MapChip\\hondana0103.png",//10
				"data\\MapChip\\hondana0104.png",//11
				"data\\MapChip\\hondana0105.png",//12
				"data\\MapChip\\hondana0201.png",//13
				"data\\MapChip\\hondana0202.png",//14
				"data\\MapChip\\hondana0203.png",//15
				"data\\MapChip\\hondana0204.png",//16
				"data\\MapChip\\hondana0205.png",//17
				"data\\MapChip\\hondana0206.png",//18
				"data\\MapChip\\hondana0207.png",//19
				"data\\MapChip\\hondana0208.png",//20
				"data\\MapChip\\hondana0209.png",//21
				"data\\MapChip\\tansu0101.png",//22
				"data\\MapChip\\tansu0102.png",//23
				"data\\MapChip\\tansu0103.png",//24
				"data\\MapChip\\tansu0201.png",//25
				"data\\MapChip\\tansu0202.png",//26
				"data\\MapChip\\tansu0203.png",//27
				"data\\MapChip\\tansu0204.png",//28
				"data\\MapChip\\door0101.png",//29
				"data\\MapChip\\door0102.png",//30
				"data\\MapChip\\table0101.png",//31
				"data\\MapChip\\table0102.png",//32
				"data\\MapChip\\table0103.png",//33
				"data\\MapChip\\table0201.png",//34
				"data\\MapChip\\table0202.png",//35
				"data\\MapChip\\table0203.png",//36
				"data\\MapChip\\table0204.png",//37
				"data\\MapChip\\table0205.png",//38
				"data\\MapChip\\table0206.png",//39
				"data\\MapChip\\table0207.png",//40
				"data\\MapChip\\table0208.png",//41
				"data\\MapChip\\table0209.png",//42
				"data\\MapChip\\table0301.png",//43
				"data\\MapChip\\table0302.png",//44
				"data\\MapChip\\table0303.png",//45
				"data\\MapChip\\table0304.png",//46
				"data\\MapChip\\table0305.png",//47
				"data\\MapChip\\table0306.png",//48
				"data\\MapChip\\table0307.png",//49
				"data\\MapChip\\table0308.png",//50
				"data\\MapChip\\table0309.png",//51
				"data\\MapChip\\tv0101.png",//52
				"data\\MapChip\\tv0102.png",//53
				"data\\MapChip\\dai0101.png",//54
				"data\\MapChip\\dai0102.png",//55
				"data\\MapChip\\dai0103.png"}//56
				
		,1);
	}
	
	// 抽象メソッドの実装
	//　0～6:移動可能　7以上:障害物　
	@Override
	public int[][] createMap() {
		int[][] map = {
				{ 1, 8, 14, 13, 15, 1, 1, 22, 1, 1, 1, 1, 1, 29, 1, 2},
				{ 1, 9, 17, 16, 18, 2, 2, 23, 25, 26, 2, 2, 2, 30, 1, 1},
				{ 1, 10, 20, 19, 21, 0, 0, 24, 27, 28, 0, 0, 0, 0, 0, 1},
				{ 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 43, 44, 45, 0, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 46, 47, 48, 0, 52, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 49, 50, 51, 0, 53, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 56, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 31, 34, 35, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 32, 37, 38, 39, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 32, 40, 41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, 1},
				{ 1, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 7, 1},
				{ 1, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}				
		};
		return map;
	}

	public boolean checkGridPoint(Player mazeSpritePlayer) {
		// 丸め誤差処理用変数の生成
		double mazeSpritePositionX = new BigDecimal(mazeSpritePlayer
				.getPosition().getX()).setScale(1, BigDecimal.ROUND_DOWN)
				.doubleValue();
		double mazeSpritePositionY = new BigDecimal(mazeSpritePlayer
				.getPosition().getY()).setScale(1, BigDecimal.ROUND_DOWN)
				.doubleValue();
		
		// ステージの構成オブジェクトの位置とプレイヤーの位置が同じかどうかっ判定する
		for (int i = 0; i < this.getStageObjectList().size(); i++) {
			if (
					mazeSpritePositionX == this.getStageObjectList().get(i).getPosition().getX()
					&& mazeSpritePositionY == this.getStageObjectList().get(i).getPosition().getY()
				){
				return true;
			}

		}
		return false;
	}
}
