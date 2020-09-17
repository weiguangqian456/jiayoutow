package com.edawtech.jiayou.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.base.VsBaseFragment;
import com.edawtech.jiayou.config.base.common.VsBizUtil;
import com.edawtech.jiayou.config.base.common.VsUpdateAPK;
import com.edawtech.jiayou.config.bean.LoginInfo;
import com.edawtech.jiayou.config.bean.RedBagEntity;
import com.edawtech.jiayou.config.bean.ResultEntity;
import com.edawtech.jiayou.config.constant.DfineAction;
import com.edawtech.jiayou.config.constant.GlobalVariables;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.config.home.dialog.CustomShareDialog;
import com.edawtech.jiayou.config.home.entity.CustomShareEntity;
import com.edawtech.jiayou.config.home.test.ShareListener;
import com.edawtech.jiayou.config.service.VsCoreService;
import com.edawtech.jiayou.json.me.JSONArray;
import com.edawtech.jiayou.json.me.JSONException;
import com.edawtech.jiayou.json.me.JSONObject;
import com.edawtech.jiayou.net.test.RetrofitCallback;
import com.edawtech.jiayou.retrofit.ApiService;
import com.edawtech.jiayou.retrofit.RetrofitClient;
import com.edawtech.jiayou.ui.activity.AddressListActivity;
import com.edawtech.jiayou.ui.activity.CaptureActivity;
import com.edawtech.jiayou.ui.activity.CollectionActivity;
import com.edawtech.jiayou.ui.activity.GrowMoneyActivity;
import com.edawtech.jiayou.ui.activity.KcMyQcodeActivity;
import com.edawtech.jiayou.ui.activity.MainActivity;
import com.edawtech.jiayou.ui.activity.MyLoginActivity;
import com.edawtech.jiayou.ui.activity.RefuelBalanceActivity;
import com.edawtech.jiayou.ui.activity.VsLoginActivity;
import com.edawtech.jiayou.ui.activity.VsMyBalanceDetailActivity;
import com.edawtech.jiayou.ui.activity.VsMyInfoTextActivity;
import com.edawtech.jiayou.ui.activity.VsRechargeActivity;
import com.edawtech.jiayou.ui.activity.VsSetingActivity;
import com.edawtech.jiayou.ui.activity.WeiboShareWebViewActivity;
import com.edawtech.jiayou.ui.view.BadgeView;
import com.edawtech.jiayou.ui.view.CircleImageView;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.ImageFileUtils;
import com.edawtech.jiayou.utils.PreferencesUtils;
import com.edawtech.jiayou.utils.ToastUtils;
import com.edawtech.jiayou.utils.db.provider.VsNotice;
import com.edawtech.jiayou.utils.sp.SharePreferencesHelper;
import com.edawtech.jiayou.utils.tool.ArmsUtils;
import com.edawtech.jiayou.utils.tool.CheckLoginStatusUtils;
import com.edawtech.jiayou.utils.tool.CoreBusiness;
import com.edawtech.jiayou.utils.tool.CustomLog;
import com.edawtech.jiayou.utils.tool.IntentFilterUtils;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.NetStateChangedReceiver;
import com.edawtech.jiayou.utils.tool.NetUtils;
import com.edawtech.jiayou.utils.tool.SkipPageUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.edawtech.jiayou.utils.tool.ValidClickListener;
import com.edawtech.jiayou.utils.tool.VsNetWorkTools;
import com.edawtech.jiayou.utils.tool.VsUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.edawtech.jiayou.config.base.Const.REQUEST_CODE;

public class MineFragment extends VsBaseFragment implements android.view.View.OnClickListener {

    private static final String TAG = MineFragment.class.getSimpleName();
    private View mParent;
    private FragmentActivity mActivity;
    private TextView balance_textview_05;
    private String mRecommendInfo = "http://fir.im/phonevoice";
    private CircleImageView header_img;
    private RelativeLayout rl_become_qishou;
    private RelativeLayout rl_voice;

    /**
     * 更新检测
     */
    private static final char MSG_ID_SendUpgradeMsg = 71;

    /**
     * 查询余额成功
     */
    public final char MSG_ID_CheckBalanceSucceed = 90;
    /**
     * TOKEN错误
     */
    private final char MSG_TOKEN_ERROR = 96;

    /**
     * 查询余额失败
     */
    private final char MSG_ID_CheckBalanceFail = 91;
    /**
     * 二维码
     */
    private RelativeLayout vs_myselft_qcodelayout;
    /**
     * 显示手机号或者邮箱
     */
    private TextView vs_myselft_account;
    private final char MYSELF_SPEAKER_1 = 100;
    /**
     * 加载邀请人数成功（并且数量大于存储的数量）
     */
    private final char MYSELF_INVITE_1 = 200;
    /**
     * 加载邀请人数失败
     */
    private final char MYSELF_INVITE_2 = 201;
    private final char MYSELF_RICH_MESSAGE_3 = 300;
    private final char MYSELF_UPDATE_4 = 400;


    private LinearLayout ll_address, ll_recharge, ll_share, ll_scan_bind, ll_shop_manage, ll_my_code, ll_setting, ll_help, ll_elec_deal, collectionLl;

    private LinearLayout user_status;
    private ImageView iv_user_status;
    private TextView tv_user_status;

    private BadgeView customserviceBg;
    private LinearLayout customserviceLl;

    /**
     * 广播标志，防止崩溃
     */
    private int receiver_flag = -1;
    private int receiver1_flag = -1;
    private int receiver2_flag = -1;
    public final char MSG_ID_CheckMoneySucceed = 92;
    public final char MSG_ID_CheckMoneyFail = 93;

    /**
     * 红包套餐相关
     */
    private LinearLayout balance_layout_01, balance_layout_02, balance_layout_03;
    private TextView balance_textview_01, balance_textview_02, balance_textview_03, more_textview_07;

    public MainActivity activity;


