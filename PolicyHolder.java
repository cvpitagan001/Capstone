import java.time.LocalDate;
import java.sql.*;

public class PolicyHolder extends Helper {
    PAS main = new PAS();
    Policy policy = new Policy();
    private String firstName, lastName, driversLicenseNum, type;
    private LocalDate dateOfBirth, driversLicenseIssued;

    public void load() {
        try {
            System.out.println("\nPolicy Holder Details");
            System.out.print("Enter first name: ");
            this.firstName = get.nextLine();
            System.out.print("Enter last name: ");
            this.lastName = get.nextLine();
            System.out.print("Enter date of birth(YYYY-MM-DD): ");
            String dob = get.nextLine();
            this.dateOfBirth = LocalDate.parse(dob);
            System.out.print("Enter driver's license number: ");
            this.driversLicenseNum = get.nextLine();
            System.out.print("Enter driver's license date issued(YYYY-MM-DD): ");
            String dateIssued = get.nextLine();
            this.driversLicenseIssued = LocalDate.parse(dateIssued);
            this.type = account.getFirstName().equals(firstName) && account.getLastName().equals(lastName) ? "account_owner" : "dependent";

            store();
        } catch(Exception e) {
            printError("Something went wrong");
            main.backToMenu();
        }
    }

    public void store() {
        try {
            connect();
            String query = "INSERT INTO policy_holder (customer_acc_no, type, first_name, last_name, date_of_birth, dv_license_num, dv_license_issued) " +
            "VALUES('"+ account.getAccountNo() +"','"+ this.type +"', '"+ this.firstName +"', '"+ this.lastName +"', '"+ this.dateOfBirth +"', '"+ this.driversLicenseNum +"', '"+ this.driversLicenseIssued +"')";
            createStmt = conn.createStatement();
            createStmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            result = createStmt.getGeneratedKeys();
            if(result.next()) policy.update(result.getInt(1));
        } catch(Exception e) {
            e.printStackTrace();
            printError("Policy holder create failed");
            main.backToMenu();
        }
    }

    public LocalDate getDriversLicenseIssued() {
        return driversLicenseIssued;
    }
}
