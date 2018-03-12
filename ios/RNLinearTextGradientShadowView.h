#import "RNLinearTextGradientShadowViewDelegate.h"
#import "RNTextGradientShadowView.h"

@interface RNLinearTextGradientShadowView : RNTextGradientShadowView <RNLinearTextGradientShadowViewDelegate>

@property (nonatomic, assign) CGPoint gradientStart;
@property (nonatomic, assign) CGPoint gradientEnd;

@end
