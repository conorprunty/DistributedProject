import java.util.Properties;
import java.io.*;
import org.omg.CORBA.*;
import KitchenNaming.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.*;
import com.google.gson.*;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.net.InetAddress;

public class KitchenNamingClient {
	public static NamingContextExt rootCtx;
	public static final String[] devices = { "Microwave", "Fridge", "Oven", "CoffeeMaker", "All Devices" };
	public static final String[] devices2 = { "Microwave", "Fridge", "Oven", "CoffeeMaker" };
	public static final String[] microwaveOptions = { "Open/Close", "On/Off", "Change Heat", "Set Timer" };
	public static final String[] allDevices ={"On/Off"};
	public static final String[] fridgeOptions = { "Open/Close", "Set Temperature", "On/Off" };
	public static final String[] ovenOptions = { "Open/Close", "On/Off", "Set Temperature" };
	public static final String[] coffeeMakerOptions = { "On/Off", "Set Timer" };
	public static final String[] openClose = { "Open", "Close" };
	public static final String[] onOff = { "On", "Off" };
	public static String result = "";
	public static int userHeatAmount = 0;
	public static int userTempAmount = 0;
	public static int userTimerAmount = 0;
	private String[] serviceNames = null;

	public static void list(NamingContext n, String indent) {
		try {
			final int batchSize = 1;
			BindingListHolder bList = new BindingListHolder();
			BindingIteratorHolder bIterator = new BindingIteratorHolder();
			n.list(batchSize, bList, bIterator);
			if (bList.value.length != (int) 0)
				for (int i = 0; i < bList.value.length; i++) {
					BindingHolder bh = new BindingHolder(bList.value[0]);
					do {
						String stringName = rootCtx.to_string(bh.value.binding_name);
						System.out.println(indent + stringName);
						if (bh.value.binding_type.value() == BindingType._ncontext) {
							String _indent = "----" + indent;

							NameComponent[] name = rootCtx.to_name(stringName);
							NamingContext sub_context = NamingContextHelper.narrow(n.resolve(name));
							list(sub_context, _indent);
						}
					} while (bIterator.value.next_one(bh));
				}
			else
				System.out.println(indent + "Nothing more in this context.");
		} catch (Exception e) {
			System.out.println("An exception occurred. " + e);
			e.printStackTrace();
		}
	}

	// http://stackoverflow.com/questions/9276487/samples-with-jmdns/9286640#9286640
	static class SampleListener implements ServiceListener {
		@Override
		public void serviceAdded(ServiceEvent event) {
			System.out.println("Service added   : " + event.getName() + "." + event.getType());
		}

		@Override
		public void serviceRemoved(ServiceEvent event) {
			System.out.println("Service removed : " + event.getName() + "." + event.getType());
		}

		@Override
		public void serviceResolved(ServiceEvent event) {
			System.out.println("Service resolved: " + event.getInfo());
		}
	}

