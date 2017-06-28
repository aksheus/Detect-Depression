import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.*;
import java.io.*;

public class ClassifierBuilder{


	private NaiveBayesUpdateable NbClassifier;

	private Instances TrainingData;

	//private Instances TestData;

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

		int Stop = (int) Math.floor((GetNumberOfInstances()*percentage)/100);

	//	System.out.println("Stop : "+Stop);

		int Start = 0;

		Instance Current;

		while(((Current = DataLoader.getNextInstance(TrainingData)) != null) && (Start<=Stop)){

				NbClassifier.updateClassifier(Current);
			    Start++;
		}
	//	System.out.println("Start : "+ Start);
	
	}

	public void PrintClassifier(){

		System.out.println(NbClassifier);
	}

	public void EvaluateAgainstTestSet(Instances TestData) throws Exception{

		

		Evaluation Eval = new Evaluation(TestData);

		double [] Predictions = NbClassifier.distributionForInstance(TestData.get(2));

		for(int Index = 0; Index < Predictions.length; Index++ ){

			System.out.println("Probability of class "+
				                TestData.classAttribute().value(Index)+
				                ": "+
				                 Double.toString(Predictions[Index]));
		}

		Eval.evaluateModel(NbClassifier,TestData);

		System.out.println(Eval.toSummaryString());

	}


}