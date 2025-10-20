import 'dart:convert';
import 'dart:io';

import 'package:dio/src/multipart_file.dart' as dio;
import 'package:dropdown_textfield/dropdown_textfield.dart';
import 'package:flutter/material.dart';
import 'package:form_field_validator/form_field_validator.dart';
import 'package:get/get.dart';
import 'package:image/image.dart' as img;
import 'package:local_market_ui/common_utils/constants.dart';
import 'package:scaler/scaler.dart';

import '../product_models/product.dart';
import 'add_product_screen.dart';

class ProductDetailScreen extends StatefulWidget {
  const ProductDetailScreen({super.key});

  @override
  State<ProductDetailScreen> createState() => _ProductDetailScreenState();
}

class _ProductDetailScreenState extends State<ProductDetailScreen> {
  Map<String, TextEditingController> formsControllerMap = {
    "title": TextEditingController(),
    "prize": TextEditingController(),
    "discountPercentage": TextEditingController(),
    "material": TextEditingController(),
    "description": TextEditingController(),
    "age": TextEditingController(),
  };

  var productCategory;
  var productForCustomer;
  var productAvailablitiy;
  var isSpecialProduct;
  var keyController = TextEditingController();
  var valueController = TextEditingController();
  List<AdditionalAttribute> additionalAttributeList = [];
  late Product product;
  ApnaShopConstant apnaShopConstant = ApnaShopConstant.getApnaShopConstant();

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

    product = Product(
      0,
      formsControllerMap['title']?.value.text,
      formsControllerMap['description']?.value.text,
      int.parse(formsControllerMap['prize']!.value.text),
      double.parse(formsControllerMap['discountPercentage']!.value.text),
      productCategory,
      productForCustomer,
      null,
      null,
      null,
      null,
      additionalAttributeList,
      null,
    );

    print(product);

    print(_formKey.currentState);
    //_formKey.currentState?.save();
    List<File> compressedFileList = [];
    for (File file in productImageList) {
      compressedFileList.add(compressAndResizeImage(file));
    }
    final multipartFiles = await toMultiPartFiles(compressedFileList);
    print(multipartFiles);
    apnaShopConstant
        .getProductApiClient()
        .addProductV2(multipartFiles, jsonEncode(product), "srk@gmail.com")
        .then((value) {
          print("add product is done : $value");
        });
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

