import java.util.Properties;
import java.io.*;
import org.omg.CORBA.*;
import KitchenNaming.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.*;

public class KitchenNamingClient
{
    public static NamingContextExt rootCtx;
    public static final String[] devices = { "Microwave", "Fridge", "Oven", "CoffeeMaker" };
    public static final String[] microwaveOptions = { "Open/Close", "On/Off", "Change Heat", "Set Timer" };
    public static final String[] fridgeOptions = { "Open/Close", "Set Temperature" };
    public static final String[] ovenOptions = { "Open/Close","On/Off", "Set Temperature" };
    public static final String[] coffeeMakerOptions = { "On/Off", "Set Timer" };
    public static final String[] openClose = { "Open", "Close" };
    public static final String[] onOff = { "On", "Off" };
    public static String result = "";
    public static int userHeatAmount = 0;
    public static int userTempAmount = 0;
    public static int userTimerAmount = 0;

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
              userOpts(addRef);
        
             
            
        
        
        
            
             
                
//              System.out.println("Your connected devices are: microwave, fridge, coffeemaker, oven");
//              System.out.println("Please enter your device name");
//              Scanner scan = new Scanner(System.in);
//              String userDevice = scan.next();
//                String result = "";
//
//              if(userDevice.equalsIgnoreCase("microwave")){
//                   System.out.println("Type 'o' for on/off?\nType 'c' for open/close the door\nType 'h' to change the heat\n Type 't' to set the timer");
//                  String userOption = scan.next();
//
//                  if(userOption.equalsIgnoreCase("o")){
//                      System.out.println("Do you want it on or off?");
//                      String onOff = scan.next();
//                      boolean onOffSwitch;
//                      if(onOff.equals("on")){
//                        onOffSwitch = true;
//                      }
//                      else if (onOff.equals("off")){
//                        onOffSwitch = false;
//                      }
//                      else{
//                          onOffSwitch = false;
//                      }
//
//                     result = addRef.turnOnOff(onOffSwitch, userDevice);
//                  }
//                     if(userOption.equalsIgnoreCase("c")){
//                         System.out.println("Do you want to open or close the door?");
//                         String openClose = scan.next();
//                         boolean openCloseButton;
//                         if(openClose.equalsIgnoreCase("open")){
//                             openCloseButton = true;
//                         }
//                         else if (openClose.equalsIgnoreCase("close")){
//                         openCloseButton = false;
//                        }
//                         else{
//                          openCloseButton = false;
//                         }
//                     result = addRef.openCloseDoor(openCloseButton, userDevice);
//                     }
//
//                  //heat change setting
//                  if(userOption.equalsIgnoreCase("h")){
//                         System.out.println("Please set the temperature required.");
//                         String heatAmount = scan.next();
//                        int userHeatAmount=0;
//                          try{
//                                userHeatAmount = Integer.parseInt(heatAmount);
//                            }catch (NumberFormatException ex) {
//                                System.out.println("You did not enter a number.");
//                            }
//                      result = addRef.changeHeat(userHeatAmount, userDevice);
//                     }
//
//                  //set timer
//                  if(userOption.equalsIgnoreCase("t")){
//                         System.out.println("Please set the timer to the required amount of minutes.");
//                         String userTime = scan.next();
//                        double time = 0.0;
//                          try{
//                                time = Double.parseDouble(userTime);
//                            }catch (NumberFormatException ex) {
//                                System.out.println("You did not enter a number.");
//                            }
//                      result = addRef.setTimer(time, userDevice);
//                     }
//
//
//                  //output to console
//                  System.out.println("Result of method: " + result);
//                }
//
//                          else if(userDevice.equalsIgnoreCase("fridge")){
//                                   System.out.println("Type 'c' for open/close the door\nType 'h' to change the heat");
//                                  String userOption = scan.next();
//                                     if(userOption.equalsIgnoreCase("c")){
//                                         System.out.println("Do you want to open or close the door?");
//                                         String openClose = scan.next();
//                                         boolean openCloseButton;
//                                         if(openClose.equalsIgnoreCase("open")){
//                                             openCloseButton = true;
//                                         }
//                                         else if (openClose.equalsIgnoreCase("close")){
//                                         openCloseButton = false;
//                                        }
//                                         else{
//                                          openCloseButton = false;
//                                         }
//                                     result = addRef.openCloseDoor(openCloseButton, userDevice);
//                                     }
//
//                                  //heat change setting
//                                  if(userOption.equalsIgnoreCase("h")){
//                                         System.out.println("Please set the temperature required.");
//                                         String heatAmount = scan.next();
//                                        int userHeatAmount=0;
//                                          try{
//                                                userHeatAmount = Integer.parseInt(heatAmount);
//                                            }catch (NumberFormatException ex) {
//                                                System.out.println("You did not enter a number.");
//                                            }
//                                      result = addRef.changeHeat(userHeatAmount, userDevice);
//                                     }
//
//                                  //output to console
//                                  System.out.println("Result of method: " + result);
//                                }
//                                else if(userDevice.equalsIgnoreCase("coffeemaker")){
//                                         System.out.println("Type 'o' for on/off?\nType 'h' to change the heat\nType 't' to set the timer");
//                                        String userOption = scan.next();
//                                        if(userOption.equalsIgnoreCase("o")){
//                                            System.out.println("Do you want it on or off?");
//                                            String onOff = scan.next();
//                                            boolean onOffSwitch;
//                                            if(onOff.equals("on")){
//                                              onOffSwitch = true;
//                                            }
//                                            else if (onOff.equals("off")){
//                                              onOffSwitch = false;
//                                            }
//                                            else{
//                                                onOffSwitch = false;
//                                            }
//
//                                           result = addRef.turnOnOff(onOffSwitch, userDevice);
//                                        }
//
//                                        //heat change setting
//                                        if(userOption.equalsIgnoreCase("h")){
//                                               System.out.println("Please set the temperature required.");
//                                               String heatAmount = scan.next();
//                                              int userHeatAmount=0;
//                                                try{
//                                                      userHeatAmount = Integer.parseInt(heatAmount);
//                                                  }catch (NumberFormatException ex) {
//                                                      System.out.println("You did not enter a number.");
//                                                  }
//                                            result = addRef.changeHeat(userHeatAmount, userDevice);
//                                           }
//
//                                           //set timer
//                                           if(userOption.equalsIgnoreCase("t")){
//                                                  System.out.println("Please set the timer to the required amount of minutes.");
//                                                  String userTime = scan.next();
//                                                 double time = 0.0;
//                                                   try{
//                                                         time = Double.parseDouble(userTime);
//                                                     }catch (NumberFormatException ex) {
//                                                         System.out.println("You did not enter a number.");
//                                                     }
//                                               result = addRef.setTimer(time, userDevice);
//                                              }
//
//                                        //output to console
//                                        System.out.println("Result of method: " + result);
//                                      }
//
//                                                  else if(userDevice.equalsIgnoreCase("oven")){
//                                                         System.out.println("Type 'o' for on/off?\nType 'c' for open/close the door\nType 'h' to change the heat");
//                                                        String userOption = scan.next();
//
//                                                        if(userOption.equalsIgnoreCase("o")){
//                                                            System.out.println("Do you want it on or off?");
//                                                            String onOff = scan.next();
//                                                            boolean onOffSwitch;
//                                                            if(onOff.equals("on")){
//                                                              onOffSwitch = true;
//                                                            }
//                                                            else if (onOff.equals("off")){
//                                                              onOffSwitch = false;
//                                                            }
//                                                            else{
//                                                                onOffSwitch = false;
//                                                            }
//
//                                                           result = addRef.turnOnOff(onOffSwitch, userDevice);
//                                                        }
//                                                           if(userOption.equalsIgnoreCase("c")){
//                                                               System.out.println("Do you want to open or close the door?");
//                                                               String openClose = scan.next();
//                                                               boolean openCloseButton;
//                                                               if(openClose.equalsIgnoreCase("open")){
//                                                                   openCloseButton = true;
//                                                               }
//                                                               else if (openClose.equalsIgnoreCase("close")){
//                                                               openCloseButton = false;
//                                                              }
//                                                               else{
//                                                                openCloseButton = false;
//                                                               }
//                                                           result = addRef.openCloseDoor(openCloseButton, userDevice);
//                                                           }
//
//                                                        //heat change setting
//                                                        if(userOption.equalsIgnoreCase("h")){
//                                                               System.out.println("Please set the temperature required.");
//                                                               String heatAmount = scan.next();
//                                                              int userHeatAmount=0;
//                                                                try{
//                                                                      userHeatAmount = Integer.parseInt(heatAmount);
//                                                                  }catch (NumberFormatException ex) {
//                                                                      System.out.println("You did not enter a number.");
//                                                                  }
//                                                            result = addRef.changeHeat(userHeatAmount, userDevice);
//                                                           }
//                                                        //output to console
//                                                        System.out.println("Result of method: " + result);
//                                                      }




            } catch (Exception e) {
                System.out.println("ERROR : " + e) ;
                e.printStackTrace(System.out);
            }
	}
    
    public static void userOpts(Kitchen addRef){
        JFrame frame = new JFrame("Kitchen");
            String chosenDevice = (String) JOptionPane.showInputDialog(frame, 
            "What is your chosen device?",
            "Chosen Device",
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            devices, 
            devices[0]);
        
            JOptionPane.showMessageDialog(null, "Your device is: "+chosenDevice);
        
            if(chosenDevice.equals("Microwave")){
                String microwaveChoice = (String) JOptionPane.showInputDialog(frame, 
                "What would you like to do with the "+chosenDevice+"?",
                "Microwave",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                microwaveOptions, 
                microwaveOptions[0]);
                
                if(microwaveChoice.equals("Open/Close")){
                    String openCloseChoice = (String) JOptionPane.showInputDialog(frame, 
                    "Would you like to open or close it?",
                    "Open/Close",
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    openClose, 
                    openClose[0]);
                    
                    boolean openCloseButton;
                    if(openCloseChoice.equalsIgnoreCase("Open")){
                        openCloseButton = true;
                    }
                    else if (openCloseChoice.equalsIgnoreCase("Close")){
                        openCloseButton = false;
                    }
                    else{
                        openCloseButton = false;
                    }
                    result = addRef.openCloseDoor(openCloseButton, chosenDevice);
                    JOptionPane.showMessageDialog(null, result);
                }
                if(microwaveChoice.equals("On/Off")){
                    String onOffChoice = (String) JOptionPane.showInputDialog(frame, 
                    "Would you like it to be switched on or off?",
                    "On/Off",
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    onOff, 
                    onOff[0]);
                    
                    boolean onOffSwitch;
                    if(onOff.equals("on")){
                        onOffSwitch = true;
                    }
                    else if (onOff.equals("off")){
                        onOffSwitch = false;
                    }
                    else{
                        onOffSwitch = false;
                    }
                    result = addRef.turnOnOff(onOffSwitch, chosenDevice);
                    JOptionPane.showMessageDialog(null, result);
                }
                if(microwaveChoice.equals("Change Heat")){
                    String heatChoice = JOptionPane.showInputDialog(frame, "What heat would you like?");
                    try{
                        userHeatAmount = Integer.parseInt(heatChoice);
                        result = addRef.changeHeat(userHeatAmount, chosenDevice);
                        JOptionPane.showMessageDialog(null, result);
                    }catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "You didn't enter a number");
                    }  
                }
                if(microwaveChoice.equals("Set Timer")){
                    String timerChoice = JOptionPane.showInputDialog(frame, "What time would you like to set?");
                    try{
                        userTimerAmount = Integer.parseInt(timerChoice);
                        result = addRef.setTimer(userTimerAmount, chosenDevice);
                        JOptionPane.showMessageDialog(null, result);
                    }catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "You didn't enter a number");
                    }  
                }
            }
        
            if(chosenDevice.equals("Fridge")){
                String fridgeChoice = (String) JOptionPane.showInputDialog(frame, 
                "What would you like to do with the "+chosenDevice+"?",
                "Fridge",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                fridgeOptions, 
                fridgeOptions[0]);
                
                if(fridgeChoice.equals("Open/Close")){
                    String openCloseChoice = (String) JOptionPane.showInputDialog(frame, 
                    "Would you like to open or close it?",
                    "Open/Close",
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    openClose, 
                    openClose[0]);
                    
                    boolean openCloseButton;
                    if(openCloseChoice.equalsIgnoreCase("Open")){
                        openCloseButton = true;
                    }
                    else if (openCloseChoice.equalsIgnoreCase("Close")){
                        openCloseButton = false;
                    }
                    else{
                        openCloseButton = false;
                    }
                    result = addRef.openCloseDoor(openCloseButton, chosenDevice);
                    JOptionPane.showMessageDialog(null, result);
                }
                if(fridgeChoice.equals("Set Temperature")){
                    String tempChoice = JOptionPane.showInputDialog(frame, "What temperature would you like to set it to?");
                    try{
                        userTempAmount = Integer.parseInt(tempChoice);
                        result = addRef.changeHeat(userTempAmount, chosenDevice);
                        JOptionPane.showMessageDialog(null, result);
                    }catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "You didn't enter a number");
                    }  
                }
            }
        
        
         if(chosenDevice.equals("Oven")){
                String ovenChoice = (String) JOptionPane.showInputDialog(frame, 
                "What would you like to do with the "+chosenDevice+"?",
                "Oven",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                ovenOptions, 
                ovenOptions[0]);
                
                if(ovenChoice.equals("Open/Close")){
                    String openCloseChoice = (String) JOptionPane.showInputDialog(frame, 
                    "Would you like to open or close it?",
                    "Open/Close",
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    openClose, 
                    openClose[0]);
                    
                    boolean openCloseButton;
                    if(openCloseChoice.equalsIgnoreCase("Open")){
                        openCloseButton = true;
                    }
                    else if (openCloseChoice.equalsIgnoreCase("Close")){
                        openCloseButton = false;
                    }
                    else{
                        openCloseButton = false;
                    }
                    result = addRef.openCloseDoor(openCloseButton, chosenDevice);
                    JOptionPane.showMessageDialog(null, result);
                }
             
             if(ovenChoice.equals("On/Off")){
                    String onOffChoice = (String) JOptionPane.showInputDialog(frame, 
                    "Would you like it to be switched on or off?",
                    "On/Off",
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    onOff, 
                    onOff[0]);
                    
                    boolean onOffSwitch;
                    if(onOff.equals("on")){
                        onOffSwitch = true;
                    }
                    else if (onOff.equals("off")){
                        onOffSwitch = false;
                    }
                    else{
                        onOffSwitch = false;
                    }
                    result = addRef.turnOnOff(onOffSwitch, chosenDevice);
                    JOptionPane.showMessageDialog(null, result);
                }
                if(ovenChoice.equals("Set Temperature")){
                    String tempChoice = JOptionPane.showInputDialog(frame, "What temperature would you like to set it to?");
                    try{
                        userTempAmount = Integer.parseInt(tempChoice);
                        result = addRef.changeHeat(userTempAmount, chosenDevice);
                        JOptionPane.showMessageDialog(null, result);
                    }catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "You didn't enter a number");
                    }  
                }
            }
        
        if(chosenDevice.equals("CoffeeMaker")){
                String coffeeMakerChoice = (String) JOptionPane.showInputDialog(frame, 
                "What would you like to do with the "+chosenDevice+"?",
                "CoffeeMaker",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                coffeeMakerOptions, 
                coffeeMakerOptions[0]);
             
             if(coffeeMakerChoice.equals("On/Off")){
                    String onOffChoice = (String) JOptionPane.showInputDialog(frame, 
                    "Would you like it to be switched on or off?",
                    "On/Off",
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    onOff, 
                    onOff[0]);
                    
                    boolean onOffSwitch;
                    if(onOff.equals("on")){
                        onOffSwitch = true;
                    }
                    else if (onOff.equals("off")){
                        onOffSwitch = false;
                    }
                    else{
                        onOffSwitch = false;
                    }
                    result = addRef.turnOnOff(onOffSwitch, chosenDevice);
                    JOptionPane.showMessageDialog(null, result);
                }
                 if(coffeeMakerChoice.equals("Set Timer")){
                    String timerChoice = JOptionPane.showInputDialog(frame, "What time would you like to set?");
                    try{
                        userTimerAmount = Integer.parseInt(timerChoice);
                        result = addRef.setTimer(userTimerAmount, chosenDevice);
                        JOptionPane.showMessageDialog(null, result);
                    }catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "You didn't enter a number");
                    }  
                }
            }
            //asks user to run again
            //http://stackoverflow.com/questions/15853112/joptionpane-yes-no-option
            int dialogButton = JOptionPane.YES_NO_OPTION;
            if (JOptionPane.showConfirmDialog(null, "Choose device again?", "WARNING",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    userOpts(addRef);
            } else {
                    System.exit(0);
            }
    }
}
