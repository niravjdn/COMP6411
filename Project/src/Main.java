import java.io.*;
import java.util.ArrayList;


public class Main {
	public static void main(String[] args) throws IOException {
		ArrayList<String> customerLines = readAndReturnLines("customers.txt");
		ArrayList<String> banksLines = readAndReturnLines("banks.txt");
		System.out.println(customerLines);
		System.out.println(banksLines);
		ArrayList<Customer> customers = new ArrayList<Customer>();
		ArrayList<Bank> banks = new ArrayList<Bank>();
		
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
		
		System.out.println(customers);
		System.out.println(banks);
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
	
	public Customer(String name, int balance) {
		this.name = name;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", balance=" + balance + "]";
	}
}

class Bank {
	String name;
	int availableBalance;
	
	public Bank(String name, int balance) {
		this.name = name;
		this.availableBalance = balance;
	}
	
	@Override
	public String toString() {
		return "Bank [name=" + name + ", availableBalance=" + availableBalance + "]";
	}
}