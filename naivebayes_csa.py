""" build the nb classifier using the weka api 
    after training need to make predictions on an 
    incremental basis for the test data 
    
   USAGE : python naivebayes_casa.py path_to_training_arff path_to_test_arff	"""

# <imports>

import sys 
import os
import weka.core.jvm as jvm
from weka.core.converters import Loader
from weka.classifiers import Classifier,PredictionOutput,Evaluation

# </imports>

# <helper functions>

def get_train_data(arff_file,iterative= True,class_index=None):

	loader = Loader('weka.core.converters.ArffLoader')
	train_data = loader.load_file(arff_file,incremental = iterative)

	# set the attribute which is the class to be predicted 
	if class_index is None:	
		train_data.class_is_last() 
	else:
		train_data.class_index = class_index
	
	return ( train_data , loader )


def get_number_of_instances( instances ):
	count = 0
	for inst in instances:
		count+=1
	return count
	

def  get_classifier ( header , instances , classifier_name = 'weka.classifiers.bayes.NaiveBayesUpdateable' , percentage = 100 ):

	count = get_number_of_instances(instances)
	stop = (count*percentage)//100
	start = 0

	classifier = Classifier(classname = classifier_name)
	classifier.build_classifier(header)
	
	for inst in instances:
		if start <= stop:
			classifier.update_classifier(inst)
		else:
			break
	return classifier 
        
	 
# </helper functions>


# <main>
if __name__ == '__main__':

	if os.path.isfile(sys.argv[1]):

		training_file = sys.argv[1]
	else:
		print 'training file does not exist'
		exit(1)

	if os.path.isfile(sys.argv[2]):

		testing_file = sys.argv[2]
	else:
		print 'testing file does not exist'
		exit(2)

	try:
		jvm.start()

	except Exception as excep:

		print 'jvm exception : {0} '.format(excep)

	# <test>
	train_header , train_instances  = get_train_data(training_file)
	classifier = get_classifier(train_header , train_instances )
        print classifier 
	# </test> 
        
# </main>


