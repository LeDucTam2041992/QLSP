package controller;

import model.Customer;
import model.Smartphone;
import model.Specifications;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Controller {
    private File file;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    final String SPEC_FILE = "Specifications.dat";
    final String CUSTOM_FILE = "Customer.dat";

    public void openFileToWrite(String fileName) {
        try {
            file = new File(fileName);
            if (!file.exists()) file.createNewFile();
            fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFileAfterWrite(String fileName) {
        try {
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFileToRead(String fileName) {
        try {
            file = new File(fileName);
            if (!file.exists()) file.createNewFile();
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFileAfterRead(String fileName) {
        try {
            bufferedReader.close();
            fileReader.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) {
        File file = new File(fileName);
        if(file.exists()) file.delete();
    }

    public void writeCustomerToFile(String fileName, Customer customer) {
        openFileToWrite(fileName);
        printWriter.println(customer.getName() + "|" + customer.getAddress() + "|" + customer.getPhoneNumber()
                + "|" + customer.getTime() + "|" + customer.getProductCode());
        closeFileAfterWrite(fileName);
    }

    public void writeSmartPhoneToFile(String fileName, Smartphone smartphone) {
        openFileToWrite(fileName);
        printWriter.println(smartphone.getProductCode() + '|' + smartphone.getName() + '|' + smartphone.getDesignBy()
                + '|' + smartphone.getPrice() + '|' + smartphone.getColor() + '|' + smartphone.getQuantity());
        closeFileAfterWrite(fileName);
    }

    public void writeSmartPhoneToFile(String fileName, LinkedList<Smartphone> smartphones) {
        openFileToWrite(fileName);
        for (Smartphone smartphone:smartphones) {
            printWriter.println(smartphone.getProductCode() + '|' + smartphone.getName() + '|' + smartphone.getDesignBy()
                    + '|' + smartphone.getPrice() + '|' + smartphone.getColor() + '|' + smartphone.getQuantity());
        }
        closeFileAfterWrite(fileName);
    }

    public void writeSpecificationsToFile(String fileName, Specifications specifications) {
        openFileToWrite(fileName);
        printWriter.println(specifications.getName() + "|" + specifications.getScreen() + "|" + specifications.getRam() + "|" + specifications.getChip()
                + "|" + specifications.getOperaSystem());
        closeFileAfterWrite(fileName);
    }

    public ArrayList<Specifications> readSpecificationsFromFile(String fileName) throws IOException {
        String data;
        ArrayList<Specifications> specs = new ArrayList<>();
        openFileToRead(fileName);
        while ((data = bufferedReader.readLine()) != null) {
            String[] datas = data.split("\\|");
            Specifications specifications = new Specifications(datas[0], datas[1], datas[2], datas[3], datas[4]);
            specs.add(specifications);
        }
        closeFileAfterRead(fileName);
        return specs;
    }

    public LinkedList<Smartphone> readSmartPhoneFromFile(String fileName) throws IOException {
        LinkedList<Smartphone> smartphones = new LinkedList<>();
        String data;
        openFileToRead(fileName);
        while ((data = bufferedReader.readLine()) != null) {
            Smartphone smartphone = createSmartPhoneFromData(data);
            smartphones.add(smartphone);
        }
        closeFileAfterRead(fileName);
        return smartphones;
    }

    private Smartphone createSmartPhoneFromData(String data) {
        String[] datas = data.split("\\|");
        Smartphone smartphone = new Smartphone(datas[0],datas[1],datas[2],Integer.parseInt(datas[3]),
                datas[4],Integer.parseInt(datas[5]));
        return smartphone;
    }

    public LinkedList<Smartphone> findProduct(String nameFind, LinkedList<Smartphone> smartphones) {
        LinkedList<Smartphone> smFind = new LinkedList<>();
        while (!smartphones.isEmpty()){
            Smartphone sm = smartphones.pop();
            if (nameFind.equalsIgnoreCase(sm.getName())) smFind.add(sm);
        }
        return smFind;
    }

    public LinkedList<Smartphone> findProduct(int priceFind, LinkedList<Smartphone> smartphones) {
        LinkedList<Smartphone> smFind = new LinkedList<>();
        while (!smartphones.isEmpty()){
            Smartphone sm = smartphones.pop();
            if (Math.abs(priceFind - sm.getPrice()) <= 1500000) smFind.add(sm);
        }
        Collections.sort(smFind);
        return smFind;
    }

    public LinkedList<Customer> readCustomerFromFile(String fileName) throws IOException {
        LinkedList<Customer> customers = new LinkedList<>();
        openFileToRead(fileName);
        String data;
        while ((data = bufferedReader.readLine()) != null) {
            String[] datas = data.split("\\|");
            Customer customer = new Customer(datas[0],datas[1],datas[2],datas[3],datas[4]);
            customers.add(customer);
        }
        closeFileAfterRead(fileName);
        return customers;
    }

    public void buySmartPhone(String codeProduct, String fileName) throws IOException {
        LinkedList<Smartphone> smartphones = readSmartPhoneFromFile(fileName);
        while (!smartphones.isEmpty()){
            Smartphone sm = smartphones.pop();
            if(codeProduct.equalsIgnoreCase(sm.getProductCode())) {
                sm.showInfo();
                ArrayList<Specifications> specs = readSpecificationsFromFile(SPEC_FILE);
                for (Specifications s:specs) {
                    if(s.getName().equalsIgnoreCase(sm.getName())) {
                        s.showInfo();
                        break;
                    }
                }
                if (sm.getQuantity() < 0 ) System.out.println("Sorry but the smartphone is out of stock! " +
                        "Please come back later!");
                else {
                    System.out.println("Choice Y to buy or any key to back!");
                    Scanner scanner = new Scanner(System.in);
                    char choice = scanner.nextLine().charAt(0);
                    if(choice == 'Y') {
                        System.out.println("Please enter your name : ");
                        String name = scanner.nextLine();
                        System.out.println("Please enter your phone number : ");
                        String phoneNumber = scanner.nextLine();
                        System.out.println("Please enter the address you want ship to : ");
                        String address = scanner.nextLine();
                        String time = java.util.Calendar.getInstance().getTime().toString();
                        Customer customer = new Customer(name,address,phoneNumber,time,sm.getProductCode());
                        writeCustomerToFile(CUSTOM_FILE,customer);
                    }
                }
                break;
            }
        }
    }
}
