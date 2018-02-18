#import <Text/RCTText.h>
#import "RNGradientValue.h"
#import "RNShadowTextGradient.h"

@interface RNTextGradient : RCTText

@property (nonatomic, strong) NSArray<RNGradientValue *> *colors;

@end
