package antaragni.in.antaragni.DataModels;

/**
 * Created by varun on 3/10/16.
 */

public class Contact {
  private final String name;
  private final String category;
  private final String post;
  private final String number;

  public Contact(String name,String category,String post,String number){
    this.name = name;
    this.category = category;
    this.post = post;
    this.number = number;
  }

  public Contact(String number, String name){
    this.name=name;
    this.number=number;
    this.category=null;
    this.post=null;
  }

  public String getName()
  {
    return name;
  }
  public String getCategory() { return category; }
  public String getNumber() { return number; }
  public String getPost() { return post; }

}
