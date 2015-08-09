package com.traylz.traylzmix;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import static com.traylz.traylzmix.CoordArray.dist;
import static com.traylz.traylzmix.CoordArray.testArray;

public class MapperActivity extends FragmentActivity
        implements OnMapReadyCallback, LocationListener {

    private LocationManager locMan;
    private String prov;
    private GoogleMap map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Trail Mapper");
        setContentView(R.layout.activity_mapper);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria crit = new Criteria();
        prov = locMan.getBestProvider(crit, false);
        Location location = locMan.getLastKnownLocation(prov);

        if (location != null) {
            toastShit("Begin your trail now!", true);
            onLocationChanged(location);
        } else {
            toastShit("Activate GPS to start trailing!", false);
        }
    }

    public void toastShit(String msg, boolean length) {
        Context context = getApplicationContext();
        Toast.makeText(context, msg, length ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.moveCamera(CameraUpdateFactory.zoomTo(18.0F));
        this.map = map;
        //map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        updatePoints();
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locMan.requestLocationUpdates(prov, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locMan.removeUpdates(this);
    }

    private static double lastLat = -9001;
    private static double lastLng = -9001;

    @Override
    public void onLocationChanged(Location location) {
        double d1 = location.getLatitude();
        double d2 = location.getLongitude();
        if (map != null) {
            LatLng pos = new LatLng(d1, d2);
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLng(pos));
        }
        if (testArray.size() == 0) {
            lastLat = -9001;
            lastLng = -9001;
        }
        // Dist tolerance is the distance to travel before adding a trail line.
        if (0.0002 <= dist(d1, d2, lastLat, lastLng)) {
            testArray.addCoordinate(d1, d2);
            lastLat = d1;
            lastLng = d2;
            updatePoints();
        } else
            updatePoints();
    }

    public void updatePoints() {
        if (map == null)
            return;
        double[][] points = testArray.getCoordinates();
        int len = testArray.size() - 1;
        for (int i = 0; i < len; i++) {
            double d1 = points[i][0];
            double d2 = points[i][1];
            double d3 = points[i + 1][0];
            double d4 = points[i + 1][1];
            map.addPolyline(new PolylineOptions()
                    .add(new LatLng(d1, d2), new LatLng(d3, d4))
                    .width(5)
                    .color(Color.RED));
        }
    }

    public void finishTrailing(View view) {
        Intent intent = new Intent(this, UploaderActivity.class);
        startActivity(intent);
    }

    public void cancelTrailing(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        testArray.clear();
        toastShit("Trailing cancelled.", false);
        startActivity(intent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
