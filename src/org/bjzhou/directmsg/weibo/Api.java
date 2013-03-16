package org.bjzhou.directmsg.weibo;

public class Api {
    
    public static final String URL_SINA_WEIBO = "https://api.weibo.com/2/";
    
    public static final String OAUTH2_ACCESS_TOKEN = URL_SINA_WEIBO + "oauth2/access_token";
    
    public static final String FRIENDS_LIST_BYID = URL_SINA_WEIBO + "friendships/friends.json";
    public static final String USER_BYID = URL_SINA_WEIBO + "users/show.json";
    public static final String AT_USER = URL_SINA_WEIBO + "search/suggestions/at_users.json";
    public static final String SEARCH_USERS = URL_SINA_WEIBO + "search/suggestions/users.json";
    
    public static final String UNREAD_COUNT = URL_SINA_WEIBO + "remind/unread_count.json";
    public static final String UNREAD_CLEAR = URL_SINA_WEIBO + "remind/set_count.json";
    
    public static final String DM_RECEIVED = URL_SINA_WEIBO + "direct_messages.json";
    public static final String DM_SENT = URL_SINA_WEIBO + "direct_messages/sent.json";
    public static final String DM_USERLIST = URL_SINA_WEIBO + "direct_messages/user_list.json";
    public static final String DM_CONVERSATION = URL_SINA_WEIBO + "direct_messages/conversation.json";
    public static final String DM_CREATE = URL_SINA_WEIBO + "direct_messages/new.json";
    public static final String DM_DESTROY = URL_SINA_WEIBO + "direct_messages/destroy.json";
    public static final String DM_BATH_DESTROY = URL_SINA_WEIBO + "direct_messages/destroy_batch";

}
