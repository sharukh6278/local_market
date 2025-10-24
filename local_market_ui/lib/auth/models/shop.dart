import 'package:json_annotation/json_annotation.dart';

part 'shop.g.dart';

@JsonSerializable()
class Shop {
  int? id;
  String? shopName;
  String? ownerEmail;
  String? ownerName;
  double? ownerPhoneNumber;
  double? shopPhoneNumber;
  double? shopAlternatePhoneNumber;
  String? createdDate;
  String? updatedDate;
  String? imageurl;

  Shop(
    this.id,
    this.shopName,
    this.ownerEmail,
    this.ownerName,
    this.ownerPhoneNumber,
    this.shopPhoneNumber,
    this.imageurl,
    this.shopAlternatePhoneNumber,
    this.createdDate,
    this.updatedDate,
  );

  factory Shop.fromJson(Map<String, dynamic> json) => _$ShopFromJson(json);
  Map<String, dynamic> toJson() => _$ShopToJson(this);
}
