package KitchenNaming;

/**
* KitchenNaming/KitchenHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Kitchen.idl
* Wednesday, March 29, 2017 9:19:34 PM IST
*/

public final class KitchenHolder implements org.omg.CORBA.portable.Streamable
{
  public KitchenNaming.Kitchen value = null;

  public KitchenHolder ()
  {
  }

  public KitchenHolder (KitchenNaming.Kitchen initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = KitchenNaming.KitchenHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    KitchenNaming.KitchenHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return KitchenNaming.KitchenHelper.type ();
  }

}
