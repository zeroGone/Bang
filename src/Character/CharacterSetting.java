package Character;

public class CharacterSetting {
	private static boolean[] check = new boolean[16];
	
	private static String[] characters = {
			"럭키듀크","로즈둘란","바트캐시디","벌쳐샘","블랙잭","수지라파예트","슬랩더킬러","시드케첨",
			"엘그링고","윌리더키드","제시존스","주르도네","캘러미티자넷","키트칼슨","페드로라미네즈","폴레그렛"
	};

	//유저마다 캐릭터 설정해주는 메소드
	public static String character() {
		int index = (int)(Math.random()*16);
		if(check[index]) return character();
		check[index]=true;
		return characters[index];
	}
}
