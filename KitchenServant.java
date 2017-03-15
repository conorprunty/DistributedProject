import KitchenNaming.*;

class KitchenServant extends _KitchenImplBase
{
  boolean microwaveOnOff;
  boolean coffeemakerOnOff;
  boolean ovenOnOff;
  String fridgeTemp;
    boolean doorOpenClose;
    String returnVal;
    String returnValTwo;
    Double tempAmount;
    
    
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

    public String changeHeat(double tempAmount, String appliance){
        this.tempAmount = tempAmount;
        if(appliance.equals("microwave")){
            return "The microwave's temperature is set to "+tempAmount;
        }
        else return "Temperature not changed.";
    }

    public String openCloseDoor(boolean openClose, String appliance){
      if(openClose == true && appliance.equals("microwave")){
        doorOpenClose = true;
        returnValTwo = "open";
      }
      else if(openClose == false && appliance.equals("microwave")){
        doorOpenClose = false;
        returnValTwo = "closed";
      }
      return "The door is " + returnValTwo;
    }

    public String temperature(){
      return "";
    }

    public String timer(){
      return "";
    }
}
