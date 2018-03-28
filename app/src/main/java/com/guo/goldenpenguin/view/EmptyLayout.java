package com.guo.goldenpenguin.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guo.goldenpenguin.App;
import com.guo.goldenpenguin.R;
import com.guo.goldenpenguin.util.ImageUtils;


/**
 * 无数据v,加载中 ，错误（listview父布局必须为RelatvieLayout）
 */
public class EmptyLayout implements View.OnClickListener{

	private Context mContext;
	private ViewGroup mLoadingView;
	private ViewGroup mEmptyView;
	private ViewGroup mErrorView;
	private BaseQuickAdapter mListView;
	private int mErrorMessageViewId;
	private int mEmptyMessageViewId;
	private int mLoadingMessageViewId;
	private LayoutInflater mInflater;
	private boolean mViewsAdded;

	public EmptyLoading getEmptyLoading() {
		return emptyLoading;
	}

	public void setEmptyLoading(EmptyLoading emptyLoading) {
		this.emptyLoading = emptyLoading;
	}

	private View.OnClickListener mLoadingButtonClickListener;
	private View.OnClickListener mEmptyButtonClickListener;
	private View.OnClickListener mErrorButtonClickListener;
	private EmptyLoading emptyLoading;
	// ---------------------------
	// static variables 
	// ---------------------------
	public final static int TYPE_FINISH=4;
	public final static int TYPE_EMPTY = 1;

	public final static int TYPE_LOADING = 2;

	public final static int TYPE_ERROR = 3;


	private int mEmptyType = TYPE_EMPTY;
	private String mErrorMessage = "Oops! Something wrong happened";
	private String mEmptyMessage = "No items yet";
	private String mLoadingMessage = "Please wait";
	private int mErrorViewButtonId = R.id.buttonError;
	private int mEmptyViewButtonId = R.id.cvproView_nosearchresult;
	private boolean mShowEmptyButton = true;
	private boolean mShowLoadingButton = true;
	private boolean mShowErrorButton = true;


	private ViewGroup mLayoutContainer;


	public ViewGroup getmLayoutContainer() {
		return mLayoutContainer;
	}

	public void setmLayoutContainer(ViewGroup mLayoutContainer) {
		this.mLayoutContainer = mLayoutContainer;
	}
	// ---------------------------
	// getters and setters
	// ---------------------------
	/**
	 * Gets the loading fragment_categors
	 * @return the loading fragment_categors
	 */
	public ViewGroup getLoadingView() {
		return mLoadingView;
	}

	/**
	 * Sets loading fragment_categors
	 * @param loadingView the fragment_categors to be shown when the list is loading
	 */
	public void setLoadingView(ViewGroup loadingView) {
		this.mLoadingView = loadingView;
	}

	/**
	 * Sets loading fragment_categors resource
	 * @param res the resource of the fragment_categors to be shown when the list is loading
	 */
	public void setLoadingViewRes(int res){
		this.mLoadingView = (ViewGroup) mInflater.inflate(res, null);
	}

	/**
	 * Gets the empty fragment_categors
	 * @return the empty fragment_categors
	 */
	public ViewGroup getEmptyView() {
		return mEmptyView;
	}

	/**
	 * Sets empty fragment_categors
	 * @param emptyView the fragment_categors to be shown when no items are available to load in the list
	 */
	public void setEmptyView(ViewGroup emptyView) {
		this.mEmptyView = emptyView;
	}

	/**
	 * Sets empty fragment_categors resource
	 * @param res the resource of the fragment_categors to be shown when no items are available to load in the list
	 */
	public void setEmptyViewRes(int res){
		this.mEmptyView = (ViewGroup) mInflater.inflate(res, null);
	}

	/**
	 * Gets the error fragment_categors
	 * @return the error fragment_categors
	 */
	public ViewGroup getErrorView() {
		return mErrorView;
	}

