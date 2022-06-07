import java.time.LocalDate;

public class Policy extends Helper {
    PAS main = new PAS();
    private String policyNo;
    private LocalDate effectiveDate, expirationDate;

    public void load() {
        clrscr();
       try {
            System.out.println("--Get a policy qoute and buy the policy--");
            account.checkAccountIfExist();
            if(account.isAccountExist() == false) {
                tryAgain("Account doesn't exist");
            } else {
                int generateNum = rand.nextInt(999999);
                policyNo = String.format("%06d", generateNum);
                System.out.print("Enter effective date (YYYY-MM-DD): ");
                String inputDate = get.nextLine();
                this.effectiveDate = LocalDate.parse(inputDate);
                this.expirationDate = this.effectiveDate.plusMonths(6);
                System.out.println("Expiration date: " + this.expirationDate);

                store();
            }
       } catch(Exception e) {
            tryAgain(e.toString());
       }
    }

    public void store() {
        try {
            connect();
            String query = "INSERT INTO policy (policy_no, customer_acc_no, effective_date, expiry_date)" +  
            "VALUES('"+ policyNo +"', '"+ account.getAccountNo() +"', '"+ this.effectiveDate +"', '"+ this.expirationDate +"')";
            prep = conn.prepareStatement(query);
            prep.execute();
        } catch(Exception e) {
            printError("Policy create failed");
            main.backToMenu();
        }
    }

    public void update(String field, String value, String policyNo) {
        try {
            connect();
            String query = "UPDATE policy SET "+ field +" = '"+ value +"' WHERE policy_no='"+ policyNo +"'";
            prep = conn.prepareStatement(query);
            prep.execute();
        } catch(Exception e) {
            e.printStackTrace();
            printError("Policy update failed");
            main.backToMenu();
        }
    }

    public void cancelPolicy() {
        try {
            clrscr();
            System.out.println("\n--Cancel Policy--");
            System.out.print("Enter policy no.: ");
            String policyNum = get.next().trim();
            if(!checkIfPolicyExist(policyNum)) {
                printError("Policy doesn't exist");
                main.backToMenu();
            } else {
                System.out.print("Enter new expiry date: ");
                String newExpiryDate = get.next().trim();
                update("expiry_date", newExpiryDate, policyNum);
                printSuccess("Policy expiry date has been updated");

                main.backToMenu();
            }

            
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    public boolean checkIfPolicyExist(String policyNo) {
        try {
            connect();
            String query = "SELECT * FROM policy WHERE policy_no='"+ policyNo +"'";
            result = createStmt.executeQuery(query);
            if(!result.next()) return false;
            return true;
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }

        return false;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void tryAgain(String msg) {
        System.out.print("\nError: " + msg + "\nTry Again? [1]Yes [2]No: ");
        int opt = get.nextInt();
        get.nextLine();

        if(opt == 1) load();
        else main.backToMenu();
    }
    
}
