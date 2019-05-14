class Game:
    def __init__(self, i, word):
        self.count = i
        self.word = word

    def set_bad_guess(self, bad_guess):
        self.bad_guess = bad_guess

    def set_missing_letters(self, missing_letters):
        self.missing_letters = missing_letters

    def set_score(self, score):
        self.score = score

    def set_status(self, status):
        self.status = status
