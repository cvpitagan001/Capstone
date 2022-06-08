import java.time.LocalDate;
import java.time.Period;

public class PolicyHolder extends Helper {
    PAS main = new PAS();
    Policy policy = new Policy("policyNo", "effectiveDate", "expirationDate", "status");
    private String firstName, lastName, driversLicenseNum, type, uuid;
    private LocalDate dateOfBirth, driversLicenseIssued;

    //user input for policy holder details
    public void load(String policyNo) {
        try {
            System.out.println("[Policy Holder Details]");
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

            store(policyNo);
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    //store policy holder details on database
    public void store(String policyNo) {
        try {
            connect();
            String query = "INSERT INTO policy_holder (uuid, customer_acc_no, type, first_name, last_name, date_of_birth, dv_license_num, dv_license_issued) " +
            "VALUES('"+ uuid +"', '"+ account.getAccountNo() +"','"+ this.type +"', '"+ this.firstName +"', '"+ this.lastName +"', '"+ this.dateOfBirth +"', '"+ this.driversLicenseNum +"', '"+ this.driversLicenseIssued +"')";
            prep = conn.prepareStatement(query);
            prep.execute();
            policy.update("policy_holder_uuid", uuid, policyNo);
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    //get self uuid
    public String getOwnUUID() {
        return uuid;
    }

    //get num of years since driver license was first issued 
    public int getDlx() {
        LocalDate today = LocalDate.now();
        return Period.between(driversLicenseIssued, today).getYears();
    }
    
}
