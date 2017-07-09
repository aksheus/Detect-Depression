import weka.core.Instances;
import weka.core.Instance;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.*;
import weka.core.converters.ArffLoader;

/*
   USAGE : java ArffReader path_to_train_arff path_to_test_arff 

   ./labeled_csa_arffs/training_csa.arff

   ./labeled_csa_arffs/test_csa.arff 

*/

public class Main{

	public static void main(String [] args){

		File CheckPath1 = new File(args[0]);
		File CheckPath2 = new File(args[1]);

		System.out.println("Copy paste the weka classifier name from weka gui : ");

		String ClassifierToBuild = System.console().readLine();

		System.out.println("Enter the % of the training data to be used : ");

		int Percentage = Integer.parseInt(System.console().readLine());

		System.out.println("Enter the policy probability threshold : ");

		double Threshold = Double.parseDouble(System.console().readLine());

		ArffReader Reader;

		if(CheckPath1.exists() && !CheckPath1.isDirectory()){

			if(CheckPath2.exists()){

			    try{

					Reader = new ArffReader();

					ClassifierBuilder Builder =  new ClassifierBuilder(
													 ClassifierToBuild,
						                             Reader.GetTrainingData(args[0]),
						                             Percentage,
						                             Threshold
					                             );
					Builder.TrainClassifier();

					Builder.PrintClassifier();

					ChunkManager Manager = new ChunkManager(args[1]);

					//Builder.EvaluateAgainstTestSet(Reader.GetAllTestData(args[1]+"/test_chunk1-2-3.arff"));

					Builder.RunEarlyRiskClassificationChunkByChunk(Manager,10);

		//			Builder.EvaluateAgainstTestSet(Reader.GetAllTestData()); 
					
                }
                catch(IOException ioe){

                        ioe.printStackTrace();
                }
                catch(Exception e){

                	e.printStackTrace();
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