# ImagePackager
#### Image Packager : An Android application searching and compressing images

### Motivation  
For storing all images I've taken, I used to compress them and stack onto an external storage like Cloud.
However, there was not the function on my gallery application.
That's why I should have moved the images to my computer, compressed them into ZIP, and then moved to the storage again.
I had to develop an image application with the compressing function for shorter progress.  

### Description  
With this application on Android, you can search images on your device with the file name or the date.
You can see the output images, and compress either all or a part of them into a ZIP file.  

### Usage  
* **Install**  
install the APK and execute the application  
&nbsp;  
<p align="center">
  <img src="/image/1install.png">
</p>  
&nbsp;  

* **Search**  
Search the images with a part of the files' name or the date  
&nbsp;  
<p align="center">
  <img src="/image/2main.png">
</p>  
&nbsp;  

* **Image Grid**  
You can see the images you've searched  
&nbsp;  
<p align="center">
  <img src="/image/3search.png">
</p>  
&nbsp;  

* **Choose pictures**  
You can select the pictures to compress with "holding" them  
&nbsp;  
<p align="center">
  <img src="/image/4chosen.png">
</p>  
&nbsp;  

* **Menu**  
As click the menu button on your phone, you can choose to compress either all pictures or the selected them  
&nbsp;  
<p align="center">
  <img src="/image/5menu.png">
</p>  
&nbsp;  

* **Compression**  
After you chose, in "/Pictures/ImagePackager" the Zip file generated
&nbsp;  
<p align="center">
  <img src="/image/6compression.png">
  <img src="/image/7result.png">
</p>  
&nbsp;  
  
### Notification  
This project is finished when Android Studio appears for the first time and most recently tested on Anroid 4.3(Jelly Bean),
which might malfunctions on your newer device. The following is modified for using up to date Android Studio from the lastest version.  
>androidsdk -> 19  
>build tools -> 28.0.2  
>gradle version -> 2.14.1  
>gradle plugin -> 4.6  
>"compile" -> "api"  
>minSDK ver. removed  
