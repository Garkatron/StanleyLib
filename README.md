#### Better than adventure
This mod uses a modified version of Fabric (Babric) and is designed only for Better than Adventure, a heavily modified version of Minecraft b1.7.3! For more information, join the discord server provided on this projects

## StanleyLib updated to 1.2.0-7.2pre2
*A temperature mod and lib*

### What's this mod?
This mod introduces a library of functions aimed at managing player body temperature, enhancing gameplay through realistic environmental challenges and efficient resource management of thermal clothing and heat sources.

### Main features:
* Accessible functions for managing player temperature
* New damage types
* Fully configurable
* Classes and methods to monitor player temperature changes
* And more!

**Gameplay features can be deactivated.**
To deactivate:
### stanleylib.cfg
```cfg
stanley.activate.temperature_management=false
```
**You can use only library features.**

## Import
##### build.gradle
```gradle
repositories {
   maven { url = "https://jitpack.io" }
}
dependencies {
   modImplementation "com.github.Garkatron:StanleyLib:${project.stanleylib_version}"
}
```
##### gradle.properties
```gradle
mod_version = release_tag_name
```
