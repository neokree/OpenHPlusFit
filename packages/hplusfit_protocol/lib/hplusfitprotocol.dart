import 'dart:async';

import 'package:flutter/services.dart';

class Hplusfitprotocol {
  static const MethodChannel _channel =
      const MethodChannel('hplusfitprotocol');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
