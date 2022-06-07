#ifndef INT_HOUSE
#define INT_HOUSE

module IntelligentHouse {

exception NotCookingFoodCurrently {};

interface FoodPreparer {
    idempotent int timeToPrepareFood();
    idempotent bool cookingInProgress();
    idempotent int timeLeftToFinishCookingFood() throws NotCookingFoodCurrently;

};

exception CookingAlreadyInProgress {};

interface EggsMachine extends FoodPreparer {
    idempotent int storedEggsAmount();
};

exception NotEnoughEggsInStorage {};

enum EggHardnessLevel {
    SOFT,
    MEDIUM,
    HARD,
};

enum ScrambledEggsAdditionalIngredient {
    PEPPER,
    ONION,
    CHIVES,
    TOMATO,
    MUSHROOMS,
};

sequence<ScrambledEggsAdditionalIngredient> ScrambledEggsAdditionalIngredientsSequence;

interface ScrambledEggsMaker extends EggsMachine {
    void prepareScrambledEggs(int eggsAmount, EggHardnessLevel hardnessLevel, ScrambledEggsAdditionalIngredientsSequence additionalIngredients) throws NotEnoughEggsInStorage, CookingAlreadyInProgress;
};

interface EggsBoiler extends EggsMachine {
    void boilEggs(int eggsAmount, EggHardnessLevel hardnessLevel) throws NotEnoughEggsInStorage, CookingAlreadyInProgress;
};

enum TeaType {
    EARLGRAY,
    OOLONG,
    GUNPOWDER,
};

interface TeaMaker extends FoodPreparer {
    void startMakingTea(TeaType type) throws CookingAlreadyInProgress;
};

interface SittingDevice {
    void extend();
    void contract();
};

interface WarmArmchair extends SittingDevice {
    void setHeating(bool state);
    idempotent bool getHeating();
};

exception PrincePoloStorageEmpty {};

interface PrincePoloSofa extends SittingDevice {
    void servePrincePolo() throws PrincePoloStorageEmpty;
    idempotent int princePoloAmountInStorage();
};

exception BulBulIntensityNotInRange {};

interface Bulbulator {
    void setBulBulIntensity(float intensity) throws BulBulIntensityNotInRange;
    idempotent float getBulBulIntensity();
    void setActive(bool state);
    idempotent bool getActive();
};

};

#endif