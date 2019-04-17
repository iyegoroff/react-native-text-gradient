### Run debug version
- Go to example folder `cd react-native-text-gradient/TextGradientExample`
- Run `npm i && npm run run:android`

### Build signed release apk with Docker
- Go to TextGradientExample folder `cd react-native-text-gradient/TextGradientExample`
- Generate keystore `npm run generate:android:signing-key`
- Open `TextGradientExample/android/gradle.properties` file and replace `qwerty`s with your passwords
- Run `npm run build:release:docker` - upon script completion apk will be copied to `TextGradientExample/text-gradient.apk` file