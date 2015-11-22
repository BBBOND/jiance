package com.kim.jiance.unit.event;

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
import com.kim.jiance.model.basicdata.EventInfo;
import com.kim.jiance.unit.machine.MachineInfoActivity;
import com.kim.jiance.utils.HttpUtil;

import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 伟阳 on 2015/11/18.
 */
public class EventListFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView list;
    private SwipeRefreshLayout swiperefreshlayout;

    private EventAdapter adapter;
    private String unitId;

    Handler getEventListHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String eventListStr = (String) msg.obj;
            if (!eventListStr.equals("null") && eventListStr != null) {
                List<EventInfo> eventList = JSON.parseArray(eventListStr, EventInfo.class);
                adapter = new EventAdapter(getContext(), R.layout.item_common, eventList);
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
        View view = inflater.inflate(R.layout.fragment_event_list, null);
        list = (ListView) view.findViewById(R.id.list);
        swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setColorSchemeColors(R.color.snow_white);
        swiperefreshlayout.setOnRefreshListener(this);
        list.setOnItemClickListener(this);
        unitId = App.getUnitID();
        refresh();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventInfo eventInfo = adapter.getItem(position);
        Intent intent = new Intent(getContext(), EventInfoActivity.class);
        intent.putExtra("eventinfo", JSON.toJSONString(eventInfo));
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
                Log.d("EventListFragment", JSON.toJSONString(map));
                try {
                    String eventListStr = HttpUtil.get(getContext(), MyURL.GETEVENTINFOSIMPL, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    message.obj = eventListStr;
                    getEventListHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("EventListFragment", "获取事件列表连接失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getEventListHandler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("EventListFragment", "获取事件列表失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getEventListHandler.sendMessage(message);
                }
            }
        }).start();
    }
}
