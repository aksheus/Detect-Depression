import os 
import argparse

"""
        Generate dev set in such a way that 

   		1) if user is picked for train then all chunks of the user fall into train
   		2) if in oversampled mode appropriate over sampled input for the correct users should be in train set
   		3) test set should not be in oversampling 
   		4) maintain ratio of positive - negative users

"""
join = lambda x,y: os.path.join(x,y)
isdir = lambda x: os.path.isdir(x)
isfile = lambda y: os.path.isfile(y)


def get_usernames(path):
	# get user names from <path>/<positive folder>/<chunk1-2>

	subdirs = [ join(path,subdir) for subdir in os.listdir(path) if isdir(join(path,subdir))]
	print 
	positive_dir = [ subdir for subdir in subdirs if 'positive' in subdir ] [0]
	target_folder = positive_dir+'/'+'chunk1-2'

	if not isdir(target_folder):
		print 'positive users subfolder does not have chunk1-2 !!'
		exit(1)

	return [ u.split('.')[0] for u in os.listdir(target_folder) if isfile(join(target_folder,u)) ]

if __name__ == '__main__':

	parser = argparse.ArgumentParser(description='USAGE :   python deveset_generator.py --path . --oversampling yes --fparam 4 ')
	parser.add_argument('-p','--path',help='path to train data folder',required=True)
	parser.add_argument('-o','--oversampling',help='if oversampling say yes else no',required=True)
	parser.add_argument('-f','--fparam',help='f parameter')
	args= vars(parser.parse_args())

	print get_usernames(args['path'])




	
