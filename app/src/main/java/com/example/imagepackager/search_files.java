package com.example.imagepackager;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore.Images;
import android.widget.Toast;

public class search_files extends AsyncTask<Void, Void, Void>{
    private String filename = Grid.keyword;
    private Bitmap result_bitmap;
    private int f_size = -1;
    public static Boolean semap = true;

    @Override
    protected void onPreExecute(){
        Grid.P.clear();
    }


    @Override
    protected Void doInBackground(Void... params) {
        Cursor cursor;
        String name;
        String path;

        cursor = App.contentresolver().query(Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);

        // if there are no image
        if (cursor == null) {
            Toast.makeText(App.getContext(), "기기에 이미지 파일이 없습니다.", Toast.LENGTH_SHORT)
                    .show();
        }

        else {

            cursor.moveToFirst();

            int size = cursor.getColumnCount();

            while (true) {
                // get file name
                name = cursor.getString(cursor
                        .getColumnIndex(Images.ImageColumns.DISPLAY_NAME));

                // get path
                path = cursor.getString(cursor
                        .getColumnIndex(Images.ImageColumns.DATA));

                // check the file in ImagePackager folder
                Boolean in_impack;
                String dir = path;

                if ( dir.contains(MainActivity.destination) )
                    in_impack = true;
                else
                    in_impack = false;

                // if the file is not in ImagePackager folder
                if ( !in_impack) {
                    // compare file name
                    if (name.contains(filename)) {
                        // 1. insert the path of file
                        Grid.P.add(path);

                        // 2. insert resized bitmap to cache
                        BitmapFactory.Options bo = new BitmapFactory.Options();
                        bo.inSampleSize = 4;
                        Bitmap bitmap = BitmapFactory.decodeFile(path, bo);
                        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                        result_bitmap = resized;

                        f_size++;

                        while (!semap) {
                            // waiting to finish the progress update
                        }
                        semap = false;
                        // 4. execute progress update
                        publishProgress();

                    }
                }

                // continue or finish
                if (cursor.isLast()) {
                    cursor.close();
                    break;
                }

                else
                    cursor.moveToNext();

            }// end of while
        }// end of else

        return null;
    }// end of doInBackground

    @Override
    protected void onProgressUpdate(Void... params){
        Grid.imgter.list = Grid.P;
        Grid.imgter.iCache.put(String.valueOf(f_size), result_bitmap);
        Grid.imgter.notifyDataSetChanged();
        ((Activity) App.getContext()).setTitle((f_size+1)  + " 개의 파일");
        semap = true;
    }

    @Override
    protected void onPostExecute(Void params){
        Toast.makeText(App.getContext(), "파일 찾기를 완료했습니다.", Toast.LENGTH_SHORT).show();
    }
}
