public class CustomerAccount extends Helper {
    PAS main = new PAS();
    private boolean isAccountExist = true;
    private String accountNo, firstName, lastName, address;

    public CustomerAccount(String accountNo, String firstName, String lastName, String address) {
        this.accountNo = accountNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    //user input for customer account
    public void load() {
        clrscr();
        try {
            System.out.println("[Creation of Account]\n");
            int generateNum = rand.nextInt(9999);
            this.accountNo = String.format("%04d", generateNum);
            System.out.print("Enter first name: ");
            this.firstName = get.nextLine();
            System.out.print("Enter last name: ");
            this.lastName = get.nextLine();
            System.out.print("Enter address: ");
            this.address = get.nextLine();
            System.out.println("Account no: " + accountNo);

            checkAccountViaName();
            if(isAccountExist == false) store();
            else {
                tryAgain("Account is already exist", "load");
            }
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    //check account if exist through firstname and lastname
    private void checkAccountViaName() {
        try {
            connect();
            String query = "SELECT * FROM customer_acc WHERE first_name = '"+ firstName +"' AND last_name = '"+ lastName +"'  ";
            result = createStmt.executeQuery(query);

            if(!result.next()) this.isAccountExist = false;
            else {
                isAccountExist = true;
                getAccountDetails(result.getString("account_no"), result.getString("first_name"), result.getString("last_name"), result.getString("address"));
            }
        } catch(Exception e) {
            printError(e.toString());
            get.nextLine();
            main.backToMenu();
        }
    }

    //store customer account details on DB
    public void store() {
        String fields = "account_no, first_name, last_name, address";
        String values = "'"+this.accountNo+"', '"+this.firstName+"', '"+this.lastName+"', '"+this.address+"'";
        String msg = "Customer account has been created";
        storeOnDB("customer_acc", fields, values, msg);
        main.backToMenu();
    }

    //check account if exist using account number
    public void checkAccountIfExist() {
        try {
            connect();
            System.out.print("Enter account number: ");
            String accNo = get.nextLine();  

            String query = "SELECT * FROM customer_acc WHERE account_no = '"+ accNo +"' ";
            result = createStmt.executeQuery(query);

            if(!result.next()) this.isAccountExist = false;
            else {
                isAccountExist = true;
                getAccountDetails(result.getString("account_no"), result.getString("first_name"), result.getString("last_name"), result.getString("address"));
            }
        } catch(Exception e) {
            printError(e.toString());
            get.nextLine();
            main.backToMenu();
        }
    }

    //search and display account details
    public void display() {
        clrscr();
        try {
            System.out.println("[Search Account]");
            System.out.print("Enter first name: ");
            this.firstName = get.next().trim();
            System.out.print("Enter last name: ");
            this.lastName = get.next().trim();
            checkAccountViaName();
            if(isAccountExist == false) {
                tryAgain("Account doesn't exist", "display");
            } else {
                System.out.println("\n[Account Details]");
                System.out.format("%-15s %-15s %-15s %-15s%n", "AccountNo", "Firstname", "Lastname", "Address");
                System.out.format("%-15s %-15s %-15s %-15s%n", account.getAccountNo(), account.getFirstName(), account.getLastName(), account.getAddress());
                main.backToMenu();
            }
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    public void tryAgain(String msg, String purpose) {
        System.out.print("\nError: " + msg + "\nTry Again? [1]Yes [2]No: ");
        int opt = get.nextInt();
        get.nextLine();

        if(opt == 1 && purpose.equals("load")) load();
        if(opt == 1 && purpose.equals("display")) display();
        else main.backToMenu();
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

    public String getAddress() {
        return address;
    }

    public boolean isAccountExist() {
        return isAccountExist;
    }

}
