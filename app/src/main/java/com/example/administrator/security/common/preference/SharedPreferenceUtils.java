package com.example.administrator.security.common.preference;


public class SharedPreferenceUtils {

    public static final String LOG_TAG = SharedPreferenceUtils.class.getSimpleName();
    //软件初次打开标志
    public static final String IS_FIRST_ENTER = "is_first_enter";
    //用户账号
    public static final String ACCOUNT = "account";
    //用户对应的服务器连接
    public static final String APP_GET_BASE_URL = "App_Get_BaseUrl";
    //用户token
    public static final String TOKEN = "token";
    //用户角色
    public static final String ROLE = "role";
    //用户密码加密后字符串
    public static final String PASSWORD = "psw";
    //是否记住密码
    public static final String IS_REMENBER = "isremenber";
    //当前时间
    public static final String CURRICULUM_DATE = "curriculumDate";
    //是否只在WIFI状态下下载
    public static final String IS_WIFI = "wifi";
    //邀请码
    public static final String INVITE_CODE="inviteCode";
    //保存当前任务的homeWorkId


    //服务器版本号
    public static final String SERVER_VERSION="server_version";

    /**
     * 存储软件初次打开标志
     */
    public static void saveIsFirst(boolean isFirst) {
        SharePreferenceHelper.getInstance().setBooleanValue(IS_FIRST_ENTER, isFirst);
    }

    /**
     * 获取软件初次打开标志
     */
    public static boolean getIsFirst() {
        return SharePreferenceHelper.getInstance().getBooleanValue(IS_FIRST_ENTER, false);
    }

    /**
     * 保存登陆的时候获取的基本地址
     */
    public static void saveAppGetBaseUrl(String url) {
        SharePreferenceHelper.getInstance().setStringValue(APP_GET_BASE_URL, url);
    }

    /**
     * 获取基本地址
     */
    public static String getAppGetBaseUrl() {
        //return SharePreferenceHelper.getInstance().getStringValue(APP_GET_BASE_URL, "http://webapiv3.ys100.com:90/apiv3/rest/v3/");
        return SharePreferenceHelper.getInstance().getStringValue(APP_GET_BASE_URL, "http://qa.ys100.com:90/api_v3/rest/v3/");
    }

    /**
     * 保存登陆的账户
     */
    public static void saveAccount(String account) {
        SharePreferenceHelper.getInstance().setStringValue(ACCOUNT, account);
    }

    /**
     * 获取登陆的账户
     */
    public static String getAccount() {
        return SharePreferenceHelper.getInstance().getStringValue(ACCOUNT);
    }

    /**
     * 保存账户token
     */
    public static void saveToken(String token) {
        SharePreferenceHelper.getInstance().setStringValue(TOKEN, token);
    }

    /**
     * 获取账户token
     */
    public static String getToken() {
        return SharePreferenceHelper.getInstance().getStringValue(TOKEN);
    }

    /**
     * 保存账户角色
     */
    public static void saveRole(String role) {
        SharePreferenceHelper.getInstance().setStringValue(ROLE, role);
    }

    /**
     * 获取账户角色
     */
    public static String getRole() {
        return SharePreferenceHelper.getInstance().getStringValue(ROLE);
    }

    /**
     * 保存用户密码加密后字符串
     */
    public static void savePsw(String psw) {
        SharePreferenceHelper.getInstance().setStringValue(PASSWORD, psw);
    }

    /**
     * 获取用户密码加密后字符串
     */
    public static String getPsw() {
        return SharePreferenceHelper.getInstance().getStringValue(PASSWORD);
    }

    /**
     * 移除用户密码加密后字符串
     */
    public static void removePsw() {
        SharePreferenceHelper.getInstance().remove(PASSWORD);
    }

    /**
     * 存储是否记住密码
     */
    public static void saveIsRem(boolean isRem) {
        SharePreferenceHelper.getInstance().setBooleanValue(IS_REMENBER, isRem);
    }

    /**
     * 获取是否记住密码
     */
    public static boolean getIsRem() {
        return SharePreferenceHelper.getInstance().getBooleanValue(IS_REMENBER, false);
    }

    /**
     * 设置当前刷新课表时间
     *
     * @param date
     */
    public static void saveCurriculumDate(String date) {
        SharePreferenceHelper.getInstance().setStringValue(CURRICULUM_DATE, date);
    }

    /**
     * 获取上次刷新课表时间
     *
     * @return
     */
    public static String getCurriculumDate() {
        return SharePreferenceHelper.getInstance().getStringValue(CURRICULUM_DATE);
    }

    /**
     * 是否只在WIFI状态下下载文件标志
     */
    public static void saveIsWifi(boolean is_disturb) {
        SharePreferenceHelper.getInstance().setBooleanValue(IS_WIFI,is_disturb);
    }
    /**
     * 是否只在WIFI状态下下载文件标志
     */
    public static boolean getIsWifi() {
        return SharePreferenceHelper.getInstance().getBooleanValue(IS_WIFI,true);
    }

    /**
     * 保存邀请码
     */
    public static void saveInviteCode(String inviteCode) {
        SharePreferenceHelper.getInstance().setStringValue(INVITE_CODE,inviteCode);
    }
    /**
     * 获取邀请码
     */
    public static String getInviteCode() {
        return SharePreferenceHelper.getInstance().getStringValue(INVITE_CODE);
    }

    /**
     * 保存当前服务器版本号
     */
    public static void saveServerVersion(String serverVersion) {
        SharePreferenceHelper.getInstance().setStringValue(SERVER_VERSION,serverVersion);
    }
    /**
     *获取服务器当前版本号
     */
    public static String getServerVersion() {
        return SharePreferenceHelper.getInstance().getStringValue(SERVER_VERSION);
    }

}
