%% @author Nirav
%% @doc @todo Add description to bank.


-module(bank).

-export([performBankOperation/2]).

% bank functions here like updating balance and updating user

performBankOperation(Bank, Bal) ->
	receive
		{From, {debitFromBank, RandomAmount}} ->
			if
				RandomAmount > Bal -> 
%%  					io:format("~w denies a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name]);
						From ! {self(), {denyFromBank, Bank}},
						io:format("");
				RandomAmount =< Bal ->
%% 						io:format("~w approves a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name]),
						Bal2 = Bal - RandomAmount,
						From ! {self(), {acceptFromBank, RandomAmount}},
						performBankOperation(Bank, Bal2)
			end
	end.
	
