package net.frygo.findmybuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ContactListActivity extends Activity implements
		OnItemClickListener {
	SimpleAdapter adapter;
	private static ListView listView;
	EditText inputSearch;


	List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactlistview);

		inputSearch = (EditText) findViewById(R.id.search);
		listView = (ListView) findViewById(R.id.listview);

		listView.setOnItemClickListener(this);
		inputSearch = (EditText) findViewById(R.id.search);
		ContentResolver cr = getContentResolver();
		Cursor phones = cr.query(Email.CONTENT_URI, null, null, null, null);

		while (phones.moveToNext()) {

			String email = phones.getString(phones.getColumnIndex(Email.DATA));
			String name = phones
					.getString((phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("email", email);
			map.put("image", Integer.toString(R.drawable.profile));
			list.add(map);

		}
		
	if(!list.isEmpty()){
		String[] from = new String[] { "name", "email","image"  };
		int[] to = new int[] { R.id.tvname, R.id.tvemail, R.id.image };
		adapter = new SimpleAdapter(this, list, R.layout.item,
				from, to);
		listView.setAdapter(adapter);
		
	}
	else {
		Toast.makeText(getApplicationContext(), "No contact found", Toast.LENGTH_SHORT).show();
			//ShowToast.showToast("No contact found", this);
	}

	
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				adapter.getFilter().filter(cs.toString().trim());  	
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> listview, View v, int position,
			long id) {
		String name,email;
		Object o = listview.getItemAtPosition(position);

		String[] a;
		a=o.toString().split("email", o.toString().length());
		String[] Text=a[1].split(",", o.toString().length());
		//name Text[1]
		a=Text[1].split("=", o.toString().length());
		name=a[1].replace("}", "");
		a=Text[0].split("=", o.toString().length());
		email=a[1];
	

		String url = URL.url + "add_contact.php";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("friendemailid", email));
		nameValuePairs.add(new BasicNameValuePair("myemailid",
				new SharedPreference(this).retrievedata("email")));
		GetData obj = new GetData(this, "add_contact", nameValuePairs, name);
		obj.execute(url);
		
	}

	public void onBackPressed()
	   {
			
	      finish();
	   }
}
