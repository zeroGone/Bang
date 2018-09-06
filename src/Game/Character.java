package Game;

public abstract class Character {
	private static boolean[] check = new boolean[16];
	private static String[] characters = {
		"럭키듀크","로즈둘란","바트캐시디","벌쳐샘","블랙잭","수지라파예트","슬랩더킬러","시드케첨",
		"엘그링고","윌리더키드","제시존스","주르도네","캘러미티자넷","키트칼슨","페드로라미네즈","폴레그렛"
	};
	
	public static String character() {
		int index = (int)(Math.random()*16);
		while(true) {
			if(!check[index]) {
				check[index]=true;
				break;
			}
			index=(int)(Math.random()*16);
		}
		return characters[index];
	}
}
