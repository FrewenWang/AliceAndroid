package com.frewen.network.function;

import com.frewen.network.response.AuraNetResponse;
import com.frewen.network.response.exception.AuraNetException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @filename: ApiResultParserFunction
 * @author: Frewen.Wong
 * @time: 12/13/20 10:08 AM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class ApiResultParserFunction<Data> implements Function<AuraNetResponse<Data>, Data> {

    @Override
    public Data apply(@NonNull AuraNetResponse<Data> response) throws Exception {
        if (AuraNetException.isResponseOk(response)) {
            return response.getData();// == null ? Optional.ofNullable(tApiResult.getData()).orElse(null) : tApiResult.getData();
        } else {
            throw new AuraNetException(response.getCode(), response.getMsg());
        }
    }
}
