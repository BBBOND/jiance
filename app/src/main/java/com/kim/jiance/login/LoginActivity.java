package com.kim.jiance.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kim.jiance.R;
import com.kim.jiance.content.App;
import com.kim.jiance.content.Common;
import com.kim.jiance.unit.UnitActivity;
import com.kim.jiance.setting.SettingActivity;
import com.kim.jiance.units.UnitListActivity;
import com.kim.jiance.utils.MySharedPreferences;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_username)
    TextInputLayout loginUsername;
    @Bind(R.id.login_password)
    TextInputLayout loginPassword;
    @Bind(R.id.auto_login_checkBox)
    CheckBox autoLoginCheckBox;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private int backKeyPressedTime = 0;
    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        init();
    }

    public void init() {
        loginDialog = new ProgressDialog(LoginActivity.this);
        loginDialog.setTitle("登陆中...");
        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        MySharedPreferences mySharedPreferences = new MySharedPreferences(LoginActivity.this);
        Map<String, Object> map = mySharedPreferences.readUsernameAndPassword();
        if (map != null) {
            loginUsername.getEditText().setText(map.get("username").toString());
            loginPassword.getEditText().setText(map.get("password").toString());
            autoLoginCheckBox.setChecked(true);
        }

        loginUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                loginUsername.setError("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                loginPassword.setError("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = loginUsername.getEditText().getText().toString().trim();
                String password = loginPassword.getEditText().getText().toString().trim();
                if (userName.equals("")) {
                    loginUsername.setError("用户名不能为空!");
                    loginUsername.requestFocus();
                } else if (password.equals("")) {
                    loginPassword.setError("密码不能为空!");
                    loginPassword.requestFocus();
                } else {
                    loginDialog.show();
                    login(userName, password);
                }
            }
        });

    }

    public void login(String userName, String password) {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(LoginActivity.this);
        Map<String, Object> map = new HashMap<>();
        if (autoLoginCheckBox.isChecked()) {
            // TODO: 2015/11/16 保存用户名密码
            map.put("username", userName);
            map.put("password", password);
            boolean flag = mySharedPreferences.saveUsernameAndPassword(map);
            if (flag) {
                Log.d("LoginActivity", "保存用户名成功");
            } else {
                Log.d("LoginActivity", "保存用户名失败");
            }
        } else {
            map.put("username", userName);
            map.put("password", "");
            mySharedPreferences.saveUsernameAndPassword(map);
        }
        // TODO: 2015/11/16 登陆
        Login login = new Login(LoginActivity.this, userName, password);
        login.login(new Login.GetLoginCallBack() {
                        @Override
                        public void getLoginResult(String result) {
                            if (result.equals(Common.CONNECTFAILED)) {
                                loginDialog.cancel();
                                Snackbar snackbar = Snackbar.make(toolbar, Common.CONNECTFAILED, Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            } else if (result.equals(Common.GETINFORMATIONFAILED)) {
                                loginDialog.cancel();
                                Snackbar snackbar = Snackbar.make(toolbar, Common.GETINFORMATIONFAILED, Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            } else if (result.equals("null")) {
                                loginDialog.cancel();
                                Snackbar snackbar = Snackbar.make(toolbar, "用户名或密码错误!", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            } else {
                                loginDialog.cancel();
                                // TODO: 2015/11/17 保存信息到全局变量
                                JSONObject jsonObject = JSON.parseObject(result);
                                String userId = jsonObject.getString("userid");
                                String roleName = jsonObject.getString("rolename");
                                App.setUserID(userId);
                                App.setRoleName(roleName);
                                Intent intent = new Intent();
                                if (roleName.equals("联网用户") || roleName.equals("接警用户") || roleName.equals("维保用户")) {
                                    // TODO: 2015/11/17 需要设置判断  ---
                                    intent.setClass(LoginActivity.this, UnitListActivity.class);
                                } else if (roleName.equals("监控用户")) {
                                    // TODO: 2015/11/17 需要设置判断  ---
                                    String unitId = jsonObject.getString("unitid");
                                    App.setUnitID(unitId);
                                    intent.setClass(LoginActivity.this, UnitActivity.class);
                                }
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && backKeyPressedTime == 0) {
            Snackbar snackbar = Snackbar.make(toolbar, "再按一次返回键退出程序", Snackbar.LENGTH_SHORT);
            snackbar.show();
            backKeyPressedTime = 1;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        backKeyPressedTime = 0;
                    }
                }
            }.start();
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && backKeyPressedTime == 1) {
            this.finish();
            System.exit(0);
        }
        return true;
    }
}
