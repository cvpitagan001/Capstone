import java.time.LocalDate;

public class RatingEngine {
    private double vpf;

    //get vehicle price factor
    public double getVpf(int year) {
        int ageOfVehicle = LocalDate.now().getYear() - year;
        if(ageOfVehicle >= 0 && ageOfVehicle < 3) return 0.01;
        if(ageOfVehicle >= 3 && ageOfVehicle < 5) return 0.008;
        if(ageOfVehicle >= 5 && ageOfVehicle < 10) return 0.007;
        if(ageOfVehicle >= 10 && ageOfVehicle < 15) return 0.006;
        if(ageOfVehicle >= 15 && ageOfVehicle < 20) return 0.004;
        if(ageOfVehicle >= 20 && ageOfVehicle < 40) return 0.002;
        if(ageOfVehicle >= 40) return 0.001;
        return vpf;
    }
    
}
