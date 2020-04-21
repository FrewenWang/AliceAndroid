package com.frewen.network.logger;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.frewen.network.core.FreeRxHttp;
import com.frewen.aura.toolkits.utils.ThrowableUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;
import androidx.collection.SimpleArrayMap;

/**
 * @filename: Logger
 * @introduction: 日志打印工具类
 * @author: Frewen.Wong
 * @time: 2019/4/14 23:03
 *         Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public final class Logger {
    /** 打印级别 */
    public static final int V = Log.VERBOSE;
    /** 打印级别 */
    public static final int D = Log.DEBUG;
    /** 打印级别 */
    public static final int I = Log.INFO;
    /** 打印级别 */
    public static final int W = Log.WARN;
    /** 打印级别 */
    public static final int E = Log.ERROR;
    /** 打印级别 */
    public static final int A = Log.ASSERT;

    /**
     * 声明日志输出级别
     */
    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    private static final char[] T = new char[]{'V', 'D', 'I', 'W', 'E', 'A'};

    private static final int FILE = 0x10;
    private static final int JSON = 0x20;
    private static final int XML = 0x30;
    /** 日志分隔符号 */
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final String TOP_CORNER = "┌";
    private static final String MIDDLE_CORNER = "├";
    private static final String LEFT_BORDER = "│ ";
    private static final String BOTTOM_CORNER = "└";
    private static final String SIDE_DIVIDER =
            "────────────────────────────────────────────────────────";
    private static final String MIDDLE_DIVIDER =
            "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = TOP_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + MIDDLE_DIVIDER + MIDDLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final int MAX_LEN = 3000;
    private static final String NOTHING = "log nothing";
    private static final String NULL = "null";
    private static final String ARGS = "args";
    private static final String PLACEHOLDER = " ";
    private static final Config CONFIG = new Config();
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().serializeNulls().create();

    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();
    /**
     * 单个线程池
     */
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    /**
     * 设置SimpleArrayMap
     */
    private static final SimpleArrayMap<Class, IFormatter> I_FORMATTER_MAP = new SimpleArrayMap<>();

    /**
     * Logger
     */
    private Logger() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * json
     */
    public static Config getConfig() {
        return CONFIG;
    }

    /**
     * json
     */
    public static void v(final Object... contents) {
        log(V, CONFIG.getGlobalTag(), contents);
    }

    /**
     * json
     */
    public static void vTag(final String tag, final Object... contents) {
        log(V, tag, contents);
    }

    /**
     * json
     */
    public static void d(final Object... contents) {
        log(D, CONFIG.getGlobalTag(), contents);
    }

    /**
     * json
     */
    public static void dTag(final String tag, final Object... contents) {
        log(D, tag, contents);
    }

    /**
     * json
     */
    public static void i(final Object... contents) {
        log(I, CONFIG.getGlobalTag(), contents);
    }

    /**
     * json
     */
    public static void iTag(final String tag, final Object... contents) {
        log(I, tag, contents);
    }

    /**
     * json
     */
    public static void w(final Object... contents) {
        log(W, CONFIG.getGlobalTag(), contents);
    }

    /**
     * json
     */
    public static void wTag(final String tag, final Object... contents) {
        log(W, tag, contents);
    }

    /**
     * json
     */
    public static void e(final Object... contents) {
        log(E, CONFIG.getGlobalTag(), contents);
    }

    /**
     * json
     */
    public static void eTag(final String tag, final Object... contents) {
        log(E, tag, contents);
    }

    /**
     * json
     */
    public static void a(final Object... contents) {
        log(A, CONFIG.getGlobalTag(), contents);
    }

    /**
     * json
     */
    public static void aTag(final String tag, final Object... contents) {
        log(A, tag, contents);
    }

    /**
     * json
     */
    public static void file(final Object content) {
        log(FILE | D, CONFIG.getGlobalTag(), content);
    }

    /**
     * json
     */
    public static void file(@TYPE final int type, final Object content) {
        log(FILE | type, CONFIG.getGlobalTag(), content);
    }

    /**
     * json
     */
    public static void file(final String tag, final Object content) {
        log(FILE | D, tag, content);
    }

    /**
     * json
     */
    public static void file(@TYPE final int type, final String tag, final Object content) {
        log(FILE | type, tag, content);
    }

    /**
     * json
     */
    public static void json(final Object content) {
        log(JSON | D, CONFIG.getGlobalTag(), content);
    }

    /**
     * json打印
     * @param type
     * @param tag
     * @param content
     */
    public static void json(@TYPE final int type, final Object content) {
        log(JSON | type, CONFIG.getGlobalTag(), content);
    }

    /**
     * json
     * @param type
     * @param tag
     * @param content
     */
    public static void json(final String tag, final Object content) {
        log(JSON | D, tag, content);
    }

    /**
     * json
     * @param type
     * @param tag
     * @param content
     */
    public static void json(@TYPE final int type, final String tag, final Object content) {
        log(JSON | type, tag, content);
    }

    /**
     * xml打印
     * @param type
     * @param tag
     * @param content
     */
    public static void xml(final String content) {
        log(XML | D, CONFIG.getGlobalTag(), content);
    }

    /**
     * xml打印
     * @param type
     * @param tag
     * @param content
     */
    public static void xml(@TYPE final int type, final String content) {
        log(XML | type, CONFIG.getGlobalTag(), content);
    }

    /**
     * xml打印
     * @param type
     * @param tag
     * @param content
     */
    public static void xml(final String tag, final String content) {
        log(XML | D, tag, content);
    }

    /**
     * xml打印
     * @param type
     * @param tag
     * @param content
     */
    public static void xml(@TYPE final int type, final String tag, final String content) {
        log(XML | type, tag, content);
    }

    /**
     * 日志打印
     * @param type
     * @param tag
     * @param contents
     */
    public static void log(final int type, final String tag, final Object... contents) {
        if (!CONFIG.isLogSwitch()) {
            return;
        }
        int typeLow = type & 0x0f;
        int typeHigh = type & 0xf0;
        if (CONFIG.isLog2ConsoleSwitch() || CONFIG.isLog2FileSwitch() || typeHigh == FILE) {
            if (typeLow < CONFIG.mConsoleFilter && typeLow < CONFIG.mFileFilter) {
                return;
            }
            final TagHead tagHead = processTagAndHead(tag);
            final String body = processBody(typeHigh, contents);
            if (CONFIG.isLog2ConsoleSwitch() && typeHigh != FILE && typeLow >= CONFIG.mConsoleFilter) {
                print2Console(typeLow, tagHead.tag, tagHead.consoleHead, body);
            }
            if ((CONFIG.isLog2FileSwitch() || typeHigh == FILE) && typeLow >= CONFIG.mFileFilter) {
                print2File(typeLow, tagHead.tag, tagHead.fileHead + body);
            }
        }
    }

    private static TagHead processTagAndHead(String tag) {
        if (!CONFIG.mTagIsSpace && !CONFIG.isLogHeadSwitch()) {
            tag = CONFIG.getGlobalTag();
        } else {
            final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            final int stackIndex = 3 + CONFIG.getStackOffset();
            if (stackIndex >= stackTrace.length) {
                StackTraceElement targetElement = stackTrace[3];
                final String fileName = getFileName(targetElement);
                if (CONFIG.mTagIsSpace && isSpace(tag)) {
                    int index = fileName.indexOf('.');  // Use proguard may not find '.'.
                    tag = index == -1 ? fileName : fileName.substring(0, index);
                }
                return new TagHead(tag, null, ": ");
            }
            StackTraceElement targetElement = stackTrace[stackIndex];
            final String fileName = getFileName(targetElement);
            if (CONFIG.mTagIsSpace && isSpace(tag)) {
                int index = fileName.indexOf('.');   // Use proguard may not find '.'.
                tag = index == -1 ? fileName : fileName.substring(0, index);
            }
            if (CONFIG.isLogHeadSwitch()) {
                String tName = Thread.currentThread().getName();
                final String head = new Formatter()
                        .format("%s, %s.%s(%s:%d)",
                                tName,
                                targetElement.getClassName(),
                                targetElement.getMethodName(),
                                fileName,
                                targetElement.getLineNumber())
                        .toString();
                final String fileHead = " [" + head + "]: ";
                if (CONFIG.getStackDeep() <= 1) {
                    return new TagHead(tag, new String[]{head}, fileHead);
                } else {
                    final String[] consoleHead =
                            new String[Math.min(
                                    CONFIG.getStackDeep(),
                                    stackTrace.length - stackIndex
                            )];
                    consoleHead[0] = head;
                    int spaceLen = tName.length() + 2;
                    String space = new Formatter().format("%" + spaceLen + "s", "").toString();
                    for (int i = 1, len = consoleHead.length; i < len; ++i) {
                        targetElement = stackTrace[i + stackIndex];
                        consoleHead[i] = new Formatter()
                                .format("%s%s.%s(%s:%d)",
                                        space,
                                        targetElement.getClassName(),
                                        targetElement.getMethodName(),
                                        getFileName(targetElement),
                                        targetElement.getLineNumber())
                                .toString();
                    }
                    return new TagHead(tag, consoleHead, fileHead);
                }
            }
        }
        return new TagHead(tag, null, ": ");
    }

    private static String getFileName(final StackTraceElement targetElement) {
        String fileName = targetElement.getFileName();
        if (fileName != null) {
            return fileName;
        }
        // If name of file is null, should add
        // "-keepattributes SourceFile,LineNumberTable" in proguard file.
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        int index = className.indexOf('$');
        if (index != -1) {
            className = className.substring(0, index);
        }
        return className + ".java";
    }

    private static String processBody(final int type, final Object... contents) {
        String body = NULL;
        if (contents != null) {
            if (contents.length == 1) {
                body = formatObject(type, contents[0]);
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0, len = contents.length; i < len; ++i) {
                    Object content = contents[i];
                    sb.append(ARGS)
                            .append("[")
                            .append(i)
                            .append("]")
                            .append(" = ")
                            .append(formatObject(content))
                            .append(LINE_SEP);
                }
                body = sb.toString();
            }
        }
        return body.length() == 0 ? NOTHING : body;
    }

    private static String formatObject(int type, Object object) {
        if (object == null) {
            return NULL;
        }
        if (type == JSON) {
            return LogFormatter.object2Json(object);
        }
        if (type == XML) {
            return LogFormatter.formatXml(object.toString());
        }
        return formatObject(object);
    }

    private static String formatObject(Object object) {
        if (object == null) {
            return NULL;
        }
        if (!I_FORMATTER_MAP.isEmpty()) {
            IFormatter iFormatter = I_FORMATTER_MAP.get(getClassFromObject(object));
            if (iFormatter != null) {
                //noinspection unchecked
                return iFormatter.format(object);
            }
        }
        return LogFormatter.object2String(object);
    }

    private static void print2Console(final int type,
            final String tag,
            final String[] head,
            final String msg) {
        if (CONFIG.isSingleTagSwitch()) {
            printSingleTagMsg(type, tag, processSingleTagMsg(type, tag, head, msg));
        } else {
            printBorder(type, tag, true);
            printHead(type, tag, head);
            printMsg(type, tag, msg);
            printBorder(type, tag, false);
        }
    }

    private static void printBorder(final int type, final String tag, boolean isTop) {
        if (CONFIG.isLogBorderSwitch()) {
            Log.println(type, tag, isTop ? TOP_BORDER : BOTTOM_BORDER);
        }
    }

    private static void printHead(final int type, final String tag, final String[] head) {
        if (head != null) {
            for (String aHead : head) {
                Log.println(type, tag, CONFIG.isLogBorderSwitch() ? LEFT_BORDER + aHead : aHead);
            }
            if (CONFIG.isLogBorderSwitch()) {
                Log.println(type, tag, MIDDLE_BORDER);
            }
        }
    }

    private static void printMsg(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                printSubMsg(type, tag, msg.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                printSubMsg(type, tag, msg.substring(index, len));
            }
        } else {
            printSubMsg(type, tag, msg);
        }
    }

    private static void printSubMsg(final int type, final String tag, final String msg) {
        if (!CONFIG.isLogBorderSwitch()) {
            Log.println(type, tag, msg);
            return;
        }
        StringBuilder sb = new StringBuilder();
        String[] lines = msg.split(LINE_SEP);
        for (String line : lines) {
            Log.println(type, tag, LEFT_BORDER + line);
        }
    }

    private static String processSingleTagMsg(final int type,
            final String tag,
            final String[] head,
            final String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(PLACEHOLDER).append(LINE_SEP);
        if (CONFIG.isLogBorderSwitch()) {
            sb.append(TOP_BORDER).append(LINE_SEP);
            if (head != null) {
                for (String aHead : head) {
                    sb.append(LEFT_BORDER).append(aHead).append(LINE_SEP);
                }
                sb.append(MIDDLE_BORDER).append(LINE_SEP);
            }
            for (String line : msg.split(LINE_SEP)) {
                sb.append(LEFT_BORDER).append(line).append(LINE_SEP);
            }
            sb.append(BOTTOM_BORDER);
        } else {
            if (head != null) {
                for (String aHead : head) {
                    sb.append(aHead).append(LINE_SEP);
                }
            }
            sb.append(msg);
        }
        return sb.toString();
    }

    private static void printSingleTagMsg(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            if (CONFIG.isLogBorderSwitch()) {
                Log.println(type, tag, msg.substring(0, MAX_LEN) + LINE_SEP + BOTTOM_BORDER);
                int index = MAX_LEN;
                for (int i = 1; i < countOfSub; i++) {
                    Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                            + LEFT_BORDER + msg.substring(index, index + MAX_LEN)
                            + LINE_SEP + BOTTOM_BORDER);
                    index += MAX_LEN;
                }
                if (index != len) {
                    Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                            + LEFT_BORDER + msg.substring(index, len));
                }
            } else {
                Log.println(type, tag, msg.substring(0, MAX_LEN));
                int index = MAX_LEN;
                for (int i = 1; i < countOfSub; i++) {
                    Log.println(type, tag,
                            PLACEHOLDER + LINE_SEP + msg.substring(index, index + MAX_LEN));
                    index += MAX_LEN;
                }
                if (index != len) {
                    Log.println(type, tag, PLACEHOLDER + LINE_SEP + msg.substring(index, len));
                }
            }
        } else {
            Log.println(type, tag, msg);
        }
    }

    /**
     * 往文件中进行输出日志
     * @param type
     * @param tag
     * @param msg
     */
    private static void print2File(final int type, final String tag, final String msg) {
        Date now = new Date(System.currentTimeMillis());
        String format = getSdf().format(now);
        String date = format.substring(0, 10);
        String time = format.substring(11);
        final String fullPath =
                CONFIG.getDir() + CONFIG.getFilePrefix() + "-" + date + "-" + CONFIG.getProcessName() + ".txt";
        if (!createOrExistsFile(fullPath)) {
            Log.e("LogUtils", "create " + fullPath + " failed!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(time)
                .append(T[type - V])
                .append("/")
                .append(tag)
                .append(msg)
                .append(LINE_SEP);
        final String content = sb.toString();
        input2File(content, fullPath);
    }

    private static SimpleDateFormat getSdf() {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            deleteDueLogs(filePath);
            boolean isCreate = file.createNewFile();
            if (isCreate) {
                printDeviceInfo(filePath);
            }
            return isCreate;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void deleteDueLogs(String filePath) {
        if (CONFIG.getSaveDays() <= 0) {
            return;
        }
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        File[] files = parentFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("^" + CONFIG.getFilePrefix() + "-[0-9]{4}-[0-9]{2}-[0-9]{2}-"
                        + CONFIG.getProcessName() + ".txt$");
            }
        });
        if (files == null || files.length <= 0) {
            return;
        }
        final int length = filePath.length();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            String curDay = filePath.substring(length - 14, length - 4);
            long dueMillis = sdf.parse(curDay).getTime() - CONFIG.getSaveDays() * 86400000L;
            for (final File aFile : files) {
                String name = aFile.getName();
                int l = name.length();
                String logDay = name.substring(l - 14, l - 4);
                if (sdf.parse(logDay).getTime() <= dueMillis) {
                    EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean delete = aFile.delete();
                            if (!delete) {
                                Log.e("LogUtils", "delete " + aFile + " failed!");
                            }
                        }
                    });
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void printDeviceInfo(final String filePath) {
        String versionName = "";
        int versionCode = 0;
        Application application = FreeRxHttp.getInstance().getContext();
        try {
            PackageInfo pi = application
                    .getPackageManager()
                    .getPackageInfo(application.getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String time = filePath.substring(filePath.length() - 14, filePath.length() - 4);
        final String head =
                "************* Log Head ****************" + "\nDate of Log        : " + time
                        + "\nDevice Manufacturer: " + Build.MANUFACTURER + "\nDevice Model       : "
                        + Build.MODEL + "\nAndroid Version    : " + Build.VERSION.RELEASE
                        + "\nAndroid SDK        : " + Build.VERSION.SDK_INT + "\nApp VersionName   "
                        + " : " + versionName + "\nApp VersionCode    : " + versionCode + "\n"
                        + "************* Log Head ****************\n\n";
        input2File(head, filePath);
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param input
     * @param filePath
     */
    private static void input2File(final String input, final String filePath) {
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(input);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("LogUtils", "log to " + filePath + " failed!");
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * * 日志打印开关的配置
     */
    public static class Config {
        private String mDefaultDir; // The default storage directory of log.
        private String mDir;        // The storage directory of log.
        private String mFilePrefix = "util"; // The file prefix of log.
        private boolean mLogSwitch = true;  // The switch of log.
        private boolean mLog2ConsoleSwitch = true;  // The logcat's switch of log.
        private String mGlobalTag = "";    // The global tag of log.
        private boolean mTagIsSpace = true;  // The global tag is space.
        private boolean mLogHeadSwitch = true;  // The head's switch of log.
        private boolean mLog2FileSwitch = false; // The file's switch of log.
        private boolean mLogBorderSwitch = true;  // The border's switch of log.
        private boolean mSingleTagSwitch = true;  // The single tag of log.
        private int mConsoleFilter = V;     // The console's filter of log.
        private int mFileFilter = V;     // The file's filter of log.
        private int mStackDeep = 1;     // The stack's deep of log.
        private int mStackOffset = 0;     // The stack's offset of log.
        private int mSaveDays = -1;    // The save days of log.
        private String mProcessName = getCurrentProcessName();

        /**
         * 日志打印开关的配置
         */
        private Config() {
            Application application = FreeRxHttp.getInstance().getContext();
            if (mDefaultDir != null) {
                return;
            }
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && application.getExternalCacheDir() != null) {
                mDefaultDir = application.getExternalCacheDir() + FILE_SEP + "log" + FILE_SEP;
            } else {
                mDefaultDir = application.getCacheDir() + FILE_SEP + "log" + FILE_SEP;
            }
        }

        /**
         * 设置 log 总开关，包括输出到控制台和文件，默认开
         * @param logSwitch
         * @return
         */
        public Config setLogSwitch(final boolean logSwitch) {
            mLogSwitch = logSwitch;
            return this;
        }

        /**
         * 设置是否输出到控制台开关，默认开
         * @param consoleSwitch
         * @return
         */
        public Config setConsoleSwitch(final boolean consoleSwitch) {
            mLog2ConsoleSwitch = consoleSwitch;
            return this;
        }

        /**
         * 设计全局的TAG
         * @param tag
         * @return
         */
        public Config setGlobalTag(final String tag) {
            if (isSpace(tag)) {
                mGlobalTag = "";
                mTagIsSpace = true;
            } else {
                mGlobalTag = tag;
                mTagIsSpace = false;
            }
            return this;
        }

        /**
         * setLogHeadSwitch
         * @param logHeadSwitch
         * @return
         */
        public Config setLogHeadSwitch(final boolean logHeadSwitch) {
            mLogHeadSwitch = logHeadSwitch;
            return this;
        }

        /**
         * setLog2FileSwitch
         * @param log2FileSwitch
         * @return
         */
        public Config setLog2FileSwitch(final boolean log2FileSwitch) {
            mLog2FileSwitch = log2FileSwitch;
            return this;
        }

        /**
         * setDir
         * @param dir
         * @return
         */
        public Config setDir(final String dir) {
            if (isSpace(dir)) {
                mDir = null;
            } else {
                mDir = dir.endsWith(FILE_SEP) ? dir : dir + FILE_SEP;
            }
            return this;
        }

        /**
         * setDir
         * @param dir
         * @return
         */
        public Config setDir(final File dir) {
            mDir = dir == null ? null : (dir.getAbsolutePath() + FILE_SEP);
            return this;
        }

        /**
         * 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd.txt"
         * @param filePrefix
         * @return
         */
        public Config setFilePrefix(final String filePrefix) {
            if (isSpace(filePrefix)) {
                mFilePrefix = "util";
            } else {
                mFilePrefix = filePrefix;
            }
            return this;
        }

        /**
         * 输出日志是否带边框开关，默认开
         * @param borderSwitch
         * @return
         */
        public Config setBorderSwitch(final boolean borderSwitch) {
            mLogBorderSwitch = borderSwitch;
            return this;
        }

        /**
         * 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
         * @param singleTagSwitch
         * @return
         */
        public Config setSingleTagSwitch(final boolean singleTagSwitch) {
            mSingleTagSwitch = singleTagSwitch;
            return this;
        }

        /**
         * log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
         * @param consoleFilter
         * @return
         */
        public Config setConsoleFilter(@TYPE final int consoleFilter) {
            mConsoleFilter = consoleFilter;
            return this;
        }

        /**
         * log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
         * @param fileFilter
         * @return
         */
        public Config setFileFilter(@TYPE final int fileFilter) {
            mFileFilter = fileFilter;
            return this;
        }

        /**
         * log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
         * @param stackDeep
         * @return
         */
        public Config setStackDeep(@IntRange(from = 1) final int stackDeep) {
            mStackDeep = stackDeep;
            return this;
        }

        /**
         * log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
         * @param stackOffset
         * @return
         */
        public Config setStackOffset(@IntRange(from = 0) final int stackOffset) {
            mStackOffset = stackOffset;
            return this;
        }

        /**
         * 设置日志可保留天数，默认为 -1 表示无限时长
         * @param saveDays
         * @return
         */
        public Config setSaveDays(@IntRange(from = 1) final int saveDays) {
            mSaveDays = saveDays;
            return this;
        }

        /**
         * 增加格式化对虾女工
         * @param iFormatter
         * @param <T>
         * @return
         */
        public final <T> Config addFormatter(final IFormatter<T> iFormatter) {
            if (iFormatter != null) {
                I_FORMATTER_MAP.put(getTypeClassFromParadigm(iFormatter), iFormatter);
            }
            return this;
        }

        /**
         * 获取进程包名
         * @return
         */
        public String getProcessName() {
            return mProcessName;
        }

        /**
         * 获取存储路径
         * @return
         */
        public String getDir() {
            return mDir == null ? mDefaultDir : mDir;
        }

        /**
         * 获取文件前缀
         * @return
         */
        public String getFilePrefix() {
            return mFilePrefix;
        }

        /**
         * isLogSwitch
         * @return
         */
        public boolean isLogSwitch() {
            return mLogSwitch;
        }

        /**
         * 日志是否输出到命令行
         * @return
         */
        public boolean isLog2ConsoleSwitch() {
            return mLog2ConsoleSwitch;
        }

        /**
         * 输出全局的TAG
         * @return
         */
        public String getGlobalTag() {
            if (isSpace(mGlobalTag)) {
                return "";
            }
            return mGlobalTag;
        }

        /**
         * 打印日志的头部信息
         * @return
         */
        public boolean isLogHeadSwitch() {
            return mLogHeadSwitch;
        }

        /**
         * 日志是否输出到文件
         * @return
         */
        public boolean isLog2FileSwitch() {
            return mLog2FileSwitch;
        }

        /**
         * 输出Log边界
         * @return
         */
        public boolean isLogBorderSwitch() {
            return mLogBorderSwitch;
        }

        /**
         * 单行输出TAG
         * @return
         */
        public boolean isSingleTagSwitch() {
            return mSingleTagSwitch;
        }

        /**
         * 获取控制台过滤级别信息
         * @return
         */
        public char getConsoleFilter() {
            return T[mConsoleFilter - V];
        }

        /**
         * 输出文件过滤信息
         * @return
         */
        public char getFileFilter() {
            return T[mFileFilter - V];
        }

        /**
         * 获取堆栈深度
         * @return
         */
        public int getStackDeep() {
            return mStackDeep;
        }

        /**
         * 获取堆栈输出数量
         * @return
         */
        public int getStackOffset() {
            return mStackOffset;
        }

        /**
         * 获取保存的日期
         * @return
         */
        public int getSaveDays() {
            return mSaveDays;
        }

        /**
         * 获取当前级进程的名称
         * @return
         */
        private static String getCurrentProcessName() {
            Application application = FreeRxHttp.getInstance().getContext();
            ActivityManager am =
                    (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
            if (am == null) {
                return "";
            }
            List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
            if (info == null || info.size() == 0) {
                return "";
            }
            int pid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo aInfo : info) {
                if (aInfo.pid == pid) {
                    if (aInfo.processName != null) {
                        return aInfo.processName;
                    }
                }
            }
            return "";
        }

        /**
         * 字符串转化
         * @return
         */
        @Override
        public String toString() {
            return "process: " + getProcessName()
                    + LINE_SEP + "switch: " + isLogSwitch()
                    + LINE_SEP + "console: " + isLog2ConsoleSwitch()
                    + LINE_SEP + "tag: " + getGlobalTag()
                    + LINE_SEP + "head: " + isLogHeadSwitch()
                    + LINE_SEP + "file: " + isLog2FileSwitch()
                    + LINE_SEP + "dir: " + getDir()
                    + LINE_SEP + "filePrefix: " + getFilePrefix()
                    + LINE_SEP + "border: " + isLogBorderSwitch()
                    + LINE_SEP + "singleTag: " + isSingleTagSwitch()
                    + LINE_SEP + "consoleFilter: " + getConsoleFilter()
                    + LINE_SEP + "fileFilter: " + getFileFilter()
                    + LINE_SEP + "stackDeep: " + getStackDeep()
                    + LINE_SEP + "stackOffset: " + getStackOffset()
                    + LINE_SEP + "saveDays: " + getSaveDays()
                    + LINE_SEP + "formatter: " + I_FORMATTER_MAP;
        }
    }

    /**
     * 日志打印的格式化工具类
     * @param <T>
     */
    public abstract static class IFormatter<T> {
        /**
         * format
         * @param t
         * @return
         */
        public abstract String format(T t);
    }

    /**
     * TAG 的头部信息
     */
    private static class TagHead {
        String tag;
        String[] consoleHead;
        String fileHead;

        TagHead(String tag, String[] consoleHead, String fileHead) {
            this.tag = tag;
            this.consoleHead = consoleHead;
            this.fileHead = fileHead;
        }
    }

    /**
     * 日志格式化工具类
     */
    private static class LogFormatter {
        static String object2String(Object object) {
            if (object.getClass().isArray()) {
                return array2String(object);
            }
            if (object instanceof Throwable) {
                return throwable2String((Throwable) object);
            }
            if (object instanceof Bundle) {
                return bundle2String((Bundle) object);
            }
            if (object instanceof Intent) {
                return intent2String((Intent) object);
            }
            return object.toString();
        }

        /**
         * 对象转成Json
         * @param object
         */
        static String object2Json(Object object) {
            if (object instanceof CharSequence) {
                return formatJson(object.toString());
            }
            try {
                return GSON.toJson(object);
            } catch (Throwable t) {
                return object.toString();
            }
        }

        /**
         * 格式化输出XML
         * @param xml
         */
        static String formatXml(String xml) {
            try {
                Source xmlInput = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(xmlInput, xmlOutput);
                xml = xmlOutput.getWriter().toString().replaceFirst(">", ">" + LINE_SEP);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return xml;
        }

        /**
         * 输出堆栈信息
         * @param e
         */
        private static String throwable2String(final Throwable e) {
            return ThrowableUtils.getFullStackTrace(e);
        }

        private static String bundle2String(Bundle bundle) {
            Iterator<String> iterator = bundle.keySet().iterator();
            if (!iterator.hasNext()) {
                return "Bundle {}";
            }
            StringBuilder sb = new StringBuilder(128);
            sb.append("Bundle { ");
            while (true) {
                String key = iterator.next();
                Object value = bundle.get(key);
                sb.append(key).append('=');
                if (value instanceof Bundle) {
                    sb.append(value == bundle ? "(this Bundle)" : bundle2String((Bundle) value));
                } else {
                    sb.append(formatObject(value));
                }
                if (!iterator.hasNext()) {
                    return sb.append(" }").toString();
                }
                sb.append(',').append(' ');
            }
        }

        /**
         * Intent转换成为String
         * @param intent
         */
        private static String intent2String(Intent intent) {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Intent { ");
            boolean first = true;
            String mAction = intent.getAction();
            if (mAction != null) {
                sb.append("act=").append(mAction);
                first = false;
            }
            Set<String> mCategories = intent.getCategories();
            if (mCategories != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("cat=[");
                boolean firstCategory = true;
                for (String c : mCategories) {
                    if (!firstCategory) {
                        sb.append(',');
                    }
                    sb.append(c);
                    firstCategory = false;
                }
                sb.append("]");
            }
            Uri mData = intent.getData();
            if (mData != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("dat=").append(mData);
            }
            String mType = intent.getType();
            if (mType != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("typ=").append(mType);
            }
            int mFlags = intent.getFlags();
            if (mFlags != 0) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("flg=0x").append(Integer.toHexString(mFlags));
            }
            String mPackage = intent.getPackage();
            if (mPackage != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("pkg=").append(mPackage);
            }
            ComponentName mComponent = intent.getComponent();
            if (mComponent != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("cmp=").append(mComponent.flattenToShortString());
            }
            Rect mSourceBounds = intent.getSourceBounds();
            if (mSourceBounds != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("bnds=").append(mSourceBounds.toShortString());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ClipData mClipData = intent.getClipData();
                if (mClipData != null) {
                    if (!first) {
                        sb.append(' ');
                    }
                    first = false;
                    clipData2String(mClipData, sb);
                }
            }
            Bundle mExtras = intent.getExtras();
            if (mExtras != null) {
                if (!first) {
                    sb.append(' ');
                }
                first = false;
                sb.append("extras={");
                sb.append(bundle2String(mExtras));
                sb.append('}');
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                Intent mSelector = intent.getSelector();
                if (mSelector != null) {
                    if (!first) {
                        sb.append(' ');
                    }
                    first = false;
                    sb.append("sel={");
                    sb.append(mSelector == intent ? "(this Intent)" : intent2String(mSelector));
                    sb.append("}");
                }
            }
            sb.append(" }");
            return sb.toString();
        }

        /**
         * 格式化输出Json
         * @param json
         */
        private static String formatJson(String json) {
            try {
                for (int i = 0, len = json.length(); i < len; i++) {
                    char c = json.charAt(i);
                    if (c == '{') {
                        return new JSONObject(json).toString(2);
                    } else if (c == '[') {
                        return new JSONArray(json).toString(2);
                    } else if (!Character.isWhitespace(c)) {
                        return json;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private static void clipData2String(ClipData clipData, StringBuilder sb) {
            ClipData.Item item = clipData.getItemAt(0);
            if (item == null) {
                sb.append("ClipData.Item {}");
                return;
            }
            sb.append("ClipData.Item { ");
            String mHtmlText = item.getHtmlText();
            if (mHtmlText != null) {
                sb.append("H:");
                sb.append(mHtmlText);
                sb.append("}");
                return;
            }
            CharSequence mText = item.getText();
            if (mText != null) {
                sb.append("T:");
                sb.append(mText);
                sb.append("}");
                return;
            }
            Uri uri = item.getUri();
            if (uri != null) {
                sb.append("U:").append(uri);
                sb.append("}");
                return;
            }
            Intent intent = item.getIntent();
            if (intent != null) {
                sb.append("I:");
                sb.append(intent2String(intent));
                sb.append("}");
                return;
            }
            sb.append("NULL");
            sb.append("}");
        }

        /**
         * 数组转化成String
         * @param object
         */
        private static String array2String(Object object) {
            if (object instanceof Object[]) {
                return Arrays.deepToString((Object[]) object);
            } else if (object instanceof boolean[]) {
                return Arrays.toString((boolean[]) object);
            } else if (object instanceof byte[]) {
                return Arrays.toString((byte[]) object);
            } else if (object instanceof char[]) {
                return Arrays.toString((char[]) object);
            } else if (object instanceof double[]) {
                return Arrays.toString((double[]) object);
            } else if (object instanceof float[]) {
                return Arrays.toString((float[]) object);
            } else if (object instanceof int[]) {
                return Arrays.toString((int[]) object);
            } else if (object instanceof long[]) {
                return Arrays.toString((long[]) object);
            } else if (object instanceof short[]) {
                return Arrays.toString((short[]) object);
            }
            throw new IllegalArgumentException("Array has incompatible type: " + object.getClass());
        }
    }

    private static <T> Class getTypeClassFromParadigm(final IFormatter<T> formatter) {
        Type[] genericInterfaces = formatter.getClass().getGenericInterfaces();
        Type type;
        if (genericInterfaces.length == 1) {
            type = genericInterfaces[0];
        } else {
            type = formatter.getClass().getGenericSuperclass();
        }
        type = ((ParameterizedType) type).getActualTypeArguments()[0];
        while (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        }
        String className = type.toString();
        if (className.startsWith("class ")) {
            className = className.substring(6);
        } else if (className.startsWith("interface ")) {
            className = className.substring(10);
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从对象获取其Class类
     * @param obj
     * @return
     */
    private static Class getClassFromObject(final Object obj) {
        Class objClass = obj.getClass();
        if (objClass.isAnonymousClass() || objClass.isSynthetic()) {
            Type[] genericInterfaces = objClass.getGenericInterfaces();
            String className;
            if (genericInterfaces.length == 1) {  // interface
                Type type = genericInterfaces[0];
                while (type instanceof ParameterizedType) {
                    type = ((ParameterizedType) type).getRawType();
                }
                className = type.toString();
            } else {   // abstract class or lambda
                Type type = objClass.getGenericSuperclass();
                while (type instanceof ParameterizedType) {
                    type = ((ParameterizedType) type).getRawType();
                }
                className = type.toString();
            }

            if (className.startsWith("class ")) {
                className = className.substring(6);
            } else if (className.startsWith("interface ")) {
                className = className.substring(10);
            }
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return objClass;
    }
}
