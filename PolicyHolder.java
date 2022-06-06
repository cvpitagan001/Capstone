import java.time.LocalDate;
import java.time.Period;

public class PolicyHolder extends Helper {
    PAS main = new PAS();
    Policy policy = new Policy();
    private String firstName, lastName, driversLicenseNum, type, uuid;
    private LocalDate dateOfBirth, driversLicenseIssued;

    public void load() {
        try {
            System.out.println("--Policy Holder Details--");
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
            uuid = getUUID(); 

            store();
        } catch(Exception e) {
            printError("Something went wrong");
            main.backToMenu();
        }
    }

    public void store() {
        try {
            connect();
            System.out.println(uuid);
            String query = "INSERT INTO policy_holder (uuid, customer_acc_no, type, first_name, last_name, date_of_birth, dv_license_num, dv_license_issued) " +
            "VALUES('"+ uuid +"', '"+ account.getAccountNo() +"','"+ this.type +"', '"+ this.firstName +"', '"+ this.lastName +"', '"+ this.dateOfBirth +"', '"+ this.driversLicenseNum +"', '"+ this.driversLicenseIssued +"')";
            prep = conn.prepareStatement(query);
            prep.execute();
            policy.update(uuid);
        } catch(Exception e) {
            e.printStackTrace();
            printError("Policy holder creation failed");
            main.backToMenu();
        }
    }

    public String getOwnUUID() {
        return uuid;
    }

    public int getDlx() {
        LocalDate today = LocalDate.now();
        return Period.between(driversLicenseIssued, today).getYears();
    }
    

}
