package com.hisdu.SESCluster.objects;

/**
 * Created by evento on 8/30/2015.
 */
public class MenuObject {
    private String title;
//    private int iconId;
//    private int iconActiveId;
//    private int notificationCount;

    public MenuObject(String title/*, int iconId, int iconActiveId, int notificationCount*/) {
        this.title = title;
//        this.iconId = iconId;
//        this.iconActiveId = iconActiveId;
//        this.notificationCount = notificationCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//
//    public int getIconId() {
//        return iconId;
//    }
//
//    public void setIconId(int iconId) {
//        this.iconId = iconId;
//    }
//
//    public int getIconActiveId() {
//        return iconActiveId;
//    }
//
//    public void setIconActiveId(int iconActiveId) {
//        this.iconActiveId = iconActiveId;
//    }
//
//    public int getNotificationCount() {
//        return notificationCount;
//    }
//
//    public void setNotificationCount(int notificationCount) {
//        this.notificationCount = notificationCount;
//    }
}
