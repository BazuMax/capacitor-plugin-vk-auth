package com.bazumax.plugins.vk

import android.content.Intent
import androidx.activity.result.ActivityResult
import com.getcapacitor.*
import com.getcapacitor.annotation.CapacitorPlugin
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

@CapacitorPlugin
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

        
        val scope = call.getArray("scope").toList<String>();
        val vkScope = VKScope.values().filter { l ->  l.toString().toLowerCase() in scope }
        VK.login(activity, vkScope)
        val intent = Intent("com.vkontakte.android.action.SDK_AUTH")
        startActivityForResult(call, intent, "vkLoginResult")
    }




     private fun vkLoginResult(call: PluginCall, result: ActivityResult) {
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

        VK.onActivityResult(282, result.resultCode, result.data, callback);
    }
}