package sr.ice.server;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ServantLocator;
import com.zeroc.Ice.UserException;

import java.util.HashMap;

public class SittingDeviceLocator implements ServantLocator {
    private final HashMap<String, SittingDeviceI> map = new HashMap<>();

    @Override
    public LocateResult locate(Current current) throws UserException {
        if(map.containsKey(current.id.name))
            return new LocateResult(map.get(current.id.name), null);
        System.out.println("Creating a new SittingDevice: " + current.id.name);
        SittingDeviceI sittingDevice;
        if(current.id.name.startsWith("princePolo")) {
            sittingDevice = new PrincePoloSofaI();
        } else {
            sittingDevice = new WarmArmchairI();
        }
        map.put(current.id.name, sittingDevice);
        return new LocateResult(sittingDevice, null);
    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) throws UserException {

    }

    @Override
    public void deactivate(String s) {

    }
}
