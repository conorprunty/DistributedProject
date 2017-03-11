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
			nc[0] = new NameComponent("Context1", "Context");
			NamingContext AddCtx = rootCtx.bind_new_context(nc);
			System.out.println("Context 'Context1' added to Name Space.");

			//bind object 1 to context 1
			nc[0] = new NameComponent("Object1", "Object");
			AddCtx.rebind(nc, AddCtx);
			//also bind object to servant class
			AddCtx.rebind(nc, addRef);
			System.out.println("Object 'Object1' added to Context1 Context.");

			//add Sub-Context 2 to Context 1
			nc[0] = new NameComponent("Sub-Context2", "Context");
			NamingContext Add3Ctx = AddCtx.bind_new_context(nc);
			System.out.println("Context 'Sub-Context2' added to Context1 context.");

			//add object 1 to sub context 2
			nc[0] = new NameComponent("Object1", "Object");
			Add3Ctx.rebind(nc, Add3Ctx);
			//also bind to servant class
			Add3Ctx.rebind(nc, addRef);
			System.out.println("Object 'Object1' added to Sub-Context2 Context.");

			//add context 2 to root
			nc[0] = new NameComponent("Context2", "Context");
			NamingContext Add2Ctx = rootCtx.bind_new_context(nc);
			System.out.println("Context 'Context2' added to Name Space.");

			//bind object 2 to context 2
			nc[0] = new NameComponent("Object2", "Object");
			Add2Ctx.rebind(nc, Add2Ctx);
			//also add to servant class
			Add2Ctx.rebind(nc, addRef);
			System.out.println("Object 'Object2' added to Context2 Context.");

			//bind object 4 to context 2
			nc[0] = new NameComponent("Object4", "Object");
			Add2Ctx.rebind(nc, Add2Ctx);
			//also add to servant class
			Add2Ctx.rebind(nc, addRef);
			System.out.println("Object 'Object4' added to Context2 Context.");

			//add sub context 1 to context 2
			nc[0] = new NameComponent("Sub-Context1", "Context");
			NamingContext Add4Ctx = Add2Ctx.bind_new_context(nc);
			System.out.println("Context 'Sub-Context1' added to Context2 context.");

			//add object 3 to sub-context 1
			nc[0] = new NameComponent("Object3", "Object");
			Add4Ctx.rebind(nc, Add4Ctx);
			//also add to servant class
			Add4Ctx.rebind(nc, addRef);
			System.out.println("Object 'Object3' added to Sub-Context1 Context.");

			//add context 3 to root
			nc[0] = new NameComponent("Context3", "Context");
			NamingContext Add5Ctx = rootCtx.bind_new_context(nc);
			System.out.println("Context 'Context3' added to Name Space.");

			//bind object 5 to context 3
			nc[0] = new NameComponent("Object5", "Object");
			Add5Ctx.rebind(nc, Add5Ctx);
			//also add to servant class
			Add5Ctx.rebind(nc, addRef);
			System.out.println("Object 'Object5' added to Context3 Context.");




      // wait for requests from clients
			orb.run();



		} catch (Exception e) {
			System.err.println("Error: "+e);
			e.printStackTrace(System.out);
		}

	}
}
