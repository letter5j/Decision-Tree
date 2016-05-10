import java.util.ArrayList;
import java.util.HashMap;

public class Gain {

	public double getEntropy(HashMap<String, Integer> calDataMap) {

		double total = 0;
		double totalCount = 0;
		for (String key : calDataMap.keySet()) {
			totalCount += calDataMap.get(key);
		}
		for (String key : calDataMap.keySet()) {
			total += -(calDataMap.get(key) / totalCount) * Math.log(calDataMap.get(key) / totalCount) / Math.log(2);
		}

		return total;

	}
	

	public Attribute getMaxGainAttribute(HashMap<String, Integer> resultCountMap, ArrayList<Attribute> attributeList) {

		double totalEntropy = getEntropy(resultCountMap);
		Attribute currentMaxAttribute = null;
		double currentMaxGain = 0;
		for (Attribute objAttr : attributeList) {

			double totalThisAttributeEntropy = 0;
			//System.out.println("######currentAttribute  " + objAttr.AttributeName);
			
			/*§Y Gain(outlook) = Entropy(S) - (5/14) * Entropy(sunny) - (4/14) * Entropy(overcast) - (5/14)*Entropy(rainy)*/
			double totalSituation_Count = 0;
			for (String situationName : objAttr.Situation_Count.keySet()) {
				totalSituation_Count += objAttr.Situation_Count.get(situationName);
			}
			for (String situationName : objAttr.SituationMap.keySet()) {
				totalThisAttributeEntropy += (objAttr.Situation_Count.get(situationName) / totalSituation_Count)
						* getEntropy(objAttr.SituationMap.get(situationName).Result_Count);
				//System.out.println(
				//		situationName + " " + getEntropy(objAttr.SituationMap.get(situationName).Result_Count));

			}
			//System.out.println(objAttr.AttributeName + " " + (totalThisAttributeEntropy));

			if ((totalEntropy - totalThisAttributeEntropy) > currentMaxGain) {
				currentMaxGain = (totalEntropy - totalThisAttributeEntropy);
				currentMaxAttribute = objAttr;
			}
		}
		System.out.println("currentMaxGainAttribute  " + currentMaxAttribute.AttributeName);
		//System.out.println("currentMaxGain  " + currentMaxGain);

		//System.out.println("totalEntropy  " + totalEntropy);
		return currentMaxAttribute;

	}
}
