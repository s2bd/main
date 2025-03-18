package animal;

import java.util.ArrayList;

public interface AnimalMonitorInterface {

    /**
     * Print details of all the sightings.
     *
     * @return the AnimalMonitor (for method chaining)
     */
    AnimalMonitor printList();

    /**
     * Print details of all the sightings of the given animal.
     *
     * @param animal The type of animal.
     * @return the AnimalMonitor (for method chaining)
     */
    AnimalMonitor printSightingsOf(String animal);

    /**
     * Print all the sightings by the given spotter.
     *
     * @param spotter The ID of the spotter.
     * @return the AnimalMonitor (for method chaining)
     */
    AnimalMonitor printSightingsBy(int spotter);

    /**
     * Return a count of the number of sightings by the spotter.
     *
     * @param spotter
     * @return The count of sightings by the given spotter.
     */
    int getCount(String animal);

    /**
     * Return a count of the number of sightings for a given reporting period.
     *
     * @param when The reporting period of interest.
     * @return The count of sightings of the given animal.
     */
    int getCount(int when) // added
    ;

    /**
     * Print a list of the types of animal considered to be endangered.
     *
     * @param animalNames     A list of animals names.
     * @param dangerThreshold Counts less-than or equal-to to this level
     *                        are considered to be dangerous.
     * @return the AnimalMonitor (for method chaining)
     */
    AnimalMonitor printEndangered(ArrayList<String> animalNames,
                                  int dangerThreshold);

    /**
     * Remove from the sightings list all of those records with
     * a count of zero.
     *
     * @return the AnimalMonitor (for method chaining)
     */
    AnimalMonitor removeZeroCounts();

    /**
     * Remove from the sightings list all of those records // added
     * for a given spotter.
     *
     * @return the AnimalMonitor (for method chaining)
     */
    AnimalMonitor removeSpotter(int spotter);

    /**
     * Return a String that contains only the names of animals for a
     * particular spotter and a particular period.
     *
     * @param spotter The spotter.
     * @param when    The reporting period of interest.
     * @return An AnimalMonitor of the records specified.
     */
    String sightingsBy(int spotter, int when);
}
