package sr.ice.server;
// **********************************************************************
//
// Copyright (c) 2003-2019 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Identity;

public class IceServer
{
	public void t1(String[] args)
	{
		int status = 0;
		Communicator communicator = null;

		try	{
			communicator = Util.initialize(args);
			ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");

			BulbulatorI bulbulatorSalon = new BulbulatorI();
			BulbulatorI bulbulatorKuchnia = new BulbulatorI();
			EggsBoilerI eggsBoiler = new EggsBoilerI(10 * 60 * 1000);
			ScrambledEggsMakerI scrambledEggsMaker = new ScrambledEggsMakerI(12*60*1000);
			TeaMakerI teaMakerKuchnia = new TeaMakerI(5 * 60 * 1000);
			TeaMakerI teaMakerBiuro = new TeaMakerI(5 * 60 * 1000);
			WarmArmchairI warmArmchairSalonPoPrawej = new WarmArmchairI();
			WarmArmchairI warmArmchairSalonPoLewej = new WarmArmchairI();
			PrincePoloSofaI princePoloSofaSalon = new PrincePoloSofaI();

			adapter.add(bulbulatorSalon, new Identity("bulbulatorSalon", "Bulbulators"));
			adapter.add(bulbulatorKuchnia, new Identity("bulbulatorKuchnia", "Bulbulators"));
			adapter.add(eggsBoiler, new Identity("eggsBoiler", "Cookers"));
			adapter.add(scrambledEggsMaker, new Identity("scrambledEggsMaker", "Cookers"));
			adapter.add(teaMakerKuchnia, new Identity("teaMakerKuchnia", "Cookers"));
			adapter.add(teaMakerBiuro, new Identity("teaMakerBiuro", "Cookers"));
			adapter.add(warmArmchairSalonPoPrawej, new Identity("warmArmchairSalonPoPrawej", "SittingDevices"));
			adapter.add(warmArmchairSalonPoLewej, new Identity("warmArmchairSalonPoLewej", "SittingDevices"));
			adapter.add(princePoloSofaSalon, new Identity("princePoloSofaSalon", "SittingDevices"));


			adapter.activate();
			
			System.out.println("Entering event processing loop...");
			
			communicator.waitForShutdown(); 		
			
		}
		catch (Exception e) {
			System.err.println(e);
			status = 1;
		}
		if (communicator != null) {
			try {
				communicator.destroy();
			}
			catch (Exception e) {
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}


	public static void main(String[] args)
	{
		IceServer app = new IceServer();
		app.t1(args);
	}
}
