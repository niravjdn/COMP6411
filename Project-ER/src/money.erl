-module(money). 
-export([start/0,printStuff/2, printFinalOutput/2]). 
-define(TIMEOUT,2500).

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
 	ets:new(table_1, [named_table, public, set, {keypos, 1}]),
	ets:new(table_2, [named_table, public, set, {keypos, 1}]),
	ets:new(table_3, [named_table, public, set, {keypos, 1}]),

	Customer_list = lists:map(fun ({K, V}) -> K end, Customers),
	Bank_list = lists:map(fun ({K, V}) -> K end, Banks),
%% 	io:format("~p~n", [Bank_list]),

	io:format("** Customers and loan objectives **"),
	
	
	maps:fold(fun(K, V, ok) ->
 		io:format("~w: ~w ~n",[K,V])
		end,ok, F_list),

	io:format("** Banks and financial resources ~n"),
	
	maps:fold(fun(K, V, ok) ->
    	ets:insert(table_3, {K,V}),
%% call usin P_ID ! {} something like this
		PR_ID = spawn (bank, performBankOperation, [a, ab, ab, ab, ab]), 
		register(K, PR_ID),
 		io:format("~w: ~w ~n",[K,V])
		end,ok, F_list_banks),

	%bank list is create, now get random bank from it and do stuff	
	Master_ID = self(),
	
	maps:fold(fun(K, V, ok) ->
    	ets:insert(table_1, {K,V}),
		ets:insert(table_2, {K,V}),
		% map of customer, custname, banklist for random
		
		P_ID = spawn (customer, executeCustomer, [Master_ID, F_list, K, Bank_list]),
%% call usin P_ID ! {} something like this
%% name and process id 	 
%% 		io:format("Registering"),
		register(K, P_ID),
		K ! {customerRequest,F_list, K, Bank_list},
%% 		io:format("~w: PID ~n",[P_ID]),
%% 		customer:receviedForCustomer(P_ID)
%% 		io:format("~w: ~w ~n",[K,V]),
		io:format("")
		end,ok, F_list),

	printStuff(Customer_list, Bank_list),
	io:format("finalll ~w",[F_list]),
	ok.	

printStuff(Customer_list, Bank_list) ->    
    receive
		{finished,Message} ->
%% 			io:format("~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ~n ~n"),
			printStuff(Customer_list, Bank_list)
					
		after ?TIMEOUT ->
%% 			io:format("~n ~n ~n ~n ~n Master has received no replies for ~n"),
			io:format("~n ~n ~n"),
			printFinalOutput(Customer_list, Bank_list),
			halt(0)			
	end.

printFinalOutput(Customer_list, Bank_list) ->
	lists:foreach(fun (Item) ->
%% 		io:format("~w ~n",[Item]),
		[{Name1,Req1}] = ets:lookup(table_1, Item),
		[{Name2,Req2}] = ets:lookup(table_2, Item),
		if
			Req2 == 0 ->
				io:format("~w has reached the objective of ~w dollar(s). Woo Hoo! ~n",[Name1, Req1]);
			true ->
				io:format("~w was only able to borrow ~w dollar(s). Boo Hoo! ~n",[Name2, Req1-Req2])
		end
	end, Customer_list),

	lists:foreach(fun (Item) ->
%% 		io:format("~w ~n",[Item]),
		[{Name,Bal}] = ets:lookup(table_3, Item),
		io:format("~w has ~w dollar(s) remaining. ~n",[Name, Bal])
	end, Bank_list).
	
	
	