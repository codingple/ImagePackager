package com.example.imagepackager;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<String> list = new ArrayList<String>();
	final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
	final int cacheSize = maxMemory;
	public LruCache<String, Bitmap> iCache;

	// ImageAdapter
	ImageAdapter(Context c) {
		mContext = c;
		iCache = new LruCache<String, Bitmap>( cacheSize){
			@Override
			protected int sizeOf( String key, Bitmap bitmap){
				return bitmap.getByteCount() / 1024;
			}
		};
	}

	// getCount()
	public int getCount() {
		return list.size();
	}

	// getItem
	public Object getItem(int position) {
		return iCache.get(String.valueOf(position));
	}

	// getItemId
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		Iterator<Integer> iter = Grid.red.iterator();
		
		imageView = new ImageView(mContext);
		imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
        imageView.setAdjustViewBounds(false);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(10,10,10,10);
        
        // check this view is selected
        while ( iter.hasNext() ){
        	if ( iter.next() == position )
        		imageView.setBackgroundColor(Color.RED);
        }

		if (iCache.get(String.valueOf(position)) == null) {

			BitmapFactory.Options bo = new BitmapFactory.Options();
			bo.inSampleSize = 4;
			Bitmap bitmap = BitmapFactory.decodeFile(list.get(position), bo);
			Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
			iCache.put(String.valueOf(position), resized);
			imageView.setImageBitmap(resized);
		}

		else {

			Bitmap bitmap = iCache.get(String.valueOf(position));
			imageView.setImageBitmap(bitmap);
		}

		return imageView;

	}// end of getView
}