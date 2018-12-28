package com.example.imagepackager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;

import android.os.AsyncTask;
import android.widget.Toast;

public class all_conv extends AsyncTask<Void, Void, Void> {
    private ArrayList<String> path;
    private final String keyword = Grid.keyword;
    private String des;
    private int count = 0;
    private long size = 0;
    private String source;
    private String filename;

    protected void onPreExecute() {
        path = Grid.P;
        count = path.size();

        String p = MainActivity.destination + "/" + keyword;
        File folder = new File(p);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        des = p;

        Toast.makeText(App.getContext(), "파일 이동을 시작합니다. 시간이 오래걸릴 수도 있습니다.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Iterator<String> pos = path.iterator();


        while (pos.hasNext()) {
            String p = pos.next();
            source = p;
            String[] s = source.split("/");
            filename = s[s.length - 1];

            File f = new File(des + "/" + filename);

            // if the file is already exist
            if (f.exists())
                continue;

            FileInputStream input = null;
            FileOutputStream output = null;
            FileChannel fin;
            FileChannel fout;


            try {
                input = new FileInputStream(source);
                output = new FileOutputStream(des + "/" + filename);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // set Channel
            fin = input.getChannel();
            fout = output.getChannel();

            // transfer
            try {
                size = fin.size();
                fin.transferTo(0, size, fout);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // close
            try {
                fin.close();
                fout.close();
                input.close();
                output.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //publishProgress();

            count--;
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void param) {

        if (path.size() == 0)
            Toast.makeText(App.getContext(), "파일이 없습니다.",
                    Toast.LENGTH_SHORT).show();

        else if (count == 0)
            Toast.makeText(App.getContext(), path.size() + "개 파일 이동이 완료되었습니다.",
                    Toast.LENGTH_SHORT).show();

        else if (count == path.size())
            Toast.makeText(App.getContext(), "모두 이미 존재하는 파일입니다.",
                    Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(App.getContext(),
                    "이미 존재하는 " + count + "개 파일 제외, " + (path.size() - count) + "개 파일 이동이 완료되었습니다.",
                    Toast.LENGTH_SHORT).show();

        Grid.semaphore = true;

    }
}
