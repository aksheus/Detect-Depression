"""
	USAGE : shuffler.py <path_to_train_arff> <percentage> <positive sample weight> <negative sample weight> <output-file-name>

	example : python shuffler.py ./train.arff 80 2 3 out

	divides training set into chunks of size 80% and 20% of original size

	and each set will maintain the ratio 2:3 for positive_samples : negative_samples

"""
from __future__ import division
from random import shuffle 
import sys
import os
import traceback


if __name__=='__main__':

	if os.path.isfile(sys.argv[1]):
		train_file = sys.argv[1]
	else:
		print 'training file does not exist (or) not found'
		exit(1)

	try:
		percentage = int(sys.argv[2])

		if percentage < 0 or percentage > 100:
			print 'percentage range must be [0-100]'
			exit(2)

		numerator = int(sys.argv[3])
		denominator = int(sys.argv[4])

		ratio = numerator/denominator

		if ratio < 0 or ratio > 1:
			print 'ratio of positive to negative samples is invalid'
			exit(3)

		outfile = sys.argv[5] 

	except Exception:
		print traceback.format_exc()

	attributes = []
	positive_samples = []
	negative_samples = []

	with open(train_file) as tf:

		data_flag= False

		for line in tf:

			pieces = line.split()

			if data_flag:

				if pieces[-2] == '1':
					positive_samples.append(line)
				elif pieces[-2] == '0':
					negative_samples.append(line)
				
			else:
				attributes.append(line)


			if pieces[0] == '@data':
				data_flag = True

		shuffle(positive_samples)
		shuffle(negative_samples)

		total_samples = len(positive_samples) + len(negative_samples)

		print total_samples

		large_chunk_halt = (total_samples * percentage ) // 100

		print large_chunk_halt

		large_chunk = []

		large_chunk_negative = ( large_chunk_halt * numerator ) // denominator 

		print large_chunk_negative

		large_chunk_positive = large_chunk_halt - large_chunk_negative

		print large_chunk_positive






		



		







