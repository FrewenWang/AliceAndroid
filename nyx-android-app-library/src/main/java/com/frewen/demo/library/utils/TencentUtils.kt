package com.frewen.demo.library.utils

import com.frewen.aura.toolkits.common.Base64Utils
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * @filename: TencentUtils
 * @introduction:
 *
 * static {
 *   TencentUtils var0 = new TencentUtils();
 *   INSTANCE = var0;
 * }
 * @author: Frewen.Wong
 * @time: 2020/5/13 21:54
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
object TencentUtils {
    const val secretId = "AKIDnjz8xpfrnajD4jttiwh7z4b7bo52D0ok69js"
    const val secretKey = "7ftsdyjglbd4ug2MgauW1Doa1KvrDlwH5s9Tm6u"
    private const val CONTENT_CHARSET = "UTF-8"
    private const val HMAC_ALGORITHM = "HmacSHA1"

    /**
     * 进行密钥签名
     */
    private fun sign(secret: String, timeStr: String): String? {
        //get signStr
        val signStr = "date: $timeStr\nsource: source"
        //get sig
        try {
            val mac1 = Mac.getInstance(HMAC_ALGORITHM)
            val secretKey = SecretKeySpec(secret.toByteArray(charset(CONTENT_CHARSET)), mac1.algorithm)
            mac1.init(secretKey)
            val hash = mac1.doFinal(signStr.toByteArray(charset(CONTENT_CHARSET)))
            val sig = Base64Utils.encode(hash)
            println("signValue--->$sig")
            return sig
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return null
    }

    fun getAuthorization(timeStr: String): String {
        val sig = sign(secretKey, timeStr)
        return "hmac id=\"$secretId\", algorithm=\"hmac-sha1\", headers=\"date source\", signature=\"$sig\""
    }

    val timeStr: String
        get() {
            val cd = Calendar.getInstance()
            val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            return sdf.format(cd.time)
        }
}