#import <UIKit/UIKit.h>

#ifndef RNTextGradientShadowViewDelegate_h
#define RNTextGradientShadowViewDelegate_h

@protocol RNTextGradientShadowViewDelegate

@property (nonatomic, copy) NSDictionary* previousComparisonKey;

- (BOOL) useGlobalCache;
- (NSArray<UIColor *> *)colors;
- (NSArray<NSNumber *> *)locations;
- (BOOL)useViewFrame;
- (UIEdgeInsets)paddingAsInsets;
- (UIColor *)calculateGradient:(CGRect)frame;
- (UIColor *)gradientWithFrame:(CGRect)frame;
- (NSDictionary *)gradientComparisonKey:(CGRect)frame;

@end

#endif /* RNTextGradientShadowViewDelegate_h */
