import weka.core.Instances;
import weka.core.Instance;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.*;
import weka.core.converters.ArffLoader;

public class ArffReader {

	private String PathToTrain;

	private String PathToTest;

	public ArffLoader Loader;


	public ArffReader (String trainPath , String testPath){

		PathToTrain = trainPath;

		PathToTest = testPath;
	}

	public Instances GetTrainingData () throws IOException, FileNotFoundException{

		Loader = new ArffLoader();

		Loader.setFile( new File(PathToTrain) );

		Instances Data = Loader.getStructure();

		Data.setClassIndex(Data.numAttributes()-1);

		return Data;

	}

	public Instances GetTrainingData (int classIndex) throws IOException, FileNotFoundException{

		ArffLoader Loader = new ArffLoader();
		
		Loader.setFile(new File(PathToTrain));

		Instances Data = Loader.getStructure();

		Data.setClassIndex(classIndex);

		return Data;

	}

		
	
}