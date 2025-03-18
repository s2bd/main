package mailtoo
;

/*
 * Copyright (C) 2022 E Brown
 *
 * This work is derived from code accompanying the Barnes and Kolling BlueJ book and 
 * is subject to their copyright
 * License is given to COMP2001 students and staff of Memorial 
 * University during the operation of the course.
 *
 */

public interface MailClientInterface {
    /**
     * Return the next mail item (if any) for this user.
     */
    MailItem getNextMailItem();

    /**
     * Print the next mail item (if any) for this user to the text
     * terminal.
     */
    void printNextMailItem();

    /**
     * Send the given message to the given recipient via
     * the attached mail server. If either field is null or blank,
     * do not send a message and return null.
     *
     * @param to      The intended recipient.
     * @param message The text of the message to be sent.
     *
     * @return The MailItem that was sent or null
     */
    MailItem sendMailItem(String to, String message);

    /**
     * Resend a MailItem to a different recipient.
     *
     * @param item              The old MailItem.
     * @param newRecipient      The new Recipient.
     *
     * @return The MailItem that was sent
     */
    MailItem reSend(MailItem item, String newRecipient);
}