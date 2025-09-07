Native JNI libraries used by usb4java for the AArch64 architecture
===

This repository contains compiled versions of [libusb4java](https://github.com/usb4java/libusb4java) for AArch64 
missing in the usb4java repository.

The libusb4java source code is compiled _as is_ without modifications using commit [710bdb3](https://github.com/usb4java/libusb4java/commit/710bdb3466a68f92ab247ee7e2bf486b1d3840e7).
Available build configurations are _release builds_, dynamically or statically linked with _libusb 1.0.29_ using

* Windows 11, VS 17 2022, MSVC 19.44.35215.0
* macOS Sequoia 15.6.1, clang 17.0.0
