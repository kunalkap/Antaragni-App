package antaragni.in.antaragni.DataModels;

import java.util.ArrayList;

/**
 * Created by varun on 15/10/16.
 */

public class Category {
  public String Event;
  public ArrayList<subEvent> subevents;
  public ArrayList<Contact> contacts;

  //Getters and setters
  public String getEvent() {
    return Event;
  }

  public void setEvent(String event) {
    this.Event = event;
  }

  public ArrayList<subEvent> getSubevents() {
    return subevents;
  }

  public void setSubevents(ArrayList<subEvent> subevents) {
    this.subevents = subevents;
  }

  public ArrayList<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(ArrayList<Contact> contacts1) {
    this.contacts = contacts1;
  }
}
