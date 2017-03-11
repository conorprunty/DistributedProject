import KitchenNaming.*;

class KitchenServant extends _KitchenImplBase
{
  boolean microwaveOnOff;
  boolean coffeemakerOnOff;
  boolean ovenOnOff;
  String fridgeTemp;
    String returnVal;
    
    public String turnOnOff(boolean onOff, String appliance){
      if(onOff == true && appliance.equals("microwave")){
        microwaveOnOff = true;
        returnVal = "on";
      }
      else if(onOff == false && appliance.equals("microwave")){
        microwaveOnOff = false;
        returnVal = "off";
      }
      return "The microwave is " + returnVal;
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
