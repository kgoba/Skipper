package com.kgoba.skipper;

import com.kgoba.skipper.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.modules.GEMFFileArchive;
import org.osmdroid.tileprovider.modules.MapTileFilesystemProvider;
import org.osmdroid.tileprovider.modules.TileWriter;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.TilesOverlay;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    private final String TAG = "Skipper";

    WeatherProvider mWeatherProvider;


    private MapView mMapView;
    private LocationManager mLocationManager;
    private float mLastLatitude;
    private float mLastLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mWeatherProvider = new WeatherProvider();

        mMapView = createMapView();
        RelativeLayout contentView = (RelativeLayout)findViewById(R.id.mainView);
        contentView.addView(mMapView);

        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // request updates from all enabled location providers
        for (String provider: mLocationManager.getProviders(true))
        {
            Log.v(TAG, "Enabled provider: " + provider);
            LocationListener locationListener = new MyLocationListener();
            mLocationManager.requestLocationUpdates(provider, 1000L, 1f, locationListener);
        }

        // set initial position (delayed to fix MapView issues)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("global", Context.MODE_PRIVATE);
                float latitude = prefs.getFloat("lastLatitude", 56.95f);
                float longitude = prefs.getFloat("lastLongitude", 24.1f);
                int lastZoom = prefs.getInt("lastZoomLevel", 11);

                mMapView.getController().setZoom(lastZoom);

                Location lastKnownLocation = new Location("test");
                lastKnownLocation.setLatitude(latitude);
                lastKnownLocation.setLongitude(longitude);
                updateLoc(lastKnownLocation);
            }
        }, 200);
    }

    @Override
    protected void onStop() {
        SharedPreferences prefs = getSharedPreferences("global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        IGeoPoint p = mMapView.getMapCenter();
        editor.putFloat("lastLatitude", (float)p.getLatitude());
        editor.putFloat("lastLongitude", (float)p.getLongitude());
        editor.putInt("lastZoomLevel", mMapView.getZoomLevel());
        editor.apply();

        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        //mLocationManager.removeUpdates(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_test:
                Thread thread = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            //Your code goes here
                            WeatherProvider.WeatherForecast forecast = mWeatherProvider.doStuff();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateLoc(Location loc) {
        GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());

        mLastLatitude = (float)loc.getLatitude();
        mLastLongitude = (float)loc.getLongitude();

        Log.v(TAG, "Location: " + String.valueOf(mLastLatitude) + " " + String.valueOf(mLastLongitude));

        mMapView.getController().setCenter(locGeoPoint);
        mMapView.invalidate();
    }

    private MapView createMapView() {
        /*
        final IRegisterReceiver registerReceiver = new SimpleRegisterReceiver(this);

        // Create a custom tile source
        String[] baseURL = { "http://tile.openstreetmap.org/" };
        final ITileSource tileSource = new XYTileSource(
                "Mapnik", ResourceProxy.string.mapnik, 1, 18, 256, ".png", baseURL);

        // Create a file cache modular provider
        final TileWriter tileWriter = new TileWriter();
        final MapTileFilesystemProvider fileSystemProvider = new MapTileFilesystemProvider(
                registerReceiver, tileSource);
        */

        ResourceProxyImpl resourceProxy = new ResourceProxyImpl(getApplicationContext());
        MapView mapView = new MapView(this, 256, resourceProxy);

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        //mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // The tile source that provides the seamarks
        OnlineTileSourceBase seamarks = new XYTileSource("seamarks", null, 0, 17, 256, ".png", new String[] { "http://tiles.openseamap.org/seamark/" } );

        // We create a new overlay based on the provider
        TilesOverlay seamarksOverlay = new TilesOverlay(new MapTileProviderBasic(this, seamarks), this);

        // We want it to be transparent while its still loading
        seamarksOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);

        // Add the overlay to the mapView
        mapView.getOverlays().add(seamarksOverlay);

        return mapView;
    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            FullscreenActivity.this.updateLoc(location);
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.v(TAG, "Status changed: " + s);
        }

        public void onProviderEnabled(String s) {
            Log.e(TAG, "PROVIDER ENABLED: " + s);
        }

        public void onProviderDisabled(String s) {
            Log.e(TAG, "PROVIDER DISABLED: " + s);
        }
    }

}
