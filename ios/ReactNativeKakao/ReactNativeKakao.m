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
RCT_ENUM_CONVERTER(KOAuthType, (@{
								  @"KOAuthTypeTalk" : @(KOAuthTypeTalk),
								  @"KOAuthTypeStory" : @(KOAuthTypeStory),
								  @"KOAuthTypeAccount" : @(KOAuthTypeAccount)
								}), KOAuthTypeTalk, integerValue)
@end


@implementation ReactNativeKakao

RCT_EXPORT_MODULE();

- (NSDictionary *)constantsToExport
{
	return @{ @"KOAuthTypeTalk" : @(KOAuthTypeTalk),
						@"KOAuthTypeStory" : @(KOAuthTypeStory),
						@"KOAuthTypeAccount" : @(KOAuthTypeAccount) };
};

/**
 Login or Signup
 @param authTypes array consists in KOAuthType.
 */
RCT_REMAP_METHOD(login,
				 authTypes: (NSArray* )authTypes
				 resolver:(RCTPromiseResolveBlock)resolve
				 rejecter:(RCTPromiseRejectBlock)reject)
{
	dispatch_async(dispatch_get_main_queue(), ^{
		[[KOSession sharedSession] close];
		NSArray *auths = (authTypes != nil) ? authTypes : @[@(KOAuthTypeTalk), @(KOAuthTypeStory), @(KOAuthTypeAccount)];
//		- (void)openWithCompletionHandler:(KOSessionCompletionHandler)completionHandler authTypes:(NSArray<NSNumber *> *)authTypes;
		[[KOSession sharedSession] openWithCompletionHandler:^(NSError *error) {
			NSLog(@"MYLOG: openWithCompletionHandler");
			
			if(error) {
				NSLog(@"Error: %@", error.description);
				NSLog(@"%@", error.description);
				
				reject(@"RNKakao", @"login error", error);
				return;
			}
			
			if ([[KOSession sharedSession] isOpen]) {
				NSLog(@"sharedSession is open");
				
				[self userInfoRequestResolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject];
				return;
			} else {
				reject(@"RNKakao", @"login canceled", nil);
				return;
			}
		} authTypes:auths];
	});
}

/**
 Get userInfo
 */
RCT_REMAP_METHOD(userInfo,
				 resolver:(RCTPromiseResolveBlock)resolve
				 rejecter:(RCTPromiseRejectBlock)reject)
{
	[self userInfoRequestResolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject];
}

- (void) userInfoRequestResolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject {
	[KOSessionTask meTaskWithCompletionHandler:^(KOUser* result, NSError *error) {
		if (result) {
			// success

			NSNumber *id = result.ID;
			NSString *nickName = [result propertyForKey:KOUserNicknamePropertyKey];
			// NSString *email = [result propertyForKey:KOUserEmailPropertyKey];
			NSString *email = result.email;
			NSString *profileImage = [result propertyForKey:KOUserProfileImagePropertyKey];
			NSString *profileImageThumnail = [result propertyForKey:KOUserThumbnailImagePropertyKey];

			NSMutableDictionary *userInfo = [NSMutableDictionary dictionary];
			[userInfo setValue:id forKey:@"id"];
			[userInfo setValue:[KOSession sharedSession].accessToken forKey:@"accessToken"];

			if (nickName != nil) {
				[userInfo setValue:nickName forKey:@"nickName"];
			}

			if (email != nil) {
				[userInfo setValue:email forKey:@"email"];
			}

			if (profileImage != nil) {
				[userInfo setValue:profileImage forKey:@"profileImage"];
			}

			if (profileImageThumnail != nil) {
				[userInfo setValue:profileImageThumnail forKey:@"profileImageThumnail"];
			}

			resolve(userInfo);
		} else {
			// failed
			reject(@"RNKakao", @"userInfo error", error);
		}
	}];
}
@end
