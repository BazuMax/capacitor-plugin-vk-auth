import Foundation
import Capacitor
import VK_ios_sdk

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(VKAuth)
public class VKAuth: CAPPlugin, VKSdkDelegate, VKSdkUIDelegate {
    public func vkSdkShouldPresent(_ controller: UIViewController!) {
        if (self.bridge?.viewController?.presentedViewController != nil) {
            self.bridge?.viewController!.dismiss(animated: true, completion: {
                self.bridge?.viewController!.present(controller, animated: true, completion: {
                })
            })
        } else {
            self.bridge?.viewController!.present(controller, animated: true, completion: {
            })
        }
    }
    
    public func vkSdkNeedCaptchaEnter(_ captchaError: VKError!) {
        print("vkSdkNeedCaptchaEnter")
    }
    
    
    public func vkSdkAccessAuthorizationFinished(with result: VKAuthorizationResult!) {
        var resultData: [String: Any] = [:]
        if (result.error != nil) {
            resultData = [
                "result": false,
                "error": result.description
            ]
            return
        } else if (result.token != nil) {
            let token = result.token!
            resultData = [
                "result": true,
                "token": token.accessToken as Any,
                "created": token.created,
                "expiresIn": token.expiresIn,
                "userId": token.userId as Any,
            ]
            if (result.token.email != nil) {
                resultData.updateValue(result.token.email!, forKey: "email")
            }
        }

        if resultData.isEmpty == false {
            self.notifyListeners("vkAuthFinished", data: resultData)
        }
    }
    
    public func vkSdkUserAuthorizationFailed() {
        self.notifyListeners("vkAuthFinished", data: [
            "result": false,
            "message": "UserAuthenticationFailed"
        ])
    }
    
    
    var VK_APP_ID   : String = "123"
    var sdkInstance : VKSdk? = nil
    
    
    @objc func initWithId(_ call: CAPPluginCall) {
        VK_APP_ID = call.getString("id") ?? "123456"
        sdkInstance = VKSdk.initialize(withAppId: VK_APP_ID)
        sdkInstance?.register(self)
        sdkInstance?.uiDelegate = self

        NotificationCenter.default.addObserver(
            self,
            selector: #selector(self.onCustomUrlOpen),
            name: Notification.Name(CAPNotifications.URLOpen.name()),
            object: nil)
        
        call.success([
            "message": "Success init with vk\(VK_APP_ID)"
        ])
    }
    
    
    @objc func auth(_ call: CAPPluginCall) {
        if sdkInstance == nil {
            call.error("SDK is not initialized, please Initialize plugin before call auth method!")
            return
        }
        
        let scope  = call.getArray("scope", String.self) ?? ["offline", "email"]
        VKSdk.wakeUpSession(scope) { (state, error) in
            if (state == VKAuthorizationState.authorized) {
                VKSdk.forceLogout();
            }
            
            DispatchQueue.main.async {
                VKSdk.authorize(scope)
            }
            
            call.success([
                "message": "Success auth request"
            ])
        }
    }
    
    
    
    @objc private func onCustomUrlOpen(notification: NSNotification){
        guard let object = notification.object as? [String:Any?] else {
            return
        }

        let url = object["url"] as! URL
        VKSdk.processOpen(url, fromApplication: nil)
        print(url.absoluteString)
    }
}
