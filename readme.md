# Purpose
This is a skeleton project that I wish I had a few days ago when trying to get a new Firebase project for Android up and running. Dispite the [Firebase documentation](https://firebase.google.com/docs/) and [Firebase's quickstart Android repository](https://github.com/firebase/quickstart-android) being first rate, it still takes time to sift through everything and parse out just what you need. This is for those who just want to get the Firebase Authentication (Google Sign In, Email and Password) and Real Time Database fragments operational asap. 

# About
The project is name Alternate Reality Games because that's the name of the new app I'm developing atm. So, because of ARGs requirements, the project also contains a skeleton implementation of a Google Maps fragment, Locations Update fragment, and Settings Activity. It also provides a relatively simple example of how to implement a navigation drawer that switches the main layout between activities and fragments.
Hope it saves you some time, happy coding :)

# Building TODOs
Follow [Firebase Android guides](https://github.com/firebase/quickstart-android) to correctly configure your new Android project:
- [Setup your online Firebase project](https://firebase.google.com/docs/android/setup)
  - For Google Sign In, this includes [adding keystore client authorizations](https://developers.google.com/android/guides/client-auth) to your firebase project
- Add your project's api keys to the source code
  - [Download your project's google-services.json file](https://support.google.com/firebase/answer/7015592) and use it to overwrite the existing dummy file app/google-services.json
  - replace firebase api key in app/src/main/res/values/string.xml
  - For Google Maps, this includes replacing the google maps api keys at app/src/debug/res/values/google-maps-api.xml and app/src/release/res/values/google-maps-api.xml
  
# Screenshot
![firebase hello world screenshot](/firebase-hello-world-screenshot.png?raw=true "Firebase hello world android app")