    private SocializeListeners.SnsPostListener mShareListener = new SocializeListeners.SnsPostListener() {
        @Override
        public void onStart() {
            CustomLog.i(TAG, "开始分享...");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int eCode, SocializeEntity socializeEntity) {
            if (eCode == 200) {
                if (!"SINA".equals(share_media.name()) && !"SMS".equals(share_media.name())) {
                    Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_LONG).show();
                }
                CustomLog.i(TAG, "分享成功");
            } else {
                String eMsg = "";
                switch (eCode) {
                    case -101:
                        eMsg = "没有Oauth授权";
                        break;
                    case -102:
                        eMsg = "未知错误";
                        break;
                    case -103:
                        eMsg = "服务器没响应";
                        break;
                    case -104:
                        eMsg = "初始化失败";
                        break;
                    case -105:
                        eMsg = "参数错误";
                        break;
                    case 40000:
                        eMsg = "取消分享";
                        break;
                    default:
                        break;
                }

                Toast.makeText(mContext, eMsg, Toast.LENGTH_SHORT).show();
            }
            CustomLog.i(TAG, "Code[" + eCode + "] " + ", share_media is " + share_media.name());
        }
    };
    private UMSocialService mController;
    private boolean isInit = false;
    private FragmentManager supportFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CustomLog.i(TAG, "MainFragment------onCreateView(),...");
        View view = inflater.inflate(R.layout.my_frament, container, false);
     //  FitStateUtils.setImmersionStateMode(getActivity(), R.color.public_color_EC6941);
        //如果是用的v4的包，则用getActivity().getSuppoutFragmentManager();
        supportFragmentManager = getActivity().getSupportFragmentManager();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注册EventBus
        EventBus.getDefault().register(this);
        CustomLog.i(TAG, "MainFragment------onActivityCreated(),...");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (MainActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        //显示用户信息
        showUserInfo();
        // 拉取个人信息
        VsBizUtil.getInstance().getMyInfoText(mContext);
        // 分享内容拉取
        String uid = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_KcId, "");
        if (uid != null && uid.length() > 0) {
            VsBizUtil.getInstance().getShareContent(mContext);
        }

        if (isInit) {
            // 绑定后刷新手机
            if (!VsUtil.isNull(VsUserConfig.getDataString(mContext, VsUserConfig.JKey_PhoneNumber))) {
                // 查余额
                searchBalance();
                searchmoney();
                String nick = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_MyInfo_Nickname);
                if (nick != null && nick.length() > 0) {
                    vs_myselft_account.setText(nick);
                } else {
                    vs_myselft_account.setText(VsUserConfig.getDataString(mContext, VsUserConfig.JKey_PhoneNumber));
                }
            } else {
                vs_myselft_account.setText("未登录");
                more_textview_07.setText("");
                balance_textview_03.setText("0分钟");
//                balance_textview_01.setText("0.00");
//                balance_textview_05.setText("0.00");
            }
        }

        // 头像显示
        mParent = getView();
        //新加的签到
        mParent.findViewById(R.id.fl_sign).setOnClickListener(new ValidClickListener() {
            @Override
            public void onValidClick() {
                if (CheckLoginStatusUtils.isLogin()) {
//                    SimBackActivity.launch(getActivity(), SimBackEnum.MINE_INTEGRAL, null);
                } else {
                    ToastUtils.show(mContext, "请先登录哦");
                }
            }
        });
        //签到
        mParent.findViewById(R.id.tv_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckLoginStatusUtils.isLogin()) {
//                    SimBackActivity.launch(getActivity(), SimBackEnum.MINE_INTEGRAL, null);
                } else {
                    ToastUtils.show(mContext, "请先登录哦");
                }
            }
        });
        //头像
        header_img = mParent.findViewById(R.id.myself_head);
        Glide.with(mContext)
                .load(CheckLoginStatusUtils.isLogin() ? new File(ImageFileUtils.mSaveHeadPortraitPath) : null)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.mall_user_image_defult)
                        .skipMemoryCache(true)//不使用内存缓存
                        .diskCacheStrategy(DiskCacheStrategy.NONE))//不使用本地缓存
                .into(header_img);

        setCustomerServiceBadgeView(getEaseMobNoReadMsgCount());  //环信
        getUserInfo();
        if (VsUtil.checkHasAccount(mContext)) {
            initData();
        }
    }

    /**
     * 显示用户基本信息
     */
    private void showUserInfo() {
        SharePreferencesHelper sp = new SharePreferencesHelper(activity, CommonParam.SP_NAME);
        int level = (int) sp.getSharePreference("level", 0);
        String levelName = (String) sp.getSharePreference("levelName", "");
        Log.e("fxx", "level=" + level + "        levelName=" + levelName);

        if (tv_user_status != null) {
            String nickname = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_MyInfo_Nickname);
            String phone = MyApplication.MOBILE;

            if (nickname != null && nickname.length() > 0) {
                vs_myselft_account.setText(nickname);
            } else {
                if (phone != null) vs_myselft_account.setText(phone);
            }

            tv_user_status.setText(levelName);
            switch (level) {
                case 0:
                    iv_user_status.setImageResource(R.drawable.mall_user_normal);
                    break;
                case 1:
                    iv_user_status.setImageResource(R.drawable.mall_user_masonry);
                    break;
                case 2:
                    iv_user_status.setImageResource(R.drawable.mall_user_goad);
                    break;
                case 3:
                    iv_user_status.setImageResource(R.drawable.mall_user_silver);
                    break;
                default:
                    break;
            }

            if (MyApplication.isLogin) {
                user_status.setVisibility(View.VISIBLE);
            } else {
                user_status.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 消息未读数
     *
     * @return
     */
    private int getEaseMobNoReadMsgCount() {
        int noReadMsgCount = 0;
//        if (ChatClient.getInstance().isLoggedInBefore()) {
//            Conversation conversation = ChatClient.getInstance().chatManager().getConversation(EASEMOB_IMSERVER);
//            noReadMsgCount = conversation.unreadMessagesCount();
//        }
//        Log.e(EASEMOB_TAGNAME, TAG + "未读消息msg===》" + noReadMsgCount);
        return noReadMsgCount;
    }

    private void setCustomerServiceBadgeView(int noReadNum) {
        if (customserviceBg != null) {
            if (0 != noReadNum) {
                customserviceBg.setVisibility(View.VISIBLE);
                customserviceBg.setText(noReadNum + "");
                if (noReadNum > 10) {
                    customserviceBg.setText("10+");
                }
            } else {
                customserviceBg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (myRedReceiver != null && receiver_flag == 0) {
            unIntentFilter(mContext, myRedReceiver);
        }
        if (loginReceiver != null && receiver1_flag == 0) {
            unIntentFilter(mContext, loginReceiver);
        }
        if (shareReceiver != null && receiver2_flag == 0) {
            unIntentFilter(mContext, shareReceiver);
        }
        //注销EventBus
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    /**
     * Create a new instance of DetailsFragment, initialized to show the text at
     * 'index'.
     */
    public static MineFragment newInstance(int index) {
        MineFragment f = new MineFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            init();
//            setCustomerServiceBadgeView(VsApplication.getInstance().getNoReadMsg());   //智齿
            setCustomerServiceBadgeView(getEaseMobNoReadMsgCount());  //环信
        }
    }

    /**
     * 重新获取用户信息
     */
    private void getUserInfo() {
        ApiService api = RetrofitClient.getInstance(mContext).Api();
        retrofit2.Call<ResultEntity> call = api.getUserLevel();
        call.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                if (response.body() == null) {
                    return;
                }
                ResultEntity result = response.body();
                LogUtils.e("获取个人数据 -> result:", response.toString());
                if (REQUEST_CODE == result.getCode()) {
                    RedBagEntity entity = JSON.parseObject(result.getData().toString(), RedBagEntity.class);
                    String userLevel = entity.getLevel();
                    if (TextUtils.isEmpty(userLevel)) {
                        PreferencesUtils.putInt(MyApplication.getContext(), VsUserConfig.JKey_MyInfo_Level, Integer.parseInt(userLevel));
                    }
                    if (entity.getRider() != null) {
                        PreferencesUtils.putBoolean(MyApplication.getContext(), VsUserConfig.JKey_MyInfo_Rider, true);
                    } else {
                        PreferencesUtils.putBoolean(MyApplication.getContext(), VsUserConfig.JKey_MyInfo_Rider, false);
                    }
                    String levelName = entity.getLevelName();
                    PreferencesUtils.putString(MyApplication.getContext(), VsUserConfig.JKey_MyInfo_LevelName, levelName);
                    if (!StringUtils.isEmpty(levelName)) {
                        if (tv_user_status != null) {
                            tv_user_status.setText(levelName);
                        }
                        if (iv_user_status != null) {
                            switch (userLevel) {
                                case "0":
                                    iv_user_status.setImageResource(R.drawable.mall_user_normal);
                                    break;
                                case "1":
                                    iv_user_status.setImageResource(R.drawable.mall_user_masonry);
                                    break;
                                case "2":   //白金会员
                                    iv_user_status.setImageResource(R.drawable.mall_user_goad);
                                    break;
                                case "3":
                                    iv_user_status.setImageResource(R.drawable.mall_user_silver);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
            }
        });
    }

    public void init() {
        if (isInit) {
            return;
        }
        isInit = true;
        CustomLog.i(TAG, "MainFragment++++++init(),...");

        mParent = getView();
        mActivity = getActivity();
        initView();

        searchBalance();
        searchmoney();
        getUserInfo();
        if (VsUtil.checkHasAccount(mContext)) {
            initData();
        }

        //网络监听广播
        registerNetChangedRecervier();
//        initShare();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
//        params.put("uid", VsUserConfig.getDataString(mContext,VsUserConfig.JKey_KcId));
//        params.put("appId", "dudu");

        RetrofitClient.getInstance(mContext).Api()
                .getWallet2(params)
                .enqueue(new RetrofitCallback<ResultEntity>() {
                    @Override
                    protected void onNext(ResultEntity result) {
                        RedBagEntity redBagEntity = JSON.parseObject(result.getData().toString(), RedBagEntity.class);
                        if (balance_textview_01 != null) {
                            balance_textview_01.setText(redBagEntity.getRed());
                        }
                        if (balance_textview_05 != null) {
                            balance_textview_05.setText(redBagEntity.getExchangePoint());
                        }
                    }
                });
    }

    private void registerNetChangedRecervier() {
        new IntentFilterUtils() {
            @Override
            public NetStateChangedReceiver getNetStateChangedReceiver() {
                return new NetStateChangedReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        handleOnNetStateChanged();
                    }

                    @Override
                    public void handleOnNetStateChanged() {
                        if (NetUtils.isNetworkAvailable(mContext)) {   //有网
                            GlobalVariables.netmode = 4;
                            searchBalance();
                            searchmoney();
                        }
                    }
                };
            }
        }.register(mContext);
    }

    /**
     * 查询余额
     */
    private void searchBalance() {
        unregisterKcBroadcast();
        // 绑定广播接收器
        if (!VsNetWorkTools.isNetworkAvailable(mContext)) {
            mToast.show("网络连接失败，请检查网络");
            return;
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalVariables.actionSeaRchbalance);
        vsBroadcastReceiver = new KcBroadcastReceiver();
        mContext.registerReceiver(vsBroadcastReceiver, filter);
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("uid", VsUserConfig.getDataString(mContext, VsUserConfig.JKey_KcId));
        CoreBusiness.getInstance().startThread(mContext, GlobalVariables.INTERFACE_BALANCE, DfineAction.authType_UID, treeMap, GlobalVariables.actionSeaRchbalance);
    }

    /**
     * 查询红包余额
     */
    private void searchmoney() {
        // unIntentFilter(mContext, myRedReceiver);
        initIntentFilter(GlobalVariables.actionSeaRchmoney, myRedReceiver);
        receiver_flag = 0;
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("uid", VsUserConfig.getDataString(mContext, VsUserConfig.JKey_KcId));
        CoreBusiness.getInstance().startThread(mContext, GlobalVariables.INTERFACE_SEACHE_MONEY, DfineAction.authType_UID, treeMap, GlobalVariables.actionSeaRchmoney);
    }

    /**
     * 广播接收红包结果
     */
    private BroadcastReceiver myRedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GlobalVariables.actionSeaRchmoney)) {
                String jStr = intent.getStringExtra(GlobalVariables.VS_KeyMsg);
                Message message = mBaseHandler.obtainMessage();
                Bundle bundle = new Bundle();
                // {"msg":"success","code":0,"data":{"balance":0,"amount":0,"drawing":0,
                // "status":"y","app_id":"wind","uid":"2343802","ctime":"2016-01-22
                // 16:21:29","frozen":0}}
                try {
                    JSONObject jData = new JSONObject(jStr);
                    String retStr = jData.getString("code");
                    if (retStr.equals("0")) {
                        JSONObject jb = jData.getJSONObject("data");
                        // 余额信息
                        String money = jb.getString("balance");
                        String status = jb.getString("status");
                        // 存储是否由于上级
                        VsUserConfig.setData(mContext, VsUserConfig.Jey_Invited_By, jb.getString("invited_by"));
                        // String bindbank = jData.getString("bind_bank");
                        bundle.putString("red_wallet", money);
                        bundle.putString("status", status);
                        // bundle.putString("bind_bank", bindbank);
                        message.what = MSG_ID_CheckMoneySucceed;
                    } else {
                        if (retStr.equals("-99")) {
                            dismissProgressDialog();
                            if (!VsUtil.isCurrentNetworkAvailable(mContext)) return;
                        }
                        bundle.putString("msg", jData.getString("reason"));
                        message.what = MSG_ID_CheckMoneyFail;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    bundle.putString("msg", getResources().getString(R.string.servicer_busying));
                    message.what = MSG_ID_CheckMoneyFail;
                }
                message.setData(bundle);
                mBaseHandler.sendMessage(message);
            }
        }

    };

    // 更新昵称
    private BroadcastReceiver loginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(VsCoreService.VS_ACTION_NICK)) {
//                String jStr = intent.getStringExtra("nick");
//                if (jStr != null && jStr.length() > 0) {
//                    vs_myselft_account.setText(jStr);
//                } else {
//                    if (VsUserConfig.getDataString(mContext, VsUserConfig.JKey_MyInfo_Mobile) !=
//                            null) {
//                        vs_myselft_account.setText(VsUserConfig.getDataString(mContext,
//                                VsUserConfig.JKey_MyInfo_Mobile));
//                    } else {
//                        vs_myselft_account.setText("未设置");
//                    }
//                }
                // Message message = mBaseHandler.obtainMessage();
                // Bundle bundle = new Bundle();
                // message.what = MSG_ID_CheckMoneySucceed;
                // message.setData(bundle);
                // mBaseHandler.sendMessage(message);
            }
        }

    };

    private BroadcastReceiver shareReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(VsUserConfig.JKey_GET_MY_SHARE_BRO)) {
                String jStr = intent.getStringExtra("data");
                if (jStr != null && jStr.length() > 0) {
//                    initShare();
                    com.edawtech.jiayou.utils.LogUtils.e("分享状态修改");
                }
            }
        }

    };

    @Override
    protected void handleKcBroadcast(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.handleKcBroadcast(context, intent);

        String jStr = intent.getStringExtra(GlobalVariables.VS_KeyMsg);
        Message message = mBaseHandler.obtainMessage();
        Bundle bundle = new Bundle();

        try {
            JSONObject getjson = new JSONObject(jStr);
            String retStr = getjson.getString("code");

            if (retStr.equals("0")) {
                // balance":20325,"gift_expire_time":"2015-01-30
                // 22:03:34","status":"y","basic_balance":20325,"
                // + ""expire_time":"2018-03-05
                // 16:12:59","app_id":"wind","uid":"1234567","
                // + ""ctime":"2015-12-30
                // 21:57:04","gift_flag":"n","gift_balance":0,"vip_info":"2016-03-12
                // 16:12:59
                JSONObject jData = getjson.getJSONObject("data");
                // 基本账户余额
                String basicbalance = jData.getString("basic_balance");
                bundle.putString("basicbalance", basicbalance);
                // 账户总余额
                String balance = jData.getString("balance");
                bundle.putString("balance", balance);
                // 有效期
                String valid_date = jData.getString("expire_time");
                bundle.putString("valid_date", valid_date);

                // 赠送余额
                String giftbalance = jData.getString("gift_balance");
                bundle.putString("giftbalance", giftbalance);

                String gift_expire_time = jData.getString("gift_expire_time");
                bundle.putString("gift_expire_time", gift_expire_time);

                VsUserConfig.setData(mContext, VsUserConfig.JKey_Balance, balance);
                VsUserConfig.setData(mContext, VsUserConfig.JKey_BasicBalance, basicbalance);
                VsUserConfig.setData(mContext, VsUserConfig.JKey_GiftBalance, giftbalance);

                VsUserConfig.setData(mContext, VsUserConfig.JKey_GiftExpireTime, gift_expire_time);
                VsUserConfig.setData(mContext, VsUserConfig.JKey_ValidDate, valid_date);

                // 包月套餐信息列表
                JSONArray mJSONArray = jData.getJSONArray("package_list");
                int i = 0;
                int len = mJSONArray.length();
                JSONObject jObj = null;
                while (i < len && (jObj = (JSONObject) mJSONArray.get(i)) != null) {
                    retStr = jObj.getString("package_name");// 包月名称
                    if (retStr.equals(""))
                        retStr.replace(getResources().getString(R.string.call_back), "");
                    bundle.putString("packagename" + i, retStr);
                    bundle.putString("call_time" + i, jObj.getString("left_call_time"));// 包月剩余时间
                    bundle.putString("eff_time" + i, jObj.getString("effect_time"));// 包月开始时间
                    bundle.putString("exp_time" + i, jObj.getString("expire_time"));// 包月到期时间
                    bundle.putString("is_time" + i, jObj.getString("is_time"));// 是否显示分钟数
                    i++;
                }
                bundle.putInt("packageNumber", i);
                message.what = MSG_ID_CheckBalanceSucceed;
            } else if (retStr.equals("1003")) {
                message.what = MSG_TOKEN_ERROR;
            } else {
                if (retStr.equals("-99")) {
                    dismissProgressDialog();
                    if (!VsUtil.isCurrentNetworkAvailable(mContext)) {
                        return;
                    }
                }
                bundle.putString("msg", getjson.getString("msg"));
                message.what = MSG_ID_CheckBalanceFail;
            }

        } catch (Exception e) {
            e.printStackTrace();
            bundle.putString("msg", getResources().getString(R.string.servicer_busying));
            message.what = MSG_ID_CheckBalanceFail;
        }
        message.setData(bundle);
        mBaseHandler.sendMessage(message);
    }

    /**
     * 初始化分享功能
     */
