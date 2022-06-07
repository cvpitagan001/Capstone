import java.time.LocalDate;

public class Policy extends Helper {
    PAS main = new PAS();
    private String policyNo, effectiveDate, expirationDate, status;
    private boolean isPolicyExist = true;

    public Policy(String policyNo, String effectiveDate, String expirationDate, String status) {
        this.policyNo = policyNo;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.status = status;
    }

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
                this.effectiveDate = get.next().trim();
                LocalDate effectDate = LocalDate.parse(this.effectiveDate);
                LocalDate expireDate = effectDate.plusMonths(6);
                System.out.println("Expiration date: " + expireDate);
                System.out.println("Policy No: " + policyNo);
                printNote("Always keep your policy number");
                store(effectDate, expireDate);
            }
       } catch(Exception e) {
            tryAgain(e.toString());
       }
    }

    public void store(LocalDate effDate, LocalDate expireDate) {
        try {
            connect();
            String query = "INSERT INTO policy (policy_no, customer_acc_no, effective_date, expiry_date)" +  
            "VALUES('"+ policyNo +"', '"+ account.getAccountNo() +"', '"+ effDate +"', '"+ expireDate +"')";
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
            selectPolicy(policyNum);
            if(isPolicyExist == false) {
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

    public void selectPolicy(String policyNo) {
        try {
            connect();
            String query = "SELECT * FROM policy WHERE policy_no='"+ policyNo +"'";
            result = createStmt.executeQuery(query);
            if(!result.next()) isPolicyExist = false;
            else {
                getPolicyDetails(result.getString("policy_no"),
                                result.getString("effective_date"),
                                result.getString("expiry_date"),
                                result.getString("status")
                                );
            }
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    public void display() {
        clrscr();
        try {
            System.out.print("Enter policy no: ");
            String policyNum = get.next().trim();
            selectPolicy(policyNum);
            if(isPolicyExist == false) {
                printError("Policy doesn't exist");
                main.backToMenu();
            } else {
                System.out.println("\n[Policy Details]");
                System.out.format("%-15s %-15s %-15s %-15s%n", "PolicyNo", "EffectiveDate", "ExpiryDate", "Status");
                System.out.format("%-15s %-15s %-15s %-15s%n", policy.getPolicyNo(), policy.getEffectiveDate(), policy.getExpirationDate(), policy.getStatus());
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

    public void tryAgain(String msg) {
        System.out.print("\nError: " + msg + "\nTry Again? [1]Yes [2]No: ");
        int opt = get.nextInt();
        get.nextLine();

        if(opt == 1) load();
        else main.backToMenu();
    }
    
}
