package com.kim.jiance.unit.machine;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kim.jiance.R;
import com.kim.jiance.model.basicdata.MachineInfo;

import java.util.List;

/**
 * Created by 伟阳 on 2015/11/19.
 */
public class MachineAdapter extends ArrayAdapter<MachineInfo> {

    private List<MachineInfo> list;
    private int viewId;
    private LayoutInflater inflater;

    public MachineAdapter(Context context, int resource, List<MachineInfo> objects) {
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
        MachineInfo machineInfo = list.get(position);
        View view = inflater.inflate(viewId, null);

        TextView item1 = (TextView) view.findViewById(R.id.item1);
        TextView item2 = (TextView) view.findViewById(R.id.item2);
        TextView item3 = (TextView) view.findViewById(R.id.item3);
        TextView item4 = (TextView) view.findViewById(R.id.item4);
        TextView item5 = (TextView) view.findViewById(R.id.item5);

        item1.setText(machineInfo.getMachineCode());
        item2.setText(machineInfo.getMachineName());
        item3.setText(machineInfo.getMachineType());
        item4.setText(String.valueOf(machineInfo.getMachineIndex()));

        String text = "";
        CharSequence charSequence = "";
        if (machineInfo.getIsUsed()) {
            text = "<font color='green'>可用</font>";
            charSequence = Html.fromHtml(text);
        } else {
            text = "<font color='red'>不可用</font>";
            charSequence = Html.fromHtml(text);
        }
        item5.setText(charSequence);

        return view;
    }
}
