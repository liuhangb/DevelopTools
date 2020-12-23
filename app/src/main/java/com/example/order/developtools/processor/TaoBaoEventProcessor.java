package com.example.order.developtools.processor;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.order.developtools.config.TaoBaoConfig;
import com.example.order.developtools.utils.DateUtils;
import com.example.order.developtools.utils.GestureActionUtils;
import com.example.order.developtools.utils.LogUtil;
import com.example.order.developtools.utils.PrintUtils;
import com.example.order.developtools.utils.ThreadUtils;
import com.example.order.developtools.widget.AlarmJob;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lh, 2020/12/9
 */
public class TaoBaoEventProcessor extends BaseEventProcessor implements TaoBaoConfig.ConfigCallback {

    private Map<String, AccessibilityNodeInfo> mEventMap = new HashMap<>(10);
    private final AlarmJob mAlarmJob;
    private String mKeyWords = TaoBaoConfig.Companion.getMKeyWords();

    public TaoBaoEventProcessor(@NonNull AccessibilityService service) {
        super(service);
        String mTaskTime = TaoBaoConfig.Companion.getMTaskTime();
        mAlarmJob = new AlarmJob(mContext, mAlarmRunnable);

        mAlarmJob.start(mTaskTime);
    }

    @Override
    public void onTaskTimeChanged(String date) {
        mAlarmJob.cancel();
        mAlarmJob.start(date);
    }

    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public String desiredPackageName() {
        return "com.taobao.taobao";
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo rootNodeInfo = mService.getRootInActiveWindow();
        if (rootNodeInfo == null) {
            return;
        }
//        clickById(mService.getRootInActiveWindow(), "com.taobao.taobao:id/button_cart_charge", TextView.class.getName());

        handleTooManyPeople();
        boolean isContainInvalidDesc = checkContentByText(rootNodeInfo, "失效宝贝", TextView.class.getName());
        if (isContainInvalidDesc) {
            clickById(rootNodeInfo, "com.taobao.taobao:id/btn_back", TextView.class.getName());
            if (!isExpired()) {
                ThreadUtils.runOnMainUI(mAlarmRunnable, 500);
            }
        } else {
            if (checkContentByText(rootNodeInfo, "提交订单", TextView.class.getName())) {
                clickByCustomText(rootNodeInfo, "提交订单", TextView.class.getName());
            }

        }
//                boolean isContainPayNow = checkContentByText(event, "立即付款", TextView.class.getName());
//                Log.d(TAG, "立即付款 :" + isContainPayNow);
    }

//    /**
//     * 是否需要更新Node
//     * @param event
//     * @return
//     */
//    private boolean isNeedUpdateNode(AccessibilityEvent event) {
//        AccessibilityNodeInfo rootInActiveWindow = mService.getRootInActiveWindow();
//        if (rootInActiveWindow == null || event == null) {
//            return false;
//        }
//
//        return (TYPE_WINDOW_CONTENT_CHANGED == event.getEventType() && checkContentById(rootInActiveWindow, "com.taobao.taobao:id/button_cart_charge", TextView.class.getName()))
//                || mNodeInfo == null;
//    }

    private boolean isExpired() {
        String mTaskTime = TaoBaoConfig.Companion.getMTaskTime();
        long expiredTime = DateUtils.dateToStamp(mTaskTime) + 60 * 1000;
        LogUtil.d("expiredTime: " + expiredTime);
        return System.currentTimeMillis() > expiredTime;
    }

    /**
     * 处理前方拥挤提示
     */
    private void handleTooManyPeople() {
        AccessibilityNodeInfo rootInActiveWindow = mService.getRootInActiveWindow();
        clickByCustomText(rootInActiveWindow, "我知道了", TextView.class.getName());
    }

    private Runnable mAlarmRunnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            if (TextUtils.isEmpty(mKeyWords)) {
                Toast.makeText(mContext, "关键词为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mService != null && mService.getRootInActiveWindow() != null &&
                    desiredPackageName().equals(mService.getRootInActiveWindow().getPackageName())) {
                AccessibilityNodeInfo rootInActiveWindow = mService.getRootInActiveWindow();
                String s = PrintUtils.printNodeInfo(rootInActiveWindow);
                Log.d(TAG, "printNodeInfo: " + s);
                // 找到目标
                AccessibilityNodeInfo destNodeByFlatNode = findDestNodeByFlatNode(rootInActiveWindow, mKeyWords, CheckBox.class.getName());
                if (destNodeByFlatNode == null) {
                    DisplayMetrics displayMetrics = mService.getResources().getDisplayMetrics();
                    final int height = displayMetrics.heightPixels;
                    final int top = (int) (height * .25);
                    final int bottom = (int) (height * .75);
                    GestureActionUtils.performSwipeDown(mService, bottom, top, new AccessibilityService.GestureResultCallback() {
                        @Override
                        public void onCompleted(GestureDescription gestureDescription) {
                            super.onCompleted(gestureDescription);
                            Log.d(TAG, "ACTION_SCROLL_FORWARD");
                            ThreadUtils.postOnMainUI(mAlarmRunnable);
                        }
                    });
//                    clickById(rootInActiveWindow, "com.taobao.taobao:id/cart_recycler_view","android.support.v7.widget.RecyclerView",
//                            AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);

                    return;
                }

                // 判断目标是否可以选中，如果不能选中则需要下拉刷新界面
                if (!destNodeByFlatNode.isEnabled()) {
                    DisplayMetrics displayMetrics = mService.getResources().getDisplayMetrics();
                    final int height = displayMetrics.heightPixels;
                    final int top = (int) (height * .25);
                    final int bottom = (int) (height * .75);
                    GestureActionUtils.performSwipeDown(mService, top, bottom, new AccessibilityService.GestureResultCallback() {
                        @Override
                        public void onCompleted(GestureDescription gestureDescription) {
                            super.onCompleted(gestureDescription);
                            // 刷新完, 再重新执行一遍流程
                            mAlarmRunnable.run();
                        }
                    });
                } else {
                    if (!destNodeByFlatNode.isChecked()) {
                        destNodeByFlatNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        // 因为控件有变更，点击结算时，必须要获取最新的getRootInActiveWindow，而且要延迟执行，否则无法生效
                        ThreadUtils.runOnMainUI(new Runnable() {
                            @Override
                            public void run() {
                                clickById(mService.getRootInActiveWindow(), "com.taobao.taobao:id/button_cart_charge", TextView.class.getName());
                            }
                        }, 200);
                    } else {
                        clickById(mService.getRootInActiveWindow(), "com.taobao.taobao:id/button_cart_charge", TextView.class.getName());

                    }

                }


            }
        }
    };

    @Override
    public void onKeyWordsChanged(@NotNull String keyWords) {
        mKeyWords = keyWords;
    }
}
