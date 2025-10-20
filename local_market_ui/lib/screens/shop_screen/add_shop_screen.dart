import 'dart:io';

import 'package:flutter/material.dart';
import 'package:form_field_validator/form_field_validator.dart';
import 'package:get/get.dart';
import 'package:image_picker/image_picker.dart';
import 'package:scaler/scaler.dart';

import '../../auth/models/shop.dart';
import '../../common_utils/constants.dart';

class AddShopScreen extends StatefulWidget {
  const AddShopScreen({super.key});

  @override
  State<AddShopScreen> createState() => _AddShopScreenState();
}

class _AddShopScreenState extends State<AddShopScreen> {
  Map<String, TextEditingController> formsControllerMap = {
    "shopName": TextEditingController(),
    "ownerEmail": TextEditingController(),
    "ownerName": TextEditingController(),
    "ownerPhoneNumber": TextEditingController(),
    "shopPhoneNumber": TextEditingController(),
    "shopAlternatePhoneNumber": TextEditingController(),
  };

  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  ApnaShopConstant apnaShopConstant = ApnaShopConstant.getApnaShopConstant();
  File pickedImage = File("");
  Future<void> _submit() async {
    if (!_formKey.currentState!.validate()) {
      print("farm is Invalid");
      return;
    }
    final data = formsControllerMap.entries;
    for (MapEntry<String, TextEditingController> item
        in formsControllerMap.entries) {
      print("key : ${item.key}  ");
    }

    print(_formKey.currentState);
    //_formKey.currentState?.save();
    double ownerPhoneNumber = double.parse(
      formsControllerMap['ownerPhoneNumber']!.value.text ?? "0",
    );
    double shopPhoneNumber = double.parse(
      formsControllerMap['shopPhoneNumber']!.value.text ?? "0",
    );
    double shopAlternatePhoneNumber = double.parse(
      formsControllerMap['shopAlternatePhoneNumber']!.value.text ?? "0",
    );

    Shop shop = Shop(
      0,
      formsControllerMap['shopName']?.value.text,
      formsControllerMap['ownerEmail']?.value.text,
      formsControllerMap['ownerName']?.value.text,
      ownerPhoneNumber,
      shopPhoneNumber,
      pickedImage.path,
      shopAlternatePhoneNumber,
      null,
      null,
    );
    apnaShopConstant.getApiClient().addShop(shop).then((response) {
      print("add shop response : $response");
    });
  }

  Future<void> pickImage(ImageSource imageType) async {
    try {
      final photo = await ImagePicker().pickImage(source: imageType);
      if (photo == null) return;
      final tempImage = File(photo.path);
      setState(() {
        pickedImage = tempImage;
      });
      Get.back();
    } catch (error) {
      debugPrint(error.toString());
    }
  }

  @override
  Widget build(BuildContext context) {
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
              width: Scaler.height(1, apnaShopConstant.getContext()),
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    const Text(
                      "Pic Image From",
                      style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                      ),
                      textAlign: TextAlign.center,
                    ),
                    ElevatedButton.icon(
                      onPressed: () {
                        pickImage(ImageSource.camera);
                      },
                      icon: const Icon(Icons.camera, color: Colors.white),
                      label: const Text("CAMERA"),
                    ),
                    ElevatedButton.icon(
                      onPressed: () {
                        pickImage(ImageSource.gallery);
                      },
                      icon: const Icon(Icons.image, color: Colors.white),
                      label: const Text("GALLERY"),
                    ),
                    ElevatedButton.icon(
                      onPressed: () {
                        Get.back();
                      },
                      icon: const Icon(Icons.close, color: Colors.white),
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

    return Scaffold(
      appBar: AppBar(
        title: Text(ApnaShopConstant.appName),
        leading: IconButton(
          icon: Image.asset(
            'assets/images/shops_icon.png',
            height: 50,
            width: 50,
          ),
          onPressed: () {
            print("icon clicked");
          },
        ),
      ),
      //body
      body: Padding(
        padding: const EdgeInsets.all(25.0),
        child: Form(
          key: _formKey,
          child: Container(
            child: Column(
              children: [
                SizedBox(
                  height: Scaler.height(0.2, context),
                  width: Scaler.width(0.25, context),
                  child: Container(
                    child: InkWell(
                      splashColor: Colors.lightBlue,
                      onTap: () {
                        if (pickedImage.path.isEmpty) {
                          imagePickerOption();
                        }
                        print("car is clicked");
                      },
                      child: SizedBox(
                        height: Scaler.height(0.19, context),
                        child: Card(
                          color: Colors.black12,
                          child: Column(
                            children: [
                              Center(
                                child: SizedBox(
                                  height: Scaler.height(0.18, context),
                                  width: Scaler.width(0.25, context),
                                  child: Container(
                                    child: pickedImage.path.isEmpty
                                        ? Icon(Icons.add)
                                        : Image.file(
                                            pickedImage,

                                            fit: BoxFit.cover,
                                          ),
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
                SizedBox(
                  height: Scaler.height(0.5, context),
                  child: SingleChildScrollView(
                    scrollDirection: Axis.vertical,
                    child: Container(
                      child: Column(
                        children: [
                          TextFormField(
                            decoration: InputDecoration(labelText: 'Shop Name'),
                            keyboardType: TextInputType.text,
                            controller: formsControllerMap['shopName'],
                            validator: MultiValidator([
                              RequiredValidator(
                                errorText: "shopName is Required",
                              ),
                            ]).call,
                          ),
                          TextFormField(
                            decoration: InputDecoration(
                              labelText: 'Shop owner Email',
                            ),
                            keyboardType: TextInputType.text,
                            controller: formsControllerMap['ownerEmail'],
                            validator: MultiValidator([
                              RequiredValidator(
                                errorText: "Shop Owner Email is Required",
                              ),
                            ]).call,
                          ),
                          TextFormField(
                            decoration: InputDecoration(
                              labelText: 'Shop owner Name',
                            ),
                            keyboardType: TextInputType.text,
                            controller: formsControllerMap['ownerName'],
                            validator: MultiValidator([
                              RequiredValidator(
                                errorText: "Shop Owner name is Required",
                              ),
                            ]).call,
                          ),
                          TextFormField(
                            decoration: InputDecoration(
                              labelText: 'Owner Phone Number',
                            ),
                            keyboardType: TextInputType.number,
                            controller: formsControllerMap['ownerPhoneNumber'],
                            validator: MultiValidator([
                              RequiredValidator(
                                errorText: "Owner Phone Number is Required",
                              ),
                            ]).call,
                          ),
                          TextFormField(
                            decoration: InputDecoration(
                              labelText: 'Shop Phone Number',
                            ),
                            keyboardType: TextInputType.number,
                            controller: formsControllerMap['shopPhoneNumber'],
                            validator: MultiValidator([
                              RequiredValidator(
                                errorText: "Shop Phone Number is Required",
                              ),
                            ]).call,
                          ),
                          TextFormField(
                            decoration: InputDecoration(
                              labelText: 'Shop Alternate Phone Number',
                            ),
                            keyboardType: TextInputType.number,
                            controller:
                                formsControllerMap['shopAlternatePhoneNumber'],
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
                SizedBox(height: MediaQuery.of(context).size.width * 0.1),
                Container(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      SizedBox(
                        height: 38,
                        width: 100,
                        child: ElevatedButton(
                          style: ElevatedButton.styleFrom(
                            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                          ),
                          child: Text("Submit"),
                          onPressed: () => _submit(),
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
