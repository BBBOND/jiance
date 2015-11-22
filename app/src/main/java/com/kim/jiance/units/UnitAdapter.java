package com.kim.jiance.units;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kim.jiance.R;
import com.kim.jiance.model.more.SimpleFireControlMess;

import java.util.List;

/**
 * 单位列表Adapter
 * Created by 伟阳 on 2015/11/19.
 */
public class UnitAdapter extends ArrayAdapter<SimpleFireControlMess> {

    private List<SimpleFireControlMess> list;
    private int viewId;
    private LayoutInflater inflater;

    public UnitAdapter(Context context, int resource, List<SimpleFireControlMess> objects) {
        super(context, resource, objects);
        viewId = resource;
        list = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleFireControlMess mess = getItem(position);
        View view = inflater.inflate(viewId, null);
        TextView item1 = (TextView) view.findViewById(R.id.item1);
        TextView item2 = (TextView) view.findViewById(R.id.item2);
        TextView item3 = (TextView) view.findViewById(R.id.item3);
        TextView item4 = (TextView) view.findViewById(R.id.item4);
        TextView item5 = (TextView) view.findViewById(R.id.item5);

        item1.setText(mess.getUnitcode());
        item2.setText(mess.getUnitname());
        item3.setText(mess.getEvetcount());
        item4.setText(mess.getTotlafailure());
        double integrityrate = mess.getEquipmentintegrityrate();
        String str = "";
        CharSequence charSequence = "";
        if (integrityrate >= 0 && integrityrate < 0.3) {
            str = "<font color='#FF0000'>" + String.format("%3.2lf", integrityrate) + "%</font>";
            charSequence = Html.fromHtml(str);
        } else if (integrityrate >= 0.3 && integrityrate < 0.6) {
            str = "<font color='EEEE00'>" + String.format("%3.2lf", integrityrate) + "%</font>";
            charSequence = Html.fromHtml(str);
        } else if (integrityrate >= 0.6 && integrityrate < 0.9) {
            str = "<font color='EE4000'>" + String.format("%3.2lf", integrityrate) + "%</font>";
            charSequence = Html.fromHtml(str);
        } else if (integrityrate >= 0.9 && integrityrate <= 1) {
            str = "<font color='#00EE00'>" + String.format("%3.2lf", integrityrate) + "%</font>";
            charSequence = Html.fromHtml(str);
        }
        item5.setText(charSequence);
        return view;
    }
}
