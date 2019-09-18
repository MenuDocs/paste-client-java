# MenuDocs paste client

### Installation

You can install the wrapper with the following dependency managers.

The repo can be downloaded from jcenter.

The docs are available <a href="https://jitpack.io/com/github/duncte123/weeb.java/master-SNAPSHOT/javadoc/" target="_blank">here</a>

The current latest version is: [ ![version][] ][download]

#### Gradle
```GRADLE
repositories {
    jcenter()
}
dependencies {
    implementation group: 'org.menudocs', name: 'paste-client-java', version: '[VERSION]'
}
```

#### Maven
```XML
<repository>
    <id>jcenter</id>
    <name>jcenter-bintray</name>
    <url>https://jcenter.bintray.com</url>
</repository>

<dependency>
	<groupId>org.menudocs</groupId>
	<artifactId>paste-client-java</artifactId>
	<version>[VERSION]</version>
	<type>pom</type>
</dependency>
```

### Examples

#### Creating the client
```java
import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;
import org.menudocs.paste.PasteHost;


PasteClient client = new PasteClientBuilder()
                .setUserAgent("Example paste client")
                .setDefaultExpiry("10m")
                .setPasteHost(PasteHost.MENUDOCS) // Optional
                .build();
```

#### Creating a paste
```java
// Sync operation
String pasteID = client.createPaste("html", "<h1>testing</h1>").execute();

// Async operation
client.createPaste("html", "<h1>testing</h1>").async((pasteID) -> {
    // Use pasteID here
});
```

#### Retrieving a paste
```java
// Sync operation
Paste paste = client.getPaste(pasteID).execute();
System.out.println(paste.getPasteUrl());
System.out.println(paste.getBody());

// Async operation
client.getPaste(pasteID).async((paste) -> {
    System.out.println(paste.getPasteUrl());
    System.out.println(paste.getBody());
});
```


[version]: https://api.bintray.com/packages/duncte123/maven/paste-client-java/images/download.svg
[download]: https://bintray.com/duncte123/maven/paste-client-java/_latestVersion
