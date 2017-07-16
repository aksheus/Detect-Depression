"""
	USAGE : shuffler.py <path_to_train_arff> <percentage> <positive sample weight> <negative sample weight> <output-file-name>

	example : python shuffler.py ./train.arff 80 2 3 output_file 

	divides training set into chunks of size 80% and 20% of original size in which instances are assigned randomly 

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

		print 'negative samples  : {0}'.format(len(negative_samples))
		print 'positive samples : {0}'.format(len(positive_samples))

		total_samples = len(positive_samples) + len(negative_samples)

		print 'total_samples : {0}'.format(total_samples)

		large_chunk_halt = (total_samples * percentage ) // 100

		print 'dev set size  : {0}'.format(large_chunk_halt)

		print 'test set size : {0}'.format(total_samples - large_chunk_halt)
		
		large_chunk_negative = ( large_chunk_halt * numerator ) // denominator

		print 'required dev set negative samples {0}'.format(large_chunk_negative) 

		large_chunk_positive = large_chunk_halt - large_chunk_negative

		print 'required dev set positive samples {0}'.format(large_chunk_positive)

		
		with open('./'+outfile+'.arff','w') as out:

			try:

				for attr in attributes:
					out.write(attr)

				# these two loops will be part of the dev set if you give the same percentage to Main.java
				for x in xrange(large_chunk_positive):

					line_to_write = positive_samples.pop()
					out.write(line_to_write)

				for y in xrange(large_chunk_negative):

					line_to_write = negative_samples.pop()
					out.write(line_to_write)

				# the below part will be part of the test set

				while positive_samples:
					out.write(positive_samples.pop())

				while negative_samples:
					out.write(negative_samples.pop())

			except IndexError:
				print 'the given ratio is not possible try another one'

		print 'Done check for'+outfile+'.arff'













		



		







