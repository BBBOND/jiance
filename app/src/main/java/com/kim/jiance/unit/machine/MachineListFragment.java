package com.kim.jiance.unit.machine;

import android.content.Intent;
import android.graphics.Color;
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

import com.alibaba.fastjson.JSON;
import com.kim.jiance.R;
import com.kim.jiance.content.App;
import com.kim.jiance.content.MyURL;
import com.kim.jiance.model.basicdata.MachineInfo;
import com.kim.jiance.utils.HttpUtil;
import com.kim.jiance.view.LoadListView;

import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 伟阳 on 2015/11/17.
 */
public class MachineListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, LoadListView.LoadListener {

    @Bind(R.id.list)
    LoadListView list;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private List<MachineInfo> messList = new ArrayList<>();
    private MachineAdapter adapter;
    private String unitId;
    private int currentPage = 0;
    private static final int PAGESIZE = 15;

    Handler getMachineListHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String machineListStr = (String) msg.obj;
            if (!machineListStr.equals("null") && machineListStr != null) {
                messList = JSON.parseArray(machineListStr, MachineInfo.class);
                adapter = new MachineAdapter(getContext(), R.layout.item_machine, messList);
                list.setAdapter(adapter);
                refreshLayout.setRefreshing(false);
                currentPage = 0;
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(list, "数据获取失败,请稍后重试!", Snackbar.LENGTH_SHORT);
                snackbar.show();
                refreshLayout.setRefreshing(false);
                return false;
            }
        }
    });

    Handler loadMoreHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String machineListStr = (String) msg.obj;
            if (!machineListStr.equals("null") && machineListStr != null) {
                List<MachineInfo> newList = JSON.parseArray(machineListStr, MachineInfo.class);
                if (newList.size() < PAGESIZE)
                    currentPage--;
                messList.removeAll(newList);
                messList.addAll(newList);
                adapter.notifyDataSetChanged();
                list.loadComplete();
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(list, "没有更多数据了！", Snackbar.LENGTH_SHORT);
                snackbar.show();
                list.loadComplete();
                return false;
            }
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine_list, null);
        ButterKnife.bind(this, view);

        unitId = App.getUnitID();

        list.setOnItemClickListener(this);
        list.setLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.BLUE);
        refreshLayout.setRefreshing(true);

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
                map.put("unitid", unitId);
                map.put("pageNum", 0);
                map.put("pageSize", PAGESIZE);
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

    public void loadMore(final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("unitid", unitId);
                map.put("pageNum", page);
                map.put("pageSize", PAGESIZE);
                Log.d("MachineListFragment", JSON.toJSONString(map));
                try {
                    String machineListStr = HttpUtil.get(getContext(), MyURL.GETMACHINEINFOSIMPL, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    Log.d("MachineListFragment", machineListStr);
                    message.obj = machineListStr;
                    loadMoreHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("MachineListFragment", "获取设备列表失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    loadMoreHandler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("MachineListFragment", "获取设备列表连接失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    loadMoreHandler.sendMessage(message);
                }
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLoad() {
        loadMore(++currentPage);
    }
}
