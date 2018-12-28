package com.example.imagepackager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid extends Activity {
    public static ArrayList<String> P = new ArrayList<String>();
    public static ArrayList<Integer> red = new ArrayList<Integer>();
    public static Boolean lock = true;
    public static String keyword;
    public static ImageAdapter imgter;
    public static Boolean semaphore;
    private static final int SELECTED_COMPRESSION = 0;
    private static final int ALL_COMPRESSION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        GridView gridview = (GridView) findViewById(R.id.Grid);
        red.clear();
        semaphore = true;

        // save context of Gridview
        App.setContext(this);
        imgter = new ImageAdapter(Grid.this);

        // get file name
        Bundle intent = getIntent().getExtras();
        keyword = intent.getString("keyword");

        // find files and set gridview adapter
        search_files searching = new search_files();
        searching.execute(null, null, null);
        gridview.setAdapter(imgter);

        // click and expand
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Iterator<Integer> iter = red.iterator();
                Boolean check = true;

                // check if it is selected
                while (iter.hasNext()) {
                    if (iter.next() == position) {
                        check = false;
                        break;
                    }
                }

                // not selected
                if (check && lock) {
                    Intent intent = new Intent(Grid.this, PictureExpand.class);
                    intent.putExtra("FolderName", keyword);
                    intent.putExtra("filename", P.get(position));
                    startActivity(intent);
                }

                lock = true;
            }
        });

        // select image
        gridview.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                Iterator<Integer> iter = red.iterator();
                Boolean check = true;

                // check if it is selected
                while (iter.hasNext()) {
                    if (iter.next() == position) {
                        check = false;
                        break;
                    }
                }

                // select
                if (check) {
                    red.add(position);
                    imgter.notifyDataSetChanged();
                    Toast.makeText(Grid.this, red.size() + "개 선택됨", Toast.LENGTH_SHORT).show();
                }

                // remove
                else {
                    int ind = red.indexOf(position);
                    red.remove(ind);
                    lock = false;
                    imgter.notifyDataSetChanged();
                    Toast.makeText(Grid.this, red.size() + "개 선택됨", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

    // for convergence menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.convergence, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( semaphore ) {
            semaphore = false;
            int ID = item.getItemId();

            switch (ID) {

                // converge selected files
                case R.id.selected_conv:

                   showDialog(SELECTED_COMPRESSION);

                    break;

                // converge all files
                case R.id.all_conv:

                    showDialog(ALL_COMPRESSION);

                    break;

            }
        }

        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id){
        AlertDialog alert = null;

        switch (id) {
            case ALL_COMPRESSION:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(keyword)
                        .setMessage("압축하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // run thread
                                        all_conv_compression t = new all_conv_compression();
                                        t.execute(null, null, null);
                                    }// end of onClick

                                })
                        .setNegativeButton("아니오",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // run thread
                                        all_conv t2 = new all_conv();
                                        t2.execute(null, null, null);
                                    }
                                })//end of new Listener
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    public void onCancel(DialogInterface dialog) {
                                    }
                                });//end of new Listener

                alert = builder.create();
                return alert;


            case SELECTED_COMPRESSION:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(keyword)
                        .setMessage("압축하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // run thread
                                        selected_conv_compression t = new selected_conv_compression();
                                        t.execute(null, null, null);
                                    }// end of onClick

                                })
                        .setNegativeButton("아니오",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // run thread
                                        selected_conv t2 = new selected_conv();
                                        t2.execute(null, null, null);
                                    }
                                })//end of new Listener);
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {
                            }
                        });//end of new Listener

                alert = builder2.create();
                return alert;

        }// end of switch

        return null;
    }
}
