package sr.ice.server;

import IntelligentHouse.EggsMachine;
import com.zeroc.Ice.Current;

public abstract class EggsMachineI extends FoodPreparerI implements EggsMachine {
    protected EggsMachineI(int preparationTime) {
        super(preparationTime);
    }

    @Override
    public int storedEggsAmount(Current current) {
        return 100; // Read from device should be here
    }
}