	/**
	 * Sets error fragment_categors
	 * @param errorView the fragment_categors to be shown when list could not be loaded due to some error
	 */
	public void setErrorView(ViewGroup errorView) {
		this.mErrorView = errorView;
	}

	/**
	 * Sets error fragment_categors resource
	 * @param res the resource of the fragment_categors to be shown when list could not be loaded due to some error
	 */
	public void setErrorViewRes(int res){
		this.mErrorView = (ViewGroup) mInflater.inflate(res, null);
	}

	/**
	 * Gets the list view for which this library is being used
	 * @return the list view
	 */
	public BaseQuickAdapter getListView() {
		return mListView;
	}

	/**
	 * Sets the list view for which this library is being used
	 * @param listView
	 */
	public void setListView(BaseQuickAdapter listView) {
		this.mListView = listView;
	}

	/**
	 * Gets the last set state of the list view
	 * @return loading or empty or error
	 */
	public int getEmptyType() {
		return mEmptyType;
	}

	/**
	 * Sets the state of the empty view of the list view
	 * @param emptyType loading or empty or error
	 */
	public void setEmptyType(int emptyType) {
		this.mEmptyType = emptyType;
		changeEmptyType();
	}

	/**
	 * Gets the message which is shown when the list could not be loaded due to some error
	 * @return the error message 
	 */
	public String getErrorMessage() {
		return mErrorMessage;
	}

	/**
	 * Sets the message to be shown when the list could not be loaded due to some error
	 * @param errorMessage the error message
	 * @param messageViewId the id of the text view within the error fragment_categors whose text will be changed into this message
	 */
	public void setErrorMessage(String errorMessage, int messageViewId) {
		this.mErrorMessage = errorMessage;
		this.mErrorMessageViewId = messageViewId;
	}

	/**
	 * Sets the message to be shown when the list could not be loaded due to some error
	 * @param errorMessage the error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.mErrorMessage = errorMessage;
	}

	/**
	 * Gets the message which will be shown when the list will be empty for not having any item to display
	 * @return the message which will be shown when the list will be empty for not having any item to display
	 */
	public String getEmptyMessage() {
		return mEmptyMessage;
	}

	/**
	 * Sets the message to be shown when the list will be empty for not having any item to display
	 * @param emptyMessage the message
	 */
	public void setEmptyMessage(String emptyMessage, int messageViewId) {
		this.mEmptyMessage = emptyMessage;
		this.mEmptyMessageViewId = messageViewId;
	}

	/**
	 * Sets the message to be shown when the list will be empty for not having any item to display
	 * @param emptyMessage the message
	 */
	public void setEmptyMessage(String emptyMessage) {
		this.mEmptyMessage = emptyMessage;
	}

	/**
	 * Gets the message which will be shown when the list is being loaded
	 * @return
	 */
	public String getLoadingMessage() {
		return mLoadingMessage;
	}

	/**
	 * Sets the message to be shown when the list is being loaded
	 * @param loadingMessage the message
	 * @param messageViewId the id of the text view within the loading fragment_categors whose text will be changed into this message
	 */
	public void setLoadingMessage(String loadingMessage, int messageViewId) {
		this.mLoadingMessage = loadingMessage;
		this.mLoadingMessageViewId = messageViewId;
	}

	/**
	 * Sets the message to be shown when the list is being loaded
	 * @param loadingMessage the message
	 */
	public void setLoadingMessage(String loadingMessage) {
		this.mLoadingMessage = loadingMessage;
	}



	/**
	 * Gets the OnClickListener which perform when LoadingView was click
	 * @return
	 */
	public View.OnClickListener getLoadingButtonClickListener() {
		return mLoadingButtonClickListener;
	}

	/**
	 * Sets the OnClickListener to LoadingView
	 * @param loadingButtonClickListener OnClickListener Object
	 */
	public void setLoadingButtonClickListener(View.OnClickListener loadingButtonClickListener) {
		this.mLoadingButtonClickListener = loadingButtonClickListener;
	}

