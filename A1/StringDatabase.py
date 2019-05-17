"""
This is a class which deals with I/O operation, here It reads and parse the file.
"""
class StringDatabse:

    def StringDatabse(self):
        pass

    def returnListOfWorkds(self):
        with open('four_letters.txt') as fi:
            list_Words = [target.replace("\n","") for line in fi for target in line.split(" ")]
            return  list_Words


#https://stackoverflow.com/questions/16922214/reading-a-text-file-and-splitting-it-into-single-words-in-python