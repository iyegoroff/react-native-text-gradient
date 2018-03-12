#import "RNTextGradientShadowViewDelegate.h"
#import "RCTBaseTextShadowView.h"

@interface RNTextGradientUtils : NSObject

+ (UIColor *)calculateGradient:(CGRect)frame
                    shadowView:(RCTBaseTextShadowView <RNTextGradientShadowViewDelegate> *)shadowView
                  patternCache:(NSMutableDictionary *)patternCache;

+ (NSArray<UIColor *> *)convertColors:(NSArray<NSNumber *> *)colors;

+ (BOOL)isTextGradientShadowView:(RCTShadowView *)shadowView;

@end

#define MUST_BE_OVERRIDEN()                                                                                                                             \
NSString *reason = [NSString stringWithFormat:@"Method %@ must be overriden in %@ class", NSStringFromSelector(_cmd), NSStringFromClass([self class])]; \
@throw [NSException exceptionWithName:NSInternalInconsistencyException reason:reason userInfo:nil];
