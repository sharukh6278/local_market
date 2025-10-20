import 'package:flutter/material.dart';

class NewPasswordWidget extends StatefulWidget {
  const NewPasswordWidget({super.key});

  @override
  _NewPasswordWidgetState createState() => _NewPasswordWidgetState();
}

class _NewPasswordWidgetState extends State<NewPasswordWidget> {
  final _formKey = GlobalKey<FormState>();
  var isLoading = false;
  var email;
  var password;
  final emailController = TextEditingController();
  final passwordController = TextEditingController();

  void _submit() {
    final isValid = _formKey.currentState?.validate();
    print("Email : ${emailController.value.text} password : ${passwordController.value.text}");
    if (!isValid!) {
      return;
    }
    _formKey.currentState?.save();
  }

  void getHelpInLogin() {
    print("reseting password....");
    Navigator.pushNamed(context, '/reset_password');
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Form(
        key: _formKey,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[
            Text(
              "New Password",
              style: TextStyle(fontSize: 24.0, fontWeight: FontWeight.bold),
            ),
            Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                //text input
                TextFormField(
                  decoration: InputDecoration(labelText: 'New Password'),
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
                SizedBox(
                  height: MediaQuery.of(context).size.width * 0.1,
                ),
                TextFormField(
                  decoration: InputDecoration(labelText: 'Confirm Password'),
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
                SizedBox(
                  height: MediaQuery.of(context).size.width * 0.1,
                ),
                SizedBox(
                  height: 38,
                  width: 200,
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                        textStyle: const TextStyle(color: Colors.white),
                        backgroundColor: Colors.blue,
                        padding: EdgeInsets.fromLTRB(20, 0, 20, 0)),
                    child: Text(
                      "Change Password",
                      style: TextStyle(fontSize: 18.0, color: Colors.white),
                    ),
                    onPressed: () => _submit(),
                  ),
                )
              ],
            ),
          ],
        ),
      ),
    );
  }
}
