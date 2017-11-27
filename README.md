# PicBlast
Sample app to demonstrate MVP pattern, Reactive programming, Dagger2 and Retrofit2. This app uses Imgur.com APIs to fetch albums and images and displays it in android app.

# How to use the code

Install JDK8+, Androi studio 3.0+

To use this app you need an account in https://imgur.com/.
- First of all create some albums and upload some photos
- Register an app in https://api.imgur.com/oauth2/addclient
- Make sure you enter a unique callback url here (like: imgurtest_yourname://sample)
- Update the client ID in org.drulabs.picblast.di.NetworkModuel.java (first statement inside class) with CLIENT ID of the app you created in the previous step
- Open manifest file and look for LoginActivity, update the android scheme with the callback URL you selected in step 3 (for this case it will <data android:scheme="imgurtest_yourname"/>)
- If you see some error while building, clean the rebuild the project.

if you still see some error please leave an issue in this repository

API documentation for imgur is here: https://apidocs.imgur.com/



