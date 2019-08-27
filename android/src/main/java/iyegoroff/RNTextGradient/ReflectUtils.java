package iyegoroff.RNTextGradient;

import androidx.annotation.Nullable;

import com.facebook.common.logging.FLog;
import com.facebook.react.common.ReactConstants;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class ReflectUtils {

    @SuppressWarnings("unchecked")
    static <T> T getFieldValue(Object target, String name, @Nullable Class type) {
        type = type == null ? target.getClass() : type;

        try {
            Field field = type.getDeclaredField(name);
            field.setAccessible(true);

            return (T) field.get(target);

        } catch (Exception e) {
            FLog.w(ReactConstants.TAG, "Can't get " + type.getName() + " field " + name);
            FLog.w(ReactConstants.TAG, e.getMessage());
        }

        return null;
    }

    @SuppressWarnings({"unchecked", "SameParameterValue"})
    static <T> T invokeMethod(Object target, String name, @Nullable Class type) {
        type = type == null ? target.getClass() : type;

        try {
            Method method = type.getDeclaredMethod(name);
            method.setAccessible(true);

            return (T) method.invoke(target);

        } catch (Exception e) {
            FLog.w(ReactConstants.TAG, "Can't invoke " + type.getName() + " method " + name);
            FLog.w(ReactConstants.TAG, e.getMessage());
        }

        return null;
    }
}
