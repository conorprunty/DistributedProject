package KitchenNaming;


/**
* KitchenNaming/_KitchenImplBase.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Kitchen.idl
* Saturday, April 8, 2017 11:42:34 AM IST
*/

public abstract class _KitchenImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements KitchenNaming.Kitchen, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _KitchenImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("turnOnOff", new java.lang.Integer (0));
    _methods.put ("changeHeat", new java.lang.Integer (1));
    _methods.put ("openCloseDoor", new java.lang.Integer (2));
    _methods.put ("setTimer", new java.lang.Integer (3));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // KitchenNaming/Kitchen/turnOnOff
       {
         String json = in.read_string ();
         String $result = null;
         $result = this.turnOnOff (json);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // KitchenNaming/Kitchen/changeHeat
       {
         String json = in.read_string ();
         String $result = null;
         $result = this.changeHeat (json);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // KitchenNaming/Kitchen/openCloseDoor
       {
         String json = in.read_string ();
         String $result = null;
         $result = this.openCloseDoor (json);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // KitchenNaming/Kitchen/setTimer
       {
         String json = in.read_string ();
         String $result = null;
         $result = this.setTimer (json);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:KitchenNaming/Kitchen:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _KitchenImplBase
