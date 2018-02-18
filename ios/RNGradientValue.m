#import "RNGradientValue.h"

@implementation RNGradientValue

- (instancetype)initWithRange:(NSRange)range
                    isRawText:(BOOL)isRawText
                colorForFrame:(RNGradientBlock)colorForFrame
{
  if ((self = [super init])) {
    self.range = range;
    self.isRawText = isRawText;
    self.colorForFrame = colorForFrame;
  }
  
  return self;
}

@end
