# CMake的构建提示
# https://developer.android.com/studio/projects/configure-cmake?hl=zh-cn
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html
#CMake 构建脚本是一个纯文本文件，您必须将其命名为 CMakeLists.txt，
#并在其中包含 CMake 构建您的 C/C++ 库时需要使用的命令。如果您的原生源代码文件还没有 CMake 构建脚本，
#您需要自行创建一个，并在其中包含适当的 CMake 命令。
# CMakeLists.txt ：设置构建本地库所需的CMake最低版本。
cmake_minimum_required(VERSION 3.4.1)


# 下面我们学习一下cmake创建一个变量，使用的是set
set(msg WelocomeToAndroidJniWorld)
# CMake是不区分大小写的: SET(var helloJni)
message(${msg})
set(msg 1234567)
message(${msg})
# 当前的Cmake的文件路径的常量
message(${CMAKE_CURRENT_LIST_FILE})
message(${CMAKE_CURRENT_LIST_DIR})


# 由于People是在people的二级目录下，如果我们想要在全局的搜索路径进行搜索
# 我们就需要放到全局的搜索路径下面，使用下面：
include_directories(people/)
# 这样我们在进行导入People类的时候
# 双引号是从相对路径进行搜索
# #include "people/People.h"
# 而尖括号是从全局搜索路径进行搜索.因为我们已经通过
# include_directories(people/) 将其加入到全局搜索目录下面，所以就可以直接使用尖括号
# include <People.h>
# 放到全局的搜索路径下面，使用下面：
include_directories(base/)
include_directories(jni/)
include_directories(thread/)

# 添加源文件或者库.根据C++文件，我们要把hello-jni.cpp编译成一个hello-jni的SO
add_library( # Sets the name of the library.
        hello-jni   # 库的名字
        # Sets the library as a shared library.
        SHARED  #库的类型 共享库（动态）库、如果是STATIC的话，就是静态库
        # Provides a relative path to your source file(s).
        hello-jni.cpp
        jni/jni_basic_type.cpp
        )  # 库的源文件
# 我们创建一个的动态链接库，我们想将people/People.cpp也打包成一个SO库
add_library( # Sets the name of the library.
        poeple-jni   # 库的名字
        # Sets the library as a shared library.
        SHARED  #库的类型 共享库（动态）库、如果是STATIC的话，就是静态库
        # Provides a relative path to your source file(s).
        people/People.cpp)  # 库的源文件

# =========================Jni的函数的动态注册，通过add_library会再生成一个jni的SO================================
add_library(
        # 设置库的名字。Java层调用可以使用：System.loadLibrary("dynamic-register-jni");
        dynamic-register-jni
        # Sets the library as a shared library.
        SHARED  #库的类型 共享库（动态）库、如果是STATIC的话，就是静态库
        jni/jni_dynamic_load.cpp
        jni/jni_invoke_method.cpp
        thread/jni_thread.cpp
        base/jvm.cpp
)  # 库关联所有的源文件



# ================OpenGL ES的Native库，通过add_library会再生成一个jni的SO==========================
add_library(
        # 设置库的名字。Java层调用可以使用：System.loadLibrary("nyx-opengles-lib");
        nyx-opengles-lib
        # Sets the library as a shared library.
        SHARED                          #库的类型 共享库（动态）库、如果是STATIC的话，就是静态库
        opengles/hello_opengl_es.cpp    # 库关联所有的源文件
)


# 引用NDK中的库log，命名为log-lib。用于在安卓系统中查找一些动态库
find_library( # Sets the name of the path variable.
        log-lib  #库路径对应的变量名
        log)   #NDK中的库名


# 关联库(链接库)，确保hello-jni中 能使用log库。
# 我们将我们的hello-jni和poeple-jni的链接库进行关联起来
target_link_libraries( # Specifies the target library.
        hello-jni
        poeple-jni
        dynamic-register-jni
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})

