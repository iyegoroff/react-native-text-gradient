#import <UIKit/UIKit.h>

typedef UIColor *(^RNGradientBlock)(CGRect frame);

@interface RNGradientValue : NSObject

@property (nonatomic, assign) NSRange range;
@property (nonatomic, assign) BOOL isRawText;
@property (nonatomic, copy) RNGradientBlock colorForFrame;

- (instancetype)initWithRange:(NSRange)range
                    isRawText:(BOOL)isRawText
                colorForFrame:(RNGradientBlock)colorForFrame;

@end