	/**
	 * Gets the OnClickListener which perform when EmptyView was click
	 * @return
	 */
	public View.OnClickListener getEmptyButtonClickListener() {
		return mEmptyButtonClickListener;
	}

	/**
	 * Sets the OnClickListener to EmptyView
	 * @param emptyButtonClickListener OnClickListener Object
	 */
	public void setEmptyButtonClickListener(View.OnClickListener emptyButtonClickListener) {
		this.mEmptyButtonClickListener = emptyButtonClickListener;
	}

	/**
	 * Gets the OnClickListener which perform when ErrorView was click
	 * @return
	 */
	public View.OnClickListener getErrorButtonClickListener() {
		return mErrorButtonClickListener;
	}

	/**
	 * Sets the OnClickListener to ErrorView
	 * @param errorButtonClickListener OnClickListener Object
	 */
	public void setErrorButtonClickListener(View.OnClickListener errorButtonClickListener) {
		this.mErrorButtonClickListener = errorButtonClickListener;
	}

	/**
	 * Gets if a button is shown in the empty view
	 * @return if a button is shown in the empty view
	 */
	public boolean isEmptyButtonShown() {
		return mShowEmptyButton;
	}

	/**
	 * Sets if a button will be shown in the empty view
	 * @param showEmptyButton will a button be shown in the empty view
	 */
	public void setShowEmptyButton(boolean showEmptyButton) {
		this.mShowEmptyButton = showEmptyButton;
	}

	/**
	 * Gets if a button is shown in the loading view
	 * @return if a button is shown in the loading view
	 */
	public boolean isLoadingButtonShown() {
		return mShowLoadingButton;
	}

	/**
	 * Sets if a button will be shown in the loading view
	 */
	public void setShowLoadingButton(boolean showLoadingButton) {
		this.mShowLoadingButton = showLoadingButton;
	}

	/**
	 * Gets if a button is shown in the error view
	 * @return if a button is shown in the error view
	 */
	public boolean isErrorButtonShown() {
		return mShowErrorButton;
	}

	/**
	 * Sets if a button will be shown in the error view
	 */
	public void setShowErrorButton(boolean showErrorButton) {
		this.mShowErrorButton = showErrorButton;
	}


	/**
	 * Gets the ID of the button in the error view
	 * @return the ID of the button in the error view
	 */
	public int getErrorViewButtonId() {
		return mErrorViewButtonId;
	}

	/**
	 * Sets the ID of the button in the error view. This ID is required if you want the button the error view to be click-able.
	 * @param errorViewButtonId the ID of the button in the error view
	 */
	public void setErrorViewButtonId(int errorViewButtonId) {
		this.mErrorViewButtonId = errorViewButtonId;
	}







	// ---------------------------
	// private methods
	// ---------------------------	

	private void changeEmptyType() {

		setDefaultValues();
		refreshMessages();

		// insert views in the root view
		if (!mViewsAdded) {
			LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			lp.addRule(RelativeLayout.CENTER_VERTICAL);
			RelativeLayout rl = new RelativeLayout(mContext);
			rl.setLayoutParams(lp);
			if (mEmptyView!=null) rl.addView(mEmptyView);
			if (mLoadingView!=null) rl.addView(mLoadingView);
			if (mErrorView!=null) rl.addView(mErrorView);
			mViewsAdded = true;
			if (mListView!=null){
				mListView.setEmptyView(rl);
			}
			if (mLayoutContainer!=null){
				mLayoutContainer.addView(rl,0);
			}
		}


		// change empty type
		if (mListView!=null||mLayoutContainer!=null) {
			switch (mEmptyType) {
				case TYPE_EMPTY:
					if (mEmptyView!=null) mEmptyView.setVisibility(View.VISIBLE);
					if (mErrorView!=null) mErrorView.setVisibility(View.GONE);
					if (mLoadingView!=null) {
						mLoadingView.setVisibility(View.GONE);
					}
					break;
				case TYPE_ERROR:
					if (mEmptyView!=null) mEmptyView.setVisibility(View.GONE);
					if (mErrorView!=null) mErrorView.setVisibility(View.VISIBLE);
					if (mLoadingView!=null) {
						mLoadingView.setVisibility(View.GONE);
					}
					break;
				case TYPE_FINISH:
					if (mEmptyView!=null) mEmptyView.setVisibility(View.GONE);
					if (mErrorView!=null) mErrorView.setVisibility(View.GONE);
					if (mLoadingView!=null) {
						mLoadingView.setVisibility(View.GONE);

					}
					break;
				case TYPE_LOADING:
					if (mEmptyView!=null) mEmptyView.setVisibility(View.GONE);
					if (mErrorView!=null) mErrorView.setVisibility(View.GONE);
					if (mLoadingView!=null) {
						mLoadingView.setVisibility(View.VISIBLE);

					}
					break;
				default:
					break;
			}
		}
	}

