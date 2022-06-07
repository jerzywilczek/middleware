package sr.ice.server;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ServantLocator;
import com.zeroc.Ice.UserException;

import java.util.HashMap;

public class FoodPreparerLocator implements ServantLocator {
    private final HashMap<String, FoodPreparerI> map = new HashMap<>();

    @Override
    public LocateResult locate(Current current) throws UserException {
        if(map.containsKey(current.id.name))
            return new LocateResult(map.get(current.id.name), null);
        System.out.println("Creating a new FoodPreparer: " + current.id.name);
        FoodPreparerI foodPreparer;
        if(current.id.name.startsWith("eggsBoiler")) {
            foodPreparer = new EggsBoilerI(10 * 60 * 1000);
        } else if (current.id.name.startsWith("scrambled")) {
            foodPreparer = new ScrambledEggsMakerI(12 * 60 * 1000);
        } else {
            foodPreparer = new TeaMakerI(5 * 60 * 1000);
        }
        map.put(current.id.name, foodPreparer);
        return new LocateResult(foodPreparer, null);
    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) throws UserException {

    }

    @Override
    public void deactivate(String s) {

    }
}
