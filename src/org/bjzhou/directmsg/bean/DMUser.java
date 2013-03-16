package org.bjzhou.directmsg.bean;


public class DMUser {
    
    private User user;
    private DMessage direct_message;
    private int unread_count;
    
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public DMessage getDirect_message() {
        return direct_message;
    }
    public void setDirect_message(DMessage direct_message) {
        this.direct_message = direct_message;
    }
    public int getUnread_count() {
        return unread_count;
    }
    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }
    
    

}
