package views;

import library.IntentIntegrator;
import library.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.groupten.thecabpool.R;

import controllers.SecurityController;
import controllers.ShareController;

public class ScanCodeScreen extends FragmentActivity implements View.OnClickListener{

	Button btnScanCode;
	Button btnSubmitCode;
	static TextView lblCode;
	static String code;
	static double[] scanLocation = new double[2];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_screen);
		
		btnScanCode = (Button) findViewById(R.id.btnScanCode); 
		btnSubmitCode = (Button) findViewById(R.id.btnSubmitCode);
		lblCode = (TextView) findViewById(R.id.lblScannedCode);
		
		SecurityController controller = new SecurityController(this);

		btnScanCode.setOnClickListener(this);
		btnSubmitCode.setOnClickListener(controller);
		
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = service.getBestProvider(criteria, false);
		Location location = service.getLastKnownLocation(provider);
		LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
		scanLocation[0] = userLocation.latitude;
		scanLocation[1] = userLocation.longitude;
	}
	
	public static double[] getLocation(){
		return scanLocation;
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		  if (scanResult != null) {
		    code = scanResult.getContents().toString();
		    lblCode.setText("Code scanned, press 'Submit Code' to verify");
		  }
		  else{
			  code = "Error";
		  }
		}
	
	public static String getCode(){
		return code;
	}
	
	@Override
	public void onClick(View v) {
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}



	public static void setCode(String msg) {
		lblCode.setText(msg);		
	}

}