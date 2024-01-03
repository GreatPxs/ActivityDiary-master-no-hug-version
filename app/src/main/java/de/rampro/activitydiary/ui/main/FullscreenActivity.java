package de.rampro.activitydiary.ui.main;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import de.rampro.activitydiary.R;
import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class FullscreenActivity extends AppCompatActivity {
    private TextView tv_tianqi;
    private TextView tv_wendu;
    private TextView tv_fengxiang;
    private TextView tv_fengli;
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    public String Cid;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinnerCities);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                Toast.makeText(FullscreenActivity.this, selectedCity, Toast.LENGTH_SHORT).show();// 这里可以执行其他与选中上海相关的逻辑
                if ("上海".equals(selectedCity)) {
                    Cid = "101021500";
                    queryWeather();// 假设上海的ID是"SH"
                }
                if ("北京".equals(selectedCity)) {
                    Cid = "101010100";
                    queryWeather();// 假设上海的ID是"SH"
                }
                if ("天津".equals(selectedCity)) {
                    Cid = "101030100";
                    queryWeather();
                }
                if ("广州".equals(selectedCity)) {
                    Cid = "101280101";
                    queryWeather();
                }
                if ("武汉".equals(selectedCity)) {
                    Cid = "101200101";
                    queryWeather();
                }
                if ("成都".equals(selectedCity)) {
                    Cid = "101270101";
                    queryWeather();
                }
                if ("昆明".equals(selectedCity)) {
                    Cid = "101290101";
                    queryWeather();
                }
                if ("重庆".equals(selectedCity)) {
                    Cid = "101040100";
                    queryWeather();
                }
                if ("深圳".equals(selectedCity)) {
                    Cid = "101280601";
                    queryWeather();
                }
                if ("西安".equals(selectedCity)) {
                    Cid = "101110101";
                    queryWeather();
                }
                if ("南京".equals(selectedCity)) {
                    Cid = "101190101";
                    queryWeather();
                }
                // 如果您还想对其他城市进行类似的处理，可以在这里添加更多的else if语句。
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { } // 什么都不做，因为我们只关心选中的项。
        });
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        tv_tianqi = (TextView) findViewById(R.id.tv_tianqi);
        tv_wendu = (TextView) findViewById(R.id.tv_wendu);
        tv_fengxiang = (TextView) findViewById(R.id.tv_fengxiang);
        tv_fengli = (TextView) findViewById(R.id.tv_fengli);

        HeConfig.init("HE2401021906121339", "d4624b941fbb4e3da8294e1a058738a7");
        HeConfig.switchToDevService();
        queryWeather();


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void queryWeather() {
        QWeather.getWeatherNow(FullscreenActivity.this, Cid, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
            public static final String TAG = "he_feng_now";

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ", e);
                System.out.println("Weather Now Error:" + new Gson());
            }

            @Override


            public void onSuccess(WeatherNowBean weatherBean) {
                //Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean));
                System.out.println("获取天气成功： " + new Gson().toJson(weatherBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK == weatherBean.getCode()) {
                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();
                    String tianqi=now.getText();
                    String wendu=now.getTemp()+"℃";
                    String fengli=now.getWindScale();
                    String fengxiang=now.getWindDir();

                    // Create a new Runnable to update the UI on the main thread
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            tv_tianqi.setText("当前天气:"+tianqi);
                            tv_wendu.setText("当前温度:"+wendu);
                            tv_fengxiang.setText("风向："+fengxiang);
                            tv_fengli.setText("风力："+fengli+"级");
                        }
                    };

                    // Get a handler for the main thread and post the Runnable to it
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(runnable);
                } else {
                    // Handle the case where the status is not OK here...
                }
            }
            });
    }

    ;


}