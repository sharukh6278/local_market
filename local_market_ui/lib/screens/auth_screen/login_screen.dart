import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:local_market_ui/common_utils/constants.dart';

import '../../auth/models/login.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _formKey = GlobalKey<FormState>();
  var isLoading = false;
  var email;
  var password;
  final emailController = TextEditingController();
  final passwordController = TextEditingController();
  final flutterSecureStorage = FlutterSecureStorage();

  var apnaShopConstant = ApnaShopConstant.getApnaShopConstant();

  //_LoginWidgetState(): apiClient=ApiClient(Dio());
  Future<void> _submit() async {
    email = emailController.value.text;
    password = passwordController.value.text;

    final isValid = _formKey.currentState?.validate();
    print("Email : $email and password : $password");
    if (!isValid!) {
      return;
    }
    _formKey.currentState?.save();

    print("getting jwt token");
    var jwtToken = await apnaShopConstant
        .getApiClient()
        .login(
          Login(emailController.value.text, passwordController.value.text),
        )
        .then((jwtToken) {
          apnaShopConstant.setJWTToken(
            "${jwtToken.tokenType} ${jwtToken.accessToken}",
          );
          flutterSecureStorage.write(
            key: "jwtToken",
            value: "${jwtToken.tokenType} ${jwtToken.accessToken}",
          );
        });

    flutterSecureStorage.write(key: "email", value: emailController.value.text);
    apnaShopConstant.getApiClient().getUser(emailController.value.text).then((
      userModel,
    ) {
      apnaShopConstant.setUserModel(userModel);
      apnaShopConstant.setContext(context);
      apnaShopConstant.setLoggedInUserEmail(email);
      String user = jsonEncode(userModel);
      print("user info as json : $user");
      flutterSecureStorage.write(key: "user", value: user);
      Navigator.pushNamed(context, "/", arguments: {"email": email});
    });

    // var user= await apiClient.getUser(email);
    // print("user : $user");
    // if(user!=null){
    //   Navigator.of(context).push(MaterialPageRoute(builder: (context) => App()));
    //
    // }
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
              Text(
                "Login to Apna Mall ",
                style: TextStyle(fontSize: 24.0, fontWeight: FontWeight.bold),
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  //styling
                  TextFormField(
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
                  //box styling
                  SizedBox(height: MediaQuery.of(context).size.width * 0.1),
                  //text input
                  TextFormField(
                    decoration: InputDecoration(labelText: 'Password'),
                    keyboardType: TextInputType.emailAddress,
                    onFieldSubmitted: (value) {},
                    obscureText: true,
                    controller: passwordController,
                    validator: (value) {
                      if (value!.isEmpty) {
                        return 'Enter a valid password!';
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: MediaQuery.of(context).size.width * 0.1),
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
                  Container(
                    child: Center(
                      child: Row(
                        children: [
                          Padding(
                            padding: const EdgeInsets.only(left: 10, top: 20),
                            child: Text('Forgot your login details? '),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(left: 1.0, top: 20),
                            child: InkWell(
                              onTap: getHelpInLogin,
                              child: Text(
                                'Get help logging in.',
                                style: TextStyle(
                                  fontSize: 14,
                                  color: Colors.blue,
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
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
