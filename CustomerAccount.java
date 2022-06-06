public class CustomerAccount extends Helper {
    PAS main = new PAS();
    Policy policy = new Policy();
    private boolean isAccountExist = true;
    private String accountNo, firstName, lastName, address;

    public CustomerAccount(String accountNo, String firstName, String lastName, String address) {
        this.accountNo = accountNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public void load() {
        clrscr();
        try {
            System.out.println("Creation of Account\n");
            int generateNum = rand.nextInt(9999);
            this.accountNo = String.format("%04d", generateNum);
            System.out.print("Enter first name: ");
            this.firstName = get.nextLine();
            System.out.print("Enter last name: ");
            this.lastName = get.nextLine();
            System.out.print("Enter address: ");
            this.address = get.nextLine();

            store();
        } catch(Exception e) {
            printError("Something went wrong");
            main.backToMenu();
        }
    }

    public void store() {
        try {
            connect();
            String query = "INSERT INTO customer_acc (account_no, first_name, last_name, address) VALUES('"+this.accountNo+"', '"+this.firstName+"', '"+this.lastName+"', '"+this.address+"')";
            prep = conn.prepareStatement(query);
            prep.execute();
            printSuccess("Customer account has been created");
            main.backToMenu();
        } catch(Exception e) {
            printError("Customer create failed");
            main.backToMenu();
        }
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isAccountExist() {
        return isAccountExist;
    }

    public void checkAccountIfExist() {
        try {
            connect();
            System.out.print("Enter account number: ");
            String accNo = get.nextLine();  

            String query = "SELECT * FROM customer_acc WHERE account_no = '"+ accNo +"' ";
            result = createStmt.executeQuery(query);

            if(!result.next()) this.isAccountExist = false;
            else {
                getAccountDetails(result.getString("account_no"), result.getString("first_name"), result.getString("last_name"), result.getString("address"));
            }
        } catch(Exception e) {
            printError(e.toString());
            get.nextLine();
            main.backToMenu();
        }
    }

}
