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

		ArffReader Reader;

		if(CheckPath1.exists() && !CheckPath1.isDirectory()){

			if(CheckPath2.exists() && !CheckPath2.isDirectory()){

			    try{

					Reader = new ArffReader(args[0],args[1]);

					ClassifierBuilder Builder =  new ClassifierBuilder(
						                             Reader.GetTrainingData(),
						                             Reader.GetLoader()
						                             );
					Builder.TrainClassifier(100);

					Builder.PrintClassifier();

					
                }
                catch(IOException ioe){

                        System.out.println(ioe.getStackTrace());
                }
                catch(Exception e){

                	System.out.println(e.getStackTrace());
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