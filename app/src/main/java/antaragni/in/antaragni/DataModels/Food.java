package antaragni.in.antaragni.DataModels;

/**
 * Created by varun on 3/10/16.
 */

public class Food {
  private final String name;
  private Venue venue;

  public Food(String name, Venue venue)
  {
    this.name = name;
    this.venue = venue;
  }

  public String getName()
  {
    return name;
  }

  public Venue getVenue()
  {
    return venue;
  }

}
