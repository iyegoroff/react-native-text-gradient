#import "RNShadowLinearTextGradient.h"

//based on this: https://github.com/ViccAlexander/Chameleon/blob/master/Pod/Classes/Objective-C/UIColor%2BChameleon.m

@implementation RNShadowLinearTextGradient

- (UIColor *)gradientWithFrame:(CGRect)frame
{
  @try {
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
    
    CGPoint start = CGPointMake(self.gradientStart.x * frame.size.width + frame.origin.x,
                                self.gradientStart.y * frame.size.height + frame.origin.y);
    CGPoint end = CGPointMake(self.gradientEnd.x * frame.size.width + frame.origin.x,
                              self.gradientEnd.y * frame.size.height + frame.origin.y);
    
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
  } @catch (NSException *exception) {
    NSLog(@"gradientWithFrame %@", exception.reason);
  }
}

- (NSDictionary *)gradientComparisonKey:(CGRect)frame
{
  return @{@"locations": self.locations ?: @[],
           @"colors": self.colors ?: @[],
           @"start": [NSValue valueWithCGPoint:self.gradientStart],
           @"end": [NSValue valueWithCGPoint:self.gradientEnd],
           @"frame": [NSValue valueWithCGRect:frame]};
}

- (void)setGradientStart:(CGPoint)start
{
  _gradientStart = start;
  [self dirtyText];
}

- (void)setGradientEnd:(CGPoint)end
{
  _gradientEnd = end;
  [self dirtyText];
}

@end
