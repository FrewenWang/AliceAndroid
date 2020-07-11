# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
# CMakeLists.txt ：设置构建本地库所需的CMake最低版本。

cmake_minimum_required(VERSION 3.4.1)


add_library( hello-jni
             SHARED
             src/main/cpp/hello-jni.cpp)

include_directories(src/main/cpp/include/)

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
              log )   #NDK中的库名

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

# 将预构建库关联到我们自己的原生库
# 关联库，确保native-lib 中 能使用log库
target_link_libraries( # Specifies the target library.
                       hello-jni

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )