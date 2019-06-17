% Nirav Patel - 40081268


-module(bank).

-export([performBankOperation/5, performBankOperation2/5]).

% bank functions here like updating balance and updating user

performBankOperation(CurrentCustomer, Randombank, RandomAmount, MapOfCustomer, BankList) ->
	receive 
		{From, CurrentCustomer, Randombank, RandomAmount, MapOfCustomer, BankList} ->	
			[{Name,Req}] = ets:lookup(nirav_table_c2, CurrentCustomer),
			[{Bank,Bal}] = ets:lookup(nirav_table_b, Randombank),
%% 			io:format("Performing Operation ~w  ~w ~n",[RandomAmount, Bal]),
			if
				RandomAmount > Bal -> 
%% 		 			io:format("~w denies a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name]),
					Name ! {denied, MapOfCustomer, CurrentCustomer, Bank, RandomAmount, BankList};
				RandomAmount =< Bal ->
%% 					io:format("~w where is ~n",[whereis(Name)]),
		 			erlang:display("Hello"),
					% do stuff and update table
					Bal2 = Bal - RandomAmount,
					Req2 = Req - RandomAmount,
%% 					io:format("~w ~n",[Bal2]),
  					ets:insert(nirav_table_c2, {Name,Req2}),
 					ets:insert(nirav_table_b, {Bank,Bal2}),
%% 					io:format("~w aaaaaapproves a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name]),
%% 					io:format("~w ~n",[Name]),
%%  					io:format("~w where is ~n",[whereis(Name)]),

%% Nirav debug here 					
%% 					if
%% 						whereis(Name) == true ->
%% 							io:format("~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ")
%% 					end,

					
 					Name ! {approved, MapOfCustomer, CurrentCustomer, Bank, RandomAmount, BankList}
					% call receive
					% call customer receive
			end
		end.

performBankOperation2(CurrentCustomer, Randombank, RandomAmount, MapOfCustomer, BankList) ->
	
			[{Name,Req}] = ets:lookup(nirav_table_c2, CurrentCustomer),
			[{Bank,Bal}] = ets:lookup(nirav_table_b, Randombank),
			
%% 			if Name == "jill" ->
%%  				io:format("Performing Operation ~w  ~w ~n",[RandomAmount, Bal])
%% 			end,
			
			if
				RandomAmount > Bal -> 
%% 		 			io:format("~w denies a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name]),
					Name ! {denied, MapOfCustomer, CurrentCustomer, Bank, RandomAmount, BankList};
				RandomAmount =< Bal ->
%% 					io:format("~w where is ~n",[whereis(Name)]),
		 			erlang:display("Hello"),
					% do stuff and update table
					Bal2 = Bal - RandomAmount,
					Req2 = Req - RandomAmount,
%% 					io:format("~w ~n",[Bal2]),
  					ets:insert(nirav_table_c2, {Name,Req2}),
 					ets:insert(nirav_table_b, {Bank,Bal2}),
%% 					io:format("~w aaaaaapproves a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, Name]),
%% 					io:format("~w ~n",[Name]),
%%  					io:format("~w where is ~n",[whereis(Name)]),

%% Nirav debug here 					
%% 					if
%% 						whereis(Name) == true ->
%% 							io:format("~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ")
%% 					end,

					
 					Name ! {approved, MapOfCustomer, CurrentCustomer, Bank, RandomAmount, BankList}
					% call receive
					% call customer receive
			end.
