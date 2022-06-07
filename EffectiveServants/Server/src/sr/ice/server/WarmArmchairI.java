package sr.ice.server;

import IntelligentHouse.WarmArmchair;
import com.zeroc.Ice.Current;

public class WarmArmchairI extends SittingDeviceI implements WarmArmchair {
    private boolean heating = false;
    @Override
    public void setHeating(boolean state, Current current) {
        heating = state;
    }

    @Override
    public boolean getHeating(Current current) {
        return heating;
    }
}
