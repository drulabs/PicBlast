# PicBlast
Sample app to demonstrate MVP pattern, Reactive programming, Dagger2 and Retrofit2. This app uses Imgur.com APIs to fetch albums and images and displays it in android app.

# How to use the code

Install JDK8+, Androi studio 3.0+

To use this app you need an account in https://imgur.com/.
- First of all create some albums and upload some photos
- Register an app in https://api.imgur.com/oauth2/addclient
- Make sure you enter a unique callback url here (like: imgurtest_yourname://sample)
- Update the client ID in **org.drulabs.picblast.di.NetworkModuel.java** (first statement inside class) with CLIENT ID of the app you created in the previous step
- Open manifest file and look for **LoginActivity**, update the android scheme with the callback URL you selected in step 3 (for this case it will be < data android:scheme="imgurtest_yourname"/>)
- If you see some error while building, clean the rebuild the project. This project depends on libraries like dagger2, objectbox etc, that perform some auto code generation. So you may have to rebuild the project everytime you launch the project. 

if you still see some error please leave an issue in this repository

API documentation for imgur is here: https://apidocs.imgur.com/

### A recording of the session: https://youtu.be/PSwsBvxyY7I
### Presentation: https://speakerdeck.com/drulabs/reactive-programming-in-android
### RxJava2 Experiments: RxJava2 Experiments: https://github.com/drulabs/RxJava2DemoAndExperiments.git


