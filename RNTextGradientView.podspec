require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "RNTextGradientView"
  s.version      = package['version']
  s.summary      = "Text gradient for React-Native"

  s.authors      = { "iyegoroff" => "iegoroff@gmail.com" }
  s.homepage     = "https://github.com/iyegoroff/react-native-text-gradient"
  s.license      = "MIT"
  s.platform     = :ios, "8.0"

  s.source       = { :git => "https://github.com/iyegoroff/react-native-text-gradient.git" }
  s.source_files  = "ios/**/*.{h,m}"

  s.dependency 'React'
end