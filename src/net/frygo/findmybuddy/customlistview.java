package net.frygo.findmybuddy;



import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;






import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;


public class customlistview extends Activity implements OnItemClickListener {
	public static final String ARRAY_NAME = "contact";
	
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String PHOTOURL="photourl";
	private static int RESULT_LOAD_IMAGE = 1;
	public static List<Item> arrayOfList;
	public static ListView listView;
	static NewsRowAdapter objAdapter;
	String myemail;
	static Activity custom;
	EditText inputSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent in = new Intent();
		in.setAction("net.frygo.findmybuddy.starter");
		sendBroadcast(in);		
		//if (Utils.isNetworkAvailable(this))
		{
		if(Utils.isLocationAvailable(this)){
		custom=customlistview.this;
			myemail=new SharedPreference(customlistview.this).retrievedata("email");
			if (!myemail.equalsIgnoreCase("default")){
				inputSearch=(EditText)findViewById(R.id.search);
				
				user_listview_handler db=new user_listview_handler(this);
				List<userinfo> contacts = db.getAllContacts();
				 
			
				if(contacts.size()>0)
				{
					setContentView(R.layout.listview);
					Log.e("ARUN","ARUN= 2");
					listView = (ListView) findViewById(R.id.listview);
					Log.e("ARUN","ARUN= 3");
					listView.setOnItemClickListener(this);
					Log.e("ARUN","ARUN= 4");
					 listView.setOnItemLongClickListener(new OnItemLongClickListener() {
					        @Override
					        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					                int position, long arg3) {
					        	
				        	if(!arrayOfList.isEmpty()){	
							showDeleteDialog(position);
							}
					   
				        	return false;
				        }
				    }); 
					
			        for (userinfo cn : contacts) {
			        	Log.e("ARUN","ARUN= 0");
			         Item objItem = new Item();
			         Log.e("ARUN","ARUN= 00");
						objItem.setName(cn.getName());
						Log.e("ARUN","ARUN= 000");
						objItem.setEmail(cn.getEmail());
						Log.e("ARUN","ARUN= 0000");
						objItem.setLink(cn.getImgid());
						Log.e("ARUN","ARUN= 00000");
						customlistview.arrayOfList.add(objItem);
						Log.e("ARUN","ARUN= 000000");
						Log.e("ARUN","ARUN="+String.valueOf(arrayOfList.size()));
						customlistview.setAdapterToListview();
						Log.e("ARUN","ARUN= 0000000");
					   	Log.e("ARUN","ARUN= 0000000000");
			        }
			    	Log.e("ARUN","ARUN= 1");
			  
				
			        
			        
				}
				else
				{
				
					arrayOfList = new ArrayList<Item>();	 	
			final String url = URL.url+"customlistview.php";
		 final ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		 nameValuePairs.add(new BasicNameValuePair("email_id_1",myemail));
		    GetData obj = new GetData(this,ARRAY_NAME,nameValuePairs);		
		obj.execute(url);		
	
				}
		}//end of file exists
			else {
				Intent i=new Intent(customlistview.this,Register.class);
				startActivity(i);
				finish();
				}//selectionpage
			Bundle extras = getIntent().getExtras();
			if(extras != null){
			String getnotification=extras.getString("alert");
			generatealertnotification(getnotification);
			}//extras!=null
		}	
		else 
			Utils.generatenotification(Settings.ACTION_LOCATION_SOURCE_SETTINGS,"Please enable Wireless networks and GPS satellites","Location Services is not Active",this);
		}
		//else
			//Utils.generatenotification(android.provider.Settings.ACTION_WIRELESS_SETTINGS,"Please connect your device to internet","Internet Services is not Active",this);	
	}//on create
	// My AsyncTask start...
