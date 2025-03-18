## File IO
```java
Path path = Paths.get("filename.extension");
BufferedReader r = Files.newBufferedReader(path);
String response = reader.readLine();
while(response != null){
  // do something, e.g. add line to an arraylist
  response = reader.readLine(); // goto next line
}
```

## Exceptions
```java
public void samplemethod() throws EXCEPTION
{
  // method body
}
/**
*
*/
public void samplemethod() throws EXCEPTION
{
  try {
  }
  catch (EXCEPTION err) {
    // exception body
  } // place multiple exceptions below/above
  catch (FileNotFoundException err){
    // exception body
  }
  catch (IOException err){
    // exception body
  }
}
```
