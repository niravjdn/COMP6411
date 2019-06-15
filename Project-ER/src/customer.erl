%% @author Nirav
%% @doc @todo Add description to customer.


-module(customer).

%% ====================================================================
%% API functions
%% ====================================================================
-export([executeCustomer/3,receviedForCustomer/1]).
-define(TIMEOUT,1500).
-import(lists,[nth/2]). 


%% ====================================================================
%% Internal functions
%% ====================================================================


executeCustomer(MapOfCustomer, CurrentCustomer, BankList) ->
 	io:format("Customer-name ~w ~n",[CurrentCustomer]),
	[{_,Bal}] = ets:lookup(table_2, CurrentCustomer),
	io:format("balance ~w ~n",[Bal]),
	LengthOfList = length(BankList),
	io:format("length - ~w ~n",[LengthOfList]),
	if
	  (Bal >= 0) and (LengthOfList > 0) -> 
		Index = rand:uniform(length(BankList)),
		io:format("Index ~w ~n",[Index]),
		io:fwrite("~p~n",[nth(Index,BankList)]),
		%now call bank function and wait for receive to call this another time or quit
		SleepTime = rand:uniform(90) + 10,
		timer:sleep(SleepTime)
	end.

	%BankList is a local list of bank for particular customer.
	%% add receive quote here
	%% call bank here until some point and then process called here will call receivedForCustomer

receviedForCustomer(M_id) ->    
    receive
		{intro,Sender,Receiver,Ms} ->
			io:format("~w received intro message from ~w [~w]~n",[Receiver,Sender,Ms]),
			receviedForCustomer(M_id);
		{reply,Sender,Receiver,Ms} ->
			io:format("~w received reply message from ~w [~w]~n",[Sender,Receiver,Ms]),
			receviedForCustomer(M_id)

		after ?TIMEOUT -> io:format("TImeout ~n")			
	end.
