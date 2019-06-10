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
		
		for(String line : customerLines) {
			String currentLine = line.replace("{", "");
			currentLine = currentLine.replace("}.", "");
			String[] ar = currentLine.split(",");
			customers.add(new Customer(ar[0], Integer.parseInt(ar[1])));
		}
		
		for(String line : banksLines) {
			String currentLine = line.replace("{", "");
			currentLine = currentLine.replace("}.", "");
			String[] ar = currentLine.split(",");
			banks.add(new Bank(ar[0], Integer.parseInt(ar[1])));
		}

		int numOfCustomers = customers.size();
		int numOfBanks = banks.size();
		
		System.out.println("** Customers and loan objectives **");
		for(Customer c : customers) {
			System.out.println(c);
			
			//set all banks by default in each customer list
			c.setBankList(banks);
		}

		System.out.println("** Banks and financial resources **");
		for(Bank b : banks) {
			System.out.println(b);
		}
		
		
		//deal with thread and contact bank
		Random random = new Random();
		
		//reference : https://stackoverflow.com/questions/20389890/generating-a-random-number-between-1-and-10-java
		do {
			controller = checkController();
			int amountToRequest = random.nextInt(50) + 1;
			Bank randomBank = banks.get(random.nextInt(numOfBanks));
			int sleepTime = random.nextInt(91)+10;
			
			//wait for the sleepTime
			//choose random bank and request with random amount
			
			
			
		}while(controller);
		
		
	}

	private static boolean checkController() {
		for(Customer c : customers) {
			if(c.balance != 0) {
				return false;
			}
		}
		
		for(Bank b : banks) {
			if(b.availableBalance != 0) {
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

class Customer {
	String name;
	int balance;
	ArrayList<Bank> banksList;
	public Customer(String name, int balance) {
		this.name = name;
		this.balance = balance;
		
	}

	@Override
	public String toString() {
		return name+": "+balance;
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
	
	public boolean withDraw(Customer c,int amount) {
		if((availableBalance - amount) >= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name+": "+availableBalance;
	}
}






//Reference : https://www.geeksforgeeks.org/method-block-synchronization-java/