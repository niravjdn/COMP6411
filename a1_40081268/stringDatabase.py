"""
This is a class which deals with I/O operation, here It reads and parse the file.
"""
import os
import sys


class StringDatabse:

    def __init__(self):
        pass

    def returnListOfWorkds(self):
        """
        This method return list of words from the file
        :return: list of words
        """
        file_name = "four_letters.txt"
        dirpath = os.path.dirname(__file__)
        filepath = os.path.join(dirpath, file_name)
        with open(filepath, "r") as fi:
            list_Words = [target.replace("\n","") for line in fi for target in line.split(" ")]
            return list_Words


#https://stackoverflow.com/questions/16922214/reading-a-text-file-and-splitting-it-into-single-words-in-python