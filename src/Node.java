import java.util.HashMap;

public class Node {
		public boolean isLeafNode;
		public String AttributeName;	//�U�@�ӨM���ݩ�
		public String SituationName;	//�W�@�ӨM���ݩʪ����p
		public String ParentName;		//�W�@�ӨM���ݩ�
		public Integer ChildCount;
		// public HashMap<String, Boolean> SituationMap = new HashMap<String,
		// Boolean>();
		public HashMap<String, Node> ChildNodes = new HashMap<String, Node>();

		public Node() {
			this.ChildCount = 0;
		}
	}