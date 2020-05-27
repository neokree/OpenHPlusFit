#import "HplusfitprotocolPlugin.h"
#if __has_include(<hplusfitprotocol/hplusfitprotocol-Swift.h>)
#import <hplusfitprotocol/hplusfitprotocol-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "hplusfitprotocol-Swift.h"
#endif

@implementation HplusfitprotocolPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftHplusfitprotocolPlugin registerWithRegistrar:registrar];
}
@end
