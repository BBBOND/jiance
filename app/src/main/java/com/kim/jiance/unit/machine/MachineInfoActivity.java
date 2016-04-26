package com.kim.jiance.unit.machine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kim.jiance.R;
import com.kim.jiance.model.basicdata.MachineInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 伟阳 on 2015/11/21.
 */
public class MachineInfoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.machine_code)
    TextView machineCode;
    @Bind(R.id.machine_name)
    TextView machineName;
    @Bind(R.id.machine_type)
    TextView machineType;
    @Bind(R.id.machine_index)
    TextView machineIndex;
    @Bind(R.id.machine_maker)
    TextView machineMaker;
    @Bind(R.id.machine_version)
    TextView machineVersion;
    @Bind(R.id.machine_is_used)
    TextView machineIsUsed;

    private MachineInfo machineInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_machine);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String machineInfoStr = intent.getStringExtra("machineinfo");
        machineInfo = JSON.parseObject(machineInfoStr, MachineInfo.class);
        init();

    }

    public void init() {
        machineCode.setText(machineInfo.getMachineCode());
        machineName.setText(machineInfo.getMachineName());
        machineType.setText(machineInfo.getMachineType());
        machineIndex.setText(String.valueOf(machineInfo.getMachineIndex()));
        machineMaker.setText(machineInfo.getMaker());
        machineVersion.setText(machineInfo.getMachineVersion());
        String text = "";
        CharSequence charSequence = "";
        if (machineInfo.getIsUsed() != null)
            if (machineInfo.getIsUsed()) {
                text = "<font color='green'>可用</font>";
                charSequence = Html.fromHtml(text);
            } else {
                text = "<font color='red'>不可用</font>";
                charSequence = Html.fromHtml(text);
            }
        machineIsUsed.setText(charSequence);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
