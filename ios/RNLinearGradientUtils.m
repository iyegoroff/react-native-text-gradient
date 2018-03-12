#import "RNLinearGradientUtils.h"

// based on this: https://github.com/ViccAlexander/Chameleon/blob/master/Pod/Classes/Objective-C/UIColor%2BChameleon.m

@implementation RNLinearGradientUtils

+ (UIColor *)gradientWithFrame:(CGRect)frame
                    shadowView:(RCTBaseTextShadowView <RNLinearTextGradientShadowViewDelegate> *)shadowView
{
  NSMutableArray *cgColors = [[NSMutableArray alloc] init];
  for (UIColor *color in [shadowView colors]) {
    [cgColors addObject:(id)[color CGColor]];
  }
  
  CGSize contextSize = CGSizeMake(frame.size.width + frame.origin.x,
                                  frame.size.height + frame.origin.y);
  UIGraphicsBeginImageContextWithOptions(contextSize, NO, [UIScreen mainScreen].scale);
  
  //Default to the RGB Colorspace
  CGColorSpaceRef myColorspace = CGColorSpaceCreateDeviceRGB();
  CFArrayRef arrayRef = (__bridge CFArrayRef)cgColors;
  
  NSUInteger locationsCount = [[shadowView locations] count];
  CGFloat locations[locationsCount];
  for (int i = 0; i < locationsCount; ++i) {
    locations[i] = [[shadowView locations] objectAtIndex:i].floatValue;
  }
  
  //Create our Fradient
  CGGradientRef myGradient = CGGradientCreateWithColors(myColorspace, arrayRef, locations);
  
  CGPoint start = CGPointMake([shadowView gradientStart].x * frame.size.width + frame.origin.x,
                              [shadowView gradientStart].y * frame.size.height + frame.origin.y);
  CGPoint end = CGPointMake([shadowView gradientEnd].x * frame.size.width + frame.origin.x,
                            [shadowView gradientEnd].y * frame.size.height + frame.origin.y);
  
  // Draw our Gradient
  CGContextDrawLinearGradient(UIGraphicsGetCurrentContext(),
                              myGradient,
                              start,
                              end,
                              kCGGradientDrawsAfterEndLocation);
  // Grab it as an Image
  UIImage *backgroundColorImage = UIGraphicsGetImageFromCurrentImageContext();
  
  // Clean up
  CGColorSpaceRelease(myColorspace); // Necessary?
  CGGradientRelease(myGradient); // Necessary?
  UIGraphicsEndImageContext();
  return [UIColor colorWithPatternImage:backgroundColorImage];
}

+ (NSDictionary *)gradientComparisonKey:(CGRect)frame
                             shadowView:(RCTBaseTextShadowView <RNLinearTextGradientShadowViewDelegate> *)shadowView
{
  return @{@"locations": [shadowView locations] ?: @[],
           @"colors": [shadowView colors] ?: @[],
           @"start": [NSValue valueWithCGPoint:[shadowView gradientStart]],
           @"end": [NSValue valueWithCGPoint:[shadowView gradientEnd]],
           @"frame": [NSValue valueWithCGRect:frame]};
}

@end