	public static void main(String args[]) {

		try {

			final JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			String type = "_http._tcp.local.";
			if (args.length > 0) {
				type = args[0];
			}
			jmdns.addServiceListener(type, new SampleListener());

			jmdns.close();
			System.out.println("Closing JmDNS");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			NameComponent nc[] = new NameComponent[2];

			// initialize and also create the ORB
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			rootCtx = NamingContextExtHelper.narrow(objRef);

			// Use list function to run through the Name space
			list(rootCtx, "---->");
			System.out.println("Printing Done");
			// run method in servant class and invoke method on object bound to
			// Object4
			nc[0] = new NameComponent("Microwave", "Context");
			nc[1] = new NameComponent("turnOnOff", "Object");

			org.omg.CORBA.Object objRefAdd = rootCtx.resolve(nc);
			Kitchen addRef = KitchenHelper.narrow(objRefAdd);
			// put result of method into string
			userOpts(addRef);

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

	// begin GUI building
	public static void userOpts(Kitchen addRef) {

		// instantiate Gson object
		Gson gson = new Gson();
		gson = new GsonBuilder().disableHtmlEscaping().create();
		JFrame frame = new JFrame("Kitchen");
		// get connected device
		String chosenDevice = (String) JOptionPane.showInputDialog(frame, "What is your chosen device?",
				"Chosen Device", JOptionPane.QUESTION_MESSAGE, null, devices, devices[0]);
		if (chosenDevice == null) {
			JOptionPane.showMessageDialog(null, "You did not choose a device. Exiting application.");
			System.exit(0);
		}

		//convert string array to string
		StringBuilder builder = new StringBuilder();
			for(String s : devices2) {
			    builder.append(s + " ");
			}
			String dev = builder.toString();


		//for jmDNS - multicast to all devices
		if (chosenDevice.equals("All Devices")) {
			String choice = (String) JOptionPane.showInputDialog(frame,
					"What would you like to do with " + chosenDevice + "?", "Microwave",
					JOptionPane.QUESTION_MESSAGE, null, allDevices, allDevices[0]);

					if (choice.equals("On/Off")) {
						String onOffChoice = (String) JOptionPane.showInputDialog(frame,
								"Would you like it to be switched on or off?", "On/Off", JOptionPane.QUESTION_MESSAGE, null,
								onOff, onOff[0]);

						if (onOffChoice == null) {
							JOptionPane.showMessageDialog(null, "How dare you hit cancel, back to the start you go!");
							userOpts(addRef);
						}

						boolean onOffSwitch;
						if (onOffChoice.equalsIgnoreCase("on")) {
							onOffSwitch = true;
						} else if (onOffChoice.equalsIgnoreCase("off")) {
							onOffSwitch = false;
						} else {
							onOffSwitch = false;
						}

						//convert to json
					  String json = "{'onoff': '" + onOffSwitch +"', 'device': '"+ dev +"'}";
						System.out.println(json);
						//json = gson.toJson(json);

						result = addRef.turnOnOff(json);
						JOptionPane.showMessageDialog(null, result);
					}


					if (choice == null) {
						JOptionPane.showMessageDialog(null, "You did not choose an option");
						userOpts(addRef);
					}
				}


		// if microwave
		if (chosenDevice.equals("Microwave")) {
			String microwaveChoice = (String) JOptionPane.showInputDialog(frame,
					"What would you like to do with the " + chosenDevice + "?", "Microwave",
					JOptionPane.QUESTION_MESSAGE, null, microwaveOptions, microwaveOptions[0]);

			if (microwaveChoice == null) {
				JOptionPane.showMessageDialog(null, "You did not choose an option");
				userOpts(addRef);
			}

			if (microwaveChoice.equals("Open/Close")) {
				String openCloseChoice = (String) JOptionPane.showInputDialog(frame,
						"Would you like to open or close it?", "Open/Close", JOptionPane.QUESTION_MESSAGE, null,
						openClose, openClose[0]);

				if (openCloseChoice == null) {
					JOptionPane.showMessageDialog(null, "How dare you hit cancel, back to the start you go!");
					userOpts(addRef);
				}

				boolean openCloseButton;
				if (openCloseChoice.equalsIgnoreCase("Open")) {
					openCloseButton = true;
				} else if (openCloseChoice.equalsIgnoreCase("Close")) {
					openCloseButton = false;
				} else {
					openCloseButton = false;
				}
				// send request to server
				String json = "{'openclose': '" + openCloseButton +"', 'device': '"+ chosenDevice +"'}";
				result = addRef.openCloseDoor(json);
				JOptionPane.showMessageDialog(null, result);
			}
			if (microwaveChoice.equals("On/Off")) {
				String onOffChoice = (String) JOptionPane.showInputDialog(frame,
						"Would you like it to be switched on or off?", "On/Off", JOptionPane.QUESTION_MESSAGE, null,
						onOff, onOff[0]);

				if (onOffChoice == null) {
					JOptionPane.showMessageDialog(null, "How dare you hit cancel, back to the start you go!");
					userOpts(addRef);
				}

				boolean onOffSwitch;
				if (onOffChoice.equalsIgnoreCase("on")) {
					onOffSwitch = true;
				} else if (onOffChoice.equalsIgnoreCase("off")) {
					onOffSwitch = false;
				} else {
					onOffSwitch = false;
				}

				String json = "{'onoff': '" + onOffSwitch +"', 'device': '"+ chosenDevice +"'}";
				result = addRef.turnOnOff(json);
				JOptionPane.showMessageDialog(null, result);
			}
			if (microwaveChoice.equals("Change Heat")) {
				String heatChoice = JOptionPane.showInputDialog(frame, "What heat would you like?");
				// error handling
				try {
					userHeatAmount = Integer.parseInt(heatChoice);

					String json = "{'temp': '" + userHeatAmount +"', 'device': '"+ chosenDevice +"'}";
					result = addRef.changeHeat(json);
					JOptionPane.showMessageDialog(null, result);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "You didn't enter a number");
				}
			}
			if (microwaveChoice.equals("Set Timer")) {
				String timerChoice = JOptionPane.showInputDialog(frame, "What time would you like to set?");
				// error handling
				try {
					userTimerAmount = Integer.parseInt(timerChoice);
					String json = "{'time': '" + userTimerAmount +"', 'device': '"+ chosenDevice +"'}";
					result = addRef.setTimer(json);
					JOptionPane.showMessageDialog(null, result);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "You didn't enter a number");
				}
			}
		}
		// if fridge
		else if (chosenDevice.equals("Fridge")) {
			String fridgeChoice = (String) JOptionPane.showInputDialog(frame,
					"What would you like to do with the " + chosenDevice + "?", "Fridge", JOptionPane.QUESTION_MESSAGE,
					null, fridgeOptions, fridgeOptions[0]);

			if (fridgeChoice == null) {
				JOptionPane.showMessageDialog(null, "You did not choose an option");
				userOpts(addRef);
			}

			if (fridgeChoice.equals("On/Off")) {
				String onOffChoice = (String) JOptionPane.showInputDialog(frame,
						"Would you like it to be switched on or off?", "On/Off", JOptionPane.QUESTION_MESSAGE, null,
						onOff, onOff[0]);

				if (onOffChoice == null) {
					JOptionPane.showMessageDialog(null, "How dare you hit cancel, back to the start you go!");
					userOpts(addRef);
				}

				boolean onOffSwitch;
				if (onOffChoice.equalsIgnoreCase("on")) {
					onOffSwitch = true;
				} else if (onOffChoice.equalsIgnoreCase("off")) {
					onOffSwitch = false;
				} else {
					onOffSwitch = false;
				}

				String json = "{'onoff': '" + onOffSwitch +"', 'device': '"+ chosenDevice +"'}";
				result = addRef.turnOnOff(json);
				JOptionPane.showMessageDialog(null, result);
			}

			if (fridgeChoice.equals("Open/Close")) {
				String openCloseChoice = (String) JOptionPane.showInputDialog(frame,
						"Would you like to open or close it?", "Open/Close", JOptionPane.QUESTION_MESSAGE, null,
						openClose, openClose[0]);

				if (openCloseChoice == null) {
					JOptionPane.showMessageDialog(null, "How dare you hit cancel, back to the start you go!");
					userOpts(addRef);
				}

				boolean openCloseButton;
				if (openCloseChoice.equalsIgnoreCase("Open")) {
					openCloseButton = true;
				} else if (openCloseChoice.equalsIgnoreCase("Close")) {
					openCloseButton = false;
				} else {
					openCloseButton = false;
				}
				// send to server
				String json = "{'openclose': '" + openCloseButton +"', 'device': '"+ chosenDevice +"'}";
				result = addRef.openCloseDoor(json);
				JOptionPane.showMessageDialog(null, result);
			}
			if (fridgeChoice.equals("Set Temperature")) {
				String tempChoice = JOptionPane.showInputDialog(frame, "What temperature would you like to set it to?");
				// handle errors
				try {
					userTempAmount = Integer.parseInt(tempChoice);

					String json = "{'temp': '" + userTempAmount +"', 'device': '"+ chosenDevice +"'}";
					result = addRef.changeHeat(json);
					JOptionPane.showMessageDialog(null, result);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "You didn't enter a number");
				}
			}
		}

