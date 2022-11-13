package com.frewen.android.demo.di.modules

import com.frewen.android.demo.business.samples.navigation.annotation.discovery.DiscoveryFragment
import com.frewen.android.demo.business.samples.navigation.annotation.discovery.content.DailyQuestionFragment
import com.frewen.android.demo.business.samples.navigation.annotation.home.HomeFragment
import com.frewen.android.demo.business.samples.navigation.annotation.profile.MyProfileFragment
import com.frewen.android.demo.business.samples.navigation.annotation.recommend.RecommendFragment
import com.frewen.android.demo.business.samples.navigation.annotation.recommend.content.EyeRecommendFragment
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
     * Class<com.frewen.android.demo.logic.samples.navigation.annotation.discovery.content.DailyQuestionFragment>
     *  需要添加：
     * @ContributesAndroidInjector
     * abstract fun contributeDailyQuestionFragment(): DailyQuestionFragment
     */
    @ContributesAndroidInjector
    abstract fun contributeDailyQuestionFragment(): DailyQuestionFragment

    /**
     * 如果EyeRecommendFragment实现了Injectable接口
     *
     * 注意！！！！！
     * 所有使用实现自Injectable的类。需要有注册inject的注解的实例对象，否则会报下面的错误：
     *   Process: com.frewen.android.demo.debug, PID: 2570
     *              java.lang.IllegalArgumentException:
     *              No injector was found for com.frewen.android.demo.logic.samples.navigation.annotation.discovery.DiscoveryFragment
     */
    @ContributesAndroidInjector
    abstract fun contributeEyeRecommendFragment(): EyeRecommendFragment
}
