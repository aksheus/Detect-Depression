import weka.core.Instances;
import weka.core.Instance;
import java.io.*;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class ArffReader {

	private String PathToTrain;

	private String PathToTest;

	private ArffLoader Loader;


	public ArffReader (String trainPath , String testPath){

		PathToTrain = trainPath;

		PathToTest = testPath;
	}

	public Instances GetTrainingData () throws Exception {

		DataSource Source = new DataSource(PathToTrain);

		Instances Data = Source.getDataSet();

		Data.setClassIndex(Data.numAttributes()-1);

		return Data;

	}


	public Instances GetTrainingData (int classIndex) throws Exception {

		DataSource Source = new DataSource(PathToTrain);

		Instances Data = Source.getDataSet();

		Data.setClassIndex(classIndex);

		return Data;

	}


	public Instances GetTrainingDataIncremental () throws IOException, FileNotFoundException{

		Loader = new ArffLoader();

		Loader.setFile( new File(PathToTrain) );

		Instances Data = Loader.getStructure();

		Data.setClassIndex(Data.numAttributes()-1);

		return Data;

	}


	public Instances GetTrainingDataIncremental (int classIndex) throws IOException, FileNotFoundException{

		Loader = new ArffLoader();
		
		Loader.setFile(new File(PathToTrain));

		Instances Data = Loader.getStructure();

		Data.setClassIndex(classIndex);

		return Data;

	}

	public ArffLoader GetLoader(){

		return Loader;
	}

	// Do class attribute and it's associated values be chucked off in test data before this ?
	public Instances GetAllTestData () throws Exception {

		BufferedReader BReader = new BufferedReader(
							     new FileReader(PathToTest)
			                     );

		Instances Data = new Instances(BReader);

		BReader.close();

		Data.setClassIndex(Data.numAttributes()-1);

		return Data;
	}
	
}