		// if oven
		else if (chosenDevice.equals("Oven")) {
			String ovenChoice = (String) JOptionPane.showInputDialog(frame,
					"What would you like to do with the " + chosenDevice + "?", "Oven", JOptionPane.QUESTION_MESSAGE,
					null, ovenOptions, ovenOptions[0]);

			if (ovenChoice == null) {
				JOptionPane.showMessageDialog(null, "You did not choose an option");
				userOpts(addRef);
			}

			if (ovenChoice.equals("Open/Close")) {
				String openCloseChoice = (String) JOptionPane.showInputDialog(frame,
						"Would you like to open or close it?", "Open/Close", JOptionPane.QUESTION_MESSAGE, null,
						openClose, openClose[0]);

				if (openCloseChoice == null) {
					JOptionPane.showMessageDialog(null, "How dare you hit cancel, back to the start you go!");
					userOpts(addRef);
				}

				boolean openCloseButton;
				if (openCloseChoice.equalsIgnoreCase("Open")) {
					openCloseButton = true;
				} else if (openCloseChoice.equalsIgnoreCase("Close")) {
					openCloseButton = false;
				} else {
					openCloseButton = false;
				}

				// send to server
				String json = "{'openclose': '" + openCloseButton +"', 'device': '"+ chosenDevice +"'}";
				result = addRef.openCloseDoor(json);
				JOptionPane.showMessageDialog(null, result);
			}

			if (ovenChoice.equals("On/Off")) {
				String onOffChoice = (String) JOptionPane.showInputDialog(frame,
						"Would you like it to be switched on or off?", "On/Off", JOptionPane.QUESTION_MESSAGE, null,
						onOff, onOff[0]);

				if (onOffChoice == null) {
					JOptionPane.showMessageDialog(null, "How dare you hit cancel, back to the start you go!");
					userOpts(addRef);
				}

				boolean onOffSwitch;
				if (onOffChoice.equalsIgnoreCase("on")) {
					onOffSwitch = true;
				} else if (onOffChoice.equalsIgnoreCase("off")) {
					onOffSwitch = false;
				} else {
					onOffSwitch = false;
				}

				String json = "{'onoff': '" + onOffSwitch +"', 'device': '"+ chosenDevice +"'}";

				result = addRef.turnOnOff(json);
				JOptionPane.showMessageDialog(null, result);
			}
			if (ovenChoice.equals("Set Temperature")) {
				String tempChoice = JOptionPane.showInputDialog(frame, "What temperature would you like to set it to?");
				// error handling
				try {
					userTempAmount = Integer.parseInt(tempChoice);

					String json = "{'temp': '" + userTempAmount +"', 'device': '"+ chosenDevice +"'}";
					result = addRef.changeHeat(json);
					JOptionPane.showMessageDialog(null, result);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "You didn't enter a number");
				}
			}
		}
		// if coffee maker
		else if (chosenDevice.equals("CoffeeMaker")) {
			String coffeeMakerChoice = (String) JOptionPane.showInputDialog(frame,
					"What would you like to do with the " + chosenDevice + "?", "CoffeeMaker",
					JOptionPane.QUESTION_MESSAGE, null, coffeeMakerOptions, coffeeMakerOptions[0]);

			if (coffeeMakerChoice == null) {
				JOptionPane.showMessageDialog(null, "You did not choose an option");
				userOpts(addRef);
			}

			if (coffeeMakerChoice.equals("On/Off")) {
				String onOffChoice = (String) JOptionPane.showInputDialog(frame,
						"Would you like it to be switched on or off?", "On/Off", JOptionPane.QUESTION_MESSAGE, null,
						onOff, onOff[0]);

				if (onOffChoice == null) {
					JOptionPane.showMessageDialog(null, "How dare you hit cancel, back to the start you go!");
					userOpts(addRef);
				}

				boolean onOffSwitch;
				if (onOffChoice.equalsIgnoreCase("on")) {
					onOffSwitch = true;
				} else if (onOffChoice.equalsIgnoreCase("off")) {
					onOffSwitch = false;
				} else {
					onOffSwitch = false;
				}

				// send to server, get response
				String json = "{'onoff': '" + onOffSwitch +"', 'device': '"+ chosenDevice +"'}";

				result = addRef.turnOnOff(json);
				JOptionPane.showMessageDialog(null, result);
			}
			if (coffeeMakerChoice.equals("Set Timer")) {
				String timerChoice = JOptionPane.showInputDialog(frame, "What time would you like to set?");
				// error handling
				try {
					userTimerAmount = Integer.parseInt(timerChoice);

					String json = "{'time': '" + userTimerAmount +"', 'device': '"+ chosenDevice +"'}";
					result = addRef.setTimer(json);
					JOptionPane.showMessageDialog(null, result);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "You didn't enter a number");
				}
			}
		}
		// asks user to run again
		// http://stackoverflow.com/questions/15853112/joptionpane-yes-no-option
		int dialogButton = JOptionPane.YES_NO_OPTION;
		if (JOptionPane.showConfirmDialog(null, "Choose device again?", "WARNING",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			userOpts(addRef);
		} else {
			System.exit(0);
		}
	}
}
