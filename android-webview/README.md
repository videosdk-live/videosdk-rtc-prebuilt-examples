# Video SDK No Code Prebuilt App Using Android Webview.

## What is it?

This code sample demonstrates a one-to-one and group video call application built with [Video SDK RTC Prebuilt SDK](https://docs.videosdk.live/docs/guide/prebuilt-video-and-audio-calling/getting-started) and [Video SDK RTC JS SDK](https://docs.videosdk.live/docs/realtime-communication/sdk-reference/javascript-sdk/setup)

- Built for serverless video calling experience.
- Scale it upto 5,000 participants with low code.
- 10,000 minutes free on monthly basis.
- Inbuilt video and audio quality optimization.
- Inbuilt chat poll, whiteboard, Q and A support.

## Features

- [x] Completely Low code and serverless.
- [x] Video API with real-time audio, video and data streams
- [x] 5,000+ participants support
- [x] Chat support with rich media.
- [x] Screen sharing with HD and Full HD.
- [x] Play realtime video in meeting
- [x] Connect it with social media such as Facebook, Youtube etc (RTMP out support).
- [x] Intelligent speaker switch
- [x] Record your meetings on cloud
- [x] Inbuilt support of whiteboard, poll and Q & A.
- [x] Customize UI as per your needs.

## Browser Support

Visit our official guide for [Browser Support](https://docs.videosdk.live/docs/realtime-communication/see-also/device-browser-support)

## Prerequisites

Before proceeding, ensure that your development environment meets the following requirements:

1. Java Development Kit.
2. Android Studio 3.0 or later.
3. Android SDK API Level 18 or higher.
4. A mobile device that runs Android 4.3 or later.

## Getting started

1. Clone the repo

   ```sh
   git clone https://github.com/videosdk-live/videosdk-rtc-prebuilt-examples.git
   cd android-webview
   ```

2. Update the `token` and `meetingId` in the `MainActivity.java` file..

```javascript
//...
private String token = "REPLACE_YOUR_TOKEN_HERE";

private String prebuiltVersion = "0.3.43";

private String meetingId = "YOUR_MEETING_ID";

//...
```

3. Run the android app with **Shift+F10** or the ▶️ **Run** from toolbar.

## Resources

Visit, [https://www.videosdk.live/](https://www.videosdk.live/) to generate API key and secret.
