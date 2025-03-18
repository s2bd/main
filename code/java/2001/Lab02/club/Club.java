package club;
import java.util.ArrayList;

/**
 * Store details of club memberships.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Club implements ClubInterface
{
    // Define any necessary fields here ...
    private ArrayList<Membership> members;

    /**
     * Constructor for objects of class Club
     */
    public Club()
    {
        // Initialise any fields here ...
        members = new ArrayList<>(); // membership info is stored in an ArrayList
    }

    /**
     * Add a new member to the club's list of members.
     * @param member The member object to be added.
     */
    public void join(Membership member)
    {
        int index;
        // using a for loop to iterate through the memberships to see if member already exists
        for(index=0;index<members.size();index++){
            if(members.get(index).equals(member) || members.get(index).getName().equals(member.getName())){
                return; // member not added  if already exists
            }
        }
        members.add(member); // member is added if not already existing
    }

    /**
     * @return The number of members (Membership objects) in
     *         the club.
     */
    public int numberOfMembers()
    {
        return members.size(); // returns size of ArrayList
    }

    /**
     * Remove from the club’s collection all members who joined in the given month and year,
     * and return them stored in a separate collection object.
     *
     * @param month The month of the membership.
     * @param year  The year of the membership.
     * @return The members who joined in the given month and year.
     */
    public ArrayList purge(int month, int year)
    {
        int index;
        ArrayList<Membership> purgedMembers = new ArrayList<>();
        for(index=0;index<members.size();index++){
            if(members.get(index).getMonth()==month && members.get(index).getYear()==year){
                // Membership purgedMember = new Membership(members.get(index).getName(),members.get(index).getMonth(),members.get(index).getYear());
                purgedMembers.add(members.get(index)); // purged member added to new collection
                members.remove(index); // purged member removed from club
            }
        }
        return purgedMembers; // returns new collection of members
    }

    /**
     * Determine the number of members who joined in the given month.
     *
     * @param month: The month we are interested in.
     * @return The number of members who joined in that month.
     **/
    public int joinedInMonth(int month)
    {
        if(month<1 || month>12){
            System.out.println("Month must be within the range 1-12");
            return 0;
        }
        int numOfMembers=0, index;
        for(index=0;index<members.size();index++){
            if(members.get(index).getMonth()==month){
                numOfMembers+=1;
            }
        }
        return numOfMembers;
    }

    /**
     * Check if the name exists among members.
     *
     * @param name of the member
     * @return the membership record of the individual or null if not a member
     */
    public Membership find(String name)
    {
        int index;
        for(index=0;index<members.size();index++){
            if(members.get(index).getName()==name || members.get(index).getName().equals(name)){
                return members.get(index);
            }
        }
        return null;
    }

}