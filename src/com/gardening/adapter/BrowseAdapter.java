package com.gardening.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gardening.model.SearchResult;
import com.gardening.util.ImageLoader;
import com.maesinfo.gardening.R;

public class BrowseAdapter extends BaseAdapter {
	private JSONArray mData;
	private Activity activity;
	private LayoutInflater mInflater;
	public ImageLoader imageLoader;

	public BrowseAdapter(Activity activity, JSONArray jsonArrayResults) {
		this.mData = jsonArrayResults;
		this.activity = activity;
		mInflater = LayoutInflater.from(this.activity);
		imageLoader=new ImageLoader(activity.getApplicationContext());
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.browse_list_row, parent,
					false);
			holder = new ViewHolder();
			holder.commonName = (TextView) convertView
					.findViewById(R.id.browse_comnon_name);
			holder.titleName = (TextView) convertView
					.findViewById(R.id.browse_title);
			holder.picture = (ImageView) convertView
					.findViewById(R.id.browse_plant_image);
			holder.favorite = (ImageView) convertView
					.findViewById(R.id.browse_favorites);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		JSONObject itemData;
		String commonName = "";
		String titleName = "";
		String picUrl = "";
		try {
			itemData = (JSONObject) mData.get(position);
			commonName = itemData.getString(SearchResult.PREFERED_COMMON_NAME);
			titleName = itemData.getString(SearchResult.BOTANICAL_NAME);
			picUrl = itemData.getString(SearchResult.PHOTO_URL);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		holder.commonName.setText(commonName);
		holder.titleName.setText(titleName);
		imageLoader.displayImage(picUrl, activity, holder.picture);
		return convertView;
	}

	public static class ViewHolder {
		ImageView picture;
		TextView titleName;
		TextView commonName;
		ImageView favorite;
	}
}
