import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:hplusfitprotocol/hplusfitprotocol.dart';

void main() {
  const MethodChannel channel = MethodChannel('hplusfitprotocol');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await Hplusfitprotocol.platformVersion, '42');
  });
}
