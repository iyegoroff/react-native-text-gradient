#import <Text/Text/RCTTextView.h>
#import "RNGradientValue.h"
#import "RNShadowTextGradient.h"

@interface RNTextGradient : RCTTextView

@property (nonatomic, strong) NSArray<RNGradientValue *> *colors;

@end