	private void refreshMessages() {
//		if (mEmptyMessageViewId>0 && mEmptyMessage!=null) ((TextView)mEmptyView.findViewById(mEmptyMessageViewId)).setText(mEmptyMessage);
//		if (mLoadingMessageViewId>0 && mLoadingMessage!=null) ((TextView)mLoadingView.findViewById(mLoadingMessageViewId)).setText(mLoadingMessage);
//		if (mErrorMessageViewId>0 && mErrorMessage!=null) ((TextView)mErrorView.findViewById(mErrorMessageViewId)).setText(mErrorMessage);
	}

	private void setDefaultValues() {
		if (mEmptyView==null) {
			mEmptyView = (ViewGroup) mInflater.inflate(R.layout.view_empty, null);

		}
		if (mLoadingView==null) {
			mLoadingView = (ViewGroup) mInflater.inflate(R.layout.view_loading, null);
			ImageView gifImg=(ImageView)mLoadingView.findViewById(R.id.cvproView_loading);
			// TODO: 2017/8/19 加载图待定 
			ImageUtils.image().display(gifImg,R.mipmap.bg_loading, App.getInstance());

		}
		if (mErrorView==null) {
			mErrorView = (ViewGroup) mInflater.inflate(R.layout.view_error, null);
			View errorViewButton = mErrorView.findViewById(mErrorViewButtonId);
			errorViewButton.setOnClickListener(this);
		}


	}



	// ---------------------------
	// public methods
	// ---------------------------

	/**
	 * Constructor
	 * @param context the context (preferred context is any activity)
	 */
	public EmptyLayout(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Constructor
	 * @param context the context (preferred context is any activity)
	 * @param listView the list view for which this library is being used
	 */
	public EmptyLayout(Context context, BaseQuickAdapter listView) {
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mListView = listView;
	}


	/**
	 * Shows the empty fragment_categors if the list is empty
	 */
	public void showEmpty() {
		this.mEmptyType = TYPE_EMPTY;
		changeEmptyType();
	}

	/**
	 * Shows loading fragment_categors if the list is empty
	 */
	public void showLoading() {
		this.mEmptyType = TYPE_LOADING;
		changeEmptyType();
	}

	/**
	 * Shows error fragment_categors if the list is empty
	 */
	public void showError() {
		this.mEmptyType = TYPE_ERROR;
		changeEmptyType();
	}
	public void finish(){
		this.mEmptyType=TYPE_FINISH;
		if (mEmptyView!=null) mEmptyView.setVisibility(View.GONE);
		if (mErrorView!=null) mErrorView.setVisibility(View.GONE);
		if (mLoadingView!=null) {
			mLoadingView.setVisibility(View.GONE);

		}
	}
	public 	interface EmptyLoading{
		void resetloading();
	}
	@Override
	public void onClick(View v) {
		if (emptyLoading!=null){
		emptyLoading.resetloading();}
	}
}
