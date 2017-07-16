"""
	USAGE : shuffler.py <path_to_train_arff> <percentage> <positive:negative_porportion> <output-file-name>

"""
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

		proportion = float(sys.argv[3])

		if proportion < 0 or proportion > 1 :
			print 'proportion must be a ratio between 0 and 1'

		outfile = sys.argv[4] 

	except Exception:
		print traceback.format_exc()

	attributes = []
	positive_samples = []
	negative_samples = []

	with open(train_file) as tf:

		data_flag= False

		debug = True

		for line in tf:

			pieces = line.split()

			if data_flag:

				if debug:
					print 'line : {0}'.format(line)
					print 'line [-2] : {0}'.format(line[-2])
					debug = False

				if pieces[-2] == '1':
					positive_samples.append(line)
				elif pieces[-2] == '0':
					negative_samples.append(line)
				
			else:
				attributes.append(line)


			if pieces[0] == '@data':
				data_flag = True

		print len(attributes)
		print attributes
		print len(positive_samples)
		#print positive_samples
		print len(negative_samples)
		#print negative_samples







