#import "RNTextGradient.h"

@implementation RNTextGradient

- (instancetype)initWithFrame:(CGRect)frame
{
  if ((self = [super initWithFrame:frame])) {
    self.colors = [NSMutableArray new];
  }
  return self;
}

- (void)drawRect:(CGRect)rect {
  @try {
    NSLayoutManager *layoutManager = [self.textStorage.layoutManagers firstObject];
    NSTextContainer *textContainer = [layoutManager.textContainers firstObject];
    
    for (RNGradientValue *gradient in self.colors) {
      CGRect frame = [layoutManager boundingRectForGlyphRange:gradient.range
                                              inTextContainer:textContainer];
      UIColor* color = gradient.colorForFrame(frame);
      
      if (gradient.isRawText) {
        [self.textStorage addAttribute:NSForegroundColorAttributeName
                                 value:color
                                 range:gradient.range];
      }
    }
    
    [super drawRect:rect];
  } @catch (NSException *exception) {
    NSLog(@"draw react %@", exception.reason);
  }
}

@end
