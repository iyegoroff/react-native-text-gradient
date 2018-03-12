#import <React/RCTShadowView.h>
#import "RCTTextShadowView.h"
#import "RNTextGradientShadowViewDelegate.h"

NS_ASSUME_NONNULL_BEGIN

@interface RNTextGradientShadowView : RCTTextShadowView <RNTextGradientShadowViewDelegate>

@property (nonatomic, copy) NSArray<NSNumber *> *locations;
@property (nonatomic, copy) NSArray *colors;
@property (nonatomic, assign) BOOL useViewFrame;
@property (nonatomic, assign) BOOL useGlobalCache;

@property (nonatomic, copy) NSDictionary* previousComparisonKey;

@end

NS_ASSUME_NONNULL_END

