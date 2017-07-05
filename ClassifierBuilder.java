import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.bayes.*;
import weka.core.Utils;
import weka.classifiers.*;
import java.io.*;

public class ClassifierBuilder{


	private NaiveBayesUpdateable NbClassifier;

	private Instances TrainingData;

	private ArffLoader DataLoader;

	private Classifier MyClassifier;

	private double PredictionThreshold;

	private int TrainingPercentage;

	public ClassifierBuilder(String classifierName,Instances instances,int percentage){

		TrainingData = instances;

		TrainingPercentage = percentage;

		try{
		
			String [] NameAndOptions = Utils.splitOptions(classifierName);

			String ClassName = NameAndOptions[0];

			NameAndOptions[0]="";

			MyClassifier = (Classifier) Utils.forName(Classifier.class,ClassName,NameAndOptions);
		}
		catch(Exception excep){

			excep.printStackTrace();
		}
	
	}

	public ClassifierBuilder(String classifierName,Instances instances,int percentage,double threshold){

		TrainingData = instances;

		TrainingPercentage = percentage;

		PredictionThreshold = threshold;

		try{
		
			String [] NameAndOptions = Utils.splitOptions(classifierName);

			String ClassName = NameAndOptions[0];

			NameAndOptions[0]="";

			MyClassifier = (Classifier) Utils.forName(Classifier.class,ClassName,NameAndOptions);
		}
		catch(Exception excep){

			excep.printStackTrace();
		}
	
	}

	// in case of inceremental (or) Updateable classifier only 
	public ClassifierBuilder(Instances instances , ArffLoader loader) {

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


	public void TrainClassifier() throws Exception{

		int SubSetSize = (int) Math.floor((TrainingData.numInstances()*TrainingPercentage)/100);
		
		int Start = 0;

		try{
				Instances TrainingDataSubSet = new Instances(TrainingData,Start,SubSetSize);

				MyClassifier.buildClassifier(TrainingDataSubSet);
		}
		catch(IllegalArgumentException iae){

			iae.printStackTrace();
		}

	}

	// this method is only valid to train classifiers implementing the 'Updateable' interface

	public void TrainClassifierIncremental(int percentage) throws Exception{


		NbClassifier.buildClassifier(TrainingData);

		int Stop = (int) Math.floor((GetNumberOfInstances()*percentage)/100);

		int Start = 0;

		Instance Current;

		while(((Current = DataLoader.getNextInstance(TrainingData)) != null) && (Start<=Stop)){

				NbClassifier.updateClassifier(Current);
			    Start++;
		}
	
	} 

	public void PrintClassifier(){

		System.out.println(MyClassifier);
	}

	public void EvaluateAgainstTestSet(Instances TestData) throws Exception{

		

		Evaluation Eval = new Evaluation(TestData);

		double [] Predictions = MyClassifier.distributionForInstance(TestData.get(2));

		for(int Index = 0; Index < Predictions.length; Index++ ){

			System.out.println("Probability of class "+
				                TestData.classAttribute().value(Index)+
				                ": "+
				                 Double.toString(Predictions[Index]));
		}

		Eval.evaluateModel(MyClassifier,TestData);

		System.out.println(Eval.toSummaryString());

	}

	// Policy function to decide when predcition is good enough as final prediction
	private boolean Policy(double probability){

		if(probability >= PredictionThreshold ){
			return true;
		}
		else {
			return false;
		}

	}

	/*
	public void RunEarlyRiskClassification(ChunkReader myChunkReader){


	}
	*/

}