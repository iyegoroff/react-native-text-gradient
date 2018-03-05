#import <Text/Text/RCTTextShadowView.h>

@interface RNShadowTextGradient : RCTTextShadowView

@property (nonatomic, copy) NSArray<NSNumber *> *locations;
@property (nonatomic, copy) NSArray *colors;
@property (nonatomic, assign) BOOL useViewFrame;
@property (nonatomic, assign) BOOL useGlobalCache;

@end
