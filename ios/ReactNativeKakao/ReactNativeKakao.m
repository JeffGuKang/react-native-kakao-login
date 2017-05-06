//
//  ReactNativeKakao.m
//  ReactNativeKakao
//
//  Created by Jeff Kang on 4/24/17.
//  Copyright © 2017 Jeff Gu Kang. All rights reserved.
//

#import "ReactNativeKakao.h"

@implementation ReactNativeKakao

RCT_EXPORT_MODULE();

RCT_REMAP_METHOD(login,
				 resolver:(RCTPromiseResolveBlock)resolve
				 rejecter:(RCTPromiseRejectBlock)reject)
{
	dispatch_async(dispatch_get_main_queue(), ^{
		[[KOSession sharedSession] close];
		[[KOSession sharedSession] openWithCompletionHandler:^(NSError *error) {
			if(error) {
			   reject(@"RNK: LOGIN FAILED", @"faild", error);
			   return;
			}

			if ([[KOSession sharedSession] isOpen]) {
				[self loginProcessResolve:resolve rejecter:reject];
			} else {
				// failed
				NSLog(@"login canceled.");
				//NSError *error = [NSError errorWithDomain:@"kakaologin" code:1 userInfo:nil];
				reject(@"RNK: LOGIN CANCELED", @"canceled", nil);
			}
		}];
	});
}

@end
