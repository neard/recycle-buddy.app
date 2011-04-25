package net.itjustwerx.recyclebuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.*;
import android.widget.*;



public class search extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search);

		//Initialize needed widgets
		final CheckBox cbAnything = (CheckBox) findViewById(R.id.cbAny);
		final CheckBox cbPlastic = (CheckBox) findViewById(R.id.cbPlastic);
		final CheckBox cbGlass = (CheckBox) findViewById(R.id.cbGlass);
		final CheckBox cbCardboard = (CheckBox) findViewById(R.id.cbCardboard);
		final CheckBox cbPaper = (CheckBox) findViewById(R.id.cbPaper);
		final CheckBox cbAluminum = (CheckBox) findViewById(R.id.cbAluminum);
		final EditText etCityState = (EditText) findViewById(R.id.etCityState);
		final EditText etZipCode = (EditText) findViewById(R.id.etZipCode);
		final TextView tvTips = (TextView) findViewById(R.id.tvTips);
		final Button btSearch = (Button) findViewById(R.id.btSearch);

		OnClickListener oclOtherCheckBoxes = new OnClickListener() {
			@Override
			public void onClick(View v) {
				cbAnything.setChecked(false);
			}
		};
		cbAnything.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cbPlastic.setChecked(false);
				cbGlass.setChecked(false);
				cbCardboard.setChecked(false);
				cbPaper.setChecked(false);
				cbAluminum.setChecked(false);
			}
		});
		cbPlastic.setOnClickListener(oclOtherCheckBoxes);
		cbGlass.setOnClickListener(oclOtherCheckBoxes);
		cbCardboard.setOnClickListener(oclOtherCheckBoxes);
		cbPaper.setOnClickListener(oclOtherCheckBoxes);
		cbAluminum.setOnClickListener(oclOtherCheckBoxes);

		btSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent getResults = new Intent(v.getContext(), Results.class);
				getResults.putExtra("CityState", etCityState.getText().toString());
				getResults.putExtra("ZipCode", etZipCode.getText().toString());
				String checkedBoxes = "";
				if (cbAnything.isChecked()) {
					checkedBoxes = "Plastic, Cardboard, Glass, Paper, Aluminum";
				} else {
					if (cbPlastic.isChecked()) {
						checkedBoxes += "Plastic";
					}
					if (cbCardboard.isChecked()) {
						if (checkedBoxes != "")
							checkedBoxes += ",";
						checkedBoxes += "Cardboard";
					}
					if (cbGlass.isChecked()) {
						if (checkedBoxes != "")
							checkedBoxes += ",";
						checkedBoxes += "Glass";
					}
					if (cbPaper.isChecked()) {
						if (checkedBoxes != "")
							checkedBoxes += ",";
						checkedBoxes += "Paper";
					}
					if (cbAluminum.isChecked()) {
						if (checkedBoxes != "")
							checkedBoxes += ",";
						checkedBoxes += "Aluminum";
					}
				}
				getResults.putExtra("Materials", checkedBoxes);
				startActivity(getResults);
			}
		});

		//Set tips text to random tip from site.
		try {tvTips.setText(new BufferedReader(new InputStreamReader(new DefaultHttpClient().execute(new HttpGet(new URI("http://recyclebuddy.itjustwerx.net/gettip.php"))).getEntity().getContent())).readLine());} catch (IllegalStateException e1) {} catch (ClientProtocolException e) {} catch (IOException e) {} catch (URISyntaxException e) {}
		tvTips.setSelected(true);
		/*This whole block is replaced by the line above
		 * Will be modified and used for getting the listings in Results class
        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://recyclebuddy.itjustwerx.net/gettip.php"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader
            (new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            tvTips.setText(page);
            } catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {

				e.printStackTrace();
			} finally {
            if (in != null) {
                try {
                    in.close();
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}*/
	}
}