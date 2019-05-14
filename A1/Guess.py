from A1.StringDatabase import StringDatabse
from A1.Game import Game
import random


def guessWord(word_list):
    return word_list[random.randint(0,4029)]


def printResults(game_list):
    print("Game   Word      Status        Bad Guesses     Missing Letters    Score")
    print("----   ----    -----------     ------------    ---------------    -----")
    for game in game_list:
        print(f"{game.count}      {game.word}      {game.status}            {game.guess_attempt}                  {game.missing_letters}           {game.score}")


def handleSingleGuess(char, temp, currentWord):
    print(f'You found {currentWord.count(char)} letters')
    for idx,c in enumerate(currentWord):
        if(c == char):
            temp = list(temp)
            temp[idx] = char
            temp = "".join(temp)

    print(f'Current Guess: {temp}')
    return temp


if __name__ == '__main__':
    Stringdb = StringDatabse()
    word_list = Stringdb.returnListOfWorkds()
    print(len(word_list))

    #guess any random word from list

    print('** The great guessing game ** \n')
    count = 0
    game_over = False
    game_quit = False
    game_list = list()
    while count < 2:
        print("Starting")
        count = count + 1;
        currentWord = guessWord(word_list)
        currentWord='laid'
        print(currentWord)
        temp = "----"
        game = Game(count, currentWord)
        print(len(game.frequency_list))
        game_over = False
        while not game_over:
            choice = input('g = guess, t = tell me, l for a letter, and q to quit\n')
            #take in put and take action and update game model
            if (choice == 'g'):
                guess_value = input("Enter Guess:\n")

                if(guess_value == currentWord):
                    print('Congratulations, Starting new game\n')
                    game.set_status("Success")
                    game.calculate_final_score(temp, currentWord)
                    break
                #only wrong guess attemp is counted
                game.increase_guess_attemp()
            elif (choice == 't'):
                print(f"The word is {currentWord}")
                game.set_status("Gave up")
                game.calculate_final_score(temp, currentWord, True)
                break;
            elif (choice == 'l'):
                char = input('Enter a letter:\n')
                temp = handleSingleGuess(char, temp, currentWord)
                game.increase_letter_attempt()
                #if all are discovered, mark its mark as zero
                if("-" not in temp):
                    game_over = True
                    game.calculate_final_score(temp, currentWord)
            elif (choice == 'q'):
                game_quit = True
                break
            else:
                print('Please Enter Valid Input \n')

        game_list.append(game)

        if(game_quit):
            break
    
    printResults(game_list)

def calculate_score(game):
    pass




