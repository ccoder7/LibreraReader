package com.foobnix.pdf.info;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.adclient.android.sdk.listeners.ClientAdListener;
import com.adclient.android.sdk.type.AdType;
import com.adclient.android.sdk.type.ParamsType;
import com.adclient.android.sdk.view.AbstractAdClientView;
import com.adclient.android.sdk.view.AdClientView;
import com.foobnix.android.utils.Dips;
import com.foobnix.android.utils.LOG;
import com.foobnix.android.utils.TxtUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

public class ADS {

    public static boolean isNativeEnable = false;

    public static int ADS_MAX_POS = 5;
    public static long FULL_SCREEN_TIMEOUT = TimeUnit.SECONDS.toMillis(20);

    public static void onPause(final AdView adView) {
        if (adView != null) {
            adView.pause();
        }
    }

    public static void onPauseNative(final NativeExpressAdView adView) {
        if (adView != null) {
            adView.pause();
        }
    }

    public static AdRequest adRequest = new AdRequest.Builder()//
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)//
            .addTestDevice("E0A9E8CB1E71AE8C3F6F64D692E914DB")//
            .addTestDevice("465253044271C009F461C81CFAC406BA")//
            .addTestDevice("ECC8DAFFDFD6BE5A3C799695FC4853E8")//
            .build();//

    public static int[] getNumberOfColumsAndWidth() {
        final int width = Dips.screenWidth();
        final int height = Dips.screenHeight();
        if (width > height && width > Dips.dpToPx(600)) {
            return new int[] { 2, width / 2 };
        }
        return new int[] { 1, width };
    }

    public static void onResumeNative(final NativeExpressAdView adView) {
        if (adView != null) {
            AppsConfig.checkIsProInstalled(adView.getContext());
            adView.resume();
        }
    }

    public static void onResume(final AdView adView) {

        if (adView != null) {
            AppsConfig.checkIsProInstalled(adView.getContext());
            adView.resume();
        }
    }

    public static void onResumeEP(final AdClientView adClientView) {
        if (adClientView != null) {
            adClientView.resume();
        }
    }

    public static void onPauseEP(final AdClientView adClientView) {
        if (adClientView != null) {
            adClientView.pause();
        }
    }

    public static void activateEP(final Activity a, AdClientView adViewNative) {
        AdClientView adClientView = a.findViewById(R.id.adClientView);

        if (!AppsConfig.IS_EP) {
            adClientView.setVisibility(View.GONE);
            adClientView = null;
            return;
        }

        HashMap<ParamsType, Object> configuration = new HashMap<ParamsType, Object>();
        configuration.put(ParamsType.AD_PLACEMENT_KEY, "58296e9c9472044364aa6770d8409ede");
        configuration.put(ParamsType.ADTYPE, AdType.NATIVE_AD.toString());
        configuration.put(ParamsType.AD_SERVER_URL, "http://appservestar.com/");
        adClientView.setConfiguration(configuration);

        adClientView.addClientAdListener(new ClientAdListener() {
            @Override
            public void onReceivedAd(AbstractAdClientView adClientView) {
                Log.d("TestApp", "--> Ad received callback.");
            }

            @Override
            public void onFailedToReceiveAd(AbstractAdClientView adClientView) {
                Log.d("TestApp", "--> Ad failed to be received callback.");
            }

            @Override
            public void onClickedAd(AbstractAdClientView adClientView) {
                Log.d("TestApp", "--> Ad clicked callback.");
            }

            @Override
            public void onLoadingAd(AbstractAdClientView adClientView, String message) {
                Log.d("TestApp", "--> Ad loaded callback.");
            }

            @Override
            public void onClosedAd(AbstractAdClientView adClientView) {
                Log.d("TestApp", "--> Ad closed callback.");
            }
        });
        adClientView.load();

    }

    public static void activateNative(final Activity a, NativeExpressAdView adViewNative) {
        try {

            final FrameLayout frame = (FrameLayout) a.findViewById(R.id.adFrame);
            if (!AppsConfig.checkIsProInstalled(a)) {

                final String unitID = AppsConfig.ADMOB_NATIVE_SMALL;

                if (TxtUtils.isEmpty(unitID) || Build.VERSION.SDK_INT <= 9) {
                    frame.setVisibility(View.GONE);
                    return;
                }

                try {
                    Class.forName("android.os.AsyncTask");
                } catch (Throwable ignore) {
                }

                destoryNative(adViewNative);
                adViewNative = new NativeExpressAdView(a);
                adViewNative.setAdUnitId(unitID);
                int adSizeHeight = Dips.screenHeightDP() / 8;
                LOG.d("adSizeHeight", adSizeHeight);
                adViewNative.setAdSize(new AdSize(AdSize.FULL_WIDTH, Math.max(82, adSizeHeight)));

                adViewNative.loadAd(ADS.adRequest);

                if (frame != null) {
                    frame.setVisibility(View.VISIBLE);
                    frame.removeAllViews();
                    frame.addView(adViewNative);
                }

                adViewNative.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int arg0) {
                        frame.removeAllViews();
                        frame.setVisibility(View.GONE);
                        // frame.addView(proAdsLayout(a));
                    }
                });
            } else {
                frame.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            LOG.e(e);
        }

    }

    public static synchronized void activate(final Activity a, final AdView adView) {
        try {
            final FrameLayout frame = (FrameLayout) a.findViewById(R.id.adFrame);
            if (!AppsConfig.checkIsProInstalled(a)) {

                String unitID = AppsConfig.ADMOB_CLASSIC;
                if (TxtUtils.isEmpty(unitID) || Build.VERSION.SDK_INT < 10) {
                    frame.setVisibility(View.GONE);
                    return;
                }
                try {
                    Class.forName("android.os.AsyncTask");
                } catch (Throwable ignore) {
                }
                showAds(a, adView, unitID);
            } else {
                frame.setVisibility(View.GONE);
            }

            // if (ADSRemote.adID != null) {
            // unitID = ADSRemote.adID;
            // showAds(a, adView, unitID);
            // } else {
            // ADSRemote.initID(a, new ResultResponse<String>() {
            //
            // @Override
            // public void onResult(String unitID) {
            // ADSRemote.adID = unitID;
            // showAds(a, adView, unitID);
            // }
            // });
            // }

        } catch (final Exception e) {
            Log.e("TEST", "load ads", e);
        }
    }

    private static void showAds(final Activity a, AdView adView, String unitID) {
        LOG.d("DEBUG", "AD_UNIT_ID:" + unitID);

        destory(adView);

        adView = new AdView(a);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(unitID);

        FrameLayout frame = (FrameLayout) a.findViewById(R.id.adFrame);
        frame.setVisibility(View.VISIBLE);
        frame.removeAllViews();
        frame.addView(adView);

        final View proAds = proAdsLayout(a);

        adView.loadAd(adRequest);
        adView.setAdListener(new MyAdListener(proAds, frame));
    }

    private static View proAdsLayout(final Activity a) {
        final View proAds = LayoutInflater.from(a).inflate(R.layout.ads, null, false);
        proAds.setVisibility(View.VISIBLE);
        proAds.findViewById(R.id.text2).setSelected(true);
        proAds.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                Urls.openPdfPro(a);
            }
        });

        return proAds;
    }

    public static class MyAdListener extends AdListener {

        private final View proAds;
        private final FrameLayout frame;

        public MyAdListener(final View view, final FrameLayout frame) {
            this.proAds = view;
            this.frame = frame;
        }

        @Override
        public void onAdFailedToLoad(final int errorCode) {
            LOG.d("Error code onAdFailedToLoad", errorCode);
            try {
                frame.removeAllViews();
                frame.addView(proAds);
            } catch (Exception e) {
                LOG.e(e);
            }
        }

    };

    public static void destoryNative(NativeExpressAdView adView) {
        if (adView != null) {
            adView.destroy();
            adView = null;
        }
    }

    public static void destory(AdView adView) {
        if (adView != null) {
            adView.destroy();
            adView = null;
        }
    }

}
