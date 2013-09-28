package com.ajjpj.amapper.javabean2;


import com.ajjpj.amapper.core2.diff.ADiff;
import com.ajjpj.amapper.core2.tpe.AQualifier;

import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * @author arno
 */
public interface JavaBeanMapper {
    <T> T map(Object source, JavaBeanType<?> sourceType, AQualifier sourceQualifier, T target, JavaBeanType<T> targetType, AQualifier targetQualifier);
    <T> T map(Object source, Class<?> sourceClass, Class<?> sourceElementClass, T target, Class<T> targetClass, Class<?> targetElementClass);
    <T> T map(Object source, Class<?> sourceClass, T target, Class<T> targetClass);
    <T> T map(Object source, Class<?> sourceClass, Class<T> targetClass);
    <T> T map(Object source, Class<T> targetClass);

    <T> T map(Object source, T target);

    <S,T> List<T> mapList(Collection<?> source, Class<S> sourceElementClass, List<T> target, Class<T> targetElementClass);
    <S,T> List<T> mapList(Collection<?> source, Class<S> sourceElementClass, Class<T> targetElementClass);

    <S,T> Set<T> mapSet(Collection<?> source, Class<S> sourceElementClass, Set<T> target, Class<T> targetElementClass);
    <S,T> Set<T> mapSet(Collection<?> source, Class<S> sourceElementClass, Class<T> targetElementClass);

    <T> T map(Object source, JavaBeanType<?> sourceType, T target, JavaBeanType<T> targetType);

    ADiff diff(Object sourceOld, Object sourceNew, JavaBeanType<?> sourceType, AQualifier sourceQualifier, JavaBeanType<?> targetType, AQualifier targetQualifier);
    ADiff diff(Object sourceOld, Object sourceNew, JavaBeanType<?> sourceType, JavaBeanType<?> targetType);
    ADiff diff(Object sourceOld, Object sourceNew, Class<?> sourceType, Class<?> targetType);

    ADiff diffList(List<?> sourceOld, List<?> sourceNew, Class<?> sourceElementType, Class<?> targetElementType);
    ADiff diffSet(Set<?> sourceOld, Set<?> sourceNew, Class<?> sourceElementType, Class<?> targetElementType);
}
