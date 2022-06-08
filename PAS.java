public class PAS extends Helper {

    public static void main(String[] args) {
        PAS main = new PAS();
        main.mainMenu();
    }

    // Menu
    public void mainMenu() {
        account = new CustomerAccount("", "", "", "");
        policy = new Policy("", "", "", "", 0);
        policyHolder = new PolicyHolder();
        vehicle = new Vehicle("", "", "", "", "", 0, 0.0, 0.0, "");
        claim = new Claim("", "", "", "", "", 0.0);
        try {
            System.out.println("[The Automobile Insurance Policy and Claims Administration System (PAS) Specification]\n");   
            System.out.println("[1] Create new customer account");
            System.out.println("[2] Get a policy qoute and buy the policy");
            System.out.println("[3] Cancel a specific policy");
            System.out.println("[4] File an accident claim against a policy. All claims must be maintained by system and should be searchable.");
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
                    policyHolder.load(policy.getPolicyNo());
                    vehicle.load(policyHolder.getDlx(), account.getAccountNo(), policyHolder.getOwnUUID(), policy.getPolicyNo());
                    break;
                case 3:
                    policy.cancelPolicy();
                    break;
                case 4:
                    claim.load();
                    break;
                case 5:
                    account.display();
                    break;
                case 6:
                    policy.display();
                    break;
                case 7:
                    claim.display();
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

    //Back to menu
    public void backToMenu() {
        try {
            System.out.print("Go back to menu? Press [1]YES [2]NO: ");
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