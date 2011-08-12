package com.maesinfo.gardening;

import java.io.IOException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.gardening.adapter.BrowseAdapter;
import com.gardening.model.SearchResult;
import com.gardening.util.FusionTableReader;

public class BrowseActivity extends ListActivity {

	ImageButton b1, b2, b3, b4, b5, b6, b7;
	ListView list;
	BrowseAdapter browseAdapter;
	JSONArray mResearchData = new JSONArray();;
	Handler handler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_host_browse_view);
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					setBrowseAdapter();
					break;
				default:
					break;
				}
			}

		};

		getWidgets();
		String strUrl = getResources().getString(R.string.test_query_str1);
		new LoadDataFromWeb().execute(strUrl);

		onClick_done(b1);

		// try {
		// test();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// String strQuery =
		// "SELECT Botanical Name, Prefered Common Name, URL FROM 1257668";
//		String strQuery = "SELECT URL FROM 1257668";
		// getDataFromWeb(strQuery);
	}

	private void setBrowseAdapter() {
		browseAdapter = new BrowseAdapter(BrowseActivity.this,
				BrowseActivity.this.mResearchData);
		list.setAdapter(browseAdapter);
	}

	private void getWidgets() {
		b1 = (ImageButton) this.findViewById(R.id.imageButton0);
		b2 = (ImageButton) this.findViewById(R.id.imageButton1);
		b3 = (ImageButton) this.findViewById(R.id.imageButton2);
		b4 = (ImageButton) this.findViewById(R.id.imageButton3);
		b5 = (ImageButton) this.findViewById(R.id.imageButton4);
		b6 = (ImageButton) this.findViewById(R.id.imageButton5);
		b7 = (ImageButton) this.findViewById(R.id.imageButton6);
		list = this.getListView();
	}

	public void onClick_done(View targets) {
		setButtonsDefault();
		// setButtonsDefault1();
		setButtonBackground(targets);
	}

	private void setButtonBackground(View v) {
		// TODO Auto-generated method stub
		ImageButton ib = (ImageButton) v;
		ib.setBackgroundResource(R.drawable.imagebutton_selected);

	}

	// reset buttons default
	private void setButtonsDefault() {
		b1.setBackgroundResource(R.drawable.imagebutton_default);
		// b1.setBackgroundResource(R.drawable.button_selector);
		b2.setBackgroundResource(R.drawable.imagebutton_default);
		b3.setBackgroundResource(R.drawable.imagebutton_default);
		b4.setBackgroundResource(R.drawable.imagebutton_default);
		b5.setBackgroundResource(R.drawable.imagebutton_default);
		b6.setBackgroundResource(R.drawable.imagebutton_default);
		b7.setBackgroundResource(R.drawable.imagebutton_default);
	}

	private void setButtonsDefault1() {
		b1.setBackgroundResource(R.drawable.button_selector);
		b2.setBackgroundResource(R.drawable.button_selector);
		b3.setBackgroundResource(R.drawable.button_selector);
		b4.setBackgroundResource(R.drawable.button_selector);
		b5.setBackgroundResource(R.drawable.button_selector);
		b6.setBackgroundResource(R.drawable.button_selector);
		b7.setBackgroundResource(R.drawable.button_selector);
	}

	private JSONArray getDataFromWeb(String strQuery) {
		// String strUrl =
		// "https://www.google.com/fusiontables/api/query?sql=SELECT+SpeakerId%2C+EventId%2C+SpeakerOrder%2C+FirstName%2C+LastName%2C+Title%2C+Topic%2C+Facebook%2C+Email%2C+Website%2C+Twitter%2C+Description%2C+PhotoUrl%2C+ScheduleDate%2C+Session+FROM+1103218+WHERE+EventId%3D2+ORDER%20BY+LastName&jsonCallback=speakers";
		// String strType = "speakers";
		// JSONArray collection =
		// FusionTableReader.getSearchResultsByUrl(strUrl,
		// strType);

		SearchResult.fusionTablesQuery = SearchResult.FUSIONTABLES_URI
				+ URLEncoder.encode(strQuery) + SearchResult.JSON_CALLBACK;

		JSONArray result = null;
		try {
			result = FusionTableReader.getSearchResultsByUrl(
					SearchResult.fusionTablesQuery,
					SearchResult.JSON_CALLBACK_TYPE);
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public void test() throws IOException, JSONException {

		String strUrl = getResources().getString(R.string.test_query_str1);
		JSONArray jsonArray = FusionTableReader.getSearchResultsByUrl(strUrl
				+ "test", "test");

		System.out.println(jsonArray);
	}

	class LoadDataFromWeb extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Toast.makeText(BrowseActivity.this, "downloading...",
					Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (!result) {
				Toast.makeText(BrowseActivity.this, "Exception",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(BrowseActivity.this, "Success",
						Toast.LENGTH_LONG).show();
			}
			BrowseActivity.this.handler.sendEmptyMessage(1);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				BrowseActivity.this.mResearchData = FusionTableReader
						.getSearchResultsByUrl(params[0] + "test", "test");
				// browseAdapter = new BrowseAdapter(BrowseActivity.this,
				// BrowseActivity.this.mResearchData);
				// list.setAdapter(browseAdapter);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
}