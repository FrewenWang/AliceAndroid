//
// Created by Frewen.Wong on 2021/10/10.
//

#include "OpenGLUtil.h"
#include "AuraLogger.h"


GLuint OpenGLUtil::createProgram(const char *pVertexShaderSource, const char *pFragShaderSource,
                                 GLuint &vertexShaderHandle, GLuint &fragShaderHandle) {
    GLuint program = 0;
    FUN_BEGIN_TIME("GLUtils::CreateProgram")
        vertexShaderHandle = loadShader(GL_VERTEX_SHADER, pVertexShaderSource);
        if (!vertexShaderHandle) return program;
        fragShaderHandle = loadShader(GL_FRAGMENT_SHADER, pFragShaderSource);
        if (!fragShaderHandle) return program;

        program = glCreateProgram();
        if (program) {
            glAttachShader(program, vertexShaderHandle);
            checkGLError("glAttachShader");
            glAttachShader(program, fragShaderHandle);
            checkGLError("glAttachShader");
            glLinkProgram(program);
            GLint linkStatus = GL_FALSE;
            glGetProgramiv(program, GL_LINK_STATUS, &linkStatus);

            glDetachShader(program, vertexShaderHandle);
            glDeleteShader(vertexShaderHandle);
            vertexShaderHandle = 0;
            glDetachShader(program, fragShaderHandle);
            glDeleteShader(fragShaderHandle);
            fragShaderHandle = 0;
            if (linkStatus != GL_TRUE) {
                GLint bufLength = 0;
                glGetProgramiv(program, GL_INFO_LOG_LENGTH, &bufLength);
                if (bufLength) {
                    char *buf = (char *) malloc((size_t) bufLength);
                    if (buf) {
                        glGetProgramInfoLog(program, bufLength, NULL, buf);
                        LOG_E("GLUtils::CreateProgram Could not link program:\n%s\n", buf);
                        free(buf);
                    }
                }
                glDeleteProgram(program);
                program = 0;
            }
        }
    FUN_END_TIME("GLUtils::CreateProgram")
    LOG_D("GLUtils::CreateProgram program = %d", program);
    return program;
}

GLuint OpenGLUtil::loadShader(GLenum shaderType, const char *pSource) {
    GLuint shader = 0;
    FUN_BEGIN_TIME("GLUtils::loadShader")
        shader = glCreateShader(shaderType);
        if (shader) {
            glShaderSource(shader, 1, &pSource, NULL);
            glCompileShader(shader);
            GLint compiled = 0;
            glGetShaderiv(shader, GL_COMPILE_STATUS, &compiled);
            if (!compiled) {
                GLint infoLen = 0;
                glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &infoLen);
                if (infoLen) {
                    char *buf = (char *) malloc((size_t) infoLen);
                    if (buf) {
                        glGetShaderInfoLog(shader, infoLen, NULL, buf);
                        LOG_E("GLUtils::LoadShader Could not compile shader %d:\n%s\n",
                              shaderType, buf);
                        free(buf);
                    }
                    glDeleteShader(shader);
                    shader = 0;
                }
            }
        }
    FUN_END_TIME("GLUtils::loadShader")
    return shader;
}

void OpenGLUtil::checkGLError(const char *pGLOperation) {
    for (GLint error = glGetError(); error; error = glGetError()) {
        LOG_E("OpenGLUtil::checkGLError GL Operation %s() glError (0x%x)\n", pGLOperation, error);
    }

}
