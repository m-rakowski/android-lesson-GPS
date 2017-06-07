package course.android.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView dostawca;
    private TextView dlugosc;
    private TextView szerokosc;
    private TextView sensors;

    private Criteria cr;
    private Location loc;
    private String mojDostawca;

    private LocationManager mylm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dostawca = (TextView) findViewById(R.id.textView1);
        dlugosc = (TextView) findViewById(R.id.textView2);
        szerokosc = (TextView) findViewById(R.id.textView3);
        sensors = (TextView) findViewById(R.id.sensors);


        cr = new Criteria();

        mylm = (LocationManager) getSystemService(LOCATION_SERVICE);
        mojDostawca = mylm.getBestProvider(cr, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = mylm.getLastKnownLocation(mojDostawca);

        dostawca.setText("dostawca: " + mojDostawca);
        dlugosc.setText("dlugosc geograficzna: " + loc.getLongitude());
        szerokosc.setText("szerokosc geograficzna: " + loc.getLatitude());

        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        for (Sensor sensor : manager.getSensorList(Sensor.TYPE_ALL)) {
            sensors.setText(sensors.getText() + "\n"
            +sensor);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mylm.requestLocationUpdates(mojDostawca, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mojDostawca = mylm.getBestProvider(cr, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = mylm.getLastKnownLocation(mojDostawca);
        dostawca.setText("dostawca: " + mojDostawca);
        dlugosc.setText("dlugosc geograficzna: " + loc.getLongitude());
        szerokosc.setText("szerokosc geograficzna: " + loc.getLatitude());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mylm.removeUpdates(this);
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
}
