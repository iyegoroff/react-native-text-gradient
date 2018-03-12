#import "RNTextGradientValue.h"

@implementation RNTextGradientValue

- (instancetype)initWithRange:(NSRange)range
                    isRawText:(BOOL)isRawText
                colorForFrame:(RNTextGradientBlock)colorForFrame
{
  if ((self = [super init])) {
    self.range = range;
    self.isRawText = isRawText;
    self.colorForFrame = colorForFrame;
  }
  
  return self;
}

@end

