#import "RCTVirtualTextShadowView.h"
#import "RNTextGradientShadowViewDelegate.h"

@interface RNVirtualTextGradientShadowView : RCTVirtualTextShadowView <RNTextGradientShadowViewDelegate>

@property (nonatomic, copy) NSArray<NSNumber *> *locations;
@property (nonatomic, copy) NSArray *colors;
@property (nonatomic, assign) BOOL useGlobalCache;

@property (nonatomic, copy) NSDictionary* previousComparisonKey;

@end
