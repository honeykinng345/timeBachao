package home.services.timeBacaho;

public class AppConfig {

private  static  String getURL_Main = "http://192.168.137.1/timeBachao";

    public static String URL_LOGIN = getURL_Main+"/android_login_api/login.php";
    // Server user register url
    public static String URL_REGISTER =  getURL_Main+"/android_login_api/register.php";
    //cat list url
    public static String URL_Cat =  getURL_Main+"/android_login_api/getCategories.php";
    public static String URL_subcat =  getURL_Main+"/android_login_api/getsub.php/";
    public  static  String URL_Main_Services =  getURL_Main+"/android_login_api/getAllsub.php";
    public static String URL_ADDReview =  getURL_Main+"/android_login_api/addReviews.php";
    public static String URL_OrderDetail =  getURL_Main+"/android_login_api/ordersApi.php";
    public static String URL_OrderItems =  getURL_Main+"/android_login_api/itemsStoreApi.php";
    public static String URL_FetchOrderDetail =  getURL_Main+"/android_login_api/sendOrderDetailToUser.php";
    public static String URL_UsersOrderListDetail=  getURL_Main+"/android_login_api/UsersOrderListDetail.php";
    public static String URL_FetchOrderDetailItems =  getURL_Main+"/android_login_api/sendOrderItems.php";
    public static String URL_Deals =  getURL_Main+"/android_login_api/dealsApi.php";
    public static String URL_UpdateUser_Information = getURL_Main+"/android_login_api/updateUser.php";
    public static String URL_SendNewOrderToEmploye =  getURL_Main+"/android_login_api/SendOrdersToEmploye.php";
    public static String URL_CanceledRequestOrderEmploye =  getURL_Main+"/android_login_api/EmployeCancelRequest.php";
    public static String URL_AcceptedRequestOrderEmploye =  getURL_Main+"/android_login_api/EmployeAcceptedRequest.php";
    public static String URL_SendOrderHistoryToEmploye = getURL_Main+"/android_login_api/SendOrderHistoryToEmploye.php";
    public static String URL_FetchRedeemUserGiftPoints = getURL_Main+"/android_login_api/redeemUserGifts.php";
    public static String URL_UpdateRedeemPoints =  getURL_Main+"/android_login_api/updateRedeemValue.php";
    public static String URL_IncreamentRedeemValue = getURL_Main+"/android_login_api/redeem.php";
    public static String URL_reviewsAll = getURL_Main+"/android_login_api/reviewsAll.php";

//iMAGES lINKS....
    public static String IMAGE_MAIN_SERVICE_URL =  getURL_Main+"/SImages/";
    public static String IMAGE_CATEGORIES_SERVICE_URL =  getURL_Main+"/Cimages/";
    public static String IMAGE_SUB_CAT_SERVICE_URL =  getURL_Main+"/Sub_image/";
    public static String IMAGE_DEALS_URL =  getURL_Main+"/dealsImages/";


    public  static  final String FCM_KEY = "AAAAu8l07MA:APA91bHKHgbOEciC49UdVdObGbBu3znZLJxqDeC9i70bkEV-qUEsyuWxjfvWiuWr4DfOzeWZFEEx_Bg4QALNsYD5H5lwJ6CLmFjyQhTX2vXrMcgwlkQqWujV0TWL4aWTI8QLiYGCFodR";
    public  static  final String FCM_TOPIC = "PUSH_NOTIFICATIONS";

    public static  boolean isEmployeCome= true;

}
