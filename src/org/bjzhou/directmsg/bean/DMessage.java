package org.bjzhou.directmsg.bean;

public class DMessage {
    
    private String id;
    private String created_at;
    private String text;
    private String sender_id;
    private String recipient_id;
    private String sender_screen_name;
    private String recipient_screen_name;
    private User sender;
    private User recipient;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getSender_id() {
        return sender_id;
    }
    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
    public String getRecipient_id() {
        return recipient_id;
    }
    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }
    public String getSender_screen_name() {
        return sender_screen_name;
    }
    public void setSender_screen_name(String sender_screen_name) {
        this.sender_screen_name = sender_screen_name;
    }
    public String getRecipient_screen_name() {
        return recipient_screen_name;
    }
    public void setRecipient_screen_name(String recipient_screen_name) {
        this.recipient_screen_name = recipient_screen_name;
    }
    public User getSender() {
        return sender;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    public User getRecipient() {
        return recipient;
    }
    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
    
    

}
