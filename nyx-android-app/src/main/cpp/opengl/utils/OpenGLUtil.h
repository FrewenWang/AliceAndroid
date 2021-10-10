//
// Created by Frewen.Wong on 2021/10/10.
//

#ifndef NYX_ANDROID_OPENGL_UTIL_H
#define NYX_ANDROID_OPENGL_UTIL_H

#include <GLES3/gl3.h>
#include <string>
#include <glm.hpp>


#define MATH_PI 3.1415926535897932384626433832802

/**
 * OpenGL的通用工具类
 */
class OpenGLUtil {

public:
    /**
     *
     * @param pVertexShaderSource  顶点着色器资源
     * @param pFragShaderSource    线段着色器资源
     * @param vertexShaderHandle
     * @param fragShaderHandle
     * @return
     */
    static GLuint createProgram(const char *pVertexShaderSource, const char *pFragShaderSource,
                                GLuint &vertexShaderHandle, GLuint &fragShaderHandle);

    /**
     * 加载着色器
     * @param shaderType  着色器类型
     * @param pSource     着色器资源
     * @return
     */
    static GLuint loadShader(GLenum shaderType, const char *pSource);

    /**
     * 查看OpenGLES的异常状态
     * @param pGLOperation
     */
    static void checkGLError(const char *pGLOperation);

};


#endif //NYX_ANDROID_OPENGL_UTIL_H

