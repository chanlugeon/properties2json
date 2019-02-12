# properties2json
properties2json switches Java properties file to JSON file.  

Generated JSON file is in the same directory as the properties file and same name as
it; just the extension name is different.  

**Note:** If a properties key has string value, but also has child properties, an error is occurred.

## Download
[properties2json.jar][jar]

## What's new
*  **12/02/2019:** Launched.

## Usage
```
java -jar target/properties2json.jar <propertiesFilePath...>
```
propertiesFilePath is variable arguments.

### Example
See [example][example].




[jar]: https://raw.githubusercontent.com/chanlugeon/properties2json/master/properties2json.jar
[example]: https://raw.githubusercontent.com/chanlugeon/properties2json/master/example