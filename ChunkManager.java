import weka.core.Instances;
import weka.core.Instance;
import java.io.*;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
import java.util.*;

public class ChunkManager{

	private String TestDataFolder;

	private List<String> Subjects;

	private static final String FileBaseName = "test_chunk";

	private String CurrentChunk;

	private ArffReader MyArffReader;

	public ChunkManager (String testDataFolder){

		TestDataFolder = testDataFolder;

		System.out.println("chunk manager constructor recieved: "+testDataFolder);

		Subjects = new ArrayList<String> ();

		CurrentChunk = FileBaseName;
	}

	public void GoToNextChunk(int index){

		if(index > 1){

			CurrentChunk+= "-"+ Integer.toString(index);
		}
		else if(index == 1){

			CurrentChunk+=Integer.toString(index);
		}
	}

	//  subjects are not in same order in all chunks 
	public List<String> GetSubjectsForCurrentChunk(){

		Subjects.add("smthn");

		return Subjects;
	}

	public Instances GetDataFromCurrentChunk() throws Exception {

		MyArffReader = new ArffReader(TestDataFolder+"/"+CurrentChunk+".arff");

		return MyArffReader.GetAllTestData();

	}


	/* public static void main(String[] args) {

		try{

			ChunkManager cm = new ChunkManager("./labeled_csa_arffs");

			for( String s : cm.GetSubjectsForCurrentChunk()){
				System.out.println(s);
			}

			for(int i=1; i<=10; i++){

				cm.GoToNextChunk(i);

				System.out.println(cm.GetDataFromCurrentChunk());

				System.out.println("################################# "+i);

			}

		}
		catch(Exception e){

			e.printStackTrace();
		}
		
	} */

}
