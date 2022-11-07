package com.frewen.android.demo.business.model;

/**
 * lombok :
 * https://zhuanlan.zhihu.com/p/32779910
 * <p>
 * Data 注解在类上；提供类所有属性的 get 和 set 方法，此外还提供了equals、canEqual、hashCode、toString 方法。
 * Setter 注解在属性上；为单个属性提供 set 方法; 注解在 类 上，为该类所有的属性提供 set 方法， 都提供默认构造方法。
 * Getter 注解在 属性 上；为单个属性提供 get 方法; 注解在 类 上，为该类所有的属性提供 get 方法，都提供默认构造方法。
 * Log4j 注解在 类 上；为类提供一个 属性名为 log 的 log4j 日志对象，提供默认构造方法。
 * AllArgsConstructor 注解在 类 上；为类提供一个全参的构造方法，加了这个注解后，类中不提供默认构造方法了。
 * NoArgsConstructor 注解在 类 上；为类提供一个无参的构造方法。
 * EqualsAndHashCode 注解在 类 上, 可以生成 equals、canEqual、hashCode 方法。
 * NonNull 注解在 属性 上，会自动产生一个关于此参数的非空检查，如果参数为空，则抛出一个空指针异常，也会有一个默认的无参构造方法。
 * Cleanup 这个注解用在 变量 前面，可以保证此变量代表的资源会被自动关闭，默认是调用资源的 close() 方法，
 * 如果该资源有其它关闭方法，可使用 @Cleanup(“methodName”) 来指定要调用的方法，也会生成默认的构造方法
 * ToString 这个注解用在 类 上，可以生成所有参数的 toString 方法，还会生成默认的构造方法。
 * RequiredArgsConstructor 这个注解用在 类 上，使用类中所有带有 @NonNull 注解的或者带有 final 修饰的成员变量生成对应的构造方法。
 *
 * @author frewen
 */
public class ContentData {

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * content clazz
     */
    private Class<?> clazz;

    public ContentData(String title, String content, Class<?> clazz) {
        this.title = title;
        this.content = content;
        this.clazz = clazz;
    }


    public ContentData(String title, Class<?> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
