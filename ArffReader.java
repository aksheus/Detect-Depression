import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.*;
/*
   USAGE : java ArffReader path_to_train_arff path_to_test_arff

   ./labeled_csa_arffs/training_csa.arff

   ./labeled_csa_arffs/test_csa.arff 

*/
public class ArffReader {

	private String PathToTrain;

	private String PathToTest;

	public ArffReader (String trainPath , String testPath){

		PathToTrain = trainPath;

		PathToTest = testPath;
	}

	public Instances GetTrainingData () throws IOException, FileNotFoundException{

		BufferedReader Reader = new BufferedReader(
			                    new FileReader (PathToTrain)
			                    );

		Instances Data = new Instances(Reader);

		Reader.close();

		Data.setClassIndex(Data.numAttributes() -1);

		return Data;

	}

	public static void main(String [] args){

		File CheckPath1 = new File(args[0]);
		File CheckPath2 = new File(args[1]);

		ArffReader Reader;

 
		if(CheckPath1.exists() && !CheckPath1.isDirectory()){

			if(CheckPath2.exists() && !CheckPath2.isDirectory()){

			    try{

					Reader = new ArffReader(args[0],args[1]);

					Instances TrainingData = Reader.GetTrainingData();

					System.out.println(TrainingData);
                }
                catch(IOException ioe){

                        System.out.println(ioe.getStackTrace());
                }
			}
			else {

				System.out.println(" Test File does not exist");
				return;
			}

		}
		else {

			System.out.println(" Training File does not exist");
			return;
		}

		
	}
}