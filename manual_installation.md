### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-text-gradient` and add `RNTextGradient.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNTextGradient.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import iyegoroff.RNTextGradient.RNTextGradientPackage;` to the imports at the top of the file
  - Add `new RNTextGradientPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-text-gradient'
  	project(':react-native-text-gradient').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-text-gradient/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-text-gradient')
  	```
