from __future__ import division 
from random import shuffle 
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


def get_usernames(path,sign):
	# get user names from <path>/<positive folder>/<chunk1-2>

	subdirs = [ join(path,subdir) for subdir in os.listdir(path) if isdir(join(path,subdir))]
	required_dir = [ subdir for subdir in subdirs if sign in subdir ] [0]
	target_folder = required_dir+'/'+'chunk1-2'

	if not isdir(target_folder):
		print sign+'users subfolder does not have chunk1-2 !!'
		exit(1)

	return [ u.split('.')[0] for u in os.listdir(target_folder) if isfile(join(target_folder,u)) ]

def get_popped_list(anylist, how_much_pop):
	new = []
	counter = 0
	while  counter < how_much_pop:
		new.append(anylist.pop())
		counter+=1
	return new 

if __name__ == '__main__':

	parser = argparse.ArgumentParser(description='USAGE :   python deveset_generator.py --path ./train --splitpercentage 70 --oversampling yes --fparam 4 ')
	parser.add_argument('-p','--path',help='path to train data folder',required=True)
	parser.add_argument('-sp','--splitpercentage',help='size of the devset in percentage',required=True)
	parser.add_argument('-o','--oversampling',help='if oversampling say yes else no',required=True)
	parser.add_argument('-f','--fparam',help='f parameter')
	args= vars(parser.parse_args())

	positive_users =  get_usernames(args['path'],'positive')
	negative_users =  get_usernames(args['path'],'negative')

	shuffle(positive_users)
	shuffle(negative_users)

	total_users = len(positive_users) + len(negative_users)
	train_size = ( total_users * int(args['splitpercentage']) ) // 100 
	test_size = total_users - train_size 

	test_positive_users = (len(positive_users) * test_size ) // train_size
	test_negative_users = (len(negative_users) * test_size ) // train_size

	train_positive_users = len(positive_users) - test_positive_users
	train_negative_users = len(negative_users) - test_negative_users

	print 'test'
	print test_positive_users
	print test_negative_users
	print test_positive_users/test_negative_users

	print 'train'
	print train_positive_users
	print train_negative_users
	print train_positive_users/train_negative_users



	

	








	
