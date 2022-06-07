package sr.ice.server;

import IntelligentHouse.BulBulIntensityNotInRange;
import IntelligentHouse.Bulbulator;
import com.zeroc.Ice.Current;

public class BulbulatorI implements Bulbulator {
    private float intensity = 0.0f;
    private boolean active = false;
    @Override
    public void setBulBulIntensity(float intensity, Current current) throws BulBulIntensityNotInRange {
        if(1.0f <= intensity || intensity <= 0.0f) {
            throw new BulBulIntensityNotInRange();
        }
        this.intensity = intensity;
    }

    @Override
    public float getBulBulIntensity(Current current) {
        return intensity;
    }

    @Override
    public void setActive(boolean state, Current current) {
        active = state;
    }

    @Override
    public boolean getActive(Current current) {
        return active;
    }
}
