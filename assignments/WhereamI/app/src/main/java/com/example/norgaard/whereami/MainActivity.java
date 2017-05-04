package com.example.norgaard.whereami;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final int MIN_TIME_BETWEEN_UPDATES_IN_MILISECONDS = 250;
    public static final int MIN_DISTANCE_BETWEEN_UPDATES_IN_METERS = 0;

    @BindView(R.id.locationTextView)
    TextView locationTextView;

    @BindView(R.id.startButton)
    Button startButton;

    @BindView(R.id.stopButton)
    Button stopButton;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        SetupLocationManager();
        SetupLocationListener();
    }

    private void SetupLocationManager() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        GetFineLocationPermission();

        Boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGpsEnabled) {
            Toast.makeText(context, "GPS is not enabled", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean GetFineLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return true;
        }
        return false;
    }

    private void SetupLocationListener() {

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationTextView.setText(location.toString());
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
        };
    }

    @OnClick(R.id.startButton)
    public void OnStartClick() {
        Toast.makeText(this, "Start button clicked!", Toast.LENGTH_SHORT).show();

        GetFineLocationPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MIN_TIME_BETWEEN_UPDATES_IN_MILISECONDS, MIN_DISTANCE_BETWEEN_UPDATES_IN_METERS, locationListener);
    }

    @OnClick(R.id.stopButton)
    public void OnStopClick() {
        Toast.makeText(this, "Stop button clicked!", Toast.LENGTH_SHORT).show();
        locationManager.clearTestProviderLocation(locationManager.GPS_PROVIDER);
    }
}