//    private void initShare() {
//        String product = DfineAction.RES.getString(R.string.product);
//        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
//        // 添加微信平台
//        UMWXHandler wxHandler = new UMWXHandler(getActivity(), DfineAction.WEIXIN_APPID, DfineAction.WEIXIN_APPSECRET);
//        wxHandler.addToSocialSDK();
//        // 添加微信朋友圈
//        UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(), DfineAction.WEIXIN_APPID, DfineAction.WEIXIN_APPSECRET);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.addToSocialSDK();
//        String url = "";
//        if (shareContent().indexOf("http") > 0) {
//            url = shareContent().substring(shareContent().indexOf("http"));
//        }
//        // 设置微信好友分享内容
//        WeiXinShareContent weixinContent = new WeiXinShareContent();
//        weixinContent.setShareContent(shareContent());// product + "电话微信分享");
//        weixinContent.setTitle(product);
//        weixinContent.setTargetUrl(url);// DfineAction.WAPURI);
//        Bitmap iconMap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
//        UMImage localImage = new UMImage(getActivity(), compressBitmap(iconMap));
//        weixinContent.setShareImage(localImage);
//        mController.setShareMedia(weixinContent);
//
//        // 设置微信朋友圈分享内容
//        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent(shareContent());// (product + "电话分享朋友圈");
//        circleMedia.setTitle(product);
//        circleMedia.setShareImage(localImage);
//        circleMedia.setTargetUrl(url);// DfineAction.WAPURI);
//        mController.setShareMedia(circleMedia);
//
//        // 添加QQ分享
//        String qqAppId = DfineAction.QqAppId;
//        String qqAppKey = DfineAction.QqAppKey;
//        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(), qqAppId, qqAppKey);
//        qqSsoHandler.addToSocialSDK();
//        // 设置分享内容
//        QQShareContent qqShareContent = new QQShareContent();
//        qqShareContent.setShareContent(shareContent());// (product + "电话分享测试qq");
//        qqShareContent.setTitle(product);
//        qqShareContent.setTargetUrl(url);// DfineAction.WAPURI);
//        qqShareContent.setShareImage(localImage);
//        mController.setShareMedia(qqShareContent);
//
//        // 添加QQ空间分享
//        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(), qqAppId, qqAppKey);
//        qZoneSsoHandler.addToSocialSDK();
//        // 设置分享内容
//        QZoneShareContent qZoneShareContent = new QZoneShareContent();
//        qZoneShareContent.setShareContent(shareContent());// (product + "电话分享qqZone");
//        qZoneShareContent.setTitle(product);
//        qZoneShareContent.setTargetUrl(url);// DfineAction.WAPURI);
//        qZoneShareContent.setShareImage(localImage);
//        mController.setShareMedia(qZoneShareContent);
//
//        // //添加SMSfenx分享
//        SmsHandler smsHandler = new SmsHandler();
//        smsHandler.addToSocialSDK();
//        // 设置短信分享内容
//        SmsShareContent smsShareContent = new SmsShareContent();
//        smsShareContent.setShareContent(shareContent());// (product + "电话短信分享");
//        mController.setShareMedia(smsShareContent);
//
//        // 添加新浪微博分享
//        // SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
//        // sinaSsoHandler.addToSocialSDK();
//        // // 设置新浪SSO handler
//        // mController.getConfig().setSsoHandler(sinaSsoHandler);
//
//        // 设置分享内容
//        SinaShareContent sinaShareContent = new SinaShareContent();
//        sinaShareContent.setShareContent(shareContent());// (product + "电话分享Sina");
//        sinaShareContent.setTitle("        T.M.");
//        sinaShareContent.setTargetUrl(url);// DfineAction.WAPURI);
//        sinaShareContent.setShareImage(localImage);
//        mController.setShareMedia(sinaShareContent);
//    }

    /**
     * 压缩图片
     *
     * @param bitMap
     */
    private Bitmap compressBitmap(Bitmap bitMap) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 99;
        int newHeight = 99;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
        return newBitMap;
    }

    /**
     * 设置分享内容
     *
     * @return
     */
    public String shareContent() {
        // 推荐好友
        String mRecommendInfo = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_GET_MY_SHARE);
        if ((mRecommendInfo == null) || "".equals(mRecommendInfo)) {
            mRecommendInfo = DfineAction.InviteFriendInfo;
        }
        return mRecommendInfo;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 初始化界面
     */
    private void initView() {
        user_status = mParent.findViewById(R.id.user_status);

        // 我的基本信息
        vs_myselft_qcodelayout = mParent.findViewById(R.id.vs_myselft_qcodelayout);
        // 个人基本信息，帐号
        balance_textview_05 = mParent.findViewById(R.id.balance_textview_05);
        vs_myselft_account = mParent.findViewById(R.id.vs_myselft_account);

        iv_user_status = mParent.findViewById(R.id.iv_user_status);
        tv_user_status = mParent.findViewById(R.id.tv_user_status);
        rl_become_qishou = mParent.findViewById(R.id.rl_become_qishou);
        rl_voice = mParent.findViewById(R.id.rl_voice);
        ll_shop_manage = mParent.findViewById(R.id.ll_shop_manage);


        String nickname = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_MyInfo_Nickname);
        String phone = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_PhoneNumber);
        if (nickname != null && nickname.length() > 0) {
            vs_myselft_account.setText(nickname);
        } else {
            if (phone != null) vs_myselft_account.setText(phone);
        }

