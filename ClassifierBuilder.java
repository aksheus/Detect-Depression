import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.*;
import java.io.*;

public class ClassifierBuilder{


	private NaiveBayesUpdateable NbClassifier;

	private Instances TrainingData;

	private ArffLoader DataLoader;


	public ClassifierBuilder(Instances instances , ArffLoader loader){

		TrainingData = instances;

		DataLoader = loader;

		NbClassifier = new NaiveBayesUpdateable();


	}

	private  int GetNumberOfInstances() throws IOException {

		int Count = 0;

		while(DataLoader.getNextInstance(TrainingData) != null){

			Count++;
		}		
 
		return Count;
	}

	public void TrainClassifier(int percentage) throws Exception{


		NbClassifier.buildClassifier(TrainingData);

		int Stop = (int) 
		Math.floor((GetNumberOfInstances()*percentage)/100);

		int Start = 0;

		Instance Current;

		while((Current = DataLoader.getNextInstance(TrainingData)) != null){

			if(Start <= Stop){

				NbClassifier.updateClassifier(Current);
			}
			else{

				break;
			}

			Start++;
		}

		
	
	}

	public void PrintClassifier(){

		System.out.println(NbClassifier);
	}


}