#import <UIKit/UIKit.h>
#import "RCTTextView.h"
#import "RNTextGradientValue.h"

NS_ASSUME_NONNULL_BEGIN

@interface RNTextGradientView : RCTTextView

@property (nonatomic, strong) NSArray<RNTextGradientValue *> *colors;

@end

NS_ASSUME_NONNULL_END

