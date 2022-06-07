import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.sql.*;
import java.time.LocalDate;

public class Helper {
    Scanner get = new Scanner(System.in);
    Random rand = new Random();
    Connection conn;
    PreparedStatement prep;
    Statement createStmt;
    ResultSet result;
    LocalDate date;
    static CustomerAccount account;
    static Policy policy;
    static Claim claim;
    PolicyHolder policyHolder;
    Vehicle vehicle;
    
    public void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone","root", "root");
            createStmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clrscr() {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

    public void printSuccess(String msg) {
        System.out.println("\nSuccess: " + msg);
    }

    public String returnSuccess(String msg) {
        return "\nSuccess: " + msg;
    }    

    public void printError(String msg) {
        System.out.println("\nError: " + msg);
    }

    public String returnError(String msg) {
        return "\nError: " + msg;
    }

    public void getAccountDetails(String accNo, String fname, String lName, String address) {
        account = new CustomerAccount(accNo, fname, lName, address);
    }

    public String getUUID() {
        String getUuid = UUID.randomUUID().toString();
        getUuid = getUuid.replaceAll("-", "");
        return getUuid;
    }

    public void getPolicyDetails(String policyNo, String effectiveDate, String expiryDate, String status) {
        policy = new Policy(policyNo, effectiveDate, expiryDate, status);
    }

    public void getClaimDetails(String claimNo, String dateOfAccident, String addressAccidentHappened, String descriptionOfAccident, String descriptionOfVehicleDamage, double estimatedCostOfRepair) {
        claim = new Claim(claimNo, dateOfAccident, addressAccidentHappened, descriptionOfAccident, descriptionOfVehicleDamage, estimatedCostOfRepair);
    }
}
