import java.util.ArrayList;

public class Vehicle extends PolicyHolder {
    PAS main = new PAS();
    RatingEngine rate = new RatingEngine();
    Policy policy = new Policy("", "", "", "", 0);
    private String make, model, type, fuelType, uuid, color;
    private int year;
    private double purchasePrice, premiumCharged, totalCost;
    private ArrayList<Vehicle> list = new ArrayList<Vehicle>();

    public Vehicle(String make, String model, String type, String fuelType, String uuid,
            int year, double purchasePrice, double premiumCharged, String color) {
        this.make = make;
        this.model = model;
        this.type = type;
        this.fuelType = fuelType;
        this.uuid = uuid;
        this.year = year;
        this.purchasePrice = purchasePrice;
        this.premiumCharged = premiumCharged;
        this.color = color;
    }

    //user input for vehicle details
    public void load(int dlx, String accountNo, String policyHolderUuid, String policyNo) {
        try {
            System.out.println("[Vehicle Details]");
            System.out.print("How many vehicles do you want to insured: ");
            int qty = get.nextInt();
            int x = 1;

            while(x <= qty) {
                System.out.println("\n[Vehicle " + x + "]");
                System.out.print("Make: ");
                make = get.next().trim();
                System.out.print("Model: ");
                model = get.next().trim();
                System.out.print("Year: ");
                year = get.nextInt();
                System.out.print("Type: ");
                type = get.next().trim();
                System.out.print("Fuel Type: ");
                fuelType = get.next().trim();
                System.out.print("Color: ");
                color = get.next().trim();
                System.out.print("Purchase price: ");
                purchasePrice = get.nextDouble();
                get.nextLine();
                premiumCharged = rate.calculatePremium(purchasePrice, year, dlx);
                System.out.printf("Premium Charged: %.2f", premiumCharged);
                totalCost = totalCost + premiumCharged;
                uuid = getUUID();

                list.add(new Vehicle(make, model, type, fuelType, uuid, year, purchasePrice, premiumCharged, color));
                if(qty == x) display(accountNo, policyNo, policyHolderUuid);
                x++;
            }
            
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();   
        }
    }

    //display vehicle details
    public void display(String accountNo, String policyNo, String policyHolderUuid) {
        clrscr();
        System.out.format("%n%-30s%n", "[Policy Holder Details]");
        System.out.format("%-15s %-15s %-15s%n", "AccountNo", "FirstName", "LastName");
        System.out.format("%-15s %-15s %-15s%n", accountNo, account.getFirstName(), account.getLastName());
        System.out.format("%n%-30s%n", "[Vehicle Details]");
        System.out.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s%n", "Make", "Model", "Type",
                                                                "FuelType", "Year", "Color", "PurchasePrice","PremiumCharged");
        for (Vehicle vehicle : list) {
            System.out.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s%n", vehicle.make, vehicle.model, vehicle.type,
                                                                                    vehicle.fuelType, vehicle.year, vehicle.color,
                                                                                    vehicle.purchasePrice,
                                                                                    vehicle.premiumCharged);
        }
        
        System.out.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s%n", "---", "---", "---",
        "---", "---", "---", "Total: ", totalCost);

        buyPolicy(accountNo, policyNo, policyHolderUuid);
    }

    //buy policy
    public void buyPolicy(String accountNo, String policyNo, String policyHolderUuid) {
        try {
            System.out.print("\n\nDo you want to buy this Policy? [1]Yes [2]No: ");
            int opt = get.nextInt();
            if(opt == 1) {
                store(accountNo, policyNo, policyHolderUuid);
                main.backToMenu();
            } else {
                deleteFromDb("policy", "policy_no", policyNo);
                deleteFromDb("policy_holder", "uuid", policyHolderUuid);
                main.backToMenu();
            }
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }

    //store policy holder details on database
    public void store(String accountNo, String policyNo, String policyHolderUuid) {
        for (Vehicle vehicle : list) {
            String fields = "uuid, customer_acc_no, policy_holder_uuid, policy_no, make, model, year, type, fuel_type, color, purchase_price, premium_charged";
            String values = "'"+ vehicle.uuid +"', '"+ accountNo +"', '"+ policyHolderUuid +"', '"+ policyNo +"', '"+ vehicle.make +"', '"+ vehicle.model +"', '"+ vehicle.year +"', '"+ vehicle.type +"', '"+ vehicle.fuelType +"', '"+ vehicle.color +"', '"+ vehicle.purchasePrice +"', '"+ vehicle.premiumCharged +"'";
            storeOnDB("vehicle", fields, values, "");
        }
        policy.update("policy", "cost", String.valueOf(totalCost), "policy_no", policyNo);
        printSuccess("Policy sold");
    }

}
