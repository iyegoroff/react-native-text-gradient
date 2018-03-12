#import "RNShadowRadialTextGradient.h"

//based on this: https://github.com/ViccAlexander/Chameleon/blob/master/Pod/Classes/Objective-C/UIColor%2BChameleon.m

@interface RCTTextShadowView ()

- (void)dirtyLayout;

@end

@implementation RNShadowRadialTextGradient

- (UIColor *)gradientWithFrame:(CGRect)frame
{
  NSMutableArray *cgColors = [[NSMutableArray alloc] init];
  for (UIColor *color in self.colors) {
    [cgColors addObject:(id)[color CGColor]];
  }
  
  CGSize contextSize = CGSizeMake(frame.size.width + frame.origin.x,
                                  frame.size.height + frame.origin.y);
  UIGraphicsBeginImageContextWithOptions(contextSize, NO, [UIScreen mainScreen].scale);
  
  //Default to the RGB Colorspace
  CGColorSpaceRef myColorspace = CGColorSpaceCreateDeviceRGB();
  CFArrayRef arrayRef = (__bridge CFArrayRef)cgColors;
  
  NSUInteger locationsCount = [self.locations count];
  CGFloat locations[locationsCount];
  for (int i = 0; i < locationsCount; ++i) {
    locations[i] = [self.locations objectAtIndex:i].floatValue;
  }
  
  //Create our Fradient
  CGGradientRef myGradient = CGGradientCreateWithColors(myColorspace, arrayRef, locations);
  
  
  // Normalise the 0-1 ranged inputs to the width of the image
  CGPoint myCentrePoint = CGPointMake(self.center.x * frame.size.width + frame.origin.x,
                                      self.center.y * frame.size.height + frame.origin.y);
  float myRadius = MIN(frame.size.width, frame.size.height) * self.radius;

  // Draw our Gradient
  CGContextDrawRadialGradient(UIGraphicsGetCurrentContext(),
                              myGradient,
                              myCentrePoint,
                              0,
                              myCentrePoint,
                              myRadius,
                              kCGGradientDrawsAfterEndLocation);
  
  // Grab it as an Image
  UIImage *backgroundColorImage = UIGraphicsGetImageFromCurrentImageContext();
  
  // Clean up
  CGColorSpaceRelease(myColorspace); // Necessary?
  CGGradientRelease(myGradient); // Necessary?
  UIGraphicsEndImageContext();
  
  return [UIColor colorWithPatternImage:backgroundColorImage];
}

- (NSDictionary *)gradientComparisonKey:(CGRect)frame
{
  return @{@"locations": self.locations ?: @[],
           @"colors": self.colors ?: @[],
           @"radius": [NSNumber numberWithFloat:self.radius],
           @"center": [NSValue valueWithCGPoint:self.center],
           @"frame": [NSValue valueWithCGRect:frame]};
}

- (void)setCenter:(CGPoint)center
{
  _center = center;
//  [self dirtyLayout];
}

- (void)setRadius:(CGFloat)radius
{
  _radius = radius;
//  [self dirtyLayout];
}

@end
