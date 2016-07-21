package net.frygo.findmybuddy;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class user_listview_handler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "_findmybuddy";

	// Contacts table name
	private static final String TABLE_CONTACTS = "user_info";

	// Contacts Table Columns names
	private static final String USER_ID = "id";
	private static final String USER_NAME = "name";
	private static final String USER_EMAIL = "email";
	private static final String USER_REGID = "regid";
	private static final String USER_IMGID = "imgid";

	public user_listview_handler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " TEXT," + USER_EMAIL + " TEXT,"
				+USER_REGID + " TEXT,"
				+ USER_IMGID + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addContact(userinfo user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_NAME, user.getName()); 
		values.put(USER_EMAIL, user.getEmail()); 
		values.put(USER_IMGID,user.getImgid());
		values.put(USER_REGID,user.getRegid());

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting All Contacts
	public List<userinfo> getAllContacts() {
		List<userinfo> contactList = new ArrayList<userinfo>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				userinfo contact = new userinfo();

				contact.setName(cursor.getString(1));
				contact.setEmail(cursor.getString(2));
				contact.setRegid(cursor.getString(3));
				contact.setImgid(cursor.getString(4));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	// Updating single contact
	public int updateContact(userinfo contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_NAME, contact.getName());
		values.put(USER_EMAIL, contact.getName());
		values.put(USER_REGID, contact.getRegid());
		values.put(USER_IMGID, contact.getImgid());
		// updating row
		return db.update(TABLE_CONTACTS, values, USER_ID + " = ?",
				new String[] { String.valueOf(contact.getEmail()) });
	}

	// Deleting single contact
	public void deleteContact(userinfo contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, USER_EMAIL + " = ?",
				new String[] { String.valueOf(contact.getEmail()) });
		db.close();
	}


	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
