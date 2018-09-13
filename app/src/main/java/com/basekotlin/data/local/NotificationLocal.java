package com.basekotlin.data.local;

import org.parceler.Parcel;

/**
 * Created by evan on 6/14/15.
 */

@Parcel
public class NotificationLocal {
    public String type = "";
    public String message;
    public String real_amount;
    public String ship_fee;
    public String estimated_time;
    public String time;
    public String driver_avatar;
    public String driver_name;
}
