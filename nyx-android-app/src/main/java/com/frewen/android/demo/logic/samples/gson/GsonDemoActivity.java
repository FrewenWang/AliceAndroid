package com.frewen.android.demo.logic.samples.gson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 学习：https://www.jianshu.com/p/fcb4f1ad4743
 */
public class GsonDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson_demo);


        initGson();

        initGsonAnnotations();


        testSerializerFeature();

        testCustomSerializer();

        testToGson();

        testPrintFormatter();


        testArray();

        testGenericType();


        testNull();

        testVersion();

        testNoArgsConstructor();

    }

    /**
     * Java对象进行序列化/反序列化或JSON字符串，GSON创建一个默认实例的类的构造方法。
     * 有一个默认的Java类的无参数构造方法是很好的。
     * 如果一个类没有默认构造函数，GSON提供一个class.google.gson.InstanceCreator接口实现来处理它。
     * 方法	详细说明
     * createInstance 参数：java.lang.reflect.Type类的实例；返回值：T类型的默认对象实例，引用对象实例的类类型。
     *
     */
    private void testNoArgsConstructor() {

    }

    /**
     * GSON提供了版本化的序列化/反序列化的Java对象的JSON表示。
     * 这有助于迭代开发和发布值对象。GSON API提供了一种机制来满足这些不同版本数据的请求。
     */
    private void testVersion() {
        User user = new User("测试版本", 18);

        System.out.println("User json for Version 1.0 ");
        Gson gson = new GsonBuilder().setVersion(1.0).setPrettyPrinting().create();
        String jsonOutput = gson.toJson(user);
        System.out.println(jsonOutput);

        System.out.println("User json for Version 1.1 ");
        gson = new GsonBuilder().setVersion(1.1).setPrettyPrinting().create();
        jsonOutput = gson.toJson(user);
        System.out.println(jsonOutput);
    }

    /**
     * GSON也能够对null对象进行序列化/反序列化的JSON表示。
     */
    private void testNull() {
        System.out.println("---------------------Gson测试null支持------------------");
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        User userInfo = new User(null, 18);

        String userJson = gson.toJson(userInfo);
        System.out.println(userJson);

        User user = gson.fromJson(userJson, User.class);
        System.out.println("user name: " + user.getName());
        System.out.println("user age: " + user.getAge());

    }


    /**
     * GSON使用com.google.gson.reflect.TypeToken来支持泛型类型的Java类对象，用于序列化和反序列化。
     * <p>
     * 使用TypeToken类的目的是使用Java泛型类型的类型擦除的特性。
     * <p>
     * 类型擦除发生在编译期，在这里，Java泛型类型被完全删除，以产生字节码。因此，在将JSON字符串反序列化为泛型Java类时，它可能会没有正确地反序列化。
     */
    private void testGenericType() {

        System.out.println("---------------------Gson测试泛型相关------------------");
        Gson gson = new Gson();
        StudentGeneric<Integer, String> studGenericObj1 = new StudentGeneric<Integer, String>();
        studGenericObj1.setMark(25);
        studGenericObj1.setUser("测试泛型");

        String json = gson.toJson(studGenericObj1);
        System.out.println("Serialized Output :");
        System.out.println(json);

        StudentGeneric<Integer, String> studGenericObj2 = gson.fromJson(json, StudentGeneric.class);
        System.out.println("DeSerialized Output :");
        System.out.println("Mark : " + studGenericObj2.getMark());  // 25.0
        System.out.println("User : " + studGenericObj2.getUser());
        // 在上面的代码中，StudentGeneric类接受两个泛型参数，并有各自的getter和setter方法。
        // StudentGeneric类对象使用Integer和String作为mark和name的类型来创建的。

        // 在序列化时，mark被初始化为25，但反序列化输出显示为25.0，
        // 这是一个不正确的值，因为类型擦除属性在编译时从类中删除了泛型类型的参数。

        // 使用TypeToken类来解决这个问题。getType()方法返回具有泛型参数的原始类类型，它帮助GSON正确地反序列化对象，并将正确值输出为25。
        Type studentGenericType = new TypeToken<StudentGeneric<Integer, String>>() {
        }.getType();
        StudentGeneric<Integer, String> studGenericObj3 = gson.fromJson(json, studentGenericType);
        System.out.println("TypeToken Use DeSerialized Output :");
        System.out.println("Mark : " + studGenericObj3.getMark());
    }

    private void testArray() {
        Gson gson = new GsonBuilder().create();
        // 我们定义两个数组，整数数组。字符串数组
        int[] numberArray = {121, 23, 34, 44, 52};

        String[] fruitsArray = {"apple", "oranges", "grapes"};

        String jsonNumber = gson.toJson(numberArray);
        String jsonString = gson.toJson(fruitsArray);

        System.out.println(jsonNumber);
        System.out.println(jsonString);

        int[] numCollectionArray = gson.fromJson(jsonNumber, int[].class);
        String[] fruitBasketArray = gson.fromJson(jsonString, String[].class);

        System.out.println("Number Array Length " + numCollectionArray.length);
        System.out.println("Fruit Array Length " + fruitBasketArray.length);
    }

    /**
     * GSON的序列化输出的JSON表示格式紧凑。如果有大量的Java对象集合，
     * 并且每个对象都有许多序列化的属性，那么它们紧凑的JSON表示的可读性是非常差的，而且看起来很难看。
     * <p>
     * 为了解决这个问题，GsonBuilder支持漂亮的打印配置，同时为序列化使用创建一个Gson对象。
     * 这个漂亮的打印功能通过适当的标签缩进和新的换行来美化JSON字符串的输出。
     * <p>
     * 以下是关于格式化程序的一些重要内容：
     * JsonPrintFormatter和JsonCompactFormatter是GSON中的两种格式化类型的表示。
     * JsonCompactFormatter是GSON的默认格式化程序。
     * JsonPrintFormatter用于漂亮的打印，它不会暴露在API中。所以开发者不能修改。
     * JsonPrintFormatter支持一个默认行长度为80个字符，两个字符缩进，以及右侧保持四个字符。
     * 可以通过调用GsonBuilder的setPrettyPrinting()方法来使用JsonPrintFormatter。
     */
    private void testPrintFormatter() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<User> listOfUser = new ArrayList<User>();

        User user1 = new User("格式化输出1", 1);
        User user2 = new User("格式化输出2", 2);
        listOfUser.add(user1);
        listOfUser.add(user2);
        String prettyJsonString = gson.toJson(listOfUser);
        System.out.println(prettyJsonString);

        // 上面的代码将学生列表序列化为JSON表示。它使用GsonBuilder类获得一个Gson对象。
        // 使用setPrettyPrinting()方法配置漂亮的打印。
        // 可以看到以前的代码的输出已经正确地缩进，并且阅读起来很愉快。
    }

    private void testCustomSerializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        /// 注册注册序列化适配器
        gsonBuilder.registerTypeAdapter(User.class, new UserInfoTypeSerializer());
        /// 注册反序列化适配器
        gsonBuilder.registerTypeAdapter(User.class, new UserInfoTypeDeserializer());

        Gson gson = gsonBuilder.create();

        User user = new User("自定义序列化", 12);

        String userJson = gson.toJson(user);

        System.out.println("Custom Serializer : Json String Representation ");
        System.out.println(userJson);


        User user1 = gson.fromJson(userJson, User.class);
        System.out.println("Custom DeSerializer : Java Object Creation");
        System.out.println(user1.toString());

    }

    /**
     * 序列化和反序列化
     * GSON有一些类的隐式序列化，比如Java包装类(Integer、Long、Double等等)、Java.net.url、java.net.URI、java.util.Date，等等。
     */
    private void testSerializerFeature() {
        Date data = new Date();
        Gson gson = new Gson();
        String jsonDate = gson.toJson(data);
        System.out.println(jsonDate);  // "Aug 1, 2020 8:27:36 PM"
    }

    private void testToGson() {
        User person = new User("Hello", 19);
        System.out.println(person.getAge());
        Gson gson = new Gson();
        System.out.println(gson.toJson(person));
        System.out.println("---------------");
        Gson gson1 = new GsonBuilder().create();
        System.out.println(gson1.toJson(person));
    }

    private void initGsonAnnotations() {

    }

    private void initGson() {
        /// Gson共有两种创建方式：
        Gson gson1 = new Gson();
        // 除此之外，我们可以针对Gson对象可以有很多的自定义配置
        Gson gson = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping() //默认是GSON把HTML转义的，可以进行关闭
                .create();


    }
}