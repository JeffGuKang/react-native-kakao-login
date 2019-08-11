#!/usr/bin/env bash

# Example: Clone a required repository
# git clone https://github.com/example/SomeProject

# Example: Install App Center CLI
# npm install -g appcenter-cli

# Creating virtual symbolic link for FB SDK for buddybuild
# mkdir ~/Documents  
# ln -s $APPCENTER_SOURCE_DIRECTORY/ios/FacebookSDK ~/Documents/FacebookSDK

echo "APPCENTER_SOURCE_DIRECTORY: " $APPCENTER_SOURCE_DIRECTORY
ls $APPCENTER_SOURCE_DIRECTORY
echo "APPCENTER_OUTPUT_DIRECTORY: " $APPCENTER_OUTPUT_DIRECTORY
# ls $APPCENTER_OUTPUT_DIRECTORY

# echo "Link rn-kakao-login folder"
# ln -s $APPCENTER_OUTPUT_DIRECTORY/rn-kakao-login ../../rn-kakao-login
