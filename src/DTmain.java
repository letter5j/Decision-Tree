
public class DTmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BuildTree DecisionTree = new BuildTree();
		InputDataProcessing input = new InputDataProcessing("trainData.txt");
		Node root = DecisionTree.creatNode(input, null, null);
		DecisionTree.printNode(root);
	}

}
