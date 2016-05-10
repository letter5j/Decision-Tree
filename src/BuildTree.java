public class BuildTree {


	BuildTree() {
	}

	public Node creatNode(InputDataProcessing inputProcessing, String SituationName, String ParentName) {

		Node root = new Node();
		Gain infoGain = new Gain();
		Attribute maxGainAttribute = infoGain.getMaxGainAttribute(inputProcessing.getResult().Result_Count, inputProcessing.getAttributeList());
		root.AttributeName = maxGainAttribute.AttributeName;
		root.SituationName = SituationName;
		root.ParentName = ParentName;
		root.isLeafNode = false;

		root.ChildCount = maxGainAttribute.Situation_Count.size();

		for (String situationKey : maxGainAttribute.SituationMap.keySet()) {
			/* 當決策結果只有YES或NO一種結果 */
			if (maxGainAttribute.SituationMap.get(situationKey).Result_Count.size() == 1) {
				Node leaf = new Node();
				leaf.isLeafNode = true;
				leaf.AttributeName = maxGainAttribute.SituationMap.get(situationKey).Result_Count.keySet().toString();// ********
				leaf.SituationName = situationKey;
				leaf.ParentName = root.AttributeName;
				leaf.ChildNodes = null;

				root.ChildNodes.put(situationKey, leaf);

			} else {
				
				
				root.ChildNodes.put(situationKey, creatNode(new InputDataProcessing(inputProcessing.getChildInputData(maxGainAttribute.AttributeName, situationKey)),situationKey, root.AttributeName));
				
				
			}
		}

		return root;
	}
	
	public void printNode(Node root){
		System.out.println("Attribute = " + root.AttributeName);
		System.out.println("Situation = " + root.SituationName);
		System.out.println("Parent = " + root.ParentName);
		System.out.println("is Leaf = " + root.isLeafNode);
		System.out.println( "ChildCount = " + root.ChildCount);
		if ( root.ChildNodes == null  ) {
			return ;
		}
		for ( String SituName:root.ChildNodes.keySet() ) {
			System.out.println("------------------------------------");
			printNode( root.ChildNodes.get(SituName) );
		}
	}

}
