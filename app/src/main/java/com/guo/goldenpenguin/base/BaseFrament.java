package com.guo.goldenpenguin.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guo.goldenpenguin.R;
import com.guo.goldenpenguin.listener.ResetDataListener;
import com.guo.goldenpenguin.util.ImageUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * frament 父类
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 下午8:32
 */
@SuppressLint("ValidFragment")
public abstract class BaseFrament extends Fragment {

    public final static int TYPE_EMPTY = 1;

    public final static int TYPE_LOADING = 2;

    public final static int TYPE_ERROR = 3;

    public final static int TYPE_DYNC=4;

    public final static int TYPE_FINISH=5;

    protected FragmentActivity mFragmentActivity;
    /**
     * 为整个布局的顶级View
     */
    protected View mBaseSuperView;

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


    private ResetDataListener resetDataListener;
    /**
     * 初始化数据
     */
    protected abstract void initData();


    private boolean injected = false;

    private View errorView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentActivity = getActivity();
    }
    protected boolean isVisible;

    /**
     * isVisible 判断调用时页面是线上出来还是隐藏状态
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onLazyLoad();
        } else {
            isVisible = false;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mBaseSuperView == null) {
            injected = true;
            mBaseSuperView = x.view().inject(this, inflater, container);
            //加载状态页
            mLoaddingStata= LayoutInflater.from(getActivity()).inflate(R.layout.layout_state_error,null);
            x.view().inject(this,mLoaddingStata);
            //加载中gif图
            ImageUtils.image().display(imageView,R.mipmap.bg_loading,getActivity());
            //点击重试
            mRestError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (resetDataListener!=null){
                    resetDataListener.Reset();}
                }
            });
            initData();
        } else {
            ViewGroup parent = (ViewGroup) mBaseSuperView.getParent();
            if (parent != null) {
                parent.removeView(mBaseSuperView);
            }
        }
        return mBaseSuperView;
    }

    /**
     * 显示列表加载状态
     *
     * @param adapter    列表适配器
     * @param statusType 状态类型
     */
    public  void onShowEmptyLayout(BaseQuickAdapter adapter, int statusType) {
        if (adapter!=null){
            if (adapter.getEmptyView()!=null){
                setLoadingData(statusType,adapter);
            }else{
                adapter.setEmptyView(mLoaddingStata);
                setLoadingData(statusType,adapter);
            }
        }
    }

    /**
     *设置非列表状态布局
     * @param container
     */
    public void setStataLayout(RelativeLayout container){
        if (container!=null){
            container.addView(mLoaddingStata,0);
        }
    }

    public void setResetDataListener(ResetDataListener resetDataListener){
        this.resetDataListener=resetDataListener;
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
    public void setLoadingData(int stata,BaseQuickAdapter adapter){
        switch (stata){
            case TYPE_EMPTY:
                if (adapter.getData()==null||adapter.getData().size()<=0){
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
                if (adapter.getData()==null||adapter.getData().size()<=0){
                    mEmpty.setVisibility(View.INVISIBLE);
                    mError.setVisibility(View.INVISIBLE);
                    mLoading.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }



    public  void onLazyLoad(){

    };
    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();


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
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName",context.getPackageName());
        }
        context.startActivity(localIntent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }
}
