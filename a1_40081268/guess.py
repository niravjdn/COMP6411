"""
This is a class which handles user input and utlilzes other classes in order to perform operations.
"""


import game
import  stringDatabase
import random
import re

class Guess:

    def __init__(self):
        pass

    def guessWord(self,word_list):
        """
        Get random word from list of words
        :param word_list: list of words
        :return: random word
        """
        return word_list[random.randint(0, 4029)]

    def printResults(self,game_list, total=0.0):
        """
        Print final result
        :param game_list: list of games
        :param total: default total
        """
        print("Game   Word      Status        Bad Guesses     Missing Letters    Score")
        print("----   ----    -----------     ------------    ---------------    -----")
        for game in game_list:
            print("%s      %s      %s            %s                  %s           %s" % (
                game.count, game.word, game.status, game.guess_attempt, game.missing_letters, game.score))
            current_score = game.score
            total = total + float(game.score)
        print("Final Score: %s" % total)

    def handleSingleGuess(self,char, temp, currentWord):
        """
        Check if guess of letter is correct or not
        :param char: guessed letter
        :param temp: current string
        :param currentWord: original word
        :return: new temp string after unhiding correct letter if gueesed letter is correct
        """
        print('You found %s letters'%currentWord.count(char))
        for idx, c in enumerate(currentWord):
            if (c == char):
                temp = list(temp)
                temp[idx] = char
                temp = "".join(temp)

        print('Current Guess: %s'%temp)
        return temp

    def main(self):
        """
        Main method
        """
        Stringdb = stringDatabase.StringDatabase()
        word_list = Stringdb.returnListOfWorkds()
        # print(len(word_list))
        # guess any random word from list
        print('** The great guessing game ** \n')
        count = 0
        game_over = False
        game_quit = False
        game_list = list()
        while count < 100:
            count = count + 1
            currentWord = self.guessWord(word_list)
            #print(currentWord)
            temp = "----"
            print("Current Guess: %s" % temp)
            currentGame = game.Game(count, currentWord)
            game_over = False
            while not game_over:
                choice = input('g = guess, t = tell me, l for a letter, and q to quit\n')
                addToGame = False
                # take in put and take action and update game model
                if (choice == 'g'):
                    guess_value = input("Enter Guess:\n")
                    addToGame = True
                    if (guess_value == currentWord):
                        print('Congratulations, Starting new game\n')
                        currentGame.set_status("Success")
                        currentGame.calculate_final_score(temp, currentWord)
                        break
                    # only wrong guess attemp is counted
                    currentGame.increase_guess_attemp()
                    print("Wrong guess")
                elif (choice == 't'):
                    print("The word is %s" % currentWord)
                    addToGame = True
                    currentGame.set_status("Gave up")
                    currentGame.calculate_final_score(temp, currentWord, True)
                    break
                elif (choice == 'l'):
                    char = input('Enter a letter:\n')
                    addToGame = True
                    temp = self.handleSingleGuess(char, temp, currentWord)
                    currentGame.increase_letter_attempt()
                    # if all are discovered, mark its mark as zero
                    if ("-" not in temp):
                        game_over = True
                        # All word guessed
                        # set status success but score will be zero
                        currentGame.set_status("Success")
                        currentGame.calculate_final_score(temp, currentWord)
                elif (choice == 'q'):
                    game_quit = True

                    if(re.search(r"[a-z]", temp)):
                        # it contains a-z, not all letters are hidden
                        # minus point for it
                        currentGame.calculate_final_score_for_quit(temp, currentWord)
                        currentGame.set_status("Quit   ")
                        addToGame = True
                    break
                else:
                    print('Please Enter Valid Input \n')

            if (addToGame):
                game_list.append(currentGame)

            if (game_quit):
                break
        self.printResults(game_list)


if __name__ == '__main__':
    guess = Guess()
    guess.main()





