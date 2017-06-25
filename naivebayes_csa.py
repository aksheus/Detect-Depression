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
	
	return (loader , train_data ) 
	
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
	loader , train_data = get_train_data(training_file)
	print train_data 	
	for instance in loader:
		print instance
        
	# </test> 
        
# </main>


