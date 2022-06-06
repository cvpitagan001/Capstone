public class Vehicle extends PolicyHolder {
    PAS main = new PAS();
    RatingEngine rate = new RatingEngine();
    private String make, model, type, fuelType;
    private int year;
    private double purchasePrice, premiumCharged;

    public void load(int dlx, String accountNo, String policyHolderNo, String policyNo) {
        try {
            System.out.println("--Vehicle Details--");
            System.out.print("How many vehicles do you want to insured: ");
            int qty = get.nextInt();
            int x = 0;

            while(x < qty) {
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
                System.out.print("Purchase price: ");
                purchasePrice = get.nextDouble();
                get.nextLine();
                premiumCharged = (purchasePrice * rate.getVpf(year)) + ((purchasePrice/100)/dlx);
                System.out.printf("Premium Charged: %.2f", premiumCharged);

                store(accountNo, policyNo, policyHolderNo);
                x++;
            }
            
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();   
        }
    }

    public void store(String accountNo, String policyNo, String policyHolderNo) {
        try {
            connect();
            String query = "INSERT INTO vehicle (uuid, customer_acc_no, policy_holder_no, policy_no, make, model, year, type, fuel_type, purchase_price, premium_charged)" +  
            "VALUES('"+ getUUID() +"', '"+ accountNo +"', '"+ policyNo +"', '"+ policyHolderNo +"', '"+ make +"', '"+ model +"', '"+ year +"', '"+ type +"', '"+ fuelType +"', '"+ purchasePrice +"', '"+ premiumCharged +"')";
            prep = conn.prepareStatement(query);
            prep.execute();
            printSuccess("Policy created");
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();
        }
    }
}
