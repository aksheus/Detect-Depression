"""
      Generates test data choose a chunk and it distributes it randomly so that 

      Feature Space Tree can build the required vectors 

      USAGE  : generate-test_data.py path_to_test_data path_to_target_folders which_chunk_to_distribute
"""

# <imports>
import os
import sys 
import random
#</imports>

#< helper functions>

def get_source_files(data_path,required_chunk):

	chunk_dir = os.path.join(data_path,required_chunk)

	files = [ file for file in os.listdir(chunk_dir) 
	          if os.path.isfile(
                os.path.join(chunk_dir,file)
	         ) ]

	return files

 

#</helper functions>

# <main>
if __name__ == '__main__':

	if os.path.isdir(sys.argv[1]):
		data_path = sys.argv[1]
	else:
		print 'test data directory is invalid'
		exit(1)

	if os.path.isdir(sys.argv[2]):
		target_path = sys.argv[2]
	else:
		print 'target directory is invalid'
		exit(2)

	if sys.argv[3] is not None:
		required_chunk = sys.argv[3]

	print get_source_files(data_path,required_chunk)

#</main>