package com.example.imagepackager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PictureExpand extends Activity {
	
	ImageView imageview;
	Bitmap bitmap;
	String FolderName;
	String pos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pictureexpand);
	
		Intent intent = getIntent();
		pos = intent.getExtras().get("filename").toString();
		FolderName = intent.getExtras().get("FolderName").toString();
		
		String[] Address = pos.split("/");
		String FileName = Address[Address.length - 1];
		this.setTitle(FileName);
		
		imageview = (ImageView)findViewById(R.id.ImageView);
	
		Uri uri = Uri.parse(pos);
		imageview.setImageURI(uri);
	
		imageview.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.expandmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int ID = item.getItemId();
		
		switch(ID) {
		
		case R.id.ChangeName :
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			
			alert.setTitle("파일 이름 입력");
			alert.setMessage("변경하고 싶은 이름을 입력해 주십시오.");
			
			final EditText input = new EditText(this);
			alert.setView(input);
			
			alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String InputValue = input.getText().toString();
					String[] SplitAddress = pos.split("/");
					String FileName = SplitAddress[SplitAddress.length - 1];
					
					SplitAddress = pos.split(FileName);
					
					File filePre = new File(pos);
					File fileNow = new File(SplitAddress[0] + "/" + InputValue);
					
					if(filePre.renameTo(fileNow)) {
						Toast.makeText(PictureExpand.this, "변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
						PictureExpand.this.setTitle(InputValue);
					} else {
						Toast.makeText(PictureExpand.this, "변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
					}
			});
			
			alert.show();
			break;
			
		case R.id.MovePicture :
			String Path = MainActivity.destination;
			String Path2 = Path + "/" + FolderName;
			
			String[] SplitAddress = pos.split("/");
			String FileName = SplitAddress[SplitAddress.length - 1];
			
			File file = new File(Path);
			File file2 = new File(Path2);
			
			if(!file.exists()) {
				file.mkdirs();
			}
			
			if(!file2.exists()) {
				file2.mkdirs();
			}
			
			try {
				InputStream in = new FileInputStream(pos);
				OutputStream out = new FileOutputStream(Path2 + "/" + FileName);
				byte[] buffer = new byte[1024 * 8];
				
				while(true) {
					int count = in.read(buffer);
					
					if(count == -1) {
						break;
					}
					out.write(buffer, 0, count);
				}
			} catch(Exception e) {
				
			}
			Toast.makeText(PictureExpand.this, FileName + "이 " + Path2 + "에 복사되었습니다.", Toast.LENGTH_LONG).show();
			break;
			
		case R.id.FacebookShare :
			break;
			
		case R.id.PictureInfo :
			break;
		}
		
		return true;
	}
}
