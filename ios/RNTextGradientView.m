#import "RNTextGradientView.h"

@interface RCTTextView ()
{
@protected
  NSTextStorage *_Nullable _textStorage;
}
@end

@implementation RNTextGradientView

- (instancetype)initWithFrame:(CGRect)frame
{
  if ((self = [super initWithFrame:frame])) {
    self.colors = [NSMutableArray new];
  }
  return self;
}

- (void)drawRect:(CGRect)rect {
  NSLayoutManager *layoutManager = [_textStorage.layoutManagers firstObject];
  NSTextContainer *textContainer = [layoutManager.textContainers firstObject];
  
  for (RNTextGradientValue *gradient in self.colors) {
    CGRect frame = [layoutManager boundingRectForGlyphRange:gradient.range
                                            inTextContainer:textContainer];
    UIColor* color = gradient.colorForFrame(frame);
    
    if (gradient.isRawText) {
      [_textStorage addAttribute:NSForegroundColorAttributeName
                           value:color
                           range:gradient.range];
    }
  }
  
  [super drawRect:rect];
}

@end
