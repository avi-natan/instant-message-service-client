package client.networking;

public interface WritableGUI {
	
	public void write(String s);
	public void addFriendCallback(String friend);
	public void removeFriendCallback(String name);
	public void populateFriendList(String[] friends);

}
