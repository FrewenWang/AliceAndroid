package com.frewen.android.demo.di.modules

import com.frewen.android.demo.logic.ui.discovery.DiscoveryFragment
import com.frewen.android.demo.logic.ui.discovery.content.DailyQuestionFragment
import com.frewen.android.demo.logic.ui.home.HomeFragment
import com.frewen.android.demo.logic.ui.profile.MyProfileFragment
import com.frewen.android.demo.logic.ui.recommend.RecommendFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * 首页Fragment的BindModule
 */
@Module
abstract class MainFragmentBindModule {
    //主要作用就是通过 @ContributesAndroidInjector  标记哪个类需要使用依赖注入功能
    //节省代码
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeRecommendFragment(): RecommendFragment

    @ContributesAndroidInjector
    abstract fun contributeDiscoveryFragment(): DiscoveryFragment

    @ContributesAndroidInjector
    abstract fun contributeMyProfileFragment(): MyProfileFragment

    /**
     * 报下面的错误：
     * java.lang.IllegalArgumentException: No injector factory bound for
     * Class<com.frewen.android.demo.logic.ui.discovery.content.DailyQuestionFragment>
     *  需要添加：
     * @ContributesAndroidInjector
     * abstract fun contributeDailyQuestionFragment(): DailyQuestionFragment
     */
    @ContributesAndroidInjector
    abstract fun contributeDailyQuestionFragment(): DailyQuestionFragment
}
