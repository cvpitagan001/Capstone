import java.time.LocalDate;

public class Vehicle extends PolicyHolder {
    PAS main = new PAS();
    RatingEngine rate = new RatingEngine();
    private String make, model, type, fuelType;
    private int year;
    private double purchasePrice, premiumCharged;

    public void load(int dlx) {
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
                
                x++;
            }
            
        } catch(Exception e) {
            printError(e.toString());
            main.backToMenu();   
        }
    }
}
