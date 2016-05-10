package nz.ac.op.paffjj1student.location;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * Created by Mr Nobody on 9/05/2016.
 */
public class RandomCoordinates implements IGetCoordinates {

    private Random randGen;

    public RandomCoordinates(Random randGen){
        this.randGen = randGen;
    }

    @Override
    public double CalculateLatitude() {
        int whole = randGen.nextInt(180);
        double decimal = randGen.nextDouble();
        double randLat = whole + decimal - 90; // gives a range between -90 and +90

        return randLat;
    }

    @Override
    public double CalculateLongitude() {
        int whole = randGen.nextInt(180); //upperbound is exclusive so add extra 1
        double decimal = randGen.nextDouble();
        double randLong = whole + decimal - 90; // gives a range between -90 and +90
        return randLong;
    }
}
