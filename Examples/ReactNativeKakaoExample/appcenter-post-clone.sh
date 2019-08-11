#!/usr/bin/env bash

# Example: Clone a required repository
# git clone https://github.com/example/SomeProject

# Example: Install App Center CLI
# npm install -g appcenter-cli

# Creating virtual symbolic link for FB SDK for buddybuild
# mkdir ~/Documents  
# ln -s $APPCENTER_SOURCE_DIRECTORY/ios/FacebookSDK ~/Documents/FacebookSDK

#!/usr/bin/env bash

# echo "Uninstalling all CocoaPods versions"
# sudo gem uninstall cocoapods --all --executables

# echo "Install cocoapod 1.7.4"
# sudo gem install cocoapods -v 1.7.4

echo "Pod install"
cd $APPCENTER_SOURCE_DIRECTORY/ios && pod install