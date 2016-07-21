package net.frygo.findmybuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity implements OnItemSelectedListener{
	 String email="";
	 String name="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent in = new Intent();
		   in.setAction("net.frygo.findmybuddy.starter");
		    sendBroadcast(in);
		 getregisteractivity();

    		
		    	

	 
	}
	private void getregisteractivity(){
	
		final Dialog dialog = new Dialog(this);
		final String regId=GCMregistration.getId(Register.this);
		dialog.setContentView(R.layout.newaccount);
		dialog.setTitle("Register");
		Button submit = (Button) dialog.findViewById(R.id.sign_in_button);
		final EditText username = (EditText) dialog.findViewById(R.id.user_name);
		final Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Spinner spinner = (Spinner) dialog.findViewById(R.id.emailid);
		spinner.setOnItemSelectedListener(this);
		List<String> emaillist = new ArrayList<String>();
	
		try{
		       Account[] accounts = AccountManager.get(this).getAccounts();
		       for (Account account : accounts) {
		    	   if (emailPattern.matcher(account.name).matches())
		    		 emaillist.add(account.name);     
		       }
				 if(emaillist.size()==0)
		    	  {
		    		  Toast.makeText(getApplicationContext(), "Register your phone with any valid email accounts", Toast.LENGTH_LONG).show();
		    		  finish();
		    	  }
		}
	      catch(Exception e)
	      {
	      }
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, emaillist);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(dataAdapter);
		
		submit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

       		 final ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
       			final String regId=GCMregistration.getId(Register.this);	
			    nameValuePairs.add(new BasicNameValuePair("name",username.getText().toString()));
			    nameValuePairs.add(new BasicNameValuePair("email",email));
			    nameValuePairs.add(new BasicNameValuePair("regId",regId));
				GetData obj = new GetData(Register.this, "mode",nameValuePairs);
				String ur=URL.url+"register.php";						
			obj.execute(ur);
	
				

				dialog.dismiss();

			}
		});
		dialog.show();
		
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		email=parent.getItemAtPosition(position).toString();
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
