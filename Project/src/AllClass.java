import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import sun.util.logging.resources.logging;

public class AllClass {
	static ArrayList<Customer> customers;
	static ArrayList<Bank> banks;
	static boolean controller = true;
	static boolean[] recordPrint;

	public static void runProgram() throws IOException, InterruptedException {
		ArrayList<String> customerLines = readAndReturnLines("customers.txt");
		ArrayList<String> banksLines = readAndReturnLines("banks.txt");
		customers = new ArrayList<Customer>();
		banks = new ArrayList<Bank>();

		for (String line : customerLines) {
			String currentLine = line.replace("{", "");
			currentLine = currentLine.replace("}.", "");
			String[] ar = currentLine.split(",");
			customers.add(new Customer(ar[0], Integer.parseInt(ar[1])));
		}

		for (String line : banksLines) {
			String currentLine = line.replace("{", "");
			currentLine = currentLine.replace("}.", "");
			String[] ar = currentLine.split(",");
			banks.add(new Bank(ar[0], Integer.parseInt(ar[1])));
		}

		int numOfCustomers = customers.size();
		int numOfBanks = banks.size();
		recordPrint = new boolean[numOfCustomers];

		System.out.println("** Customers and loan objectives **");
		printCustomers();

		System.out.println("** Banks and financial resources **");
		printBanks();

		// reference :
		// https://stackoverflow.com/questions/20389890/generating-a-random-number-between-1-and-10-java
//		do {
//			controller = checkController();
//
//			// wait for the sleepTime
//			// choose random bank and request with random amount
//
//		} while (controller);

		for (Customer c : customers) {
			c.start();
		}

		for (Thread t : customers) {
			// waits for this thread to die
			t.join();
		}
		System.out.println("All the threads are completed by now");
		printCustomersAfterProgramIsDone();

	}

	private static void printBanks() {
		for (Bank b : banks) {
			System.out.println(b);
		}
	}

	private static void printCustomersAfterProgramIsDone() {
		for (Customer c : customers) {
			//System.out.println(c + " balance : " + c.balance);
			
			if(c.loanAmount == c.balance) {
				System.out.println(c.name+" has reached the objective of "+c.loanAmount+" dollar(s). Woo Hoo!");
			}else{
				System.out.println(c.name+" was only able to borrow "+c.balance+" dollar(s). Boo Hoo!");
			}
		}
		
		for (Bank b : banks) {
			System.out.println(b.name+" has "+b.availableBalance+" dollar(s) remaining.");
		}
	}

	private static void printCustomers() {
		for (Customer c : customers) {
			System.out.println(c);

			// set all banks by default in each customer list
			c.setBankList(banks);
		}
	}

	private static boolean checkController() {
		for (Customer c : customers) {
			if (c.loanAmount != 0) {
				return false;
			}
		}

		for (Bank b : banks) {
			if (b.availableBalance != 0) {
				return false;
			}
		}

		return true;
	}

	public static ArrayList<String> readAndReturnLines(String fileName) throws IOException {
		BufferedReader bufReader = new BufferedReader(new FileReader(fileName));
		ArrayList<String> listOfLines = new ArrayList<>();

		String line = bufReader.readLine();
		while (line != null) {
			listOfLines.add(line);
			line = bufReader.readLine();
		}

		bufReader.close();
		return listOfLines;
	}
}

class Customer extends Thread {
	String name;
	int loanAmount;
	int balance = 0;
	ArrayList<Bank> banksList;
	Random random = new Random();
	
	public Customer(String name, int balance) {
		this.name = name;
		this.loanAmount = balance;

	}

	@Override
	public String toString() {
		return name + ": " + loanAmount;
	}

	@Override
	public void run() {
		boolean controller = true;
		while (controller) {
			// deal with thread and contact bank
			
			
			if(banksList.isEmpty()) {
				break;
			}
			
			int amountToRequest = random.nextInt(50) + 1;
			
			//Nirav Remove this
			if((loanAmount - balance) < 50) {
				amountToRequest = random.nextInt(loanAmount - balance) + 1;
			}
			
			Bank randomBank = banksList.get(random.nextInt(banksList.size()));
			int sleepTime = random.nextInt(91) + 10;
			System.out.println(
					name + " requests a loan of " + amountToRequest + " dollar(s) from " + randomBank.name + ".");
			// do operation like wait and make a call
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			boolean vr = randomBank.withDraw(this, amountToRequest);
			if (vr) {
				System.out
						.println(randomBank.name + " approves a loan of " + amountToRequest + " dollars from " + name);
				
				randomBank.availableBalance -= amountToRequest;
				this.balance += amountToRequest;
			} else {
				System.out.println(randomBank.name + " denies a loan of " + amountToRequest + " dollars from " + name);
				banksList.remove(randomBank);
				// check if bank requirement goes to negative
			}

			controller = checkController();
		}
	}

	private boolean checkController() {
		if (loanAmount == balance || banksList.isEmpty()) {
			return false;
		}

//		for (Bank b : banksList) {
//			if (b.availableBalance != 0) {
//				return true;
//			}
//		}
		// continue looping
		return true;
	}

	public void setBankList(ArrayList<Bank> banksList) {
		this.banksList = new ArrayList<Bank>(banksList);
	}
}

class Bank extends Thread{
	String name;
	int availableBalance;

	public Bank(String name, int balance) {
		this.name = name;
		this.availableBalance = balance;
	}

	public synchronized boolean withDraw(Customer c, int amount) {
		if ((availableBalance - amount) >= 0) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return name + ": " + availableBalance;
	}
}

//Reference : https://www.geeksforgeeks.org/method-block-synchronization-java/

//Reference : Thread Wait Reference
