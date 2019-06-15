%% @author Nirav
%% @doc @todo Add description to customer.


-module(customer).

-import(lists,[nth/2]).
-export([executeCustomer/3,receviedForCustomer/1, executeCustomerFirst/3]).
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
	receive
		{customerRequest,MapOfCustomer, CurrentCustomer, BankList} ->
			executeCustomerFirst(MapOfCustomer, CurrentCustomer, BankList),
			executeCustomer(MapOfCustomer, CurrentCustomer, BankList);
			
		
		{approved,MapOfCustomer,CurrentCustomer, Bank, RandomAmount, BankList} ->
			io:format("~w approveeeeeeeeeeee a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, CurrentCustomer]),
			executeCustomerFirst(MapOfCustomer, CurrentCustomer, BankList),
			executeCustomer(MapOfCustomer, CurrentCustomer, BankList);
		
		{nirav, ABC} ->
			io:format("nirav"),
			executeCustomerFirst(MapOfCustomer, CurrentCustomer, BankList),
			executeCustomer(MapOfCustomer, CurrentCustomer, BankList);
		
		{denied,MapOfCustomer,CurrentCustomer, Bank, RandomAmount, BankList} ->
			% delete from table
			io:format("~w denies a loan of ~w dollars from ~w ~n",[Bank, RandomAmount, CurrentCustomer]),
			executeCustomerFirst(MapOfCustomer, CurrentCustomer, BankList),
			executeCustomer(MapOfCustomer, CurrentCustomer, BankList)
			
	end.

executeCustomerFirst(MapOfCustomer, CurrentCustomer, BankList) ->
		[{_,Bal}] = ets:lookup(table_2, CurrentCustomer),
%% 		io:format("balance ~w ~n",[Bal]),
		LengthOfList = length(BankList),
%% 		io:format("length - ~w ~n",[LengthOfList]),
		if 
			Bal >= 50 -> 
				RandomAmount = rand:uniform(50);
			true ->
				RandomAmount = rand:uniform(Bal)
		end,
		
		if
		  	(Bal >= 0) and (LengthOfList > 0) -> 
				Index = rand:uniform(length(BankList)),
%% 				io:format("Index ~w ~n",[Index]),
				Randombank = nth(Index,BankList),
%% 				io:fwrite("~p~n", Randombank),
				io:format("~w requests a loan of  ~w dollar(s) from ~w ~n",[CurrentCustomer, RandomAmount , Randombank]),
				%now call bank function and wait for receive to call this another time or quit
				SleepTime = rand:uniform(90) + 10,
%% 				io:format("sleepTimee ~w ~n",[SleepTime]),
				timer:sleep(SleepTime),
				%call bank process that performs operation on random bank and currentCustomer
				PR_ID = spawn (bank, performBankOperation, [CurrentCustomer, Randombank, RandomAmount, MapOfCustomer, BankList]),
				PR_ID ! {self(),CurrentCustomer, Randombank, RandomAmount, MapOfCustomer, BankList}
%% 				receviedForCustomer(PR_ID)
		end.

%BankList is a local list of bank for particular customer.
%% call bank here until some point and then process called here will call receivedForCustomer

	
% excuteCustomer add it into receive, then it also call bank, bank calls back using name with some parameter, and it calls back customer
	
	
	
