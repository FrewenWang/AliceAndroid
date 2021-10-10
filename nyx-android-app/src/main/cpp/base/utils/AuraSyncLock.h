//
// Created by Frewen.Wong on 2021/10/10.
//

#ifndef NYX_ANDROID_SYNC_LOCK_H
#define NYX_ANDROID_SYNC_LOCK_H

#include <pthread.h>

/**
 * 同步锁对象
 */
class AuraSyncLock {

public:
    AuraSyncLock() {
        pthread_mutexattr_init(&m_attr);
    }

    ~AuraSyncLock() {

    }

private:
    pthread_mutex_t m_mutex;
    pthread_mutexattr_t m_attr;
};


#endif //NYX_ANDROID_SYNC_LOCK_H
