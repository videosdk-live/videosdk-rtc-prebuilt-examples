import 'package:flutter/material.dart';
import 'package:flutter_inappwebview/flutter_inappwebview.dart';
import 'package:permission_handler/permission_handler.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key}) : super(key: key);

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final String _prebuiltVersion = "0.3.21";
  final String _token = "";
  final String _meetingId = "wyea-c8vp-ivt6";
  final String _name = "John Doe";
  final bool _micEnabled = true;
  final bool _webcamEnabled = true;
  String _url = "";
  @override
  void initState() {
    _url =
        "https://embed.videosdk.live/rtc-js-prebuilt/$_prebuiltVersion/?meetingId=$_meetingId&name=$_name&micEnabled=$_micEnabled&webcamEnabled=$_webcamEnabled&token=$_token";

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text("Meeting")),
        body: Container(
            child: Column(children: <Widget>[
          Expanded(
            child: Container(
              child: InAppWebView(
                initialUrlRequest: URLRequest(url: Uri.parse(_url)),
                initialOptions: InAppWebViewGroupOptions(
                    crossPlatform: InAppWebViewOptions(
                      mediaPlaybackRequiresUserGesture: false,
                    ),
                    ios: IOSInAppWebViewOptions(
                      allowsInlineMediaPlayback: true,
                    )),
                androidOnPermissionRequest: (InAppWebViewController controller,
                    String origin, List<String> resources) async {
                  await Permission.camera.request();
                  await Permission.microphone.request();
                  return PermissionRequestResponse(
                      resources: resources,
                      action: PermissionRequestResponseAction.GRANT);
                },
              ),
            ),
          ),
        ])));
  }
}
