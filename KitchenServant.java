import KitchenNaming.*;
import com.google.gson.Gson;

class KitchenServant extends _KitchenImplBase
{
    String fridgeTemp;
    boolean doorOpenClose;
    String returnOnOff;
    String returnOpenClose;
    Double tempAmount;
    Gson gson = new Gson();


    public String turnOnOff(boolean onOff, String appliance){
        if(onOff==true){ gson.toJson(returnOnOff = "on");
        }else returnOnOff = "off";
        return gson.toJson("The "+appliance+" is " + returnOnOff);
    }

    public String changeHeat(double tempAmount, String appliance){
        return gson.toJson("The "+appliance+"'s temperature is set to "+tempAmount);
    }

    public String openCloseDoor(boolean openClose, String appliance){
        if(openClose==true){ gson.toJson(returnOpenClose = "open");
        }else returnOpenClose = "closed";
        return gson.toJson("The door of the "+appliance+ " is " + returnOpenClose);
    }

    public String temperature(){
        return "";
    }

    public String setTimer(double userTime, String appliance){
        return gson.toJson("The "+appliance+"'s timer is set to "+userTime + " minutes");
    }
}
