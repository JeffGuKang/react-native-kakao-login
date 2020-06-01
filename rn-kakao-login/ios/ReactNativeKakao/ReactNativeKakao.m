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
+ (BOOL)requiresMainQueueSetup
{
	return YES;
}

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
RCT_REMAP_METHOD(loginWithAllTypes,				 
				 resolver:(RCTPromiseResolveBlock)resolve
				 rejecter:(RCTPromiseRejectBlock)reject)
{
	dispatch_async(dispatch_get_main_queue(), ^{
		[[KOSession sharedSession] close];
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
		} authType:KOAuthTypeTalk, KOAuthTypeStory, KOAuthTypeAccount, nil];
	});
}

/**
 Login or Signup
 */
RCT_REMAP_METHOD(login,
				 loginWithResolver:(RCTPromiseResolveBlock)resolve
				 loginWithRejecter:(RCTPromiseRejectBlock)reject)
{
	dispatch_async(dispatch_get_main_queue(), ^{
		[[KOSession sharedSession] close];
		NSArray *auths = @[@(KOAuthTypeTalk)];
		
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
				 userInfoWithResolver:(RCTPromiseResolveBlock)resolve
				 userInfoWithRejecter:(RCTPromiseRejectBlock)reject)
{
	[self userInfoRequestResolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject];
}

/**
 Logout
 */
RCT_REMAP_METHOD(logout,
				 logoutWithResolver:(RCTPromiseResolveBlock)resolve
				 logoutWithRejecter:(RCTPromiseRejectBlock)reject)
{
	[[KOSession sharedSession] logoutAndCloseWithCompletionHandler:^(BOOL success, NSError *error) {
		if (error) {
			reject(@"RNKakao", @"logout error", error);
		} else {
			NSMutableDictionary *response = [NSMutableDictionary dictionary];
			[response setValue:@"Logged out" forKey:@"success"];
			
			resolve(response);
		}
	}];
}

/*!
 Related in user information permission: https://developers.kakao.com
 */
- (void) userInfoRequestResolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject {
	[KOSessionTask userMeTaskWithCompletion:^(NSError *error, KOUserMe *result) {
		if (error) {
			reject(@"RNKakao", @"userInfo error", error);
		} else if (result) {
			NSString *id = result.ID;
			NSString *nickname = result.nickname;
			NSURL *profileImage = result.profileImageURL;
			NSURL *profileImageThumbnail = result.thumbnailImageURL;
			
			// Additional Info (Optional)
			KOUserMeAccount *account = result.account;
			NSString *email = account.email;
			NSString *phoneNumber = account.phoneNumber;
			NSString *displayId = account.displayID;
			
			NSMutableDictionary *userInfo = [NSMutableDictionary dictionary];
			[userInfo setValue:id forKey:@"id"];
			[userInfo setValue:[KOSession sharedSession].token.accessToken forKey:@"accessToken"];
			
			if (nickname) [userInfo setValue:nickname forKey:@"nickname"];
			if (email) [userInfo setValue:email forKey:@"email"];
			if (profileImage) [userInfo setValue:profileImage.absoluteString forKey:@"profileImage"];
			if (profileImageThumbnail) [userInfo setValue:profileImageThumbnail.absoluteString forKey:@"profileImageThumbnail"];
			if (phoneNumber) [userInfo setValue:phoneNumber forKey:@"phoneNumber"];
			if (displayId) [userInfo setValue:displayId forKey:@"displayId"];
			
			resolve(userInfo);
		}
	}];
}
@end
