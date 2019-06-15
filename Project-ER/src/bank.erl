%% @author Nirav
%% @doc @todo Add description to bank.


-module(bank).

-export([performBankOperation/5]).

% bank functions here like updating balance and updating user

performBankOperation(CurrentCustomer, Randombank, RandomAmount, MapOfCustomer, BankList) ->
	receive 
		{debitBank, CurrentCustomer, Randombank, RandomAmount, MapOfCustomer, BankList} ->	
			[{Name,Req}] = ets:lookup(table_2, CurrentCustomer),
			[{Bank,Bal}] = ets:lookup(table_3, Randombank),
			io:format("Performing Operation ~w  ~w ~n",[RandomAmount, Bal]),
			if
				RandomAmount > Bal -> 
		 			io:format("~w denies a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name]);
				RandomAmount =< Bal ->
					io:format("~w where is ~n",[whereis(Name)]),
		 			
					% do stuff and update table
%% 					Bal2 = Bal - RandomAmount,
%% 					Req2 = Req - RandomAmount,
					io:format("~w aaaaaapproves a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name])
%% 					ets:insert(table_2, {Name,Req2}),
%% 					ets:insert(table_3, {Bank,Bal2}),
%% 					io:format("~w ~n",[Name]),
%% 					id:format("~w where is ~n",[whereis(Name)]),
%% 					Name ! {approved, MapOfCustomer, CurrentCustomer, Bank, RandomAmount, BankList},
%% 					Name ! {nirav, BankList}
					% call receive
					% call customer receive
			end
		end.