//        vs_myselft_number.setText(VsUserConfig.getDataString(mContext, VsUserConfig
//                .JKey_PhoneNumber));
        String uid = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_KcId);
        if (uid == null || uid.length() == 0) {
            vs_myselft_account.setText("未登录");
        }

        int level = PreferencesUtils.getInt(MyApplication.getContext(), VsUserConfig.JKey_MyInfo_Level);
        String levelName = PreferencesUtils.getString(MyApplication.getContext(), VsUserConfig.JKey_MyInfo_LevelName);
        if (tv_user_status != null) {
            tv_user_status.setText(levelName);
            switch (level) {
                case 0:
                    iv_user_status.setImageResource(R.drawable.mall_user_normal);
                    break;
                case 1:
                    iv_user_status.setImageResource(R.drawable.mall_user_masonry);
                    break;
                case 2:
                    iv_user_status.setImageResource(R.drawable.mall_user_goad);
                    break;
                case 3:
                    iv_user_status.setImageResource(R.drawable.mall_user_silver);
                    break;
                default:
                    break;
            }

            if (VsUserConfig.getDataString(mContext, VsUserConfig.JKey_KcId, "").isEmpty()) {
                user_status.setVisibility(View.GONE);
            } else {
                user_status.setVisibility(View.VISIBLE);
            }
        }

