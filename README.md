# MenuDocs paste client

### Installation

You can install the wrapper with the following dependency managers.

The repo can be downloaded from jcenter.

The docs are available <a href="https://jitpack.io/com/github/MenuDocs/paste-client-java/master-SNAPSHOT/javadoc/" target="_blank">here</a>

The current latest version is: [ ![version][] ][download]

#### Gradle
```gradle
repositories {
    mavenCentral()
    maven {
        name 'duncte123-jfrog'
        url 'https://duncte123.jfrog.io/artifactory/maven'
    }
}
dependencies {
    implementation group: 'org.menudocs', name: 'paste-client-java', version: '[VERSION]'
}
```

#### Maven
```xml
<repository>
    <id>jfrog-duncte123</id>
    <name>jfrog-duncte123</name>
    <url>https://duncte123.jfrog.io/artifactory/maven</url>
</repository>

<dependency>
	<groupId>org.menudocs</groupId>
	<artifactId>paste-client-java</artifactId>
	<version>[VERSION]</version>
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

#### Getting the paste url
```java
String pasteUrl = client.getPasteUrl(pasteID);
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


[version]: https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fduncte123.jfrog.io%2Fartifactory%2Fmaven%2Forg%2Fmenudocs%2Fpaste-client-java%2Fmaven-metadata.xml
[download]: https://duncte123.jfrog.io/ui/packages/gav:%2F%2Fme.duncte123:botCommons
