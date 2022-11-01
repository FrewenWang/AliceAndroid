package com.frewen.aura.perfguard.core.engine;

import com.frewen.aura.perfguard.core.base.IEngineConfig;

/**
 * @filename: IPerfGuardEngine
 * @author: Frewen.Wong
 * @time: 2021/8/14 14:19
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public interface IPerfGuardEngine<EngineConfig extends IEngineConfig> {

    boolean init(EngineConfig config);

    void startWork();

    void stopWork();

    void destroy();

    boolean initialized();

    EngineConfig config();

}
