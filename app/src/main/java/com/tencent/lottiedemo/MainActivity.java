package com.tencent.lottiedemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.OnCompositionLoadedListener;

import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {
    public static final String IMAGE_PATH = "";
    public static final String JSON_PATH = "";

    LottieAnimationView mLottieAnim;
    private boolean mIsJsonLoaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLottieAnim = findViewById(R.id.lottie_demo);
        loadLottieFromFile();
    }

    public void loadLottieFromFile() {
        if (mIsJsonLoaded) {
            mLottieAnim.playAnimation();
        }
        OnCompositionLoadedListener compositionListener = new OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                if (composition == null) {
                    return;
                }
                mLottieAnim.setComposition(composition);
                mLottieAnim.playAnimation();
                mIsJsonLoaded = true;
            }
        };
        try {
            //Raw 文件加载
            LottieComposition.Factory.fromRawFile(this, R.raw.data, compositionListener);
            //asset 文件加载
            LottieComposition.Factory.fromAssetFileName(this, "/WeAccept.json", compositionListener);
            //自定义文件目录加载
            LottieComposition.Factory.fromInputStream(new FileInputStream("/WeAccept.json"), compositionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLottieAnim(LottieComposition composition) {
        mLottieAnim.setComposition(composition);
        mLottieAnim.setImageAssetDelegate(new ImageAssetDelegate() {
            @Override
            public Bitmap fetchBitmap(LottieImageAsset asset) {
                try {
                    AssetManager.AssetInputStream fileInputStream = (AssetManager.AssetInputStream) getAssets().open("Images.WeAccept" + asset.getFileName());
//                    FileInputStream fileInputStream = new FileInputStream(asset.getFileName());
                    return BitmapFactory.decodeStream(fileInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        //设置监听器
        //custom animation speed or duration
        mLottieAnim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mLottieAnim.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

        mLottieAnim.playAnimation();
    }


}
