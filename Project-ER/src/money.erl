-module(money). 
-export([start/0]). 

start() ->
	{ok, Customers} = file:consult("customers.txt"),
%%     io:format("~w~n", [Customers]),
	{Result, Banks} = file:consult("banks.txt"),
%%     io:format("~w~n", [Banks]),
	% print stuff
	%[io:format("~w: ~w.~n",[Custoemer_name,Balance]) || {Custoemer_name, Balance} <- Customers],
    io:format("~s",["\n"]),
    F_list = maps:from_list(Customers),
	F_list_banks = maps:from_list(Banks),
	F2_list = maps:from_list(Customers),
    Method_ID = self(),
	% iterate hashmap
 	ets:new(table_1, [named_table, protected, set, {keypos, 1}]),
	ets:new(table_2, [named_table, protected, set, {keypos, 1}]),
	ets:new(table_3, [named_table, protected, set, {keypos, 1}]),

	Bank_list = lists:map(fun ({K, V}) -> K end, Banks),
	io:format("~p~n", [Bank_list]),
		

	maps:fold(fun(K, V, ok) ->
    	ets:insert(table_3, {K,V}),
%% call usin P_ID ! {} something like this 
 		io:format("")
		end,ok, F_list_banks),

	%bank list is create, now get random bank from it and do stuff	

	maps:fold(fun(K, V, ok) ->
    	ets:insert(table_1, {K,V}),
		ets:insert(table_2, {K,V}),
		% map of customer, custname, banklist for random
		P_ID = spawn (customer, executeCustomer, [F_list, K, Bank_list]),
%% call usin P_ID ! {} something like this
%% name and process id 	 
		register(K, P_ID),
%% 		io:format("~w: PID ~n",[P_ID]),
		customer:receviedForCustomer(P_ID)
%% 		io:format("~w: this  ~w.~n",[K,V])
		end,ok, F_list),

	io:format("finalll ~w",[F_list]),
	ok.	



	
	
	