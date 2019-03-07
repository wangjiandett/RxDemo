package com.moa.plugin_messenger;

import com.moa.plugin_messenger.bean.User;

public class Messenger {

    private static User user;

    public static void init(User bean) {
        Messenger.user = bean;
    }

    public static User getUser() {
        return Messenger.user;
    }
}
