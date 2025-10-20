import 'package:dropdown_textfield/dropdown_textfield.dart';
import 'package:flutter/material.dart';
import 'package:form_field_validator/form_field_validator.dart';
import 'package:local_market_ui/auth/models/role.dart';
import 'package:local_market_ui/auth/models/shop.dart';
import 'package:local_market_ui/common_utils/constants.dart';
import 'package:scaler/scaler.dart';

import '../../../auth/models/user_model.dart';

class AddUserScreen extends StatefulWidget {
  const AddUserScreen({super.key});

  @override
  State<AddUserScreen> createState() => _AddUserScreenState();
}

class _AddUserScreenState extends State<AddUserScreen> {
  Map<String, TextEditingController> formsControllerMap = {
    "name": TextEditingController(),
    "email": TextEditingController(),
    "phone": TextEditingController(),
  };

  var isUserBlocked = false;
  var selectedUserRole;

  late UserModel userModel;
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

    print(_formKey.currentState);
    //_formKey.currentState?.save();
    UserModel? userModelOld = apnaShopConstant.getUserModel();
    print(
      "formsControllerMap['phone']?.value? :  ${formsControllerMap['phone']?.value.text}",
    );
    // int phone=int.parse(formsControllerMap['phone']!.value!.text);
    // print("phone : $phone");
    UserModel userModel = UserModel(
      formsControllerMap['email']?.value.text,
      null,
      userModelOld?.id ?? 0,
      formsControllerMap['name']?.value.text,
      false,
      isUserBlocked,
      int.parse(formsControllerMap['phone']!.value.text),
      "REGISTRATION_APPROVED",
      null,
      null,
      null,
      [Role(selectedUserRole, selectedUserRole)],
      false,
    );

    print("user to be regeter  $userModel");
    apnaShopConstant.getApiClient().register(userModel, shop.shopName!).then((
      value,
    ) {
      print("add product is done : $value");
    });
  }

  late List<String> roleList;
  late Shop shop;
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
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
    shop = Shop(
      1,
      "Maharaja",
      "farooq@gmail.com",
      "Farooq",
      123467955,
      123456855,
      "imageurl",
      null,
      null,
      null,
    );
    roleList = [
      "${shop.id}_OWNER",
      "${shop.id}_MANAGER",
      "${shop.id}_HELPER",
    ];
  }

  @override
  void dispose() {
    _cnt.dispose();
    _cntMulti.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
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
                ClipOval(
                  child: SizedBox.fromSize(
                    size: Size.fromRadius(50), // Image radius
                    child: Image.asset(
                      "assets/images/network.jpeg",
                      fit: BoxFit.cover,
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
                            decoration: InputDecoration(labelText: 'Name'),
                            keyboardType: TextInputType.text,
                            controller: formsControllerMap['name'],
                            validator: MultiValidator([
                              RequiredValidator(errorText: "Name is Required"),
                            ]).call,
                          ),
                          TextFormField(
                            decoration: InputDecoration(labelText: 'Email'),
                            keyboardType: TextInputType.text,
                            controller: formsControllerMap['email'],
                            validator: MultiValidator([
                              RequiredValidator(errorText: "Email is Required"),
                            ]).call,
                          ),
                          TextFormField(
                            decoration: InputDecoration(labelText: 'phone'),
                            keyboardType: TextInputType.number,
                            controller: formsControllerMap['phone'],
                            validator: MultiValidator([
                              RequiredValidator(errorText: "Phone is Required"),
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
                                  flex: 1,
                                  child: Container(
                                    child: DropdownButtonFormField<String>(
                                      decoration: InputDecoration(
                                        labelText: 'User Role',
                                      ),
                                      initialValue: selectedUserRole,
                                      focusColor: Colors.white,
                                      onChanged: (newValue) {
                                        setState(() {
                                          selectedUserRole = newValue!;
                                        });
                                      },
                                      validator: (value) {
                                        if (value == null) {
                                          return 'User Role is Required';
                                        }
                                        return null;
                                      },
                                      items: [
                                        for (var role in roleList)
                                          DropdownMenuItem<String>(
                                            value: role,
                                            child: Text(role.split("_")[1]),
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
                                        labelText: 'Is User Blocked',
                                      ),
                                      initialValue: isUserBlocked,
                                      focusColor: Colors.white,
                                      onChanged: (newValue) {
                                        setState(() {
                                          isUserBlocked = newValue!;
                                        });
                                      },
                                      validator: (value) {
                                        if (value == null) {
                                          return 'User Blocked State is Required';
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
