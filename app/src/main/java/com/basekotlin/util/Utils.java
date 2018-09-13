package com.basekotlin.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.MailTo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.basekotlin.R;
import com.basekotlin.define.MyApplication;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.HttpException;

public abstract class Utils {
    private static int screenWidth = 0;
    private static ArrayList<Polyline> polylines;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static void hideSoftKeyboard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Activity context, View aView) {
        new Handler().postDelayed(() -> {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(aView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }, 300);
    }

    public static void hideKeyboard(Activity context) {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static boolean isCheckShowSoftKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    //CHECK EMAIL IS VALID
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

//    public static void loadAvatarFromURL(Context context, String image_path, CircleImageView imageView) {
//        if (image_path == null || image_path.isEmpty()) {
//            Picasso.with(context).load(R.drawable.ic_avatar_upload).into(imageView);
//            return;
//        }
//        try {
//            Picasso.with(context).load(image_path)
//                    .placeholder(R.drawable.ic_avatar_upload)
//                    .error(R.drawable.ic_avatar_upload).fit().centerInside().into(imageView);
//        } catch (IllegalArgumentException ex) {
//
//        }
//    }
//
//    public static void loadAvatarFromURL(Context context, String image_path, RoundedImageView imageView) {
//        if (image_path == null || image_path.isEmpty()) {
//            Picasso.with(context).load(R.drawable.ic_avatar_upload).into(imageView);
//            return;
//        }
//        try {
//            Picasso.with(context).load(image_path)
//                    .placeholder(R.drawable.ic_avatar_upload)
//                    .error(R.drawable.ic_avatar_upload).fit().centerInside().into(imageView);
//        } catch (IllegalArgumentException ex) {
//
//        }
//    }
//
//    public static void loadImageFromURL(Context context, String image_path, ImageView imageView) {
//        if (image_path == null || image_path.isEmpty()) return;
//        Picasso.with(context).load(image_path)
//                .placeholder(R.color.color_gray09)
//                .error(R.color.color_gray09).fit().centerInside().into(imageView);
//    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isEmailFormat(String emailString) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches();
    }

    public static void setTimeOutDialog(Dialog dialog, int time_out) {
        Handler h = new Handler();
        h.postDelayed(() -> dialog.dismiss(), time_out);
    }

    public static String getDurationVideo(Context context, Uri uri) {
        MediaPlayer mp = MediaPlayer.create(context, uri);
        int duration = mp.getDuration();
        mp.release();
        return String.format("%d", TimeUnit.MILLISECONDS.toSeconds(duration));
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static double getDistanceLocation(Location source, Location destination) {
        return (source.distanceTo(destination)) * 0.000621371192;
    }

    private static String getAdressFromLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> list_address;
        String address = "";
        try {
            geocoder = new Geocoder(context, Locale.getDefault());
            list_address = geocoder.getFromLocation(latitude, longitude, 1);
            if (list_address != null && list_address.size() > 0) {
                address = list_address.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static String getCityFromLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> list_address;
        String city = "";
        try {
            geocoder = new Geocoder(context, Locale.getDefault());
            list_address = geocoder.getFromLocation(latitude, longitude, 1);
            if (list_address != null && list_address.size() > 0) {
                city = list_address.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    public static class MyWebViewClient extends WebViewClient {
        private final WeakReference<Activity> m_activityRef;
        private ProgressBar m_progressBar;
        private WebView m_webView;

        public MyWebViewClient(Activity activity, WebView webView, ProgressBar progressBar) {
            m_activityRef = new WeakReference<>(activity);
            this.m_progressBar = progressBar;
            this.m_webView = webView;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            m_progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            m_progressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("tel:")) {
                final Activity activity = m_activityRef.get();
                if (activity != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    activity.startActivity(intent);
                    view.reload();
                    return true;
                }
            }

            if (url.startsWith("mailto:")) {
                final Activity activity = m_activityRef.get();
                if (activity != null) {
                    MailTo mt = MailTo.parse(url);
                    Intent i = newEmailIntent(activity, mt.getTo(), mt.getSubject(), mt.getBody(), mt.getCc());
                    activity.startActivity(i);
                    view.reload();
                    return true;
                }
            } else {
                view.loadUrl(url);
            }
            return true;
        }


        private Intent newEmailIntent(Context context, String address, String subject, String body, String cc) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", address, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            return emailIntent;
        }
    }

    public static Bitmap getBitmapFromUrl(String link) {
        try {
            URL url = new URL(link);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bitmap;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    private static double latRad(double lat) {
        double sin = Math.sin(lat * Math.PI / 180);
        double radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
        return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
    }

    private static double zoom(double mapPx, double worldPx, double fraction) {
        final double LN2 = .693147180559945309417;
        return (Math.log(mapPx / worldPx / fraction) / LN2);
    }

    public static Bitmap resizeMapIcons(Context context, int width, int height, String image_name) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(),
                context.getResources().getIdentifier(image_name, "drawable", context.getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public static int getSoftButtonsBarSizePort(Activity activity) {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    public static void checkSoftButtonsBar(Activity activity) {
        int height = Utils.getSoftButtonsBarSizePort(activity);
        if (height == 0) return;
        Window w = activity.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public static void setFitsSystemWindows(Activity activity, View view) {
        if (Utils.getSoftButtonsBarSizePort(activity) > 0) {
            view.setFitsSystemWindows(true);
        }
    }

    public static void showToast(String content) {
        StyleableToast styleableToast = new StyleableToast
                .Builder(MyApplication.s_instance)
                .text(content)
                .textColor(Color.WHITE)
                .strokeWidth(2)
                .duration(Toast.LENGTH_SHORT)
                .strokeColor(Color.WHITE)
                .backgroundColor(Color.parseColor("#e7432b"))
                .build();
        styleableToast.show();
    }

    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "02:00:00:00:00:00";
    }

    public static List<String> getUserLocation(Context context) {
        GPSTrackerUtils gpsTracker = new GPSTrackerUtils(context);
        if (!gpsTracker.canGetLocation()) {
            Utils.showToast(context.getString(R.string.turn_on_gps));
            return null;
        }
        // ARRAY LOCATIOS INCLUDE 3 ITEM: LATITUDE (0), LONGITUDE (1), ADDRESS (2).
        List<String> locations = new ArrayList<>();
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        String userAddress = getAdressFromLocation(context, latitude, longitude);
        locations.add(String.valueOf(latitude));
        locations.add(String.valueOf(longitude));
        locations.add(userAddress);
        return locations;
    }

    public static List<String> getInfoMyLocation(Context context, String address) {
        String streetName = "", stateName = "", stateCode = "", cityName = "", zipCode = "";
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        List<String> locations = new ArrayList<>();
        try {
            addresses = gcd.getFromLocationName(address, 1);
            streetName = addresses.get(0).getFeatureName() + " " + addresses.get(0).getThoroughfare();
            stateCode = getUSStateCode(addresses.get(0));
            stateName = addresses.get(0).getAdminArea();
            cityName = addresses.get(0).getLocality();
            zipCode = addresses.get(0).getPostalCode();

            if (streetName == null || streetName.isEmpty() || streetName.contains("null") ||
                    stateName == null || stateName.isEmpty() || stateName.contains("null") ||
                    cityName == null || cityName.isEmpty() || cityName.contains("null") ||
                    zipCode == null || zipCode.isEmpty() || zipCode.contains("null")) {
                return locations;
            } else {
                locations.add(streetName);
                locations.add(cityName);
                locations.add(stateName);
                locations.add(zipCode);
                locations.add(stateCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public static String getUSStateCode(Address USAddress) {
        String fullAddress = "";
        for (int j = 0; j <= USAddress.getMaxAddressLineIndex(); j++)
            if (USAddress.getAddressLine(j) != null)
                fullAddress = fullAddress + " " + USAddress.getAddressLine(j);

        String stateCode = null;
        Pattern pattern = Pattern.compile(" [A-Z]{2} ");
        String helper = fullAddress.toUpperCase().substring(0, fullAddress.toUpperCase().indexOf("USA"));
        Matcher matcher = pattern.matcher(helper);
        while (matcher.find())
            stateCode = matcher.group().trim();

        return stateCode;
    }

    public static List<String> getInfoMyLocation(Context context, Double myLocationLat, Double myLocationLon) {
        String streetName = "", stateName = "", cityName = "", zipCode = "";
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        List<String> locations = new ArrayList<>();
        try {
            addresses = gcd.getFromLocation(myLocationLat, myLocationLon, 1);
            streetName = addresses.get(0).getFeatureName() + " " + addresses.get(0).getThoroughfare();
            stateName = addresses.get(0).getAdminArea();
            cityName = addresses.get(0).getLocality();
            zipCode = addresses.get(0).getPostalCode();
            locations.add(streetName);
            locations.add(stateName);
            locations.add(cityName);
            locations.add(zipCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public static void showMessageError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            try {
                String response = ((HttpException) throwable).response().errorBody().string();
                JSONObject jsonObject = new JSONObject(response);
                String message = jsonObject.getString("message");
                if (jsonObject.has("users")) {
                    String user = jsonObject.getString("users");
                    if (user == null || user.isEmpty() || user.equals("null")) {
                        Utils.showToast(message);
                        return;
                    }
                    JSONObject jsonObjectUser = new JSONObject(user);
                    String email = jsonObjectUser.getString("email").replace("[", "").replace("]", "");
                    Utils.showToast(email);
                    return;
                }
                Utils.showToast(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void requestMapDirection(Context context, GoogleMap map, LatLng origin, LatLng destination) {

        polylines = new ArrayList<>();
        GoogleDirection.withServerKey("AIzaSyBrcGYs91PQQO0et4HgOKtlTIEnKEOmFHk")
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0)
                                    .getLegList().get(0).getDirectionPoint();
                            polylines.add(map.addPolyline(DirectionConverter.createPolyline
                                    (context, directionPositionList, 5, Color.RED)));
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(origin);
                            builder.include(destination);
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Utils.showToast(t.getMessage());
                    }
                });
    }

    public static int getBoundsZoomLevel(LatLng northeast, LatLng southwest,
                                         int width, int height) {
        final int GLOBE_WIDTH = 256; // a constant in Google's map projection
        final int ZOOM_MAX = 21;
        double latFraction = (latRad(northeast.latitude) - latRad(southwest.latitude)) / Math.PI;
        double lngDiff = northeast.longitude - southwest.longitude;
        double lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;
        double latZoom = zoom(height, GLOBE_WIDTH, latFraction);
        double lngZoom = zoom(width, GLOBE_WIDTH, lngFraction);
        double zoom = Math.min(Math.min(latZoom, lngZoom), ZOOM_MAX);
        return (int) (zoom - 2);
    }

    public static List<String> getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        List<String> locations = new ArrayList<>();
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            locations.add(String.valueOf(location.getLatitude()));
            locations.add(String.valueOf(location.getLongitude()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public static void disableSoftInputFromAppearing(EditText editText) {
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
    }

    public static String formatDateTimeLocal(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date d = null;
        try {
            d = sdf.parse(datetime);
            sdf.applyPattern("MM/dd/yyyy hh:mm");
            String datetimeFormat = sdf.format(d);
            List<String> result = convertUTCToLocalTime(Integer.parseInt(datetimeFormat.substring(11, 13)));
            if (result.get(0).equals("change day")) {
                int day = Integer.parseInt(datetimeFormat.substring(3, 5)) + 1;
                String dayChange = day < 10 ? String.valueOf("0" + day) : String.valueOf(day);
                datetimeFormat = datetimeFormat.substring(0, 3) + dayChange + datetimeFormat.substring(5, 16);
            }
            String hourLocal = result.get(1);
            datetimeFormat = datetimeFormat.substring(0, 11) + hourLocal + datetimeFormat.substring(13, 16);
            return datetimeFormat;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> convertUTCToLocalTime(int hourOfUTC) {
        List<String> result = new ArrayList<>();
        int offsetUTC = (int) getOffsetUTC();
        int hourOfLocalTime;
        if (hourOfUTC + offsetUTC < 24) {
            hourOfLocalTime = hourOfUTC + offsetUTC;
            result.add("not change day");
        } else {
            hourOfLocalTime = (offsetUTC + hourOfUTC) - 24;
            result.add("change day");
        }
        if (hourOfLocalTime < 10) result.add(String.valueOf("0" + hourOfLocalTime));
        else result.add(String.valueOf(hourOfLocalTime));
        return result;
    }

    public static String convertLocalTimeToUTC(int hourOfLocalTime) {
        int offsetUTC = (int) getOffsetUTC();
        int hourUTC;
        if (hourOfLocalTime >= offsetUTC) {
            hourUTC = hourOfLocalTime - offsetUTC;
        } else {
            int hourDebit = offsetUTC - hourOfLocalTime;
            hourUTC = 24 - hourDebit;
        }
        if (hourUTC < 10) return String.valueOf("0" + hourUTC);
        return String.valueOf(hourUTC);
    }

    public static String convertUTCToWorkingTime(String time24) {
        try {
            int offsetUTC = (int) getOffsetUTC();
            int hourUTC = Integer.parseInt(time24.substring(0, 2)) + offsetUTC;
            String hourLocal = hourUTC < 10 ? "0" + hourUTC : String.valueOf(hourUTC);
            time24 = hourLocal + time24.substring(2, 8);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date date = sdf.parse(time24);
            return new SimpleDateFormat("hh:mm aa").format(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static double getOffsetUTC() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        return tz.getOffset(now.getTime()) / 3600000.0;
    }
}

