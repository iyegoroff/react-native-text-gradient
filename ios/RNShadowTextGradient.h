#import <Text/RCTShadowText.h>

@interface RNShadowTextGradient : RCTShadowText

@property (nonatomic, copy) NSArray<NSNumber *> *locations;
@property (nonatomic, copy) NSArray *colors;
@property (nonatomic, assign) BOOL useViewFrame;
@property (nonatomic, assign) BOOL useGlobalCache;

@end
