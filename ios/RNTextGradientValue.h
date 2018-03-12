#import <UIKit/UIKit.h>

typedef UIColor *(^RNTextGradientBlock)(CGRect frame);

@interface RNTextGradientValue : NSObject

@property (nonatomic, assign) NSRange range;
@property (nonatomic, assign) BOOL isRawText;
@property (nonatomic, copy) RNTextGradientBlock colorForFrame;

- (instancetype)initWithRange:(NSRange)range
                    isRawText:(BOOL)isRawText
                colorForFrame:(RNTextGradientBlock)colorForFrame;

@end

