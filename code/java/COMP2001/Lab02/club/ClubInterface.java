package club;

import java.util.ArrayList;

public interface ClubInterface {
    /**
     * Add a new member to the club's list of members.
     * Two memberships cannot have the same name, so
     * attempt to join with existing name should not work.
     *
     * @param member The member object to be added.
     */
    void join(Membership member);

    /**
     * Check if the name exists among members.
     *
     * @param name of the member
     * @return the membership record of the individual or null if not a member
     */
    Membership find(String name);

    /**
     * @return The number of members (Membership objects) in
     * the club.
     */
    int numberOfMembers();

    /**
     * Determine the number of members who joined in the given month.
     *
     * @param month: The month we are interested in.
     * @return The number of members who joined in that month.
     **/
    int joinedInMonth(int month);

    /**
     * Remove from the club’s collection all members who joined in the given month and year,
     * and return them stored in a separate collection object.
     *
     * @param month The month of the membership.
     * @param year  The year of the membership.
     * @return The members who joined in the given month and year.
     */
    ArrayList<Membership> purge(int month, int year);
}
