"""
This is a Game class which records state of each game.
"""


class Game:
    frequency_list = [8.17, 1.49, 2.78, 4.25, 12.7, 2.23, 2.02, 6.09, 6.97, 0.15, 0.77, 4.03, 2.41, 6.75, 7.51, 1.93,
                      0.1, 5.99, 6.33, 9.06, 2.76, 0.98, 2.36, 0.15, 1.97, 0.07]


    def __init__(self, i, word):
        """
           Construct a new 'Foo' object.
           :param i: The name of foo
           :param word: The word
           :return: returns nothing
        """
        self.count = i
        self.word = word
        self.guess_attempt = 0
        self.letter_attempt = 0
        self.score = 0
        self.missing_letters = 0
        self.status = "Starting"

    def set_missing_letters(self, missing_letters):
        self.missing_letters = missing_letters

    def set_score(self, score):
        self.score = score

    def set_status(self, status):
        self.status = status

    def increase_guess_attemp(self):
        self.guess_attempt = self.guess_attempt + 1;

    def increase_letter_attempt(self):
        self.letter_attempt = self.letter_attempt + 1

    def calculate_final_score(self, temp, currentword, is_gave_up = False):
        index_list = self.get_index_of_hidden_char(temp)
        self.missing_letters = len(index_list)
        # calculate for all hidden things first
        for idx in index_list:
            character = currentword[idx]
            num = ord(character) - 97
            self.score = self.frequency_list[num] + self.score
        #print(self.score)
        if(is_gave_up):
            self.score = self.score * -1
            self.score = format(self.score, '.2f')
            return

        # minus points for wrong guess and letter guess used
        if self.letter_attempt != 0:
            self.score = self.score / self.letter_attempt

        # each guess cost 10%, like 2 guess const 20%, 3 cost 30%
        self.score = self.score - (self.score * 0.10 * self.guess_attempt)

        self.score = format(self.score, '.2f')

    def calculate_final_score_for_quit(self, temp, currentword, is_gave_up = False):
        index_list = self.get_index_of_hidden_char(temp)
        self.missing_letters = len(index_list)
        index_list = self.get_index_of_guessed_char(temp)

        # calculate for all guessed things first
        for idx in index_list:
            character = currentword[idx]
            num = ord(character) - 97
            self.score = self.frequency_list[num] + self.score
        self.score = self.score * -1
        self.score = format(self.score, '.2f')

    def get_index_of_hidden_char(self, temp):
        index_list = list()
        for idx, target in enumerate(temp):
            if(target == "-"):
                index_list.append(idx)
        return index_list

    def get_index_of_guessed_char(self, temp):
        index_list = list()
        for idx, target in enumerate(temp):
            if (target != "-"):
                index_list.append(idx)
        return index_list
