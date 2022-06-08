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

    //DB connection
    public void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone","root", "root");
            createStmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //store data on database
    public void storeOnDB(String table, String fields, String values, String msg) {
        try {
            connect();
            String query = "INSERT INTO "+ table +" ("+ fields +") VALUES("+ values +")";
            prep = conn.prepareStatement(query);
            prep.execute();
            if(msg.length() > 0) printSuccess(msg);
        } catch(Exception e) {
            printError(e.toString());
        }
    }

    //update data on database
    public void update(String table, String fields, String value, String baseField, String baseNum) {
        try {
            connect();
            String query = "UPDATE "+ table +" SET "+ fields +" = '"+ value +"' WHERE "+ baseField +"='"+ baseNum +"'";
            prep = conn.prepareStatement(query);
            prep.execute();
        } catch(Exception e) {
            printError(e.toString());
        }
    }

    //delete data on database
    public void deleteFromDb(String table, String field, String value) {
        try {
            connect();
            String query = "DELETE FROM "+ table +" WHERE "+ field +"='"+ value +"'";
            prep = conn.prepareStatement(query);
            prep.executeUpdate();
        } catch(Exception e) {
            printError(e.toString());
        }
    }

    //clear screen
    public void clrscr() {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

    // print success
    public void printSuccess(String msg) {
        System.out.println("\n[Success: " + msg + "]\n");
    }

    //return success
    public String returnSuccess(String msg) {
        return "\n[Success: " + msg + "]\n";
    }    

    //print error
    public void printError(String msg) {
        System.out.println("\n[Error: " + msg + "]\n");
    }

    //return error
    public String returnError(String msg) {
        return "\n[Error: " + msg + "]\n";
    }

    //get account details
    public void getAccountDetails(String accNo, String fname, String lName, String address) {
        account = new CustomerAccount(accNo, fname, lName, address);
    }

    //getUUID
    public String getUUID() {
        String getUuid = UUID.randomUUID().toString();
        getUuid = getUuid.replaceAll("-", "");
        return getUuid;
    }

    //get policy details
    public void getPolicyDetails(String policyNo, String effectiveDate, String expiryDate, String status, double cost) {
        policy = new Policy(policyNo, effectiveDate, expiryDate, status, cost);
    }

    //get claim details
    public void getClaimDetails(String claimNo, String dateOfAccident, String addressAccidentHappened, String descriptionOfAccident, String descriptionOfVehicleDamage, double estimatedCostOfRepair) {
        claim = new Claim(claimNo, dateOfAccident, addressAccidentHappened, descriptionOfAccident, descriptionOfVehicleDamage, estimatedCostOfRepair);
    }

    //print note
    public void printNote(String msg) {
        System.out.println("Note: " + msg);
    }
}
