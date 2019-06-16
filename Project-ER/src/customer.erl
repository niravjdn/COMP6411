%% @author Nirav
%% @doc @todo Add description to customer.


-module(customer).

-import(lists,[nth/2]).
-export([executeCustomer/3,receviedForCustomer/1, createCustomer/3]).
-define(TIMEOUT,2500).
 

%% ====================================================================
%% Internal functions
%% ====================================================================

receviedForCustomer(M_id) ->    
    receive
		{approved, CurrentCustomer, BankName, Amount, MapOfCustomer, BankList} ->
			io:format("Approved-------------------"),
			%call something
 			receviedForCustomer(M_id);
		{denied, CurrentCustomer, BankName, Amount, MapOfCustomer, BankList} ->
			io:format("Denied"),
			%call something
			receviedForCustomer(M_id)

		after ?TIMEOUT -> io:format("TImeout ~n")			
	end.

executeCustomer(MapOfCustomer, CurrentCustomer, BankList) ->
 	io:format("Customer-name ~w ~n",[CurrentCustomer]),
	[{_,Bal}] = ets:lookup(table_2, CurrentCustomer),
	io:format("balance ~w ~n",[Bal]),
	LengthOfList = length(BankList),
	io:format("length - ~w ~n",[LengthOfList]),
	if 
		Bal >= 50 -> 
			RandomAmount = rand:uniform(50);
		true ->
			RandomAmount = rand:uniform(Bal)
	end,
	
	if
	  	(Bal >= 0) and (LengthOfList > 0) -> 
			Index = rand:uniform(length(BankList)),
			io:format("Index ~w ~n",[Index]),
			Randombank = nth(Index,BankList),
			io:fwrite("~p~n", Randombank),
			%now call bank function and wait for receive to call this another time or quit
			SleepTime = rand:uniform(90) + 10,
			timer:sleep(SleepTime),
			%call bank process that performs operation on random bank and currentCustomer
			spawn (bank, performBankOperation, [CurrentCustomer, Randombank, RandomAmount, MapOfCustomer, BankList])			
	end.


createCustomer(CustomerName, Bal, BankList) ->
    receive
        {From, {requestBank, amount}} ->
			io:format("Hello here"),
			LengthOfList = length(BankList),
			if 
			Bal >= 50 -> 
				RandomAmount = rand:uniform(50);
			true ->
				RandomAmount = rand:uniform(Bal)
			end,
		
			if
			  	(Bal >= 0) and (LengthOfList > 0) -> 
					Index = rand:uniform(length(BankList)),
					io:format("Index ~w ~n",[Index]),
					Randombank = nth(Index,BankList),
					io:fwrite("~p~n", Randombank),
					%now call bank function and wait for receive to call this another time or quit
					SleepTime = rand:uniform(90) + 10,
					timer:sleep(SleepTime),
					%call bank process that performs operation on random bank and currentCustomer
					% directly call bank process by name
				    % update balance
					Balance2  = Bal			
			end,
			createCustomer(CustomerName, Balance2, BankList);
           
		{From, {denyFromBank, BankName}} ->
			LengthOfList = length(BankList),
			if 
				LengthOfList >= 2->
				createCustomer(CustomerName, Bal, lists:delete(BankName, BankList))
			end;
			
		{From, {acceptFromBank, BankName, AmountDebited}} ->
			io:format("~w approves a loan of ~w dollars from ~w ~n",[BankName, AmountDebited, CustomerName]),
			createCustomer(CustomerName, Bal - AmountDebited, BankList);
			
		  %print
        terminate ->
            ok
    end.


%BankList is a local list of bank for particular customer.
%% call bank here until some point and then process called here will call receivedForCustomer

	

	
	
	
