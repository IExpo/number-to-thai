Java utility for converting numeric amounts into their Thai Baht text representation.

This project provides a single class, `ConvertToThai`, that formats integers, doubles
and strings into Thai text.

## Usage

Add the `ConvertToThai` class to your project or include the built JAR. Then call
one of the `format` methods:

```java
String text = ConvertToThai.format(1234.54);  
// "หนึ่งพันสองร้อยสามสิบสี่บาทห้าสิบสี่สตางค์"
```

Strings with commas and currency symbols are supported as well:

```java
String text = ConvertToThai.format("-1,234.54");
// "ลบหนึ่งพันสองร้อยสามสิบสี่บาทห้าสิบสี่สตางค์"
```

See `ConvertToThaiTest` for more examples of input values and expected output.

## License

This project is provided under the MIT License.