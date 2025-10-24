import 'dart:io';

import 'package:dio/src/multipart_file.dart' as dio;
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:image/image.dart' as img;
import 'package:image_picker/image_picker.dart';
import 'package:local_market_ui/common_utils/constants.dart';
import 'package:scaler/scaler.dart';

class CommonMethods {
  static CommonMethods commonMethods = CommonMethods();

  ApnaShopConstant apnaShopConstant = ApnaShopConstant.getApnaShopConstant();

  static void getCommonMethods() {
    commonMethods;
  }

  void imagePickerOption() {
    Get.bottomSheet(
      SingleChildScrollView(
        child: ClipRRect(
          borderRadius: const BorderRadius.only(
            topLeft: Radius.circular(10.0),
            topRight: Radius.circular(10.0),
          ),
          child: Container(
            color: Colors.white,
            height: Scaler.height(0.35, apnaShopConstant.getContext()),
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  const Text(
                    "Pic Image From",
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                    textAlign: TextAlign.center,
                  ),
                  ElevatedButton.icon(
                    onPressed: () {
                      pickImage(ImageSource.camera);
                    },
                    icon: const Icon(Icons.camera),
                    label: const Text("CAMERA"),
                  ),
                  ElevatedButton.icon(
                    onPressed: () {
                      pickImage(ImageSource.gallery);
                    },
                    icon: const Icon(Icons.image),
                    label: const Text("GALLERY"),
                  ),
                  ElevatedButton.icon(
                    onPressed: () {
                      Get.back();
                    },
                    icon: const Icon(Icons.close),
                    label: const Text("CANCEL"),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  Future<File?> pickImage(ImageSource imageType) async {
    try {
      final photo = await ImagePicker().pickImage(source: imageType);
      //if (photo == null) return null;
      Get.back();
      return File(photo!.path);
      //
      // setState(() {
      //   pickedImage = tempImage;
      //   productImageList.insert(productImageList.length - 1, tempImage);
      // });
    } catch (error) {
      debugPrint(error.toString());
    }
  }

  File compressAndResizeImage(File file) {
    img.Image? image = img.decodeImage(file.readAsBytesSync());

    // Resize the image to have the longer side be 800 pixels
    int width;
    int height;

    if (image!.width > image.height) {
      width = 800;
      height = (image.height / image.width * 800).round();
    } else {
      height = 800;
      width = (image.width / image.height * 800).round();
    }

    img.Image resizedImage = img.copyResize(
      image,
      width: width,
      height: height,
    );

    // Compress the image with JPEG format
    List<int> compressedBytes = img.encodeJpg(
      resizedImage,
      quality: 85,
    ); // Adjust quality as needed

    // Save the compressed image to a file
    File compressedFile = File(
      file.path.replaceFirst('.jpg', '_compressed.jpg'),
    );
    compressedFile.writeAsBytesSync(compressedBytes);

    return compressedFile;
  }

  Future<List<dio.MultipartFile>> toMultiPartFiles(
    List<File> compressedFileList,
  ) async {
    final multipartFiles = <dio.MultipartFile>[];

    for (final file in compressedFileList) {
      if (file.path.isNotEmpty) {
        final fileBytes = await file.readAsBytes();
        final multipartFile = dio.MultipartFile.fromBytes(
          fileBytes,
          filename: file.path.split('/').last,
          contentType: dio.DioMediaType('application', 'octet-stream'),
        );
        multipartFiles.add(multipartFile);
      }
    }
    return multipartFiles;
  }
}
