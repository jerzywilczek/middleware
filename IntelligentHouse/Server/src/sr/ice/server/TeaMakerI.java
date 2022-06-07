package sr.ice.server;

import IntelligentHouse.CookingAlreadyInProgress;
import IntelligentHouse.TeaMaker;
import IntelligentHouse.TeaType;
import com.zeroc.Ice.Current;

public class TeaMakerI extends FoodPreparerI implements TeaMaker {
    protected TeaMakerI(int preparationTime) {
        super(preparationTime);
    }

    @Override
    public void startMakingTea(TeaType type, Current current) throws CookingAlreadyInProgress {
        if(cookingInProgress(current)) {
            throw new CookingAlreadyInProgress();
        }
        preparationStart = System.currentTimeMillis();
    }
}
