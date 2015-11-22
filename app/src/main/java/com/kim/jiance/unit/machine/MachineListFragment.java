package com.kim.jiance.unit.machine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.kim.jiance.R;
import com.kim.jiance.content.App;
import com.kim.jiance.content.MyURL;
import com.kim.jiance.model.basicdata.MachineInfo;
import com.kim.jiance.utils.HttpUtil;

import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 伟阳 on 2015/11/17.
 */
public class MachineListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    ListView list;
    SwipeRefreshLayout swiperefreshlayout;

    private MachineAdapter adapter;
    private String unitId;

    Handler getMachineListHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String machineListStr = (String) msg.obj;
            if (!machineListStr.equals("null") && machineListStr != null) {
                List<MachineInfo> messList = JSON.parseArray(machineListStr, MachineInfo.class);
                adapter = new MachineAdapter(getContext(), R.layout.item_common, messList);
                list.setAdapter(adapter);
                swiperefreshlayout.setRefreshing(false);
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(list, "数据获取失败,请稍后重试!", Snackbar.LENGTH_SHORT);
                snackbar.show();
                swiperefreshlayout.setRefreshing(false);
                return false;
            }
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine_list, null);
        list = (ListView) view.findViewById(R.id.list);
        swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);

        unitId = App.getUnitID();
        swiperefreshlayout.setColorSchemeColors(R.color.snow_white);
        swiperefreshlayout.setOnRefreshListener(this);
        list.setOnItemClickListener(this);

        refresh();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MachineInfo machineInfo = adapter.getItem(position);
        Intent intent = new Intent(getContext(), MachineInfoActivity.class);
        intent.putExtra("machineinfo", JSON.toJSONString(machineInfo));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userid", App.getUserID());
                map.put("unitid", unitId);
                Log.d("MachineListFragment", JSON.toJSONString(map));
                try {
                    String machineListStr = HttpUtil.get(getContext(), MyURL.GETMACHINEINFOSIMPL, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    message.obj = machineListStr;
                    getMachineListHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("MachineListFragment", "获取设备列表失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getMachineListHandler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("MachineListFragment", "获取设备列表连接失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getMachineListHandler.sendMessage(message);
                }
            }
        }).start();
    }
}
