package com.guo.goldenpenguin.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guo.goldenpenguin.R;
import com.guo.goldenpenguin.listener.ResetDataListener;
import com.guo.goldenpenguin.runtimepermissions.PermissionsManager;
import com.guo.goldenpenguin.runtimepermissions.PermissionsResultAction;
import com.guo.goldenpenguin.util.ActivityManager;
import com.guo.goldenpenguin.util.ImageUtils;
import com.guo.goldenpenguin.view.EmptyLayout;
import com.gyf.barlibrary.ImmersionBar;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 所有activity的父类
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 00 10
 */
public abstract class BaseActivity extends AppCompatActivity {

    //加载状态页
    private View mLoaddingStata;

    //错误页
    @ViewInject(R.id.error)
    private View mError;

    @ViewInject(R.id.buttonError)
    private ImageView mRestError;
    //空白页
    @ViewInject(R.id.empty)
    private View mEmpty;



    //加载中状态页
    @ViewInject(R.id.loading)
    private View mLoading;

    @ViewInject(R.id.cvproView_loading)
    private ImageView imageView;

    public final static int TYPE_EMPTY = 1;

    public final static int TYPE_LOADING = 2;

    public final static int TYPE_ERROR = 3;

    public final static int TYPE_DYNC=4;

    public final static int TYPE_FINISH=5;

    private ResetDataListener resetDataListener;

    public EmptyLayout mEmptyLayout;
    public  ImmersionBar mImmersionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        //注入view和事件
        x.view().inject(this);
        ActivityManager.push(this);

        //加载状态页
        mLoaddingStata = LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_state_error, null);
        x.view().inject(this,mLoaddingStata);
        //加载中gif图
        ImageUtils.image().display(imageView,R.mipmap.bg_loading, getBaseContext());
        //点击重试
        mRestError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resetDataListener != null){
                    resetDataListener.Reset();}
            }
        });

        if (mEmptyLayout == null) {
            mEmptyLayout = new EmptyLayout(this);
        }

        initData();
        int ColorRes = getStatusBarColor();
        //初始化，0为透明状态栏
        mImmersionBar = ImmersionBar.with(this);
        if (ColorRes == 0){
            mImmersionBar.init();
        } else {
            mImmersionBar.fitsSystemWindows(true).statusBarColor(ColorRes).init();
        }

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //消息推送启动程序
//        UmengPush.getInstance().onAppStart(this);
        //set status bar darkmode
//        new SystemBarTintManager(this).setStatusBarDarkMode(true, this);
    }

    public void setResetDataListener(ResetDataListener resetDataListener){
        this.resetDataListener=resetDataListener;
    }

    /**
     * 显示列表加载状态
     *
     * @param adapter    列表适配器
     * @param statusType 状态类型
     */
/*    public  void onShowEmptyLayout(BaseQuickAdapter adapter, int statusType) {
        if (adapter != null ) {
            if (mEmptyLayout.getListView() == null) {
                mEmptyLayout.setListView(adapter);
            }
            switch (statusType) {
                case EmptyLayout.TYPE_EMPTY:
                    mEmptyLayout.showEmpty();
                    break;
                case EmptyLayout.TYPE_ERROR:
                    mEmptyLayout.showError();
                    break;
                case EmptyLayout.TYPE_LOADING:
                    mEmptyLayout.showLoading();
                    break;
            }
        }
    }*/


    /**
     * 显示列表加载状态
     *
     * @param adapter    列表适配器
     * @param statusType 状态类型
     */
    public  void onShowEmptyLayout(BaseQuickAdapter adapter, int statusType) {
        if (adapter != null){
            if (adapter.getEmptyView() != null){
                setLoadingData(statusType, adapter);
            } else {
                adapter.setEmptyView(mLoaddingStata);
                setLoadingData(statusType, adapter);
            }
        }
    }

    /**
     * 显示加载状态
     * @param statusType
     */
    public void onShowEmptyLayout(int statusType){
        switch (statusType){
            case TYPE_EMPTY:
                mEmpty.setVisibility(View.VISIBLE);
                mError.setVisibility(View.INVISIBLE);
                mLoading.setVisibility(View.INVISIBLE);
                break;

            case TYPE_ERROR:
                mEmpty.setVisibility(View.INVISIBLE);
                mError.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.INVISIBLE);
                break;

            case TYPE_LOADING:
                mEmpty.setVisibility(View.INVISIBLE);
                mError.setVisibility(View.INVISIBLE);
                mLoading.setVisibility(View.VISIBLE);
                break;

            case TYPE_DYNC:
                mEmpty.setVisibility(View.INVISIBLE);
                mError.setVisibility(View.INVISIBLE);
                mLoading.setVisibility(View.INVISIBLE);
                break;

            case TYPE_FINISH:
                mEmpty.setVisibility(View.INVISIBLE);
                mError.setVisibility(View.INVISIBLE);
                mLoading.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * 设置加载状态
     * @param stata
     */
    public void setLoadingData(int stata, BaseQuickAdapter adapter){
        switch (stata){
            case TYPE_EMPTY:
                if (adapter.getData() == null || adapter.getData().size() <= 0){
                    mEmpty.setVisibility(View.VISIBLE);
                    mError.setVisibility(View.INVISIBLE);
                    mLoading.setVisibility(View.INVISIBLE);}
                break;

            case TYPE_ERROR:
                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                mEmpty.setVisibility(View.INVISIBLE);
                mError.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.INVISIBLE);
                break;

            case TYPE_LOADING:
                mEmpty.setVisibility(View.INVISIBLE);
                mError.setVisibility(View.INVISIBLE);
                mLoading.setVisibility(View.VISIBLE);
                break;

            case TYPE_DYNC:
                if (adapter.getData() == null || adapter.getData().size() <= 0){
                    mEmpty.setVisibility(View.INVISIBLE);
                    mError.setVisibility(View.INVISIBLE);
                    mLoading.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //隐藏dialog
    }

    /**
     * 请求权限
     */
    @TargetApi(23)
    protected void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//                Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
//                Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 请求权限
     */
    @TargetApi(23)
    protected void requestPermissions(Activity activity, String[] permissions) {
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity,permissions,new PermissionsResultAction(){

            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(String permission) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }
        super.onDestroy();
        if (ActivityManager.current() != null && ActivityManager.current().hashCode()==this.hashCode()) {
            ActivityManager.pop(this);
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            ActivityManager.pop();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 应用信息界面
     * @param context
     */
    protected void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }
    //
//	/**
//	 * 初始化UI
//	 */
//	protected abstract void initUI();
//	/**
//	 * 初始化监听
//	 */
//	protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 获取状态栏颜色
     * @return
     */
    protected abstract  int getStatusBarColor();
        /*
        Uncomment if you disable PrivateFactory injection. See CalligraphyConfig#disablePrivateFactoryInjection()
     */
//    @Override
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public View onCreateView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        return CalligraphyContextWrapper.onActivityCreateView(this, parent, super.onCreateView(parent, name, context, attrs), name, context, attrs);
//    }


}
