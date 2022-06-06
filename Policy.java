import java.time.LocalDate;

public class Policy extends Helper {
    PAS main = new PAS();
    static String policyNo;
    private LocalDate effectiveDate, expirationDate;
    
    public void load() {
        clrscr();
       try {
            System.out.println("--Get a policy qoute and buy the policy--");
            account.checkAccountIfExist();
            if(account.isAccountExist() == false) {
                printError("Account number doesn't exist");
                main.backToMenu();
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
            printError("Something went wrong");
            main.backToMenu();
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

    public void update(String policyHolderUuid) {
        try {
            connect();
            String query = "UPDATE policy SET policy_holder_uuid = '"+ policyHolderUuid +"' WHERE policy_no='"+ policyNo +"'";
            prep = conn.prepareStatement(query);
            prep.execute();
        } catch(Exception e) {
            e.printStackTrace();
            printError("Policy update failed");
            main.backToMenu();
        }
    }

    public String getPolicyNo() {
        return policyNo;
    }
    
}
