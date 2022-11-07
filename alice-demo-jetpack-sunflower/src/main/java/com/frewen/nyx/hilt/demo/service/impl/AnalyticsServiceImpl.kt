package com.frewen.nyx.hilt.demo.service.impl

import com.frewen.nyx.hilt.demo.service.AnalyticsService
import javax.inject.Inject

class AnalyticsServiceImpl @Inject constructor() : AnalyticsService {

    override fun analyticsMethods() {
        print("AnalyticsServiceImpl analyticsMethods Called")
    }
}