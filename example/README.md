### Run debug version
- Go to example folder `cd react-native-text-gradient/example`
- Run `npm i && npm run run:android`

### Build signed release apk with Docker
- Go to example folder `cd react-native-text-gradient/example`
- Generate keystore `npm run generate:android:signing-key`
- Open `example/android/gradle.properties` file and replace `qwerty`s with your passwords
- Run `npm run build:release:docker` - upon script completion apk will be copied to `example/text-gradient.apk` file