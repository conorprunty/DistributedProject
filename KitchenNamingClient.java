import java.util.Properties;
import java.io.*;
import org.omg.CORBA.*;
import KitchenNaming.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;
import java.util.Scanner;


public class KitchenNamingClient
{
    public static NamingContextExt rootCtx;

    public static void list(NamingContext n, String indent) {
        try {
                final int batchSize = 1;
                BindingListHolder bList = new BindingListHolder() ;
                BindingIteratorHolder bIterator = new BindingIteratorHolder();
                n.list(batchSize, bList, bIterator) ;
                if (bList.value.length != (int) 0)
                    for (int i = 0; i < bList.value.length; i++) {
                        BindingHolder bh = new BindingHolder(bList.value[0]);
                        do {
                            String stringName = rootCtx.to_string(bh.value.binding_name);
                            System.out.println(indent + stringName) ;
                            if (bh.value.binding_type.value() == BindingType._ncontext) {
                                String _indent = "----" + indent;

                                NameComponent [] name = rootCtx.to_name(stringName);
                                NamingContext sub_context = NamingContextHelper.narrow(n.resolve(name));
                                list(sub_context, _indent);
                            }
                        } while (bIterator.value.next_one(bh));
                    }
                else
                    System.out.println(indent + "Nothing more in this context.") ;
        }
        catch (Exception e) {
            System.out.println("An exception occurred. " + e) ;
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {
	try{
            NameComponent nc[]= new NameComponent[2];

	           // initialize and also create the ORB
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            rootCtx = NamingContextExtHelper.narrow(objRef);

            // Use list function to run through the Name space
            list(rootCtx, "---->");
            System.out.println("Printing Done");
            	//run method in servant class and invoke method on object bound to Object4
              nc[0] = new NameComponent("Context2", "Context");
              nc[1] = new NameComponent("Object4", "Object");

              org.omg.CORBA.Object objRefAdd = rootCtx.resolve(nc);
              Kitchen addRef = KitchenHelper.narrow(objRefAdd);
              //put result of method into string
        
              //get user input
              System.out.println("Please enter your device");
              Scanner keyboard = new Scanner(System.in);
              String input = keyboard.next();

              System.out.println("Do you want it on or off?");
              String onOff = keyboard.next();
              boolean testbool;
              if(onOff.equals("on")){
                testbool = true;
              }
              else if (onOff.equals("off")){
                testbool = false;
              }
              else{
                  testbool = false;
              }

            	String result = addRef.turnOnOff(testbool, input);
            	System.out.println("Result of method: " + result);

            } catch (Exception e) {
                System.out.println("ERROR : " + e) ;
                e.printStackTrace(System.out);
            }
	}
}
