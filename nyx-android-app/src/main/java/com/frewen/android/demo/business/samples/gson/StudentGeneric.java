package com.frewen.android.demo.business.samples.gson;

/**
 * @filename: StudentGeneric
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/2 09:04
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class StudentGeneric<T, User> {

    T mark;
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public T getMark() {
        return mark;
    }

    public void setMark(T mark) {
        this.mark = mark;
    }

}
