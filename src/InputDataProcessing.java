import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputDataProcessing {

	private ArrayList<Attribute> AttributeList = new ArrayList<Attribute>();
	private ArrayList<Situation> SituationList = new ArrayList<Situation>();
	private Result objResult = new Result();
	private ArrayList<String[]> fixedInput = new ArrayList<String[]>();

	InputDataProcessing(String inputFileLocation) {
		try {
			@SuppressWarnings("resource")
			BufferedReader inputFile = new BufferedReader(new FileReader(inputFileLocation));
			ArrayList<String> currentInputList = new ArrayList<String>();
			/* 讀行 */
			while (inputFile.ready()) {
				currentInputList.add(inputFile.readLine());
			}
			dataProcess(currentInputList);

		} catch (

		FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	InputDataProcessing(ArrayList<String> input){
		dataProcess(input);
	}
	
	private void dataProcess(ArrayList<String> currentInputList) {

		String[] HeadingText = currentInputList.get(0).split(",");
		this.fixedInput.add(HeadingText);
		
		for (int i = 0; i < HeadingText.length - 1; i++) {

			Attribute objAttr = new Attribute();
			objAttr.AttributeName = HeadingText[i];
			this.AttributeList.add(objAttr);
		}

		// 結果 PLAY = YES OR NO
		this.objResult.ResultName = HeadingText[HeadingText.length - 1];

		/* 讀行 */
		for (int currentInputLine = 1; currentInputLine < currentInputList.size(); currentInputLine++) {

			String[] tempLine = currentInputList.get(currentInputLine).split(",");
			this.fixedInput.add(tempLine);
			
			/* 每一行結果前 */
			for (int i = 0; i < tempLine.length - 1; i++) {
				Attribute objAttr = this.AttributeList.get(i);
				if (!objAttr.Situation_Count.containsKey(tempLine[i])) { // 當tempLine[i]這條屬性第一次出現時
					objAttr.Situation_Count.put(tempLine[i], 1);

					/* 記錄每一個situation造成的結果 */
					Situation objSitu = new Situation();
					objSitu.SituationName = tempLine[i];
					if (!objSitu.Result_Count.containsKey(tempLine[tempLine.length - 1])) { // YESNO不存在的話新增一個
						objSitu.Result_Count.put(tempLine[tempLine.length - 1], 1);
					} else {
						objSitu.Result_Count.put(tempLine[tempLine.length - 1],
								objSitu.Result_Count.get(tempLine[tempLine.length - 1]) + 1);
					}

					// 把此situation放入對應的Attribute
					objAttr.SituationMap.put(tempLine[i], objSitu);

				} else {
					objAttr.Situation_Count.put(tempLine[i], objAttr.Situation_Count.get(tempLine[i]) + 1);

					Situation objSitu = objAttr.SituationMap.get(tempLine[i]);
					if (!objSitu.Result_Count.containsKey(tempLine[tempLine.length - 1])) {
						objSitu.Result_Count.put(tempLine[tempLine.length - 1], 1);
					} else {
						objSitu.Result_Count.put(tempLine[tempLine.length - 1],
								objSitu.Result_Count.get(tempLine[tempLine.length - 1]) + 1);
					}
				}
			}

			/* 記錄每一行之結果值 */
			if (!objResult.Result_Count.containsKey(tempLine[tempLine.length - 1])) {
				objResult.Result_Count.put(tempLine[tempLine.length - 1], 1);
				objResult.ResultList.add(tempLine[tempLine.length - 1]);
			} else {
				objResult.Result_Count.put(tempLine[tempLine.length - 1],
						objResult.Result_Count.get(tempLine[tempLine.length - 1]) + 1);
			}
		}
	}

	public ArrayList<String> getChildInputData(String maxGainAttributeName, String situationName) {
		ArrayList<String> childInputList = new ArrayList<String>();
		int getAttributeIndex = 0;
		String headAttribute = "";
		for(int i = 0; i < this.fixedInput.get(0).length; i++){
			if(maxGainAttributeName.equals(this.fixedInput.get(0)[i])){
				getAttributeIndex = i;
				continue;
			}
			if(i == this.fixedInput.get(0).length - 1){
				headAttribute += this.fixedInput.get(0)[i];
				break;
			}
			headAttribute += this.fixedInput.get(0)[i] + ",";

		}
		childInputList.add(headAttribute);
		
		
		for(int i = 1; i < this.fixedInput.size(); i++){
			
			String[] tempLine = this.fixedInput.get(i);
			String tempStr = ""; 
			if(tempLine[getAttributeIndex].equals(situationName)){
				for(int j = 0; j < tempLine.length; j++){
					if(j == getAttributeIndex) continue;
					if(j == tempLine.length - 1){
						tempStr += tempLine[j];
						break;
					}
					tempStr += tempLine[j] + ",";
				}
			}
			childInputList.add(tempStr);
		}
		return childInputList;
	}

	public ArrayList<Attribute> getAttributeList() {
		return this.AttributeList;
	}

	public ArrayList<Situation> getSituationList() {
		for (Attribute attr : this.AttributeList) {
			for (String SituationName : attr.SituationMap.keySet()) {
				this.SituationList.add(attr.SituationMap.get(SituationName));
			}
		}
		return this.SituationList;
	}

	public Result getResult() {
		return this.objResult;
	}
}
