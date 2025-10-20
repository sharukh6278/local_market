import 'package:flutter/material.dart';
import 'package:local_market_ui/screens/auth_screen/new_password_screen.dart';

import '../../common_utils/constants.dart';

class ResetPasswordScreen extends StatefulWidget {
  const ResetPasswordScreen({super.key});

  @override
  _ResetPasswordScreenState createState() => _ResetPasswordScreenState();
}

class _ResetPasswordScreenState extends State<ResetPasswordScreen> {
  final _formKey = GlobalKey<FormState>();
  var isLoading = false;
  var email;
  var password;
  final emailController = TextEditingController();
  final otpController = TextEditingController();
  final newPasswordController = TextEditingController();
  final confirmPasswordController = TextEditingController();
  bool showOTPWidget = false;
  bool showEmailWidget = true;
  bool showNewPasswordWidget = false;

  void _submit() {
    final isValid = _formKey.currentState?.validate();
    print(
      "Email : ${emailController.value.text} password : ${otpController.value.text}",
    );
    if (!isValid!) {
      return;
    }
    _formKey.currentState?.save();
  }

  void sendOtp() {
    setState(() {
      showOTPWidget = true;
      showEmailWidget = false;
    });

    print("$showEmailWidget and $showOTPWidget");
  }

  void submitOtp() {
    setState(() {
      showOTPWidget = false;
      showEmailWidget = false;
      showNewPasswordWidget = true;
    });

    print("$showEmailWidget and $showOTPWidget");
  }

  void getHelpInLogin() {
    print("reseting password....");
    Navigator.pushNamed(context, '/reset_password');
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

        //form
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              Padding(
                padding: const EdgeInsets.all(25.0),
                child: Image.asset(
                  'assets/images/shops.png',
                  height: 150,
                  width: 150,
                ),
              ),
              Visibility(
                visible: !showNewPasswordWidget,
                child: Container(
                  child: Center(
                    child: Text(
                      "Reset Password",
                      style: TextStyle(
                        fontSize: 24.0,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ),
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  //styling
                  Visibility(
                    visible: showEmailWidget,
                    child: Container(
                      child: Center(
                        child: TextFormField(
                          decoration: InputDecoration(labelText: 'E-Mail'),
                          keyboardType: TextInputType.emailAddress,
                          onFieldSubmitted: (value) {
                            email = value;
                          },
                          controller: emailController,
                          validator: (value) {
                            if (value!.isEmpty ||
                                !RegExp(
                                  r"^[a-zA-Z0-9.a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\.[a-zA-Z]+",
                                ).hasMatch(value)) {
                              return 'Enter a valid email!';
                            }
                            return null;
                          },
                        ),
                      ),
                    ),
                  ),
                  //box styling
                  if (showEmailWidget)
                    SizedBox(height: MediaQuery.of(context).size.width * 0.1),
                  //text input
                  Visibility(
                    visible: showEmailWidget,
                    child: Container(
                      width: 150,
                      height: 38,
                      color: Colors.blue,
                      child: Center(
                        child: SizedBox(
                          height: 38,
                          width: 150,
                          child: ElevatedButton(
                            style: ElevatedButton.styleFrom(
                              textStyle: const TextStyle(color: Colors.white),
                              backgroundColor: Colors.blue,
                              padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                            ),
                            child: Text(
                              "Send otp",
                              style: TextStyle(
                                fontSize: 18.0,
                                color: Colors.white,
                              ),
                            ),
                            onPressed: () => sendOtp(),
                          ),
                        ),
                      ),
                    ),
                  ),

                  Visibility(
                    visible: showOTPWidget,
                    child: Container(
                      child: Center(
                        child: TextFormField(
                          decoration: InputDecoration(labelText: 'Enter otp'),
                          keyboardType: TextInputType.text,
                          onFieldSubmitted: (value) {},
                          obscureText: true,
                          controller: otpController,
                          validator: (value) {
                            if (value!.isEmpty) {
                              return 'Enter a valid otp';
                            }
                            return null;
                          },
                        ),
                      ),
                    ),
                  ),

                  if (showOTPWidget)
                    SizedBox(height: MediaQuery.of(context).size.width * 0.1),
                  Visibility(
                    visible: showOTPWidget,
                    child: Container(
                      width: 150,
                      height: 38,
                      color: Colors.blue,
                      child: Center(
                        child: SizedBox(
                          height: 38,
                          width: 150,
                          child: ElevatedButton(
                            style: ElevatedButton.styleFrom(
                              textStyle: const TextStyle(color: Colors.white),
                              backgroundColor: Colors.blue,
                              padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                            ),
                            child: Text(
                              "Submit otp",
                              style: TextStyle(
                                fontSize: 18.0,
                                color: Colors.white,
                              ),
                            ),
                            onPressed: () => submitOtp(),
                          ),
                        ),
                      ),
                    ),
                  ),
                  Visibility(
                    visible: showNewPasswordWidget,
                    child: Container(child: Center(child: NewPasswordWidget())),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
