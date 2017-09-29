package com.example.cognitev.nearby.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.cognitev.nearby.NearbyApplication;
import com.example.cognitev.nearby.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Salma on 9/21/2017.
 */

public abstract class BaseFragment<P extends BaseContract.BaseViewModel> extends Fragment implements BaseContract.BaseView {
    protected static final int TRANSPARENT_COLOR = -1;
    protected View rootView;
    protected ViewStub mainViewStub;
    protected ViewStub loadingViewStub;
    protected ViewStub errorViewStub;
    protected Context mContext;


    @BindView(R.id.main_content)
    View contentView;

    @BindView(R.id.main_loading)
    View loadingView;

    @BindView(R.id.main_error)
    View errorView;


    TextView errorText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mainViewStub = (ViewStub) rootView.findViewById(R.id.main_content);
        loadingViewStub = (ViewStub) rootView.findViewById(R.id.main_loading);
        errorViewStub = (ViewStub) rootView.findViewById(R.id.main_error);

        int mainLayoutID = getLayoutRes();
        if (mainLayoutID != -1) {
            mainViewStub.setLayoutResource(mainLayoutID);
            mainViewStub.inflate();
        }

        int loadingLayoutID = getLoadingLayout();
        if (loadingLayoutID != -1) {
            loadingViewStub.setLayoutResource(loadingLayoutID);
            loadingViewStub.inflate();
        }

        int errorLayoutID = getErrorLayout();
        if (loadingLayoutID != -1) {
            errorViewStub.setLayoutResource(errorLayoutID);
            errorViewStub.inflate();
        }



        ButterKnife.bind(this, rootView);

        rootView.setBackgroundResource(R.color.transparent);

        if (loadingView == null) {
            throw new NullPointerException(
                    "Loading view is null! Have you specified a loading view in your layout xml file?"
                            + " You have to give your loading View the id R.id.loadingView");
        }

        if (contentView == null) {
            throw new NullPointerException(
                    "Content view is null! Have you specified a content view in your layout xml file?"
                            + " You have to give your content View the id R.id.contentView");
        }
        if (errorView == null) {
            throw new NullPointerException(
                    "Content view is null! Have you specified a content view in your layout xml file?"
                            + " You have to give your content View the id R.id.layout_main_error");
        }
        View view = errorView.findViewById(R.id.error_text);

        if (view != null) {
            errorText = (TextView) view;
        }
        showContent();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    protected int getLoadingLayout() {
        return R.layout.layout_main_loading;
    }

    protected int getErrorLayout() {
        return R.layout.layout_main_error;
    }

    protected abstract int getLayoutRes();

    protected abstract void setupView();


    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }


    @Override
    public void showError(String error) {
        errorText.setText(error);
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

}
