//
//  ReactNativeKakao.m
//  ReactNativeKakao
//
//  Created by Jeff Kang on 4/24/17.
//  Copyright © 2017 Jeff Gu Kang. All rights reserved.
//

#import "ReactNativeKakao.h"
#import <React/RCTConvert.h>

@implementation RCTConvert (KOAuthType)
RCT_ENUM_CONVERTER(KOAuthType, (@{ @"KOAuthTypeTalk" : @(KOAuthTypeTalk),
																						 @"KOAuthTypeStory" : @(KOAuthTypeStory),
																						 @"KOAuthTypeAccount" : @(KOAuthTypeAccount)}),
									 KOAuthTypeTalk, integerValue)
@end


@implementation ReactNativeKakao

- (NSDictionary *)constantsToExport
{
	return @{ @"KOAuthTypeTalk" : @(KOAuthTypeTalk),
						@"KOAuthTypeStory" : @(KOAuthTypeStory),
						@"KOAuthTypeAccount" : @(KOAuthTypeAccount) };
};

RCT_EXPORT_MODULE();

RCT_REMAP_METHOD(login,
								 authTypes: (NSArray* )authTypes
				 resolver:(RCTPromiseResolveBlock)resolve
				 rejecter:(RCTPromiseRejectBlock)reject)
{
	dispatch_async(dispatch_get_main_queue(), ^{
		[[KOSession sharedSession] close];
		[[KOSession sharedSession] openWithCompletionHandler:^(NSError *error) {
			if(error) {
				reject(@"RNKakao", @"login faild", error);
				return;
			}
			
			if ([[KOSession sharedSession] isOpen]) {
				[self loginResolve:resolve rejecter:reject];
			} else {
				reject(@"RNKakao", @"login canceled", nil);
			}
		} authParams:nil authTypes:authTypes];
	});
}

- (void)loginResolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject {
	[KOSessionTask meTaskWithCompletionHandler:^(KOUser* result, NSError *error) {
		if (result) {
			// success
			NSDictionary *response = @{@"user": result};
			resolve(response);
		} else {
			// failed
			reject(@"RNKakao", @"login error", error);
		}
	}];
}
@end
