package com.example.pl_registration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BaseActivity extends Activity {

	private static final String path = "/sdcard/";
	// private static final String path =
	// Environment.getExternalStorageDirectory().getPath();
	private static final String filename = "PL_reg";

	protected ArrayAdapter<String> adapter;
	protected ListView listView;

	protected Set<String> nameList ;
	protected void dataupdate() {
		Log.e("test", String.valueOf(this.adapter.getCount()));

		FileOutputStream fos = null;
		try {
			fos = openFileOutput(filename, MODE_PRIVATE | MODE_WORLD_READABLE
					| Context.MODE_WORLD_WRITEABLE);

			for (int i = 0; i < this.adapter.getCount(); i++) {
				fos.write(new String(this.adapter.getItem(i) + ",").getBytes());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1);
		listView.setAdapter(adapter);
		nameList = new HashSet();
		FileInputStream fis = null;
		try {
			File file = getBaseContext().getFileStreamPath(filename);
		
			if (file.exists()) {
				fis = openFileInput(filename);
				byte[] b = new byte[1024];
				int i = fis.read(b);
				if ( i > 0 ) {
					String namefile = new String(b, 0, i);
					Log.e("Init", namefile);
					String namelist[] = namefile.split(",");
					for (String name : namelist) {
						if (name != null && name.length() > 0) {
							this.adapter.add(name);
							this.nameList.add(name);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	@Override
	protected void onDestroy() {
		// 此行一定要擺最後
		super.onDestroy();
	}

}
