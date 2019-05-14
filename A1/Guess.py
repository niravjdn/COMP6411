from A1.StringDatabase import StringDatabse
import random

if __name__ == '__main__':
    Stringdb = StringDatabse()
    word_list = Stringdb.returnListOfWorkds()
    print(len(word_list))

    #guess any random word from list
    currentWord = word_list[random.randint(0,4029)]

    print('** The great guessing game ** \n\nCurrent Guess: ---- \n\ng = guess, t = tell me, l for a letter, and q to quit\n\n')
    print(currentWord)