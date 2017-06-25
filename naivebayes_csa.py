""" build the nb classifier using the weka api 
    after training need to make predictions on an 
    incremental basis for the test data """

# <imports>

import sys 
import weka.core.jvm as jvm
from weka.core.converters import Loader
from weka.classifiers import Classifier,PredictionOutput,Evaluation

# </imports>


