//
//  ViewController.swift
//  Prebuilt Webview iOS
//
//  Created by iMac on 01/11/22.
//

import UIKit
import WebKit
import SafariServices

class ViewController: UIViewController, SFSafariViewControllerDelegate {

    public var prebuiltVersion = "0.3.43" // VideoSDK Prebuilt Version
    public var token = "YOUR_TOKEN" //Replace your token here
    public var meetingId = "YOUR_MEETINGID" //Replace your meeting id

    public var name = "John Doe"
    public var webcamEnabled = "true"
    public var micEnabled = "true"

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        var meetingUrl = URLComponents()
        meetingUrl.scheme = "https"
        meetingUrl.host = "embed.videosdk.live"
        meetingUrl.path = "/rtc-js-prebuilt/"+prebuiltVersion
        meetingUrl.queryItems = [
            URLQueryItem(name: "name", value: name),
            URLQueryItem(name: "micEnabled", value: micEnabled),
            URLQueryItem(name: "webcamEnabled", value: webcamEnabled),
            URLQueryItem(name: "meetingId", value: meetingId),
            URLQueryItem(name: "token", value: token),
        ]
        let safariVC = SFSafariViewController(url: meetingUrl.url!)
        safariVC.delegate = self
        present(safariVC, animated: true)
    }
}
