import KitchenNaming.*;

class KitchenServant extends _KitchenImplBase
{
  boolean microwaveOnOff;
  boolean coffeemakerOnOff;
  boolean ovenOnOff;
  String fridgeTemp;

    public String turnOnOff(boolean onOff, String appliance){
      String returnVal = "";
      if(onOff == true && appliance.equals("microwave")){
        microwaveOnOff = false;
        returnVal = "off";
      }
      else if(onOff == false && appliance.equals("microwave")){
        microwaveOnOff = true;
        returnVal = "on";
      }
      return "Microwave is now " + returnVal;
    }

    public String changeHeat(){
      return "";
    }

    public String openCloseDoor(){
      return "";
    }

    public String temperature(){
      return "";
    }

    public String timer(){
      return "";
    }
}
