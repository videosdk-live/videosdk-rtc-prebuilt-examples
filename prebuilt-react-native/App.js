/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {Component} from 'react';

import WebView from 'react-native-webview';

const prebuiltVersion = '0.3.21';
const token = 'YOUR_TOKEN HERE';
const meetingId = 'wyea-c8vp-ivt6';
const name = 'Jhon Doe';
const micEnabled = true;
const webcamEnabled = true;
const roomParameters = `?meetingId=${meetingId}&name=${name}
      &micEnabled=${micEnabled}&webcamEnabled=${webcamEnabled}&token=${token}`;
const roomUrl = `https://embed.videosdk.live/rtc-js-prebuilt/${prebuiltVersion}/${roomParameters}`;

export default class App extends Component {
  render() {
    return (
      <WebView
        startInLoadingState
        source={{uri: roomUrl}}
        mediaPlaybackRequiresUserAction={false}
        // iOS specific:
        allowsInlineMediaPlayback
        // Android specific:
        javaScriptEnabled
        domStorageEnabled
      />
    );
  }
}
