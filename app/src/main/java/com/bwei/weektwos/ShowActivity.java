package com.bwei.weektwos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import adapter.ShowAdapter;
import api.Apis;
import bean.BannerBean;
import bean.CommodityListBean;
import bean.MyContentData;
import bean.ShowBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import presenter.PresenterImp;
import view.IView;

public class ShowActivity extends AppCompatActivity implements IView {


    @BindView(R.id.recy)
    RecyclerView mRecy;
    @BindView(R.id.text_one)
    TextView mTextOne;
    @BindView(R.id.text_two)
    TextView mTextTwo;
    @BindView(R.id.draw)
    DrawerLayout mDraw;
    @BindView(R.id.banner)
    XBanner mBanner;
    @BindView(R.id.recyView)
    RecyclerView mRecyView;

    private PresenterImp presenter;
    private List<String> mImage;
    private ShowAdapter showAdapter;
    private List<CommodityListBean> datas = new ArrayList<>();
    private int page;
    private List<MyContentData.ResultsBean> lists = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        presenter = new PresenterImp(this);
        mImage = new ArrayList<>();
        intiData();
        initView();
        initDataLoad();
    }

    private void initDataLoad() {
        presenter.startRequestGet(Apis.URL_SHOW, ShowBean.class);
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecy.setLayoutManager(linearLayoutManager);

        GridLayoutManager manager = new GridLayoutManager(ShowActivity.this, 3);
        mRecyView.setLayoutManager(manager);
    }

    private void intiData() {
        presenter.startRequestGet(Apis.URL_BANNER, BannerBean.class);
        presenter.startRequestGet(Apis.URL_SHOWVIEW, MyContentData.class);
    }

    public void aaa(View view) {
        mDraw.openDrawer(Gravity.LEFT);
    }


    @Override
    public void onRequestSuccess(Object o) {
        if (o instanceof BannerBean) {
            BannerBean bannerBean = (BannerBean) o;
            if (bannerBean == null) {
                Toast.makeText(ShowActivity.this, bannerBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < bannerBean.getResult().size(); i++) {
                    mImage.add(bannerBean.getResult().get(i).getImageUrl());
                    initImageData();
                }
            }
        } else if (o instanceof ShowBean) {
            ShowBean showBean = (ShowBean) o;
            List<CommodityListBean> rxxp = showBean.getResult().getRxxp().get(0).getCommodityList();
            List<CommodityListBean> mlss = showBean.getResult().getMlss().get(0).getCommodityList();
            List<CommodityListBean> pash = showBean.getResult().getPzsh().get(0).getCommodityList();
            showAdapter = new ShowAdapter(datas, this);
            datas.addAll(rxxp);
            datas.addAll(mlss);
            datas.addAll(pash);
            mRecy.setAdapter(showAdapter);
        }else if (o instanceof MyContentData) {
            lists.clear();
            MyContentData myContentData = (MyContentData) o;
            lists.addAll(myContentData.getResults());

            myAdapter = new MyAdapter(lists, ShowActivity.this);
            mRecyView.setAdapter(myAdapter);
        }

    }

    private void initImageData() {
        mBanner.setData(mImage, null);
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(ShowActivity.this).load(mImage.get(position)).into((ImageView) view);
            }
        });
    }

    @Override
    public void onRequestFail(String error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDeath();
    }

    @OnClick({R.id.text_one, R.id.text_two, R.id.draw})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.text_one:
                Intent intent = new Intent(ShowActivity.this, ShowActivity.class);
                startActivity(intent);
                break;
            case R.id.text_two:
                Intent intentText = new Intent(ShowActivity.this, ShopingActivity.class);
                startActivity(intentText);
                break;
            case R.id.draw:
                break;
        }
    }
}

