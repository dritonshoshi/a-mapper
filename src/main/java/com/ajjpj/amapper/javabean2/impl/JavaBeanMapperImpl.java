package com.ajjpj.amapper.javabean2.impl;

import com.ajjpj.amapper.core2.*;
import com.ajjpj.amapper.core2.diff.ADiff;
import com.ajjpj.amapper.core2.exclog.AMapperLogger;
import com.ajjpj.amapper.core2.impl.AMapperImpl;
import com.ajjpj.amapper.core2.tpe.AQualifier;
import com.ajjpj.amapper.core2.tpe.AType;
import com.ajjpj.amapper.core2.tpe.CanHandleSourceAndTargetCache;
import com.ajjpj.amapper.javabean2.JavaBeanMapper;
import com.ajjpj.amapper.javabean2.JavaBeanMappingHelper;
import com.ajjpj.amapper.javabean2.JavaBeanType;
import com.ajjpj.amapper.javabean2.JavaBeanTypes;
import com.ajjpj.amapper.r2.AMapper;
import com.ajjpj.amapper.util.func.AFunction0;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author arno
 */
public class JavaBeanMapperImpl<H extends JavaBeanMappingHelper> implements JavaBeanMapper {
    private final AMapper inner;

    public JavaBeanMapperImpl(CanHandleSourceAndTargetCache<AObjectMappingDef<?, ?, ? super H>, AObjectMappingDef<Object, Object, H>> objectMappings,
                              CanHandleSourceAndTargetCache<AValueMappingDef <?, ?, ? super H>, AValueMappingDef <Object, Object, H>> valueMappings,
                              AMapperLogger logger,
                              AFunction0<H, Exception> helperFactory,
                              AIdentifierExtractor identifierExtractor,
                              AContextExtractor contextExtractor,
                              CanHandleSourceAndTargetCache<APreProcessor, APreProcessor> preProcessors,
                              CanHandleSourceAndTargetCache<APostProcessor, APostProcessor> postProcessors) {
        this.inner = new AMapperImpl<H, Exception> (objectMappings, valueMappings, logger, helperFactory, identifierExtractor, contextExtractor, preProcessors, postProcessors);
    }

    @SuppressWarnings("unchecked")
    @Override public <T> T map(Object source, AType sourceType, AQualifier sourceQualifier, T target, AType targetType, AQualifier targetQualifier) throws Exception {
        return (T) inner.map(source, sourceType, sourceQualifier, target, targetType, targetQualifier).getOrElse(null);
    }

    @Override public <T> T map(Object source, Class<?> sourceClass, Class<?> sourceElementClass, T target, Class<T> targetClass, Class<?> targetElementClass) throws Exception {
        return map(source, JavaBeanTypes.create(sourceClass, sourceElementClass), AQualifier.NO_QUALIFIER, target, JavaBeanTypes.create(targetClass, targetElementClass), AQualifier.NO_QUALIFIER);
    }

    @Override public <T> T map(Object source, Class<?> sourceClass, T target, Class<T> targetClass) throws Exception {
        return map(source, JavaBeanTypes.create(sourceClass), AQualifier.NO_QUALIFIER, target, JavaBeanTypes.create(targetClass), AQualifier.NO_QUALIFIER);
    }

    @Override public <T> T map(Object source, Class<?> sourceClass, Class<T> targetClass) throws Exception {
        return map(source, sourceClass, null, targetClass);
    }

    @Override public <T> T map(Object source, Class<T> targetClass) throws Exception {
        if(source == null) {
            return null;
        }

        return map(source, source.getClass(), null, targetClass);
    }

    @SuppressWarnings("unchecked")
    @Override public <T> T map(Object source, T target) throws Exception {
        if(source == null) {
            return null;
        }
        return map(source, source.getClass(), target, (Class<T>) target.getClass());
    }

    @SuppressWarnings("unchecked")
    @Override public <S, T> List<T> mapList(Collection<?> source, Class<S> sourceElementClass, List<T> target, Class<T> targetElementClass) throws Exception {
        return map(source, Collection.class, sourceElementClass, target, List.class, targetElementClass);
    }

    @SuppressWarnings("unchecked")
    @Override public <S, T> List<T> mapList(Collection<?> source, Class<S> sourceElementClass, Class<T> targetElementClass) throws Exception {
        return map(source, Collection.class, sourceElementClass, null, List.class, targetElementClass);
    }

    @SuppressWarnings("unchecked")
    @Override public <S, T> Set<T> mapSet(Collection<?> source, Class<S> sourceElementClass, Set<T> target, Class<T> targetElementClass) throws Exception {
        return map(source, Collection.class, sourceElementClass, target, Set.class, targetElementClass);
    }

    @SuppressWarnings("unchecked")
    @Override public <S, T> Set<T> mapSet(Collection<?> source, Class<S> sourceElementClass, Class<T> targetElementClass) throws Exception {
        return map(source, Collection.class, sourceElementClass, null, Set.class, targetElementClass);
    }

    @Override public <T> T map(Object source, AType sourceType, T target, AType targetType) throws Exception {
        return map(source, sourceType, AQualifier.NO_QUALIFIER, target, targetType, AQualifier.NO_QUALIFIER);
    }

    @Override public ADiff diff(Object sourceOld, Object sourceNew, AType sourceType, AQualifier sourceQualifier, AType targetType, AQualifier targetQualifier) throws Exception {
        return inner.diff(sourceOld, sourceNew, sourceType, sourceQualifier, targetType, targetQualifier);
    }

    @Override public ADiff diff(Object sourceOld, Object sourceNew, AType sourceType, AType targetType) throws Exception {
        return diff(sourceOld, sourceNew, sourceType, AQualifier.NO_QUALIFIER, targetType, AQualifier.NO_QUALIFIER);
    }

    @Override public ADiff diff(Object sourceOld, Object sourceNew, Class<?> sourceType, Class<?> targetType) throws Exception {
        return diff(sourceOld, sourceNew, JavaBeanTypes.create(sourceType), JavaBeanTypes.create(targetType));
    }

    @Override public ADiff diffList(List<?> sourceOld, List<?> sourceNew, Class<?> sourceElementType, Class<?> targetElementType) throws Exception {
        return diff(sourceOld, sourceNew, JavaBeanTypes.create(List.class, sourceElementType), JavaBeanTypes.create(List.class, targetElementType));
    }

    @Override
    public ADiff diffSet(Set<?> sourceOld, Set<?> sourceNew, Class<?> sourceElementType, Class<?> targetElementType) throws Exception {
        return diff(sourceOld, sourceNew, JavaBeanTypes.create(Set.class, sourceElementType), JavaBeanTypes.create(Set.class, targetElementType));
    }
}
