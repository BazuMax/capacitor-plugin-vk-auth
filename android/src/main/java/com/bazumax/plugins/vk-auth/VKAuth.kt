package com.bazumax.plugins.vk

import android.content.Intent
import com.getcapacitor.*
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import okhttp3.internal.notify

@NativePlugin(
        // VK AuthResult request code
        requestCodes= [282]
)
class VKAuth : Plugin() {
    @PluginMethod
    fun initWithId(call: PluginCall) {
        val value = call.getString("id")


        val ret = JSObject()
        ret.put("id", value + "kek")
        call.success(ret)
    }
    @PluginMethod
    fun auth(call: PluginCall) {
        val value = call.getString("id")

        val scope = call.getArray("scope", JSArray(arrayOf("offline"))).toList<String>();
        val vkScope = VKScope.values().filter { l ->  l.toString().toLowerCase() in scope }
        VK.login(bridge.activity, vkScope)

        val ret = JSObject()
        ret.put("id", value + "kek")
        call.success(ret)
    }


    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                val ret = JSObject()
                        .put("result", true)
                        .put("token", token.accessToken)
                        .put("created", token.created)
//                        .put("expiresIn", token.)
                        .put("userId", token.userId)
                        .put("email", token.email)
                notifyListeners("vkAuthFinished",  ret)
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
                val ret = JSObject()
                        .put("result", false)
                        .put("error", errorCode)
                notifyListeners("vkAuthFinished",  ret)
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.handleOnActivityResult(requestCode, resultCode, data)
        }
    }
}