//        if (VsUtil.isLogin(mContext.getResources().getString(R.string.login_prompt4), mContext)) {
//            user_status.setVisibility(View.GONE);
//        } else {
//            user_status.setVisibility(View.VISIBLE);
//        }

        // 注册拉取个人信息成功广播
        initIntentFilter(VsCoreService.VS_ACTION_NICK, loginReceiver);
        // 分享内容广播
        initIntentFilter(VsUserConfig.JKey_GET_MY_SHARE_BRO, shareReceiver);
        receiver1_flag = 0;
        receiver2_flag = 0;
        // 拉取个人信息
        VsBizUtil.getInstance().getMyInfoText(mContext);

        // 我的话费
//        rl_my_account = (RelativeLayout) mParent.findViewById(R.id.rl_my_account);
//        my_balance_tv = (TextView) mParent.findViewById(R.id.my_balance_tv);

//        vs_seting_exit = (RelativeLayout) mParent.findViewById(R.id.vs_seting_exit);


        // 高亮红点和文字提醒
//		iv_red_dot = (ImageView) mParent.findViewById(R.id.iv_red_dot);
//		vs_about_help = (RelativeLayout) mParent.findViewById(R.id.vs_about_help);


        VsUserConfig.setData(mContext, VsUserConfig.JKey_FRIEND_INVITE, "http://fir.im/phonevoice");
        mRecommendInfo = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_FRIEND_INVITE);

        balance_layout_01 = (LinearLayout) mParent.findViewById(R.id.balance_layout_01);
        balance_layout_02 = (LinearLayout) mParent.findViewById(R.id.balance_layout_02);
        balance_layout_03 = (LinearLayout) mParent.findViewById(R.id.balance_layout_03);
        balance_textview_01 = (TextView) mParent.findViewById(R.id.balance_textview_01);
        balance_textview_02 = (TextView) mParent.findViewById(R.id.balance_textview_02);
        balance_textview_03 = (TextView) mParent.findViewById(R.id.balance_textview_03);
        more_textview_07 = (TextView) mParent.findViewById(R.id.more_textview_07);

        // ================订单相关============
//        checkMoreOrderLl = (LinearLayout) mParent.findViewById(R.id.ll_check_more_order);
//        waitPayLl = (LinearLayout) mParent.findViewById(R.id.ll_wait_pay);
//        waitSendLl = (LinearLayout) mParent.findViewById(R.id.ll_wait_send);
//        waitReceiveLl = (LinearLayout) mParent.findViewById(R.id.ll_wait_receive);
//        doneOrderLl = (LinearLayout) mParent.findViewById(R.id.ll_order_done);
//========骑手和联系人============
        rl_become_qishou.setOnClickListener(this);
        rl_voice.setOnClickListener(this);
        //==================function单项相关
        ll_address = (LinearLayout) mParent.findViewById(R.id.ll_address);
        ll_recharge = (LinearLayout) mParent.findViewById(R.id.ll_recharge);
        ll_share = (LinearLayout) mParent.findViewById(R.id.ll_share);
        ll_my_code = (LinearLayout) mParent.findViewById(R.id.ll_my_code);
        ll_scan_bind = (LinearLayout) mParent.findViewById(R.id.ll_scan_bind);
        ll_setting = (LinearLayout) mParent.findViewById(R.id.ll_setting);
        ll_shop_manage.setOnClickListener(this);
        ll_help = (LinearLayout) mParent.findViewById(R.id.ll_help);
        ll_elec_deal = (LinearLayout) mParent.findViewById(R.id.ll_elec_deal);
        collectionLl = (LinearLayout) mParent.findViewById(R.id.ll_collection);

        customserviceBg = (BadgeView) mParent.findViewById(R.id.bg_customservice);
        customserviceLl = (LinearLayout) mParent.findViewById(R.id.ll_customservice);

        vs_myselft_qcodelayout.setOnClickListener(this);

        //=======红包相关==========
        balance_layout_01.setOnClickListener(this);
        balance_layout_02.setOnClickListener(this);
        balance_layout_03.setOnClickListener(this);

        //========订单============
//        checkMoreOrderLl.setOnClickListener(this);
//        waitPayLl.setOnClickListener(this);
//        waitSendLl.setOnClickListener(this);
//        waitReceiveLl.setOnClickListener(this);
//        doneOrderLl.setOnClickListener(this);

        //========function=======
        ll_address.setOnClickListener(this);
        ll_recharge.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        ll_my_code.setOnClickListener(this);
        ll_scan_bind.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        ll_elec_deal.setOnClickListener(this);
        collectionLl.setOnClickListener(this);
        customserviceLl.setOnClickListener(this);

        // 检测更新
