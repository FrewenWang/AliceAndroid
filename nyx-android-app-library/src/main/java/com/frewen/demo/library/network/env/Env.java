package com.frewen.demo.library.network.env;

/**
 * @filename: Env
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/5/13 20:15
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public enum Env {
    /**
     * 开发环境，外部用户无法访问，开发人员使用
     * 代码一般使用dev分支的代码,版本变动很大。
     */
    DEV(0),
    /**
     * 外部用户无法访问，专门给测试人员使用的，版本相对稳定。
     * 代码分支除了可以使用master分支外，其他的分支也是可以的。
     */
    TEST(1),
    /**
     * 灰度环境，外部用户可以访问，但是可能是一个单独版本，部分内测用法可访问
     * 但是服务器配置相对低，其它和生产一样。
     */
    PRE(2),
    /**
     * 生产环境，面向外部用户的环境，连接上互联网即可访问的正式环境。
     */
    PROD(3);


    private int value;

    Env(int value) {
        this.value = value;
    }
}
