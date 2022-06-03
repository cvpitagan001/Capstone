import java.time.LocalDate;

public class Policy extends Helper {
    CustomerAccount account = new CustomerAccount("accountNo", "firstName", "lastName", "address");
    PAS main = new PAS();
    private String policyNo;
    private LocalDate effectiveDate, expirationDate;
    
    public void load() {
       clrscr();
       System.out.println("Get a policy qoute and buy the policy\n");
       account.checkAccountIfExist();
       if(account.isAccountExist() == false) {
           printError("Account number doesn't exist");
           main.backToMenu();
       } else {
            int generateNum = rand.nextInt(999999);
            this.policyNo = String.format("%06d", generateNum);
            System.out.print("Enter effective date (YYYY-MM-DD): ");
            String inputDate = get.nextLine();
            this.effectiveDate = LocalDate.parse(inputDate);
            this.expirationDate = this.effectiveDate.plusMonths(6);

            store();
       }
    }

    public void store() {
        try {
            connect();
            String query = "INSERT INTO policy (policy_no, customer_acc_no, effective_date, expiry_date) VALUES('"+ this.policyNo +"', '"+ account.getAccountNo() +"', '"+ this.effectiveDate +"', '"+ this.expirationDate +"')";
            prep = conn.prepareStatement(query);
            prep.execute();
        } catch(Exception e) {
            printError("Policy create failed");
            main.backToMenu();
        }
    }


    
}
