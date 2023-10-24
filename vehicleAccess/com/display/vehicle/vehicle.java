package vehicleAccess.com.display.vehicle;

public class vehicle {
    private int vehiclePrice;
    private String VehicleName, VehiclePlate;

    public vehicle(String VehicleName, String VehiclePlate, int VehiclePrice) {
        this.VehicleName = VehicleName;
        this.VehiclePlate = VehiclePlate;
        this.vehiclePrice = VehiclePrice;
    }

    public String getVehName() {
        return VehicleName;
    }

    public String getVehiclePlate() {
        return VehiclePlate;
    }

    public int getVehiclePrice() {
        return vehiclePrice;
    }
}
