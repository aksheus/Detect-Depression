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

# <main>
if __name__ == '__main__':

	if os.path.isfile(sys.argv[1]):
		training_file = sys.argv[1]
	else:
		print 'training file does not exist'

	if os.path.isfile(sys.argv[2]):
		testing_file = sys.argv[2]
	else:
		print 'testing file does not exist'

# </main>
