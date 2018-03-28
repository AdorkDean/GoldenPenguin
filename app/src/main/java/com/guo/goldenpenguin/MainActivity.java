package com.guo.goldenpenguin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guo.goldenpenguin.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
@ViewInject(R.id.tvVEIW)
private TextView mHello;

    @Override
    protected void initData() {
        mHello.setText("asdfasf");
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.colorAccent;
    }
}
