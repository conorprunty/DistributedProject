import KitchenNaming.*;
import com.google.gson.*;

class KitchenServant extends _KitchenImplBase
{
    String fridgeTemp;
    boolean doorOpenClose;
    String returnOnOff;
    String returnOpenClose;
    Double tempAmount;
    Gson gson = new Gson();

    public String turnOnOff(String json){
      gson = new GsonBuilder().disableHtmlEscaping().create();
      JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
      boolean onOff  = jobj.get("onoff").getAsBoolean();
      String appliance = jobj.get("device").toString();
        if(onOff==true){ gson.toJson(returnOnOff = "on");
        }else returnOnOff = "off";
        return gson.toJson("The "+appliance+" is " + returnOnOff);
    }

    public String changeHeat(String json){
      gson = new GsonBuilder().disableHtmlEscaping().create();
      JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
      double tempAmount  = jobj.get("temp").getAsDouble();
      String appliance = jobj.get("device").toString();
        return gson.toJson("The "+appliance+"'s temperature is set to "+tempAmount);
    }

    public String openCloseDoor(String json){
      gson = new GsonBuilder().disableHtmlEscaping().create();
      JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
      boolean openClose  = jobj.get("openclose").getAsBoolean();
      String appliance = jobj.get("device").toString();
        if(openClose==true){ gson.toJson(returnOpenClose = "open");
        }else returnOpenClose = "closed";
        return gson.toJson("The door of the "+appliance+ " is " + returnOpenClose);
    }

    public String setTimer(String json){
      gson = new GsonBuilder().disableHtmlEscaping().create();
      JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
      double userTime  = jobj.get("time").getAsDouble();
      String appliance = jobj.get("device").toString();
        return gson.toJson("The "+appliance+"'s timer is set to "+userTime + " minutes");
    }
}
