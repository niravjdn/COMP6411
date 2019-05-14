from A1.StringDatabase import StringDatabse
from A1.Game import Game
import random


def guessWord(word_list):
    return word_list[random.randint(0,4029)]


def printResults():
    pass


def handleSingleGuess(char, currentWord):
    print(f'You found {currentWord.count(char)} letters')
    temp = currentWord
    for c in temp:
        if(c != char):
            temp = temp.replace(c,'_')

    print(f'Current Guess: {temp}')



if __name__ == '__main__':
    Stringdb = StringDatabse()
    word_list = Stringdb.returnListOfWorkds()
    print(len(word_list))

    #guess any random word from list

    print('** The great guessing game ** \n')
    count = 0
    game_over = False
    game_quit = False
    game_list = []
    while count < 100:
        count = count + 1;
        currentWord = guessWord(word_list)
        game_list.append(Game(count, currentWord))
        print(currentWord)
        while not game_over:
            choice = input('g = guess, t = tell me, l for a letter, and q to quit')
            #take in put and take action and update game model
            if (choice == 'g'):
                guess_value = input("Enter Guess")
                if(guess_value == currentWord):
                    print('Congratulations, Starting new game')
                    break
            elif (choice == 't'):
                print(f"The word is {currentWord}")
                break;
            elif (choice == 'l'):
                char = input('Enter a letter')
                handleSingleGuess(char, currentWord)
            elif (choice == 'q'):
                game_quit = True
                break
            else:
                print('Please Enter Valid Input \n')

        if(game_quit):
            break
    
    printResults()