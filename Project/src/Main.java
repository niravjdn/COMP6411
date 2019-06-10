import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	static ArrayList<Customer> customers;
	static ArrayList<Bank> banks;
	static boolean controller = true;

	public static void main(String[] args) throws IOException {
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

		System.out.println("** Customers and loan objectives **");
		for (Customer c : customers) {
			System.out.println(c);

			// set all banks by default in each customer list
			c.setBankList(banks);
		}

		System.out.println("** Banks and financial resources **");
		for (Bank b : banks) {
			System.out.println(b);
		}

		// reference :
		// https://stackoverflow.com/questions/20389890/generating-a-random-number-between-1-and-10-java
//		do {
//			controller = checkController();
//
//			// wait for the sleepTime
//			// choose random bank and request with random amount
//
//		} while (controller);
		
		for(Customer c : customers) {
			c.start();
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
		while(controller) {
			
			controller = checkController();
			// deal with thread and contact bank
			Random random = new Random();

			int amountToRequest = random.nextInt(50) + 1;
			Bank randomBank = banksList.get(random.nextInt(banksList.size()));
			int sleepTime = random.nextInt(91) + 10;
			System.out.println(name+" requests a loan of "+amountToRequest+" dollar(s) from "+ randomBank.name +".");
			//do operation like wait and make a call
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			boolean vr = randomBank.withDraw(this, amountToRequest);
			if(vr) {
				System.out.println(randomBank.name+" approves a loan of "+amountToRequest+" dollars from "+name);
				randomBank.availableBalance -= amountToRequest;
				this.loanAmount -= amountToRequest;
			}else {
				System.out.println(randomBank.name+" denies a loan of "+amountToRequest+" dollars from "+name);
				//print deny remove bank from list
				// check if bank requirement goes to negative
			}
		}
	}

	private boolean checkController() {
		
		
		for (Bank b : banksList) {
			if (b.availableBalance != 0) {
				return true;
			}
		}
		//continue looping
		return false;
	}
	
	public void setBankList(ArrayList<Bank> banksList) {
		this.banksList = new ArrayList<Bank>(banksList);
	}
}

class Bank {
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