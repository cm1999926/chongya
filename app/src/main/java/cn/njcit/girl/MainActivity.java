package cn.njcit.girl;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.RetrofitUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mFlCardBack, mFlCardFront, mFlContainer;
    private AnimatorSet mRightOutSet, mLeftInSet;
    private boolean mIsShowBack = false;
    /**
     * 晚安
     */
    private TextView mEvening;
    /**
     * 早安
     */
    private TextView mMorning;

    private RetrofitUtils retrofitUtils = new RetrofitUtils();
    private Map< String, String > map = new HashMap<>();
    private Button mDd;
    private List< Integer > pic_morning = new ArrayList<>();
    private List< Integer > pic_evening = new ArrayList<>();
    private ImageView mImgNight;
    private ImageView mImgMorning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initView();
        initData();
    }

    private void initView() {
        mEvening = findViewById( R.id.evening );
        mMorning = findViewById( R.id.morning );
        mFlCardBack = findViewById( R.id.fl_back );
        mFlCardFront = findViewById( R.id.fl_front );
        mFlContainer = findViewById( R.id.main_fl_container );
        mDd = (Button) findViewById( R.id.dd );
        mDd.setOnClickListener( this );
        mImgNight = (ImageView) findViewById( R.id.img_night );
        mImgMorning = (ImageView) findViewById( R.id.img_morning );
    }

    private void initData() {
        pic_morning.add( R.drawable.z1 );
        pic_morning.add( R.drawable.z2 );
        pic_morning.add( R.drawable.z3 );
        pic_morning.add( R.drawable.z4 );
        pic_morning.add( R.drawable.z5 );
        pic_morning.add( R.drawable.z6 );
        pic_morning.add( R.drawable.z7 );
        pic_morning.add( R.drawable.z8 );

        pic_evening.add( R.drawable.w1 );
        pic_evening.add( R.drawable.w2 );
        pic_evening.add( R.drawable.w3 );
        pic_evening.add( R.drawable.w4 );
        pic_evening.add( R.drawable.w5 );
        pic_evening.add( R.drawable.w6 );
        pic_evening.add( R.drawable.w7 );
        pic_evening.add( R.drawable.w8 );

        map.put( "key", "6166c8355775714ea3cd1ba1554e83dc" );
        initAnimatorSet(); // 设置动画
        setCameraDistance(); // 设置镜头距离
        setTextStyle();
        SharedPreferences sharedPreferences = getSharedPreferences( "data", 0 );
        boolean Evening = sharedPreferences.getBoolean( "YEvening", false );
        boolean Morning = sharedPreferences.getBoolean( "YMorning", false );
        int n = sharedPreferences.getInt( "pic",0 );
        if (Evening) {
            mEvening.setText( sharedPreferences.getString( "evening", "" ) );
            mFlCardFront.setVisibility( View.GONE );
            mDd.setText( "起床啦~" );
            mImgNight.setBackgroundResource( pic_evening.get( n ) );
            mIsShowBack = true;
        }
        if (Morning) {
            mMorning.setText( sharedPreferences.getString( "morning", "" ) );
            mFlCardBack.setVisibility( View.GONE );
            mDd.setText( "晚安~" );
            mImgMorning.setBackgroundResource( pic_morning.get( n ) );
            mIsShowBack = false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.dd:
                flipCard();
                break;
        }
    }

    private void setTextStyle() {
        AssetManager manager = getAssets();
        Typeface typeface = Typeface.createFromAsset( manager, "fonts/haibaoyuanyuan.ttf" );
        mMorning.setTypeface( typeface );
        mEvening.setTypeface( typeface );
    }

    // 翻转卡片
    public void flipCard() {
        Date date = new Date( System.currentTimeMillis() );
        SimpleDateFormat dateFormat = new SimpleDateFormat( "HH" );
        String time = dateFormat.format( date );
        Log.d( "time", time );
        int t = 0;
        // 正面朝上
        if (!mIsShowBack ) {
            if (Integer.parseInt( time ) >= 21){
                mFlCardBack.setVisibility( View.VISIBLE );
                mFlCardFront.setVisibility( View.VISIBLE );
                mRightOutSet.setTarget( mFlCardFront );
                mLeftInSet.setTarget( mFlCardBack );
                mIsShowBack = true;
                mDd.setText( "起床啦~" );
                getEvening();
                t = 0;
            }else {
                t = 1;
            }
        } else if (mIsShowBack) { // 背面朝上
            if ( Integer.parseInt( time ) <= 9 ){
                mFlCardBack.setVisibility( View.VISIBLE );
                mFlCardFront.setVisibility( View.VISIBLE );
                mRightOutSet.setTarget( mFlCardBack );
                mLeftInSet.setTarget( mFlCardFront );
                mIsShowBack = false;
                mDd.setText( "晚安~" );
                getMorning();
                t = 0;
            }else{
                t = 1;
            }
        }
        if (t == 0){
            mRightOutSet.start();
            mLeftInSet.start();
        }
    }

    private void getMorning() {
        final int n = (int) (Math.random() * 8);
        mImgMorning.setBackgroundResource( pic_morning.get( n ) );
        Call< Morning > call = retrofitUtils.getRetrofitIntreface().getMorning( map );
        call.enqueue( new Callback< Morning >() {
            @Override
            public void onResponse(Call< Morning > call, Response< Morning > response) {
                if (response.isSuccessful()) {
                    List< Morning.NewslistBean > list = response.body().getNewslist();
                    Morning.NewslistBean news = list.get( 0 );
                    String s = news.getContent();
                    SharedPreferences.Editor editor = getSharedPreferences( "data", 0 ).edit();
                    editor.putString( "morning", s );
                    editor.putInt( "pic",n );
                    editor.putBoolean( "YMorning", true );
                    editor.putBoolean( "YEvening", false );
                    editor.apply();
                    mMorning.setText( s );
                }
            }

            @Override
            public void onFailure(Call< Morning > call, Throwable t) {

            }
        } );
    }

    private void getEvening() {
        final int n = (int) (Math.random() * 8);
        mImgNight.setBackgroundResource( pic_evening.get( n ) );
        Call< Night > call2 = retrofitUtils.getRetrofitIntreface().getNight( map );
        call2.enqueue( new Callback< Night >() {
            @Override
            public void onResponse(Call< Night > call, Response< Night > response) {
                if (response.isSuccessful()) {
                    List< Night.NewslistBean > list = response.body().getNewslist();
                    Night.NewslistBean news = list.get( 0 );
                    SharedPreferences.Editor editor = getSharedPreferences( "data", 0 ).edit();
                    String s = news.getContent();
                    editor.putString( "evening", s );
                    editor.putInt( "pic",n );
                    editor.putBoolean( "YEvening", true );
                    editor.putBoolean( "YMorning", false );
                    editor.apply();
                    mEvening.setText( s );
                }
            }

            @Override
            public void onFailure(Call< Night > call, Throwable t) {

            }
        } );
    }

    private void initAnimatorSet() {
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator( this, R.animator.anim_out_xml );
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator( this, R.animator.anim_in );

        // 设置点击事件
        mRightOutSet.addListener( animatorListenerAdapter );
        mLeftInSet.addListener( animatorListenerAdapter );
    }

    // 改变视角距离, 贴近屏幕,这个必须设置，因为如果不这么做，沿着Y轴旋转的过程中有可能产生超出屏幕的3D效果。
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance( scale );
        mFlCardBack.setCameraDistance( scale );
    }

    AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart( animation );
            mFlContainer.setClickable( false );//在动画执行过程中，不许允许接收点击事件
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd( animation );
            mFlContainer.setClickable( true );//在动画执行过程中，不许允许接收点击事件
        }
    };


}
