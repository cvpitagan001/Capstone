public class Claim extends Helper {
    PAS main = new PAS();
    private String claimNo, dateOfAccident, addressAccidentHappened, 
    descriptionOfAccident, descriptionOfVehicleDamage;
    private double estimatedCostOfRepair;
    private boolean isClaimExist = true;

    public Claim(String claimNo, String dateOfAccident, String addressAccidentHappened, String descriptionOfAccident,
            String descriptionOfVehicleDamage, double estimatedCostOfRepair) {
        this.claimNo = claimNo;
        this.dateOfAccident = dateOfAccident;
        this.addressAccidentHappened = addressAccidentHappened;
        this.descriptionOfAccident = descriptionOfAccident;
        this.descriptionOfVehicleDamage = descriptionOfVehicleDamage;
        this.estimatedCostOfRepair = estimatedCostOfRepair;
    }

    //user input for claim details
    public void load() {
        clrscr();
        try {
            System.out.print("Enter policy no: ");
            String policyNum = get.next().trim();
            policy.selectPolicy(policyNum);

            if(policy.getIsPolicyExist() == false) tryAgain("Policy doesn't exist");
            else {
                int generateNum = rand.nextInt(999999);
                claimNo = String.format("C%06d", generateNum);
                System.out.print("Enter date of accident(YYYY-MM-DD): ");
                this.dateOfAccident = get.next().trim();
                System.out.print("Enter address of accident: ");
                this.addressAccidentHappened = get.next().trim();
                System.out.print("Enter description of accident: ");
                this.descriptionOfAccident = get.next().trim();
                System.out.print("Enter description of vehicle damage: ");
                this.descriptionOfVehicleDamage = get.next().trim();
                System.out.print("Enter estimated cost of repair: ");
                this.estimatedCostOfRepair = get.nextDouble();
                System.out.println("Claim no: " + claimNo);
                printNote("Always keep your claim number");
                store(policyNum);
            }
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    //store claim details on database
    public void store(String policyNo) {
        try {
            connect();
            String query = "INSERT INTO claim (policy_no, claim_no, date_of_accident, address_of_accident, desc_of_accident, desc_vehicle_damage, est_cost_repairs)" +  
            "VALUES('"+ policyNo +"', '"+ claimNo +"', '"+ dateOfAccident +"', '"+ addressAccidentHappened +"', '"+ descriptionOfAccident +"', '"+ descriptionOfVehicleDamage +"', '"+ estimatedCostOfRepair +"')";
            prep = conn.prepareStatement(query);
            prep.execute();
            printSuccess("Claim success");
            main.backToMenu();
        } catch(Exception e) {
            printError("Claim create failed");
            main.backToMenu();
        }
    }

    //get claim details base on the claim number that the user inputted
    public void selectClaim() {
        try {
            clrscr();
            connect();
            System.out.print("Enter claim no: ");
            String claimNum = get.next().trim();
            String query = "SELECT * FROM claim WHERE claim_no='"+ claimNum +"'";
            result = createStmt.executeQuery(query);
            if(!result.next()) isClaimExist = false;
            else {
                getClaimDetails(claimNum, result.getString("date_of_accident"), 
                                          result.getString("address_of_accident"),
                                          result.getString("desc_of_accident"),
                                          result.getString("desc_vehicle_damage"),
                                          result.getDouble("est_cost_repairs")
                                          );
            }
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    //display claim details base on the claim number
    public void display() {
        clrscr();
        try {
            selectClaim();
            if(isClaimExist == false) {
                printError("Claim doesn't exist");
                main.backToMenu();
            } else {
                System.out.println("\n[Claim Details]");
                System.out.format("%-15s %-15s %-20s %-20s %-20s %-20s%n", "ClaimNo", "DateOfAccident", "AddressOfAccident", "DescOfAccident", "VehicleDamage", "EstCostRepairs");
                System.out.format("%-15s %-15s %-20s %-20s %-20s %-20s%n", claim.claimNo, claim.dateOfAccident, claim.addressAccidentHappened, claim.descriptionOfAccident, claim.descriptionOfVehicleDamage, claim.estimatedCostOfRepair);
                main.backToMenu();
            }
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }
    

    public void tryAgain(String msg) {
        System.out.print("\nError: " + msg + "\nTry Again? [1]Yes [2]No: ");
        int opt = get.nextInt();
        get.nextLine();

        if(opt == 1) load();
        else main.backToMenu();
    }
    
}