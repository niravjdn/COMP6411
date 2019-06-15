%% @author Nirav
%% @doc @todo Add description to bank.


-module(bank).

-export([performBankOperation/5]).

% bank functions here like updating balance and updating user

performBankOperation(CurrentCustomer, Randombank, RandomAmount, MapOfCustomer, BankList) ->
	[{Name,Req}] = ets:lookup(table_2, CurrentCustomer),
	[{Bank,Bal}] = ets:lookup(table_3, Randombank),
	if
		RandomAmount >= Bal -> 
 			io:format("~w denies a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name]);
		true ->
			io:format("~w approves a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name]),
			% do stuff and update table
			Bal2 = Bal - RandomAmount,
			Req2 = Req - RandomAmount,
			ets:insert(table_2, {Name,Req2}),
			ets:insert(table_3, {Bank,Bal2}),
			P_ID = spawn (customer, receviedForCustomer, [1]),
			P_ID ! {approved,CurrentCustomer, Bank, RandomAmount, MapOfCustomer, BankList}
			% call receive
			% call customer receive
	end.
