/**
	Copyright 2011 Catch.com, Inc.

   	Licensed under the Apache License, Version 2.0 (the "License");
   	you may not use this file except in compliance with the License.
   	You may obtain a copy of the License at

    	http://www.apache.org/licenses/LICENSE-2.0

   	Unless required by applicable law or agreed to in writing, software
   	distributed under the License is distributed on an "AS IS" BASIS,
   	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   	See the License for the specific language governing permissions and
   	limitations under the License
*/

package com.gardening.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FusionTableReader {
	/**
	 * This function is meant for Array of objects from the query
	 * @param Url
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static JSONArray getSearchResultsByUrl(String Url, String type) throws IOException
	{
		URL request = new URL(Url);
		String retString = Common.getContent((InputStream) request.getContent());
		
		retString = retString.substring(type.length() + 1, retString.length() - 1);
		
		Log.e("retString", retString);
		
		JSONObject jsontable = new JSONObject();
		
		try {
			jsontable = new JSONObject(retString).getJSONObject("table");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray ret = new JSONArray();
		
		try {
			for(int i = 0; i < jsontable.getJSONArray("rows").length(); i++)
			{
				ret.put(AddJSONObject(jsontable.getJSONArray("cols"), jsontable.getJSONArray("rows").getJSONArray(i)));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
	
	/**
	 * This is simply adding JSON Object to Array
	 * @param cols
	 * @param row
	 * @return
	 */
	public static JSONObject AddJSONObject(JSONArray cols, JSONArray row)
	{
		JSONObject ret = new JSONObject();
		for(int i = 0; i < cols.length(); i ++)
		{
			try {
				ret.put((String) cols.get(i), row.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}
}
