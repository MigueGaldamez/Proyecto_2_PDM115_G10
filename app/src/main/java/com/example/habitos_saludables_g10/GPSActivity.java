package com.example.habitos_saludables_g10;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class GPSActivity extends Activity {
    Button obtenerDir;
    EditText edtlatitud;
    EditText edtlongitud;
    EditText edtaltitud;
    TextView edtdireccion;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        obtenerDir = (Button) findViewById(R.id.btnObtenerDatosPos);
        edtlatitud = (EditText) findViewById(R.id.edtLatitud);
        edtlongitud = (EditText) findViewById(R.id.edtlongitud);
        edtaltitud = (EditText) findViewById(R.id.edtAltitud);
        edtdireccion = (TextView) findViewById(R.id.edtDireccion);
        obtenerDir.setOnClickListener(onClickDireccion);
        locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);
    }
    View.OnClickListener onClickDireccion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Geocoder g = new Geocoder(getApplicationContext());
            List<Address> ad = null;
            try {
                ad = g.getFromLocation(
                        Double.valueOf(edtlatitud.getText().toString()),
                        Double.valueOf(edtlongitud.getText().toString()), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ad != null && ad.isEmpty() == false) {
                edtdireccion.setText(ad.get(0).getThoroughfare() + ","
                        + ad.get(0).getSubAdminArea() + ","
                        + ad.get(0).getCountryName());
            }
        }
    };
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            edtlatitud.setText(String.valueOf(location.getLatitude()));
            edtlongitud.setText(String.valueOf(location.getLongitude()));
            edtaltitud.setText(String.valueOf(location.getAltitude()));
        }
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    };
    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    // int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
}
