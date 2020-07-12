# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
# CMakeLists.txt ：设置构建本地库所需的CMake最低版本。
cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# 添加源文件或者库.根据C++文件
add_library( # Sets the name of the library.
        hello-jni   # 库的名字

        # Sets the library as a shared library.
        SHARED  #库的类型 共享库（动态）库、如果是STATIC的话，就是静态库

        # Provides a relative path to your source file(s).
        hello-jni.cpp)  # 库的源文件

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

# 引用NDK中的库log，命名为log-lib
find_library( # Sets the name of the path variable.
        log-lib  #库路径对应的变量名

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)   #NDK中的库名

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

# 关联库(链接库)，确保hello-jni中 能使用log库
target_link_libraries( # Specifies the target library.
        hello-jni
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})