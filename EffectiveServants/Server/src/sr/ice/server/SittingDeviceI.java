package sr.ice.server;

import IntelligentHouse.SittingDevice;
import com.zeroc.Ice.Current;

public abstract class SittingDeviceI implements SittingDevice {
    protected boolean extended = false;

    @Override
    public void extend(Current current) {
        extended = true;
    }

    @Override
    public void contract(Current current) {
        extended = false;
    }
}
