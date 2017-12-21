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
				"data\\MapChip\\yuka1.png",//0
				"data\\MapChip\\bed4.png",//1
				"data\\MapChip\\bed5.png",//2
				"data\\MapChip\\table0307.png",//3
				"data\\MapChip\\table0308.png",//4
				"data\\MapChip\\table0309.png",//5
				"data\\MapChip\\hondana0207.png",//6
				"data\\MapChip\\hondana0208.png",//7
				"data\\MapChip\\hondana0209.png",//8
				"data\\MapChip\\tansu0103.png",//9
				"data\\MapChip\\tansu0203.png",//10
				"data\\MapChip\\tansu0204.png",//11
				"data\\MapChip\\table0207.png",//12
				"data\\MapChip\\table0208.png",//13
				"data\\MapChip\\table0209.png",//14
				"data\\MapChip\\chair1.png",//15
				
				"data\\MapChip\\kabe1.png",//16
				"data\\MapChip\\kabe2.png",//17
				"data\\MapChip\\bed1.png",//18
				"data\\MapChip\\bed2.png",//19
				"data\\MapChip\\bed3.png",//20
				"data\\MapChip\\hondana0101.png",//21
				"data\\MapChip\\hondana0102.png",//22
				"data\\MapChip\\hondana0103.png",//23
				"data\\MapChip\\hondana0104.png",//24
				"data\\MapChip\\hondana0105.png",//25
				"data\\MapChip\\hondana0201.png",//26
				"data\\MapChip\\hondana0202.png",//27
				"data\\MapChip\\hondana0203.png",//28
				"data\\MapChip\\hondana0204.png",//29
				"data\\MapChip\\hondana0205.png",//30
				"data\\MapChip\\hondana0206.png",//31
				"data\\MapChip\\tansu0101.png",//32
				"data\\MapChip\\tansu0102.png",//33
				"data\\MapChip\\tansu0201.png",//34
				"data\\MapChip\\tansu0202.png",//35
				"data\\MapChip\\door0101.png",//36
				"data\\MapChip\\door0102.png",//37
				"data\\MapChip\\table0101.png",//38
				"data\\MapChip\\table0102.png",//39
				"data\\MapChip\\table0103.png",//40
				"data\\MapChip\\table0201.png",//41
				"data\\MapChip\\table0202.png",//42
				"data\\MapChip\\table0203.png",//43
				"data\\MapChip\\table0204.png",//44
				"data\\MapChip\\table0205.png",//45
				"data\\MapChip\\table0206.png",//46
				"data\\MapChip\\table0301.png",//47
				"data\\MapChip\\table0302.png",//48
				"data\\MapChip\\table0303.png",//49
				"data\\MapChip\\table0304.png",//50
				"data\\MapChip\\table0305.png",//51
				"data\\MapChip\\table0306.png",//52
				"data\\MapChip\\tv0101.png",//53
				"data\\MapChip\\tv0102.png",//54
				"data\\MapChip\\dai0101.png",//55
				"data\\MapChip\\dai0102.png",//56
				"data\\MapChip\\dai0103.png",//57
	            "data\\MapChip\\pc1.png",//58
	            "data\\MapChip\\pencase1.png",//59
	            "data\\MapChip\\sumaho.png",//60
	            "data\\MapChip\\syoruidana1.png",//61
	            "data\\MapChip\\calender1.png",//62
	            "data\\MapChip\\gakubuti1.png",//63
	            "data\\MapChip\\gomi1.png",//64
	            "data\\MapChip\\gomi2.png",//65
	            "data\\MapChip\\bag1.png",//66
	            "data\\MapChip\\book2.png",//67
	            "data\\MapChip\\book3.png"}//68
		
				
		,16);
	}
	
	// 抽象メソッドの実装
	//　0～6:移動可能　7以上:障害物　
	@Override
	public int[][] createMap() {
		int[][] map = {

				{ 17, 21, 21, 21, 21, 16, 16, 32, 16, 16, 16, 16, 16, 36, 16, 17},
				{ 17, 22, 29, 30, 31, 62, 17, 33, 34, 35, 63, 17, 17, 37, 17, 17},
				{ 17, 23, 6, 7, 8, 0, 0, 9, 10, 11, 0, 0, 0, 0, 0, 17},
				{ 17, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17},
				{ 17, 25, 0, 0, 0, 0, 0, 0, 0, 0, 47, 48, 49, 0, 0, 17},
				{ 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 51, 52, 0, 53, 17},
				{ 17, 64, 0, 0, 0, 0, 0, 0, 0, 0, 3, 4, 5, 0, 54, 17},
				{ 17, 65, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 68, 57, 17},
				{ 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 67, 0, 0, 0, 0, 17},
				{ 17, 38, 41, 42, 43, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17},
				{ 17, 39, 44, 45, 61, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17},
				{ 17, 59, 12, 13, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 17},
				{ 17, 39, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 20, 17},
				{ 17, 58, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 17},
				{ 17, 40, 0, 0, 0, 66, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17},
				{ 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17}				

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
