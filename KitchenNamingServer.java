import java.io.*;
import org.omg.CORBA.*;
import KitchenNaming.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;
import java.util.Properties;

public class KitchenNamingServer{

	public static void main (String args[]) {
		try{
			NameComponent nc[] = new NameComponent[1];

	    	// create and initialize Orb object
	   		ORB orb = ORB.init(args, null);
				//create and initalize AddServant Class
			KitchenServant addRef = new KitchenServant();
			KitchenServant k = new KitchenServant();

			//connect servant to ORB
			orb.connect(addRef);
	   		System.out.println("Orb connected." + orb);

			//get object reference to Naming Service
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
	   		System.out.println("Found NameService.");

			//convert the naming service to a NamingContext reference in order to use the methods there
			NamingContext rootCtx = NamingContextHelper.narrow(objRef);

			//begin to add Contexts and objects to the NamingService
			//first add context 1 to root
			nc[0] = new NameComponent("Microwave", "Context");
			NamingContext AddCtx = rootCtx.bind_new_context(nc);
			System.out.println("Context 'Microwave' added to Name Space.");

			//bind object 1 to context 1
			nc[0] = new NameComponent("openCloseDoor", "Object");
			AddCtx.rebind(nc, AddCtx);
			//also bind object to servant class
			AddCtx.rebind(nc, addRef);
			System.out.println("Object 'openCloseDoor' added to Microwave Context.");

			//bind object 2 to context 1
			nc[0] = new NameComponent("setTimer", "Object");
			AddCtx.rebind(nc, AddCtx);
			//also bind object to servant class
			AddCtx.rebind(nc, addRef);
			System.out.println("Object 'setTimer' added to Microwave Context.");

			//bind object 3 to context 1
			nc[0] = new NameComponent("changeHeat", "Object");
			AddCtx.rebind(nc, AddCtx);
			//also bind object to servant class
			AddCtx.rebind(nc, addRef);
			System.out.println("Object 'changeHeat' added to Microwave Context.");

			//bind object 4 to context 1
			nc[0] = new NameComponent("turnOnOff", "Object");
			AddCtx.rebind(nc, AddCtx);
			//also bind object to servant class
			AddCtx.rebind(nc, addRef);
			System.out.println("Object 'turnOnOff' added to Microwave Context.");

			//add context 2 to root
			nc[0] = new NameComponent("Fridge", "Context");
			NamingContext Add2Ctx = rootCtx.bind_new_context(nc);
			System.out.println("Context 'Fridge' added to Name Space.");

			//bind object 5 to context 2
			nc[0] = new NameComponent("openCloseDoor", "Object");
			Add2Ctx.rebind(nc, Add2Ctx);
			System.out.println("Object 'openCloseDoor' added to Fridge Context.");

			//bind object 6 to context 2
			nc[0] = new NameComponent("changeHeat", "Object");
			Add2Ctx.rebind(nc, Add2Ctx);
			System.out.println("Object 'changeHeat' added to Fridge Context.");

			//add context 3 to root
			nc[0] = new NameComponent("Oven", "Context");
			NamingContext Add3Ctx = rootCtx.bind_new_context(nc);
			System.out.println("Context 'Oven' added to Name Space.");

			//bind object 7 to context 3
			nc[0] = new NameComponent("openCloseDoor", "Object");
			Add3Ctx.rebind(nc, Add3Ctx);
			System.out.println("Object 'openCloseDoor' added to Oven Context.");

			//bind object 8 to context 3
			nc[0] = new NameComponent("turnOnOff", "Object");
			Add3Ctx.rebind(nc, Add3Ctx);
			System.out.println("Object 'turnOnOff' added to Oven Context.");

			//bind object 9 to context 3
			nc[0] = new NameComponent("changeHeat", "Object");
			Add3Ctx.rebind(nc, Add3Ctx);
			System.out.println("Object 'changeHeat' added to Oven Context.");

			//add context 4 to root
			nc[0] = new NameComponent("CoffeeMaker", "Context");
			NamingContext Add4Ctx = rootCtx.bind_new_context(nc);
			System.out.println("Context 'CoffeeMaker' added to Name Space.");

			//bind object 8 to context 4
			nc[0] = new NameComponent("turnOnOff", "Object");
			Add4Ctx.rebind(nc, Add4Ctx);
			System.out.println("Object 'turnOnOff' added to CoffeeMaker Context.");

			//bind object 9 to context 4
			nc[0] = new NameComponent("setTimer", "Object");
			Add4Ctx.rebind(nc, Add4Ctx);
			System.out.println("Object 'setTimer' added to CoffeeMaker Context.");

      // wait for requests from clients
			orb.run();



		} catch (Exception e) {
			System.err.println("Error: "+e);
			e.printStackTrace(System.out);
		}

	}
}
