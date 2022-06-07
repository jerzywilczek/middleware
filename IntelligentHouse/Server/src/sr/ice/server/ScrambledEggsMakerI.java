package sr.ice.server;


import IntelligentHouse.CookingAlreadyInProgress;
import IntelligentHouse.EggHardnessLevel;
import IntelligentHouse.NotEnoughEggsInStorage;
import IntelligentHouse.ScrambledEggsAdditionalIngredient;
import com.zeroc.Ice.Current;

public class ScrambledEggsMakerI extends EggsMachineI implements IntelligentHouse.ScrambledEggsMaker {
    protected ScrambledEggsMakerI(int preparationTime) {
        super(preparationTime);
    }

    @Override
    public void prepareScrambledEggs(int eggsAmount, EggHardnessLevel hardnessLevel, ScrambledEggsAdditionalIngredient[] additionalIngredients, Current current) throws CookingAlreadyInProgress, NotEnoughEggsInStorage {
        if(cookingInProgress(current)) {
            throw new CookingAlreadyInProgress();
        }
        if(eggsAmount <storedEggsAmount(current)) {
            throw new NotEnoughEggsInStorage();
        }

        this.preparationStart = System.currentTimeMillis();
        // The machine should be commanded to start cooking here
    }
}
