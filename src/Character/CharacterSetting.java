package Character;

public class CharacterSetting {
	private static boolean[] check = new boolean[16];
	
	private static String[] characters = {
			"��Ű��ũ","����Ѷ�","��Ʈĳ�õ�","���Ļ�","����","�������Ŀ�Ʈ","������ų��","�õ���÷",
			"���׸���","������Ű��","��������","�ָ�����","Ķ����Ƽ�ڳ�","ŰƮĮ��","���ζ�̳���","�����׷�"
	};

	//�������� ĳ���� �������ִ� �޼ҵ�
	public static String character() {
		int index = (int)(Math.random()*16);
		if(check[index]) return character();
		check[index]=true;
		return characters[index];
	}
}
