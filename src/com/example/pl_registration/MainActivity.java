package com.example.pl_registration;


import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends BaseActivity implements DialogInterface.OnClickListener, OnClickListener, OnItemLongClickListener{
	private EditText editText ;
	private int deleteID ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button button = (Button)findViewById(R.id.button);
		this.editText = (EditText)findViewById(R.id.editText);
		
		
		listView.setOnItemLongClickListener(this);
		button.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		// FIXME 這邊會新增資料
		String name = this.editText.getText().toString().trim() ;
		if ( name.length() > 0 ){
			if ( this.nameList.contains(name)){
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle("學號重複輸入！！"); //設定dialog 的title顯示內容
				dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {  
				    public void onClick(DialogInterface dialog, int which) {  
				    }  
				}); 
				dialog.show();
			}else {
				this.adapter.insert(name, 0) ;
				this.nameList.add(name);
				this.editText.setText("") ;
				this.dataupdate();
			}
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) {
		this.deleteID = (int)id ;
		
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);
		 
		// Setting Dialog Title
		alertDialog2.setTitle("請告：請由助教來協助");
		 
		// Setting Dialog Message
		alertDialog2.setMessage("你想刪除這筆學號？");
		 
		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("是",this);
		
		// Setting Negative "NO" Btn
		alertDialog2.setNegativeButton("否",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                // Write your code here to execute after dialog
		                dialog.cancel();
		            }
		        });
		 
		// Showing Alert Dialog
		alertDialog2.show();
		
		// 回傳 true 就把訊息攔下來不要傳送到後面繼續做事情
		return true;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// FIXME 這邊會刪除資料
		String name = adapter.getItem(this.deleteID)  ;
		this.nameList.remove(name);
		adapter.remove(name) ;
		this.dataupdate();
	}
}
