package com.frewen.android.demo.logic.samples.dagger2.data;

/**
 * @filename: InjectData
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/4/29 11:53
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class InjectData {

    private String name;

    /**
     * 我们给这个目标类添加一个@Inject注解
     * @param
     */
    public InjectData() {
    }

    /**
     * 当我们这为了防止依赖注入迷失：
     * 我们给这个目标类添加一个@Inject注解
     * @param
     */
    public InjectData(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