public void generatealertnotification(final String not){
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setCancelable(false)
	
			.setPositiveButton("Add as buddy", new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 String url=URL.url+"add_friend.php";
					  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					    nameValuePairs.add(new BasicNameValuePair("friendemailid",not));
					    nameValuePairs.add(new BasicNameValuePair("myemailid",myemail));
					    GetData obj=new GetData(customlistview.this,"add_friend",nameValuePairs);
					obj.execute(url);
					
					dialog.dismiss();


				
				}
			}).setNegativeButton("Cancel", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 String url=URL.url+"unfriend.php";
					  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					  nameValuePairs.add(new BasicNameValuePair("friendemailid",not));
					    
					    Getdatawithoutpbar obj=new Getdatawithoutpbar(customlistview.this,nameValuePairs);
					obj.execute(url);
					
					dialog.dismiss();
				}
			});
	AlertDialog diag = builder.create();

	diag.setIcon(R.drawable.logo);
	String title = this.getString(R.string.app_name);
	diag.setTitle(title);
	diag.setMessage(not+" would like to add you as buddy");
	diag.show();
	
}//end of generatenotification

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(arrayOfList.isEmpty()){
				Intent i = new Intent(this, ContactListActivity.class);
				startActivity(i);
			
			}
			else {		
				//tracking file
				 final ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
				 nameValuePairs.add(new BasicNameValuePair("friendemailid",arrayOfList.get(position).getEmail()));
				    GetData obj = new GetData(this, "success",nameValuePairs);
				String ur = URL.url+"knowthelocation.php";
						
				obj.execute(ur);

				
			}
			
		
		}


		private void showDeleteDialog(final int position) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(customlistview.this);
					
			alertDialog.setTitle("Delete ??");
			alertDialog.setMessage("Are you sure to unfriend "+arrayOfList.get(position).getName());
			alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
				
   
					 String url=URL.url+"delete_friend.php";
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					    nameValuePairs.add(new BasicNameValuePair("friendemailid",arrayOfList.get(position).getEmail()));
					    nameValuePairs.add(new BasicNameValuePair("myemailid",myemail));
					    Getdatawithoutpbar obj=new Getdatawithoutpbar(customlistview.this,nameValuePairs);
													obj.execute(url);
					
					
					arrayOfList.remove(position);
					
					if(arrayOfList.isEmpty()){
						finish();
						Intent i = new Intent(customlistview.this, customlistview.class);
						startActivity(i);
					}
					else
					
					objAdapter.notifyDataSetChanged();
					
				}
				
			});
			alertDialog.show();

		}

	
	
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		public boolean onOptionsItemSelected(MenuItem item) {
			switch(item.getItemId()){
			case R.id.help:{
				Intent i = new Intent(this, Help.class);
				startActivity(i);
				break;
			}
			case R.id.add_photo:{
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				
				startActivityForResult(i, RESULT_LOAD_IMAGE);
				break;
			}
			case R.id.add_contact:{
				Intent i = new Intent(this, ContactListActivity.class);
				startActivity(i);
				break;
			
			}
			case R.id.refresh:{
				 	Intent i =this.getPackageManager()
						 .getLaunchIntentForPackage(this.getPackageName() );
						 
						 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
						 ((Activity) this).finish();
						 this.startActivity(i);
			}
			}
			return false;
		}
		 @Override
		    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    	super.onActivityResult(requestCode, resultCode, data);
		    	
				if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					Uri selectedImage = data.getData();
					String[] filePathColumn = { MediaStore.Images.Media.DATA };
					Cursor cursor = getContentResolver().query(selectedImage,
							filePathColumn, null, null, null);
					cursor.moveToFirst();

					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String picturePath = cursor.getString(columnIndex);
					cursor.close();
					
					
					
					 Bitmap bitmapOrg = BitmapFactory.decodeFile(picturePath);
					 int h = 80; // height in pixels
					 int w = 80; // width in pixels    
					 Bitmap scaled = Bitmap.createScaledBitmap(bitmapOrg, h, w, true);
					   ByteArrayOutputStream bao = new ByteArrayOutputStream();
					   scaled.compress(Bitmap.CompressFormat.JPEG, 90, bao);
					   byte [] ba = bao.toByteArray();
					   String ba1=Base64.encodeBytes(ba);
					   nameValuePairs.add(new BasicNameValuePair("image",ba1));
					    nameValuePairs.add(new BasicNameValuePair("emailid",myemail));
					   GetData obj=new GetData(customlistview.this,"upload",nameValuePairs);
					   String url=URL.url+"ImageUpload.php";
					   obj.execute(url);
				}//end of add_photo				
				}//on activity Result
	
		 
		   public void onBackPressed()
		   {
				
		      finish();
		   }
		   public static void setAdapterToListview() {
				
				objAdapter = new NewsRowAdapter(custom, R.layout.item,
						customlistview.arrayOfList);
				listView.setAdapter(objAdapter);
			}		       

}


