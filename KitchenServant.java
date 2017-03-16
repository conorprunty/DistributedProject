import KitchenNaming.*;

class KitchenServant extends _KitchenImplBase
{
    String fridgeTemp;
    boolean doorOpenClose;
    String returnOnOff;
    String returnOpenClose;
    Double tempAmount;


    public String turnOnOff(boolean onOff, String appliance){
      //if(appliance.equals("microwave")){
        if(onOff==true){ returnOnOff = "on";
        }else returnOnOff = "off";
      //}
      return "The "+appliance+" is " + returnOnOff;
    }

    public String changeHeat(double tempAmount, String appliance){
        //if(appliance.equals("microwave")){
            return "The "+appliance+"'s temperature is set to "+tempAmount;
        //}
        //else return "Temperature not changed.";
    }

    public String openCloseDoor(boolean openClose, String appliance){
        //if(appliance.equals("microwave")){
            if(openClose==true){ returnOpenClose = "open";
            }else returnOpenClose = "closed";
        //}
      return "The door of the "+appliance+ " is " + returnOpenClose;
    }

    public String temperature(){
      return "";
    }

    public String setTimer(double userTime, String appliance){
      return "The "+appliance+"'s timer is set to "+userTime + " minutes";
    }
}
