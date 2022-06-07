package sr.ice.server;

import IntelligentHouse.NotCookingFoodCurrently;
import com.zeroc.Ice.Current;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public abstract class FoodPreparerI implements IntelligentHouse.FoodPreparer {
    protected long preparationStart = -1;
    protected final int preparationTime;

    protected FoodPreparerI(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    @Override
    public int timeToPrepareFood(Current current) {
        return preparationTime;
    }

    @Override
    public boolean cookingInProgress(Current current) {
        return cookingInProgress();
    }

    private boolean cookingInProgress() {
        checkIfCookingOver();
        return preparationStart != -1;
    }

    @Override
    public int timeLeftToFinishCookingFood(Current current) throws NotCookingFoodCurrently {
        if(!cookingInProgress()) {
            throw new NotCookingFoodCurrently();
        }

        return (int) (preparationTime - (System.currentTimeMillis() - preparationStart));
    }

    private void checkIfCookingOver() {
        if(preparationTime != -1) {
            if(System.currentTimeMillis() - preparationStart >= preparationTime) {
                preparationStart = -1;
            }
        }
    }
}
