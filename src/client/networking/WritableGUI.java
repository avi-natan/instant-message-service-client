package client.networking;


/**
 * An interface that lets the underlying {@link ClientConnection} of the client
 * to perform actions on the GUI.
 * 
 * @author Avi
 *
 */
public interface WritableGUI {
	
	/**
	 * Writes the input string into the GUI, thus displaying the string
	 * on screen.
	 * 
	 * @param s - The string to write.
	 */
	public void write(String s);
	
	/**
	 * A callback function for the {@link ClientConnection} class to
	 * use in order to perform the changes of adding a new friend to
	 * the GUI.
	 *  
	 * @param friend - The name of the friend for which GUI changes are
	 * to be made.
	 */
	public void addFriendCallback(String friend);
	
	/**
	 * A callback function for the {@link ClientConnection} class to
	 * use in order to perform the changes of removing a friend to
	 * the GUI.
	 *  
	 * @param friend - The name of the friend for which GUI changes are
	 * to be made.
	 */
	public void removeFriendCallback(String friend);
	
	/**
	 * Populates the friends list at client login and set
	 * their chat fields according to chat history of the
	 * user with each one of the friends.
	 * 
	 * @param friends - A string array representing the user's friends.
	 */
	public void initFriends(String[] friends);

}