  Future<List<dio.MultipartFile>> toMultiPartFiles(List<File> compressedFileList) async {
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

  void addAtribute() {
    if (!_formKeyAtr.currentState!.validate()) {
      print("farm is Invalid");
      return;
    }

    AdditionalAttribute additionalAttributes = AdditionalAttribute(
      0,
      keyController.value.text,
      valueController.value.text,
      null,
    );
    additionalAttributeList.add(additionalAttributes);

    print(productCategory);
  }

  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  final GlobalKey<FormState> _formKeyAtr = GlobalKey<FormState>();
  FocusNode searchFocusNode = FocusNode();
  FocusNode textFieldFocusNode = FocusNode();
  late SingleValueDropDownController _cnt;
  late MultiValueDropDownController _cntMulti;
  String initalValue = "abc";

  @override
  void initState() {
    _cnt = SingleValueDropDownController();
    _cntMulti = MultiValueDropDownController();
    super.initState();
  }

  @override
  void dispose() {
    _cnt.dispose();
    _cntMulti.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    void imagePickerOption() {
      Get.bottomSheet(
        SingleChildScrollView(
          child: Form(
            key: _formKeyAtr,
            child: ClipRRect(
              borderRadius: const BorderRadius.only(
                topLeft: Radius.circular(10.0),
                topRight: Radius.circular(10.0),
              ),
              child: Container(
                color: Colors.white,
                height: Scaler.height(0.35, context),
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
                      TextFormField(
                        decoration: InputDecoration(labelText: 'Add Key'),
                        keyboardType: TextInputType.text,
                        controller: keyController,
                        validator: (value) {
                          if (value!.isEmpty) {
                            return 'Enter a valid title!';
                          }
                          return null;
                        },
                      ),
                      TextFormField(
                        decoration: InputDecoration(labelText: 'add Value'),
                        keyboardType: TextInputType.text,
                        controller: valueController,
                        validator: MultiValidator([
                          RequiredValidator(
                            errorText: "Product Title is Required",
                          ),
                        ]).call,
                      ),
                      SizedBox(
                        height: MediaQuery.of(context).size.width * 0.05,
                      ),
                      SizedBox(
                        height: 38,
                        width: 150,
                        child: ElevatedButton(
                          style: ElevatedButton.styleFrom(
                            textStyle: const TextStyle(color: Colors.white),
                            backgroundColor: Colors.blue,
                            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                          ),
                          child: Text(
                            "Add Details",
                            style: TextStyle(
                              fontSize: 18.0,
                              color: Colors.white,
                            ),
                          ),
                          onPressed: () {
                            addAtribute();
                          },
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ),
      );
    }

    return Padding(
      padding: const EdgeInsets.all(25.0),
      child: Form(
        key: _formKey,
        child: Container(
          child: Column(
            children: [
              SizedBox(
                height: Scaler.height(0.5, context),
                child: SingleChildScrollView(
                  scrollDirection: Axis.vertical,
                  child: Container(
                    child: Column(
                      children: [
                        TextFormField(
                          decoration: InputDecoration(labelText: 'Title'),
                          keyboardType: TextInputType.text,
                          controller: formsControllerMap['title'],
                          validator: MultiValidator([
                            RequiredValidator(
                              errorText: "Product Title is Required",
                            ),
                          ]).call,
                        ),
                        TextFormField(
                          decoration: InputDecoration(labelText: 'Description'),
                          keyboardType: TextInputType.text,
                          controller: formsControllerMap['description'],
                          validator: MultiValidator([
                            RequiredValidator(
                              errorText: "Product Description is Required",
                            ),
                          ]).call,
                        ),
                        TextFormField(
                          decoration: InputDecoration(labelText: 'prize'),
                          keyboardType: TextInputType.number,
                          controller: formsControllerMap['prize'],
                          validator: MultiValidator([
                            RequiredValidator(
                              errorText: "Product Prize is Required",
                            ),
                          ]).call,
                        ),
                        TextFormField(
                          decoration: InputDecoration(
                            labelText: 'discountPercentage',
                          ),
                          keyboardType: TextInputType.number,
                          controller: formsControllerMap['discountPercentage'],
                          validator: MultiValidator([
                            RequiredValidator(
                              errorText: "Discount Percentage is Required",
                            ),
                          ]).call,
                        ),
                        TextFormField(
                          decoration: InputDecoration(labelText: 'Material'),
                          keyboardType: TextInputType.text,
                          controller: formsControllerMap['material'],
                          validator: MultiValidator([
                            RequiredValidator(
                              errorText: "Product Material is Required",
                            ),
                          ]).call,
                        ),
                        TextFormField(
                          decoration: InputDecoration(labelText: 'Age'),
                          keyboardType: TextInputType.number,
                          controller: formsControllerMap['age'],
                          validator: MultiValidator([
                            RequiredValidator(
                              errorText: "Customer Age is Required",
                            ),
                          ]).call,
                        ),
                        SizedBox(
                          height: MediaQuery.of(context).size.width * 0.05,
                        ),
                        Container(
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Flexible(
                                flex: 2,
                                child: Container(
                                  child: DropdownButtonFormField<String>(
                                    decoration: InputDecoration(
                                      labelText: 'Product Category',
                                    ),
                                    initialValue: productCategory,
                                    focusColor: Colors.white,
                                    onChanged: (newValue) {
                                      setState(() {
                                        productCategory = newValue!;
                                      });
                                    },
                                    validator: (value) {
                                      if (value == null || value.isEmpty) {
                                        return 'Product Category is Required';
                                      }
                                      return null;
                                    },
                                    items: [
                                      for (String item in productCategoryList)
                                        DropdownMenuItem<String>(
                                          value: item,
                                          child: Text(item),
                                        ),
                                    ],
                                  ),
                                ),
                              ),
                              Flexible(
                                flex: 2,
                                child: Container(
                                  child: DropdownButtonFormField<String>(
                                    decoration: InputDecoration(
                                      labelText: 'Customer Gender',
                                    ),
                                    initialValue: productForCustomer,
                                    focusColor: Colors.white,
                                    onChanged: (newValue) {
                                      setState(() {
                                        productForCustomer = newValue!;
                                      });
                                    },
                                    validator: (value) {
                                      if (value == null || value.isEmpty) {
                                        return 'Customer Gender is Required';
                                      }
                                      return null;
                                    },
                                    items: [
                                      for (String item
                                          in productForCustomerList)
                                        DropdownMenuItem<String>(
                                          value: item,
                                          child: Text(item),
                                        ),
                                    ],
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                        SizedBox(
                          height: MediaQuery.of(context).size.width * 0.05,
                        ),

                        Container(
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Flexible(
                                flex: 1,
                                child: Container(
                                  child: DropdownButtonFormField<bool>(
                                    decoration: InputDecoration(
                                      labelText: 'Product Available',
                                    ),
                                    initialValue: productAvailablitiy,
                                    focusColor: Colors.white,
                                    onChanged: (newValue) {
                                      setState(() {
                                        productAvailablitiy = newValue!;
                                      });
                                    },
                                    validator: (value) {
                                      if (value == null) {
                                        return 'Product Availablity is Required';
                                      }
                                      return null;
                                    },
                                    items: [
                                      DropdownMenuItem<bool>(
                                        value: true,
                                        child: Text("Yes"),
                                      ),
                                      DropdownMenuItem<bool>(
                                        value: false,
                                        child: Text("No"),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                              Flexible(
                                flex: 1,
                                child: Container(
                                  child: DropdownButtonFormField<bool>(
                                    decoration: InputDecoration(
                                      labelText: 'Special Product',
                                    ),
                                    initialValue: isSpecialProduct,
                                    focusColor: Colors.white,
                                    onChanged: (newValue) {
                                      setState(() {
                                        isSpecialProduct = newValue!;
                                      });
                                    },
                                    validator: (value) {
                                      if (value == null) {
                                        return 'Special Product field is Required';
                                      }
                                      return null;
                                    },
                                    items: [
                                      DropdownMenuItem<bool>(
                                        value: true,
                                        child: Text("Yes"),
                                      ),
                                      DropdownMenuItem<bool>(
                                        value: false,
                                        child: Text("No"),
                                      ),
                                    ],
                                  ),
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
              SizedBox(height: MediaQuery.of(context).size.width * 0.05),
              Container(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    SizedBox(
                      height: 38,
                      width: 130,
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                        ),
                        child: Text("Add More"),
                        onPressed: () {
                          imagePickerOption();
                        },
                      ),
                    ),
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
    );
  }
}
