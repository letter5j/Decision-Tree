import java.util.HashMap;

public class Node {
		public boolean isLeafNode;
		public String AttributeName;	//下一個決策屬性
		public String SituationName;	//上一個決策屬性的情況
		public String ParentName;		//上一個決策屬性
		public Integer ChildCount;
		// public HashMap<String, Boolean> SituationMap = new HashMap<String,
		// Boolean>();
		public HashMap<String, Node> ChildNodes = new HashMap<String, Node>();

		public Node() {
			this.ChildCount = 0;
		}
	}