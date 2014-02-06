package jp.co.webtech.android.pngopttestapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends ActionBarActivity {

    private HashMap<String, Bitmap> bitmapCache = new HashMap<String, Bitmap>();

    public class Data {
        private String file;
        private String title;

        Data(String file, String title) {
            this.file = file;
            this.title = title;
        }
    }

    private Data[] datum = {
            new Data("0a.png", "n0a"),
            new Data("0b.png", "n0b"),
            new Data("0c.png", "n0c"),
            new Data("0d.png", "n0d"),
            new Data("1a.png", "n1a"),
            new Data("1b.png", "n1b"),
            new Data("1c.png", "n1c"),
            new Data("1d.png", "n1d"),
            new Data("2a.png", "n2a"),
            new Data("2b.png", "n2b"),
            new Data("2c.png", "n2c"),
            new Data("2d.png", "n2d"),
            new Data("3a.png", "n3a"),
            new Data("3b.png", "n3b"),
            new Data("3c.png", "n3c"),
            new Data("3d.png", "n3d"),
            new Data("4a.png", "n4a"),
            new Data("4b.png", "n4b"),
            new Data("4c.png", "n4c"),
            new Data("4d.png", "n4d"),
            new Data("5a.png", "n5a"),
            new Data("5b.png", "n5b"),
            new Data("5c.png", "n5c"),
            new Data("5d.png", "n5d"),
            new Data("6a.png", "n6a"),
            new Data("6b.png", "n6b"),
            new Data("6c.png", "n6c"),
            new Data("6d.png", "n6d"),
            new Data("7a.png", "n7a"),
            new Data("7b.png", "n7b"),
            new Data("7c.png", "n7c"),
            new Data("7d.png", "n7d"),
            new Data("8a.png", "n8a"),
            new Data("8b.png", "n8b"),
            new Data("8c.png", "n8c"),
            new Data("8d.png", "n8d"),
            new Data("9a.png", "n9a"),
            new Data("9b.png", "n9b"),
            new Data("9c.png", "n9c"),
            new Data("9d.png", "n9d"),
    };

    class Item {
        TextView textView;
        ImageView imageView;
    }

    public class MyAdapter extends ArrayAdapter<Data> {
        private LayoutInflater inflater;
        private int layoutId;

        public MyAdapter(Context context, int layoutId, Data[] objects) {
            super(context, 0, objects);
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.layoutId = layoutId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item;

            if (convertView == null) {
                convertView = inflater.inflate(layoutId, parent, false);
                item = new Item();
                item.textView = (TextView) convertView.findViewById(R.id.textview);
                item.imageView = (ImageView) convertView.findViewById(R.id.imageview);
                convertView.setTag(item);
            } else {
                item = (Item) convertView.getTag();
            }
            Data data = getItem(position);
            item.textView.setText(data.title);

            Bitmap bitmap;
            if (bitmapCache.containsKey(data.file)) {
                //生成済みのBitmapがあった
                bitmap = bitmapCache.get(data.file);
            } else {
                bitmap = Util.loadBitmapFromAssets(getResources().getAssets(), data.file);
                if (bitmap != null) {
                    int thumbSize = 128;
                    bitmap = Util.createThumbnail(bitmap, thumbSize);
                    if (bitmap != null) {
                        bitmapCache.put(data.file, bitmap);
                    }
                }
            }
            if (bitmap != null) {
                item.imageView.setImageBitmap(bitmap);
            }
            System.gc();

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new MyAdapter(this, R.layout.list_item, datum));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                intent.putExtra("file", datum[position].file);
                intent.putExtra("title", datum[position].title);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_webview:
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                startActivityForResult(intent, 0);
                return true;

            case R.id.action_about:
                Util.showAbout(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
