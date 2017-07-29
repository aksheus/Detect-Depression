import os 
import argparse

"""
        Generate dev set in such a way that 

   		1) if user is picked for train then all chunks of the user fall into train
   		2) if in oversampled mode appropriate over sampled input for the correct users should be in train set
   		3) test set should not be in oversampling 
   		4) maintain ratio of positive - negative users

"""
if __name__ == '__main__':

	parser = argparse.ArgumentParser(description='USAGE :   python deveset_generator.py --path . --oversampling yes --fparam 4 ')
	parser.add_argument('-p','--path',help='path to train data folder',required=True)
	parser.add_argument('-o','--oversampling',help='if oversampling say yes else no',required=True)
	parser.add_argument('-f','--fparam',help='f parameter')
	args= vars(parser.parse_args())

	
