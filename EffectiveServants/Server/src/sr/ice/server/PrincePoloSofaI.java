package sr.ice.server;

import IntelligentHouse.PrincePoloSofa;
import IntelligentHouse.PrincePoloStorageEmpty;
import com.zeroc.Ice.Current;

public class PrincePoloSofaI extends SittingDeviceI implements PrincePoloSofa {

    @Override
    public void servePrincePolo(Current current) throws PrincePoloStorageEmpty {
        if(princePoloAmountInStorage(current) < 0){
            throw new PrincePoloStorageEmpty();
        }
        // serve the prince polo
    }

    @Override
    public int princePoloAmountInStorage(Current current) {
        return 100; // read from device here
    }
}
