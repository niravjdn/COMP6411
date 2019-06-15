% hello world program
-module(banks). 
-export([start/0, executeBank/3]). 

start() ->
	{ok, Customers} = file:consult("customers.txt"),
    io:format("~w~n", [Customers]),
	{Result, Banks} = file:consult("banks.txt"),
    io:format("~w~n", [Banks]),
	% print stuff
	%[io:format("~w: ~w.~n",[Custoemer_name,Balance]) || {Custoemer_name, Balance} <- Customers],
    io:format("~s",["\n"]),
    F_list = maps:from_list(Customers),
	F2_list = maps:from_list(Customers),
    Method_ID = self(),
	% iterate hashmap
 	%ets:new(table_1, [named_table, protected, set, {keypos, 1}]),
	maps:fold(fun(K, V, ok) ->
%%    	ets:insert(table_1, {a,K}),
		P_ID = spawn (banks, executeBank, ['rbc', F_list, K]),
%% call usin P_ID ! {} something like this 
		io:format("~w: PID ~n",[P_ID]),
		io:format("~w: this  ~w.~n",[K,V])
		end,ok, F_list),
	io:format("finalll ~w",[F_list]),
	ok.	

executeBank(Bank, MapOfCustomer, CurrentCustomer) ->
 	io:format("abc ~w",[MapOfCustomer]),
%% add receive quote here 	
	maps:put("a", 42, MapOfCustomer).
	
	