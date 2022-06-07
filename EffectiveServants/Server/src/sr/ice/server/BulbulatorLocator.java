package sr.ice.server;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ServantLocator;
import com.zeroc.Ice.UserException;

public class BulbulatorLocator implements ServantLocator {
    private final BulbulatorI bulbulator = new BulbulatorI();

    @Override
    public LocateResult locate(Current current) throws UserException {
        System.out.println("Fulfilling a request for: " + current.id.name + " with the singleton bulbulator.");
        return new LocateResult(bulbulator, null);
    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) throws UserException {

    }

    @Override
    public void deactivate(String s) {

    }

}
