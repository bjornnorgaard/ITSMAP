package com.leafcastlelabs.demoui.adaptors;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.leafcastlelabs.demoui.R;
import com.leafcastlelabs.demoui.model.Demo;

import java.util.ArrayList;

/**
 * Created by kasper on 16/04/15.
 */
public class DemoAdaptor extends BaseAdapter {

    Context context;
    ArrayList<Demo> demos;
    Demo demo;

    public DemoAdaptor(Context c, ArrayList<Demo> demoList){
        this.context = c;
        this.demos = demoList;
    }

    @Override
    public int getCount() {
        if(demos!=null) {
            return demos.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(demos!=null) {
            return demos.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater demoInflator = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = demoInflator.inflate(R.layout.demo_list_item, null);
        }

        demo = demos.get(position);
        if(demo!=null){
            TextView txtTitle = (TextView) convertView.findViewById(R.id.txtDemoTitle);
            txtTitle.setText(demo.getName());

            TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDemoDescription);
            txtDescription.setText(demo.getDescription());
        }
        return convertView;
    }
}
