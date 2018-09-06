package Game;

public abstract class Character {
	private static boolean[] check = new boolean[16];
	private static String[] characters = {
		"��Ű��ũ","����Ѷ�","��Ʈĳ�õ�","���Ļ�","����","�������Ŀ�Ʈ","������ų��","�õ���÷",
		"���׸���","������Ű��","��������","�ָ�����","Ķ����Ƽ�ڳ�","ŰƮĮ��","���ζ�̳���","�����׷�"
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
