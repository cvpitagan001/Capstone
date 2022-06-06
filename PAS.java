public class PAS extends Helper {

    public static void main(String[] args) {
        PAS main = new PAS();
        main.mainMenu();
    }

    public void mainMenu() {
        account = new CustomerAccount("accountNo", "firstName", "lastName", "address");
        policy = new Policy();
        policyHolder = new PolicyHolder();
        vehicle = new Vehicle();
        try {
            System.out.println("The Automobile Insurance Policy and Claims Administration System (PAS) Specification\n");   
            System.out.println("[1] Create new customer account");
            System.out.println("[2] Get a policy qoute and buy the policy");
            System.out.println("[3] Cancel a specific policy");
            System.out.println("[4] Create new customer account");
            System.out.println("[5] Search for a Customer account ");
            System.out.println("[6] Search for and display a specific policy");
            System.out.println("[7] Search for and display a specific claim");
            System.out.println("[8] Exit the PAS System");
            System.out.print("Option: ");
            int opt = get.nextInt();
            get.nextLine();

            switch(opt) {
                case 1: 
                    account.load();
                    break;
                case 2:
                    policy.load();
                    policyHolder.load();
                    vehicle.load(policyHolder.getDlx());
                    break;
                case 8: 
                    System.exit(0);
                default: 
                    clrscr();
                    printError("Invalid input");
                    backToMenu();
            }
        } catch(Exception e) {
            printError(e.toString());
            get.nextLine();
            backToMenu();
        }
    }

    public void backToMenu() {
        try {
            System.out.print("\nGo back to menu? Press [1]YES [2]NO: ");
            int opt = get.nextInt();
            get.nextLine();

            if(opt == 1) {
                clrscr(); 
                mainMenu();
            }
            else if(opt == 2) System.exit(0);
            else {
                printError("Invalid input");
                backToMenu();
            }
        } catch(Exception e) {
            printError(e.toString());
            get.nextLine();
            backToMenu();
        }
    }

}