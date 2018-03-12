#import "RNLinearTextGradientShadowViewDelegate.h"
#import "RCTBaseTextShadowView.h"

@interface RNLinearGradientUtils : NSObject

+ (UIColor *)gradientWithFrame:(CGRect)frame
                    shadowView:(RCTBaseTextShadowView <RNLinearTextGradientShadowViewDelegate> *)shadowView;

+ (NSDictionary *)gradientComparisonKey:(CGRect)frame
                             shadowView:(RCTBaseTextShadowView <RNLinearTextGradientShadowViewDelegate> *)shadowView;

@end
