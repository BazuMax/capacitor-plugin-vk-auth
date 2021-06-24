# Capacitor Plugin VK Auth

## Install
Use Yarn
```
yarn add capacitor-plugin-vk-auth
```
Use NPM
```
npm install capacitor-plugin-vk-auth --save
```

## Using
```javascript
VKAuth.initWithId({ id: '7569443' })
VKAuth.auth({ scope: ['offline'] });
VKAuth.addListener("vkAuthFinished", (info) => {
    console.log("vkAuthFinished was fired", JSON.stringify(info, null, 2));
});
```

## Setup VK APP

Setup App in [vk.com/dev](https://vk.com/dev)

Create Standalone-application
<p align="center">
<img src="https://user-images.githubusercontent.com/11452353/123266356-9ae1b200-d504-11eb-9148-38e7e761435f.png" width="600">
</p>

Example both for Android & IOS

P.S. Setup for ios is unnecessary
<p align="center">
<img src=".github/img/working-settings.jpg" width="600">
</p>


## Android

[Click to open useful docs for android](https://vk.com/dev/android_sdk?f=1.%20%D0%9F%D0%BE%D0%B4%D0%B3%D0%BE%D1%82%D0%BE%D0%B2%D0%BA%D0%B0%20%D0%BA%20%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8E)

Add VK APP ID to app/res/values/strings.xml
<p align="center">
<img src=".github/img/android-vkid.jpg" width="600">
</p>

## IOS

Insert vkID into your `Info.plist` file like this

<p align="center">
<img src=".github/img/ios-vkid.jpg" width="600">
</p>

[Click to open useful docs for ios](https://vk.com/dev/ios_sdk?f=1.%20%D0%9F%D0%BE%D0%B4%D0%B3%D0%BE%D1%82%D0%BE%D0%B2%D0%BA%D0%B0%20%D0%BA%20%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8E)

### Only for IOS 9
[Click to open settings for IOS 9](https://vk.com/dev/ios_sdk?f=1.2.%20%D0%98%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D1%8F%20%D0%B4%D0%BB%D1%8F%20iOS%209)


## Support
For any support create an issue and describe your problem, we can help you with our plugin ;)
