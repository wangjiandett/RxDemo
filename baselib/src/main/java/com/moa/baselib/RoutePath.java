package com.moa.baselib;

/**
 * 存放每一个module要对外开放的入口
 */
public class RoutePath {

    private static final String MODULE_APP = "/module_app/";
    private static final String MODULE_A = "/module_a/";
    private static final String MODULE_B = "/module_b/";
    private static final String MODULE_C = "/module_c/";

    //----------注意每一个界面上的@Route(path = )都是唯一的，重复会导致编译失败-----------//

    /**
     * app 对外开放的界面入口
     */
    public static final String MODULE_APP_ENTER_ACTIVITY = MODULE_APP + "main_enter";

    /**
     * module a 对外开放的界面入口
     */
    public static final String MODULE_A_ENTER_ACTIVITY = MODULE_A + "main_enter";

    /**
     * module b 对外开放的界面入口
     */
    public static final String MODULE_B_ENTER_ACTIVITY = MODULE_B + "main_enter";

    /**
     * module c 对外开放的界面入口
     */
    public static final String MODULE_C_ENTER_ACTIVITY = MODULE_C + "main_enter";


}
