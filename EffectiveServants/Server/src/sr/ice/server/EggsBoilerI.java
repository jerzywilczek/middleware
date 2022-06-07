package sr.ice.server;

import IntelligentHouse.CookingAlreadyInProgress;
import IntelligentHouse.EggHardnessLevel;
import IntelligentHouse.EggsBoiler;
import IntelligentHouse.NotEnoughEggsInStorage;
import com.zeroc.Ice.Current;

public class EggsBoilerI extends EggsMachineI implements EggsBoiler {
    protected EggsBoilerI(int preparationTime) {
        super(preparationTime);
    }

    @Override
    public void boilEggs(int eggsAmount, EggHardnessLevel hardnessLevel, Current current) throws CookingAlreadyInProgress, NotEnoughEggsInStorage {
        if(cookingInProgress(current)){
            throw new CookingAlreadyInProgress();
        }

        if(storedEggsAmount(current) < eggsAmount) {
            throw new NotEnoughEggsInStorage();
        }

        this.preparationStart = System.currentTimeMillis();
        // the machine should be commanded to start cooking here
    }
}
