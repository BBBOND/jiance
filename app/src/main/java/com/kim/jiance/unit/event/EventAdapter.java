package com.kim.jiance.unit.event;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kim.jiance.R;
import com.kim.jiance.model.basicdata.EventInfo;

import java.util.List;

/**
 * Created by 伟阳 on 2015/11/19.
 */
public class EventAdapter extends ArrayAdapter<EventInfo> {
    private List<EventInfo> list;
    private int viewId;
    private LayoutInflater inflater;

    public EventAdapter(Context context, int resource, List<EventInfo> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        viewId = resource;
        list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventInfo eventInfo = list.get(position);
        View view = inflater.inflate(viewId, null);

        TextView item1 = (TextView) view.findViewById(R.id.item1);
        TextView item2 = (TextView) view.findViewById(R.id.item2);
        TextView item3 = (TextView) view.findViewById(R.id.item3);
        TextView item4 = (TextView) view.findViewById(R.id.item4);

        item1.setText(eventInfo.getEventName());
        item2.setText(eventInfo.getHappenPlace());
        item3.setText(eventInfo.getHappenTime());
        String text = "";
        CharSequence charSequence = "";
        if (eventInfo.getIsHandle()) {
            text = "<font color='green'>已处理</font>";
            charSequence = Html.fromHtml(text);
        } else {
            text = "<font color='red'>未处理</font>";
            charSequence = Html.fromHtml(text);
        }
        item4.setText(charSequence);

        return view;
    }
}
