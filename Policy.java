import java.time.LocalDate;
import java.time.Period;

public class Policy extends Helper {
    PAS main = new PAS();
    private String policyNo, effectiveDate, expirationDate, status;
    private boolean isPolicyExist = true;
    private double cost;

    public Policy(String policyNo, String effectiveDate, String expirationDate, String status, double cost) {
        this.policyNo = policyNo;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.status = status;
        this.cost = cost;
    }

    //user input for policy details
    public void load() {
        clrscr();
       try {
            System.out.println("[Get a policy qoute and buy the policy]");
            account.checkAccountIfExist();
            if(account.isAccountExist() == false) {
                tryAgain("Account doesn't exist", "load");
            } else {
                int generateNum = rand.nextInt(999999);
                policyNo = String.format("%06d", generateNum);
                System.out.print("Enter effective date (YYYY-MM-DD): ");
                this.effectiveDate = get.next().trim();
                LocalDate effectDate = LocalDate.parse(this.effectiveDate);
                LocalDate expireDate = effectDate.plusMonths(6);
                System.out.println("Expiration date: " + expireDate);
                System.out.println("Policy no: " + policyNo);

                store(effectDate, expireDate);
            }
       } catch(Exception e) {
            tryAgain(e.toString(), "load");
       }
    }

    //store policy details on db
    public void store(LocalDate effDate, LocalDate expireDate) {
        String fields = "policy_no, customer_acc_no, effective_date, expiry_date";
        String values = "'"+ policyNo +"', '"+ account.getAccountNo() +"', '"+ effDate +"', '"+ expireDate +"'";
        String msg = "Policy has been created";
        storeOnDB("policy", fields, values, msg);
    }

    //cancel policy
    public void cancelPolicy() {
        try {
            clrscr();
            System.out.println("\n[Cancel Policy]");
            System.out.print("Enter policy no.: ");
            String policyNum = get.next().trim();
            selectPolicy(policyNum);
            if(isPolicyExist == false) {
                tryAgain("Policy doesn't exist", "cancel");
            } else {
                if(policy.status.equals("Cancelled") || policy.equals("Expired")) tryAgain("Policy has been cancelled or expired", "cancel");

                System.out.print("Enter new expiry date: ");
                String newExpiryDate = get.next().trim();
                LocalDate parseNewExpDate = LocalDate.parse(newExpiryDate);
                LocalDate parseEffDate = LocalDate.parse(policy.effectiveDate);
                
                int diffInMonths = Period.between(parseEffDate, parseNewExpDate).getMonths();
                if(diffInMonths < 0 || diffInMonths > 6) tryAgain("The new expiry date should be on or after 6 months of the effective date.", "cancel");
                if(diffInMonths == 0) update("policy", "status", "Cancelled", "policy_no", policyNum);
               
                update("policy", "expiry_date", newExpiryDate, "policy_no", policyNum);
                printSuccess("Policy expiry_date||status has been updated");

                main.backToMenu();
            }

            
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    //get policy base on policy number
    public void selectPolicy(String policyNo) {
        try {
            connect();
            String query = "SELECT * FROM policy WHERE policy_no='"+ policyNo +"'";
            result = createStmt.executeQuery(query);
            if(!result.next()) isPolicyExist = false;
            else {
                isPolicyExist = true;
                getPolicyDetails(result.getString("policy_no"),
                                result.getString("effective_date"),
                                result.getString("expiry_date"),
                                result.getString("status"),
                                result.getDouble("cost")
                                );
            }
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    //search and display policy details using policy number
    public void display() {
        clrscr();
        try {
            System.out.println("[Search Policy]");
            System.out.print("Enter policy no: ");
            String policyNum = get.next().trim();
            selectPolicy(policyNum);
            if(isPolicyExist == false) {
                printError("Policy doesn't exist");
                main.backToMenu();
            } else {
                System.out.println("\n[Policy Details]");
                System.out.format("%-15s %-15s %-15s %-15s %-15s%n", "PolicyNo", "EffectiveDate", "ExpiryDate", "Status", "Cost");
                System.out.format("%-15s %-15s %-15s %-15s %-15s%n%n", policy.getPolicyNo(), policy.getEffectiveDate(), policy.getExpirationDate(), policy.getStatus(), policy.getCost());
                main.backToMenu();
            }
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getStatus() {
        return status;
    }

    public boolean getIsPolicyExist() {
        return isPolicyExist;
    }

    public double getCost() {
        return cost;
    }

    public void tryAgain(String msg, String purpose) {
        System.out.print("\nError: " + msg + "\n\nTry Again? [1]Yes [2]No: ");
        int opt = get.nextInt();
        get.nextLine();
        
        if(opt == 1 && purpose.equals("load")) load();
        if(opt == 1 && purpose.equals("cancel")) cancelPolicy();
        else main.backToMenu();
    }
    
}
