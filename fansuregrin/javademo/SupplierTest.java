package fansuregrin.javademo;

import java.util.Objects;

public class SupplierTest {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        var car = new Car(null, false);
        car = new Car(null, true);

        car = new Car(new SteeringWheel(1), false);
        // when `steeringWheel` is not null and use Supplier
        // the default steering whell object not be created!
        car = new Car(new SteeringWheel(2), true);
    }
}

@SuppressWarnings("unused")
class Car {
    private SteeringWheel steeringWheel;
    
    public Car(SteeringWheel steeringWheel, boolean useSupplier) {
        if (useSupplier) {
            this.steeringWheel = Objects.requireNonNullElseGet(steeringWheel,
                () -> new SteeringWheel());
        } else {
            this.steeringWheel = Objects.requireNonNullElse(steeringWheel,
                new SteeringWheel());
        }
    }
}

class SteeringWheel {
    double radius;

    public SteeringWheel() {
        System.out.println("created a default steering wheel!");
    }

    public SteeringWheel(double radius) {
        this.radius = radius;
    }
}