//		if (VsUserConfig.getDataBoolean(mContext, VsUserConfig.HAS_NEW_VERSION, false)) {
//			vs_cate_update_tv.setVisibility(View.VISIBLE);
//			vs_cate_update_tv.setText("有新版本");
//			vs_cate_update_tv.setTextColor(getResources().getColor(R.color.vs_gray_light));
//			updateFlag = true;
//		} else {
//			vs_cate_update_tv.setText("已是最新版本" + getVersion());
//			vs_cate_update_tv.setTextColor(getResources().getColor(R.color.vs_gray_deep));
//			updateFlag = false;
//		}

        /*
         * if (VsUserConfig.getDataString(mContext,
         * VsUserConfig.JKey_UpgradeUrl).length() > 5) {
         * iv_upgrade.setVisibility(View.VISIBLE); }
         */
        // 配置版本
        String cate = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_cateTypes, "");
        JSONObject json, js;
        try {
            json = new JSONObject(cate);
            String version = json.getString("cate");
            js = json.getJSONObject("menu");
            String red_wallet = js.getString("red_wallet");
            String pay_center = js.getString("pay_center");
            String share_to = js.getString("share_to");
            String my_code = js.getString("my_code");
            VsUserConfig.setData(mContext, VsUserConfig.JKey_Invite_person, my_code);
            String scan_bind = js.getString("scan_bind");
            String setting = js.getString("setting");
            String renew_ver = js.getString("renew_ver");
            String dail_way = js.getString("dail_way");
            String change_pwd = js.getString("change_pwd");
            String abount_us = js.getString("abount_us");
            String login_out = js.getString("login_out");
            String my_card = js.getString("my_card");

//			if ("all".equals(version)) {
//				balance_layout_01.setVisibility(View.VISIBLE);
//				vs_my_saoma.setVisibility(View.VISIBLE);
//				rl_vs_setting.setVisibility(View.VISIBLE);
//				rl_cate_update.setVisibility(View.VISIBLE);
//				rl_vs_calltype.setVisibility(View.GONE);
//				rl_vs_changepword.setVisibility(View.GONE);
//				vs_myself_about.setVisibility(View.GONE);
//				vs_seting_exit.setVisibility(View.GONE);
//				myself_lin1.setVisibility(View.GONE);
//				myself_lin2.setVisibility(View.GONE);
//				myself_lin3.setVisibility(View.GONE);
//
//				if ("y".equals(red_wallet)) {
//					balance_layout_01.setVisibility(View.VISIBLE);
//				} else {
//					balance_layout_01.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(pay_center)) {
//					rl_recharge.setVisibility(View.VISIBLE);
//				} else {
//					rl_recharge.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(my_card)) {
//					rl_excharge.setVisibility(View.VISIBLE);
//				} else {
//					rl_excharge.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(share_to)) {
//					rl_share.setVisibility(View.VISIBLE);
//					myself_line1.setVisibility(View.VISIBLE);
//				} else {
//					rl_share.setVisibility(View.GONE);
//					myself_line1.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(my_code)) {
//					vs_my_qcode.setVisibility(View.VISIBLE);
//					myself_line2.setVisibility(View.VISIBLE);
//				} else {
//					vs_my_qcode.setVisibility(View.GONE);
//					myself_line2.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(scan_bind)) {
//					vs_my_saoma.setVisibility(View.VISIBLE);
//					myself_line3.setVisibility(View.VISIBLE);
//				} else {
//					vs_my_saoma.setVisibility(View.GONE);
//					myself_line3.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(setting)) {
//					rl_vs_setting.setVisibility(View.VISIBLE);
//				} else {
//					rl_vs_setting.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(renew_ver)) {
//					rl_cate_update.setVisibility(View.VISIBLE);
//					myself_lin1.setVisibility(View.VISIBLE);
//				} else {
//					rl_cate_update.setVisibility(View.GONE);
//					myself_lin1.setVisibility(View.GONE);
//				}
//
//			} else {
//				balance_layout_01.setVisibility(View.GONE);
//				vs_my_saoma.setVisibility(View.GONE);
//				rl_vs_setting.setVisibility(View.GONE);
//				rl_vs_calltype.setVisibility(View.VISIBLE);
//				rl_vs_changepword.setVisibility(View.VISIBLE);
//				vs_myself_about.setVisibility(View.VISIBLE);
//				vs_seting_exit.setVisibility(View.VISIBLE);
//				myself_lin1.setVisibility(View.VISIBLE);
//				myself_lin2.setVisibility(View.VISIBLE);
//				myself_lin3.setVisibility(View.VISIBLE);
//				myself_lin4.setVisibility(View.VISIBLE);
//
//				if ("y".equals(pay_center)) {
//					rl_recharge.setVisibility(View.VISIBLE);
//				} else {
//					rl_recharge.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(my_card)) {
//					rl_excharge.setVisibility(View.VISIBLE);
//				} else {
//					rl_excharge.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(share_to)) {
//					rl_share.setVisibility(View.VISIBLE);
//					myself_line1.setVisibility(View.VISIBLE);
//				} else {
//					rl_share.setVisibility(View.GONE);
//					myself_line1.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(my_code)) {
//					vs_my_qcode.setVisibility(View.VISIBLE);
//					myself_line2.setVisibility(View.VISIBLE);
//				} else {
//					vs_my_qcode.setVisibility(View.GONE);
//					myself_line2.setVisibility(View.GONE);
//				}
//
//				if ("y".equals(dail_way)) {
//					rl_vs_calltype.setVisibility(View.VISIBLE);
//				} else {
//					rl_vs_calltype.setVisibility(View.GONE);
//				}
//				if ("y".equals(change_pwd)) {
//					rl_vs_changepword.setVisibility(View.VISIBLE);
//				} else {
//					rl_vs_changepword.setVisibility(View.GONE);
//				}
//				if ("y".equals(abount_us)) {
//					vs_myself_about.setVisibility(View.VISIBLE);
//				} else {
//					vs_myself_about.setVisibility(View.GONE);
//				}
//				if ("y".equals(login_out)) {
//					vs_seting_exit.setVisibility(View.VISIBLE);
//				} else {
//					vs_seting_exit.setVisibility(View.GONE);
//				}

//			}
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } finally {
            if (!(cate != null && cate.length() != 0)) {

            }
        }
        // 加载消息中心信息[本地保存的消息]
        VsNotice.loadNoticeData(mContext);

        // 获取上报错误日志标准
        VsBizUtil.getInstance().reportControl(mContext);
    }

    public Bitmap createQRImage(String url, final int width, final int height) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void HandleRightNavBtn() {
        // TODO Auto-generated method stub
        super.HandleRightNavBtn();
        VsUtil.startActivity("3015", mContext, null);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getActivity().getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.version_unkown);
        }
    }

    private String getTime(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(time);
            Date d2 = new Date(System.currentTimeMillis());// 你也可以获取当前时间
            long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            // System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
            return days + "";
        } catch (Exception e) {
            return "0";
        }
    }


    @Override
    protected void handleBaseMessage(Message msg) {
        super.handleBaseMessage(msg);
        switch (msg.what) {
            case MSG_ID_CheckBalanceSucceed:
                Bundle bbundle = msg.getData();
                float i;
                if (bbundle.getString("balance").equals("null")) {
                    i = 0;
                } else {
                    i = Float.parseFloat(bbundle.getString("basicbalance"));
                }
                i = i / 100;
                String s = String.valueOf(i);
                VsUserConfig.setData(mActivity, VsUserConfig.JKey_Balance_Left, s);
//                balance_textview_05.setText(s + "");

                // 套餐处理
                int len = bbundle.getInt("packageNumber") - 1;
                //最后一次充值的套餐名字
                String taocanName = bbundle.getString("packagename" + len);
                // 最后一次的上一次套餐的到期时间
                String start = bbundle.getString("eff_time" + len);
                // 到期时间
                String end = bbundle.getString("exp_time" + len);
                // 剩余通话时长
                String left = bbundle.getString("call_time" + len);
                String is_time = bbundle.getString("is_time" + len);
                if ("y".equals(is_time)) {     //显示时长
                    more_textview_07.setVisibility(View.VISIBLE);
                    more_textview_07.setText(end);
                    if (taocanName != null && taocanName.length() != 0) {
                        balance_textview_03.setText(left + "分钟");
                        VsUserConfig.setData(mContext, "baonian", left + "分钟");
                    } else {
                        balance_textview_03.setText("0分钟");
                    }
                } else {       //显示天数
                    more_textview_07.setVisibility(View.VISIBLE);
                    more_textview_07.setText(end);
                    balance_textview_03.setText(getTime(end) + "天");
                    VsUserConfig.setData(mContext, "baonian", getTime(end) + "天");
                }

                // 修改字体颜色
                // SpannableStringBuilder builder = new
                // SpannableStringBuilder(balance_money.getText().toString());
                // SpannableStringBuilder builderbase = new
                // SpannableStringBuilder(balance_money_base.getText().toString());
                // SpannableStringBuilder buildergif = new
                // SpannableStringBuilder(balance_money_gif.getText().toString());
                //
                // builder.setSpan(new
                // ForegroundColorSpan(getResources().getColor(color.money_text_color)),
                // 4, 9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                // builderbase.setSpan(new
                // ForegroundColorSpan(getResources().getColor(color.money_text_color)),
                // 5, 10,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                // buildergif.setSpan(new
                // ForegroundColorSpan(getResources().getColor(color.money_text_color)),
                // 5, 9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //
                // balance_money.setText(builder);
                // balance_money_base.setText(builderbase);
                // balance_money_gif.setText(buildergif);

                // vs_balance_money_minute.setText("约" +
                // bbundle.getString("calltime") + "分钟");
                // balance_time.setText(bbundle.getString("valid_date"));
                // int moneybase = Integer.parseInt(bbundle.getString("calltime"));
                // int moneybao = 0;
                // int moneyToal=0;
                // try {
                // int n = 0;

                // ArrayList<String> mNameData = new ArrayList<String>();
                // ArrayList<String> mInfoData = new ArrayList<String>();
                // while (n < len) {
                // if (n == 0) {
                // vs_balance_taocan.setVisibility(View.GONE);
                // vs_balance_taocan.setText(bbundle.getString("packagename" + n));
                // }
                // mNameData.add(bbundle.getString("packagename" + n));
                // if(bbundle.getString("call_time" + n).equals("-1")){
                // mInfoData.add("开始时间:" + bbundle.getString("eff_time" + n) +
                // "\n到期时间:" + bbundle.getString("exp_time" + n));
                // }else{
                // mInfoData.add("开始时间:" + bbundle.getString("eff_time" + n) +
                // "\n到期时间:" + bbundle.getString("exp_time" + n) + "\n剩余时长:" +
                // bbundle.getString("call_time" + n) + "分钟");
                // }
                // moneybao = moneybao +
                // Integer.parseInt(bbundle.getString("call_time" + n));
                // n++;
                // mNameData.add("测试一下");
                // mInfoData.add("开始时间:" + "xxxx:xxx:xxx" + "\n到期时间:" +
                // "xxxx:xxxx:xxxx" + "\n剩余时长:" + "1000" + "分钟");
                // i++;
                // }
                // if (n > 0)
                //
                // adapter = new RechargeMealAdapter(mContext);
                // adapter.setData(mNameData, mInfoData);
                // mBytcOtherInfoListview.setAdapter(adapter);
                // vs_balance_taocan.setOnClickListener(new mOnClickListener("包月信息",
                // mNameData, mInfoData));
                // moneyToal = moneybao + moneybase;
                // }
                // catch (Exception e) {
                // // TODO: handle exception
                // }
                break;
            case MSG_ID_CheckMoneySucceed:
                float si;
                if (msg.getData().getString("red_wallet").equals("null")) {
                    si = 0;
                } else {
                    si = Float.parseFloat(msg.getData().getString("red_wallet"));
                }
                si = si / 100;
                String ss = String.valueOf(si);
                VsUserConfig.setData(mContext, VsUserConfig.JKey_Money, ss);
//                balance_textview_01.setText(ss + "");
                String status = msg.getData().getString("status");
                VsUserConfig.setData(mContext, VsUserConfig.JKey_Status, status);
                // if(bind_wx.endsWith("y")){
                // weixin_bind_text.setText("已绑定微信号");
                // }else{
                // weixin_bind_text.setText("未绑定微信号");
                // }
                break;
            case MSG_ID_CheckMoneyFail:
                // unionpay_bind_change.setText("查询失败");
                // vs_red_money.setText("查询失败");
                // mToast.show(msg.getData().getString("msg"), Toast.LENGTH_SHORT);
                break;
            case MSG_TOKEN_ERROR:// 余额不足
                showMessageDialog_token("温馨提示", "您的账号已经在另外一个设备上登陆，请您重新登陆", false);
                vs_myselft_account.setText("未登录");
//                vs_myselft_number.setText("点击登录");
//                balance_textview_01.setText("0.00");
                balance_textview_03.setText("0分钟");
                // more_textview_07.setText("--");
//                balance_textview_05.setText("0.00");
                break;
            case MSG_ID_CheckBalanceFail:
                // mToast.show(msg.getData().getString("msg"), Toast.LENGTH_SHORT);
                break;
            case MYSELF_SPEAKER_1:
                break;
            case MYSELF_INVITE_1:
//			iv_red_dot.setVisibility(View.VISIBLE);
                break;
            case MYSELF_INVITE_2:
//			iv_red_dot.setVisibility(View.GONE);
                break;
            case MYSELF_RICH_MESSAGE_3:
                break;
            case MYSELF_UPDATE_4:
                break;
            case MSG_ID_SendUpgradeMsg:
                dismissProgressDialog();
                if (VsUserConfig.getDataString(mContext, VsUserConfig.JKey_UpgradeUrl).length() > 5) {
                    startUpdateApk();
                } else {
                    mToast.show("您的" + DfineAction.RES.getString(R.string.product) + "已是最新版本，无需升级！", Toast.LENGTH_SHORT);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 开始更新
     */
    private void startUpdateApk() {
        VsUpdateAPK mUpdateAPK = new VsUpdateAPK(mContext);
        // mUpdateAPK.DowndloadThread(KcUserConfig.getDataString(mContext,
        // KcUserConfig.JKey_UpgradeUrl), true);
        mUpdateAPK.NotificationDowndload(VsUserConfig.getDataString(mContext, VsUserConfig.JKey_UpgradeUrl), true, null);
    }


    @Override
    public void onClick(View v) {
        int order_flag = 0;
        // TODO Auto-generated method stub
        if (VsUtil.isFastDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.ll_recharge:        // 充值中心
                if (MyApplication.isLogin) {
                    startActivity(mContext, VsRechargeActivity.class);
                } else {
                    startActivity(mContext,VsLoginActivity.class);
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.login_prompt3));
                }
                break;
            case R.id.ll_shop_manage:       //商家管理
                ToastUtil.showMsg("该模块正在开发中，敬请期待！");
//                if (VsUtil.isLogin(mContext.getResources().getString(R.string.nologin_auto_hint), mContext)) {
// //                   Toast.makeText(mActivity, "版本开发中....", Toast.LENGTH_SHORT).show();
//                    startActivity(mContext, MerchantInfoActivity.class);
//                }
                break;
            case R.id.rl_become_qishou:     //骑手
                ToastUtil.showMsg("该模块正在开发中，敬请期待！");
//                ToastUtils.show(getContext(),"该模块正在开发中，敬请期待！");
//                    if(VsUtil.isLogin(mContext.getResources().getString(R.string.nologin_auto_hint),mContext)) {
//                        if(PreferencesUtils.getBoolean(VsApplication.getContext(),VsUserConfig.JKey_MyInfo_Rider)) {
//                            startActivity(mContext, RiderInfoActivity.class);
//                        }else {
//                            startActivity(mContext, SignUpActivity.class);
//                        }
//                    }
                break;
            case R.id.rl_voice:   //语音通话
//                if (VsUtil.isLogin(mContext.getResources().getString(R.string.nologin_auto_hint), mContext)) {
//                    //注意v4包的配套使用
////                    VsMainActivity vsMainActivity=new VsMainActivity();
////                    fragment = new VsDialFragment();
////                    supportFragmentManager.beginTransaction()
////                            .addToBackStack(null)
////                            .replace(R.id.realtabcontent, fragment)
////                            .show(fragment)
////                            .commit();
//
//                    vsMain.showFragment(2);
//                    vsMain.setView(2, false);
//                    vsMain.setFragmentIndicator(2);
//
//                 /*   Intent intent=new Intent();
//                    intent.putExtra("indicator",2);
//
//                    intent.setClass(getActivity(),VsMainActivity.class);
//                    startActivity(intent);*/
//
//
////                    onIndicateListener.onIndicate(v,2);
////                    Log.d("onIndicateListener执行了", "onClick: "+onIndicateListener);
//                    // setIndicator(2);
//
////                    if (VsUtil.isLogin(mContext.getResources().getString(R.string.login_prompt3), mContext)) {
////                        startActivity(mContext, DialMainActivity.class);
////				iv_red_dot.setVisibility(View.GONE);
//                    //  }
//
//                }
                break;
            case R.id.ll_help:  //帮助中心
                Intent helperIntent = new Intent();
                String[] aboutBusiness = new String[]{mContext.getResources().getString(R.string.help), "", "http://paas.edawtech.com/help/dudu_help.html"};
                helperIntent.putExtra("AboutBusiness", aboutBusiness);
                helperIntent.setClass(mContext, WeiboShareWebViewActivity.class);
                startActivity(helperIntent);
                break;
            case R.id.ll_elec_deal:     //电子协议
                final String dealUrl = "file:///android_asset/shop_service_terms.html";
                Intent dealIntent = new Intent();
                dealIntent.setClass(mContext, WeiboShareWebViewActivity.class);
                String[] aboutBusiness_deal = new String[]{mContext.getString(R.string.welcome_main_elecdeal), "service", dealUrl};
                dealIntent.putExtra("AboutBusiness", aboutBusiness_deal);
                startActivity(dealIntent);
                break;
            case R.id.ll_setting:
                // 设置
                Intent intent = new Intent(mContext, VsSetingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_collection:        //我的收藏
                if (MyApplication.isLogin) {
                    Intent intent_address = new Intent(mContext, CollectionActivity.class);
                    startActivity(intent_address);
                } else {
                    startActivity(mContext,VsLoginActivity.class);
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.nologin_auto_hint));
                }
                break;
            case R.id.ll_customservice:  //未读客服消息
                if (VsUtil.isLogin(mContext.getResources().getString(R.string.nologin_auto_hint), mContext)) {
//                    SobotUtils.getInstance(mContext).startChat();  //智齿
                    //环信
//                    if (ChatClient.getInstance().isLoggedInBefore()) {
//                        Intent chatIntent = new IntentBuilder(getActivity()).setTargetClass(ChatActivity.class).setServiceIMNumber(EASEMOB_IMSERVER).setVisitorInfo
//                                (EaseMobMsgHelper.createVisitorInfo("")).setTitleName(COMPANY_NAME).build();
//                        startActivity(chatIntent);
//                    } else {
//                        loginEaseMob();
//                    }
                }
                break;
            case R.id.vs_myselft_qcodelayout:// 进入我的个人信息
                if (MyApplication.isLogin) {
                    startActivity(new Intent(mContext, VsMyInfoTextActivity.class));
                } else {
                    startActivity(mContext,VsLoginActivity.class);

                    ToastUtil.showMsg(mContext.getResources().getString(R.string.login_prompt4));
                }
                break;
            case R.id.ll_share:     //分享
                if (MyApplication.isLogin) {
                    toShare();
                } else {
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.nologin_auto_hint));
                }
                break;
            case R.id.ll_my_code:       //我的二维码
                if (MyApplication.isLogin) {
                    SkipPageUtils.getInstance(mContext).skipPage(KcMyQcodeActivity.class, "code", mRecommendInfo);
                } else {
                    startActivity(mContext,VsLoginActivity.class);
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.nologin_auto_hint));
                }
                break;
            case R.id.ll_scan_bind:     //扫码绑定
                if (MyApplication.isLogin) {
                    SkipPageUtils.getInstance(mContext).skipPage(CaptureActivity.class, "code", "2");
                } else {
                    startActivity(mContext,VsLoginActivity.class);
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.nologin_auto_hint));
                }
                break;
            case R.id.balance_layout_01:    //成长金
                if (MyApplication.isLogin) {
                    startActivity(new Intent(mContext,GrowMoneyActivity.class));
                } else {
                    startActivity(mContext,VsLoginActivity.class);
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.login_prompt2));
                }
                break;
            case R.id.balance_layout_02:    //会员权益
                if (MyApplication.isLogin) {
                    Intent myBablanceDetailIntent = new Intent(mContext,VsMyBalanceDetailActivity.class);
                    myBablanceDetailIntent.putExtra("flag","2");
                    startActivity(myBablanceDetailIntent);
                } else {
                    startActivity(mContext,VsLoginActivity.class);
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.login_prompt2));
                }
                break;
            case R.id.balance_layout_03:     //我的积分
                if (MyApplication.isLogin) {
                    startActivity(new Intent(mContext,RefuelBalanceActivity.class));
                } else {
                    startActivity(mContext,VsLoginActivity.class);
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.login_prompt2));
                }
                break;
            case R.id.ll_address:           //收货地址
                if (MyApplication.isLogin) {
                    startActivity(new Intent(mContext,AddressListActivity.class));
                } else {
                    startActivity(mContext,VsLoginActivity.class);
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.nologin_auto_hint));
                }
                break;
            default:
                break;
        }
    }


