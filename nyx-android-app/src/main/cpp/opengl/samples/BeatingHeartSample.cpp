//
// Created by Frewen.Wong on 2021/8/1.
//

#include <utils/AuraLogger.h>
#include "BeatingHeartSample.h"

BeatingHeartSample::BeatingHeartSample() {
    LOG_D("BeatingHeartSample::constructor");
}

BeatingHeartSample::~BeatingHeartSample() {
    LOG_D("BeatingHeartSample::deConstructor");
}

void BeatingHeartSample::destroy() {
    LOG_D("BeatingHeartSample::destroy");
}

void BeatingHeartSample::init() {
    LOG_D("BeatingHeartSample::init");
}

void BeatingHeartSample::draw(int screenW, int screenH) {
    // LOG_D("BeatingHeartSample::draw");
}
