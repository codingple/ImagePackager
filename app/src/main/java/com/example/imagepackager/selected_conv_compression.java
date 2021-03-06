package com.example.imagepackager;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class selected_conv_compression extends AsyncTask<Void, Void, Void> {
    private ArrayList<String> path;
    private final String keyword = Grid.keyword;
    private String des;
    private ArrayList<Integer> positions;
    private int count = 0;
    private int tmp = 1;

    protected void onPreExecute() {
        path = Grid.P;
        positions = Grid.red;

        count = positions.size();
        String p = MainActivity.destination + "/" + keyword;
        File folder = new File(p);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        des = p;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Iterator<Integer> pos = positions.iterator();
        File f = new File(des + "/" + keyword + ".zip");
        FileOutputStream os = null;

        // if the file is already exist
        while ( f.exists() ){
            f = new File(des + "/" + keyword + "(" + tmp + ")" + ".zip");
            tmp++;
        }

        // create compression stream
        try {
            os = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(os));

        // create zip file
        while (pos.hasNext()) {
            byte[] buffer = new byte[1024];
            int k = pos.next();
            String p = path.get(k);
            File source = new File(p);
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(source);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ZipEntry input = new ZipEntry(source.getName());
            int length = 0;

            try {
                zos.putNextEntry(input);
                while((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                fis.close();
                zos.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }

            count--;
        }

        try {
            zos.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void param) {

        if (path.size() == 0)
            Toast.makeText(App.getContext(), "파일이 없습니다.",
                    Toast.LENGTH_SHORT).show();

        else if (count == 0)
            Toast.makeText(App.getContext(), positions.size() + "개 파일 압축이 완료되었습니다.",
                    Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(App.getContext(),
                    "파일 압축 에러.",
                    Toast.LENGTH_SHORT).show();

        // init red
        Grid.red.clear();
        Grid.imgter.notifyDataSetChanged();
        Grid.semaphore = true;

    }
}