//    private void loginEaseMob() {
//        String uid = VsUserConfig.getDataString(getActivity(), VsUserConfig.JKey_KcId);
//        if (!StringUtils.isEmpty(uid)) {
//            ChatClient.getInstance().login(uid, EASEMOB_DEFAULT_PASSWORD, new com.hyphenate.helpdesk.callback.Callback() {
//                @Override
//                public void onSuccess() {
//                    Log.e(EASEMOB_TAGNAME, "demo login succedd");
//                    Intent intent = new IntentBuilder(getActivity()).setTargetClass(ChatActivity.class).setServiceIMNumber(EASEMOB_IMSERVER).setVisitorInfo(EaseMobMsgHelper
//                            .createVisitorInfo("")).setTitleName(COMPANY_NAME).build();
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onError(int i, String s) {
//                    Log.e(EASEMOB_TAGNAME, "demo login fail");
//                }
//
//                @Override
//                public void onProgress(int i, String s) {
//                    Log.e(EASEMOB_TAGNAME, "demo login progress");
//                }
//            });
//        } else {
//            ToastUtils.show(getActivity(), "登录后方可联系客服");
//        }
//    }

    // 申明广播
    private void initIntentFilter(String action, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        mContext.registerReceiver(receiver, filter);
    }

    // 注销广播
    private void unIntentFilter(Context mContext, BroadcastReceiver receiver) {
        if (receiver != null) {
            mContext.unregisterReceiver(receiver);
            receiver = null;
        }
    }

    // 判断文件是否存在
    public boolean fileIsExists(String strFile) {
        if (strFile.length() != 0) {
            try {
                File f = new File(strFile);
                if (!f.exists()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private void toShare() {
        if (getFragmentManager() == null) {
            ToastUtil.showMsg("分享异常 - 05F");
        }
        String product = DfineAction.RES.getString(R.string.product);
        String url = "";
        String content = shareContent();
        if (content.indexOf("http") > 0) {
            url = content.substring(content.indexOf("http"));
        }
        Bitmap iconMap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        CustomShareDialog
                .getInstance()
                .removeShare(CustomShareDialog.SHARE_TYPE_SINA)
                .removeShare(CustomShareDialog.SHARE_TYPE_QZONE)
                .initEntity(new CustomShareEntity(content, url, product, compressBitmap(iconMap)))
                .setShareActivity(new ShareListener())
                .show(getFragmentManager(), "");
    }

    /**
     * EventBus 回调，登录成功后  刷新用户信息
     *
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUserInfo(LoginInfo.DataBean info) {
        LogUtils.e("fxx", "登录成功  刷新用户信息");
        showUserInfo();
    }

    /**
     * 退出登录后   刷新用户信息
     *
     * @param isRefresh
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUserInfo(boolean isRefresh) {
        LogUtils.e("fxx", "退出登录成功  刷新用户信息");
        if (isRefresh) {

        }
    }
}
