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
        if (self.bridge.viewController.presentedViewController != nil) {
            self.bridge.viewController.dismiss(animated: true, completion: {
                self.bridge.viewController.present(controller, animated: true, completion: {
                })
            })
        } else {
            self.bridge.viewController.present(controller, animated: true, completion: {
            })
        }
    }
    
    public func vkSdkNeedCaptchaEnter(_ captchaError: VKError!) {
        print("vkSdkNeedCaptchaEnter")
    }
    
    
    public func vkSdkAccessAuthorizationFinished(with result: VKAuthorizationResult!) {
//        result.token.
//        self.bridge.triggerDocumentJSEvent(eventName: "vkAuthFinished", [])
//        if result.token
    }
    
    public func vkSdkUserAuthorizationFailed() {
        print("vkSdkUserAuthorizationFailed")
    }
    
    
    var VK_APP_ID = "123456"
    var sdkInstance: VKSdk? = nil
    
    
    @objc func initWithId(_ call: CAPPluginCall) {
        VK_APP_ID = call.getString("id") ?? "123456"
        sdkInstance = VKSdk.initialize(withAppId: VK_APP_ID)
        sdkInstance?.register(self)
        sdkInstance?.uiDelegate = self
        
        NotificationCenter.default.addObserver(self, selector: #selector(self.onCustomUrlOpen), name: Notification.Name(CAPNotifications.URLOpen.name()), object: nil)
        
        call.success([
            "value": "Pizda blyat \(VK_APP_ID)"
        ])
    }
    
    
    @objc func auth(_ call: CAPPluginCall) {
        let scope  = call.getArray("scope", String.self) ?? ["offline",
        "email"]
        print("SCOPE: \(scope)")
        DispatchQueue.main.async {
            VKSdk.authorize(scope)
        }
        call.success([
            "value": "Pizda blyat \(VK_APP_ID)"
        ])
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
