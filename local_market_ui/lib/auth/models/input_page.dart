import 'package:json_annotation/json_annotation.dart';

part 'input_page.g.dart';

@JsonSerializable()
class InputPage {
  int? pageNumber;
  int? pageSize;
  String? sortDirection;
  String? sortColumn;

  InputPage(
    this.pageNumber,
    this.pageSize,
    this.sortDirection,
    this.sortColumn,
  );

  factory InputPage.fromJson(Map<String, dynamic> json) =>
      _$InputPageFromJson(json);
  Map<String, dynamic> toJson() => _$InputPageToJson(this);
}
