android-pkpass-viewer
=====================

I spent some time during 2012 christmas in training my Android skills. I wrote a simple pkpass file viewer. It only supports event ticket types, but there should be enough code to support easily the other types.

Note that this is only a viewer, there is no support for push notifications or other functionality from pkpass.

The features are:
* It draws the event ticket on the screen
* It checks the integrity of the files using the SHA1 hash of the manifest file.
* It does not check the cryptographic signature

