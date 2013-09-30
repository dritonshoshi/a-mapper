package com.ajjpj.amapper.javabean2;

import com.ajjpj.amapper.classes.WithQualifiers;
import com.ajjpj.amapper.classes.WithoutQualifiers;
import com.ajjpj.amapper.core2.AMapperDiffWorker;
import com.ajjpj.amapper.core2.AMapperWorker;
import com.ajjpj.amapper.core2.diff.ADiffBuilder;
import com.ajjpj.amapper.core2.path.APath;
import com.ajjpj.amapper.core2.tpe.AQualifiedSourceAndTargetType;
import com.ajjpj.amapper.javabean2.builder.JavaBeanMapperBuilder;
import com.ajjpj.amapper.javabean2.builder.JavaBeanMapping;
import com.ajjpj.amapper.javabean2.mappingdef.AbstractJavaBeanValueMappingDef;
import com.ajjpj.amapper.util.coll.AMap;
import org.junit.Test;
import scala.collection.immutable.Map;

import static org.junit.Assert.assertEquals;


/**
 * @author arno
 */
public class QualifierTest {
    final AbstractJavaBeanValueMappingDef<String, String, Object> fromQualifier = new AbstractJavaBeanValueMappingDef<String, String, Object>(String.class, String.class) {
        @Override public boolean canHandle(AQualifiedSourceAndTargetType types) {
            if (!super.canHandle(types)) {
                return false;
            }
            return types.sourceQualifier.get("qualifier-test").isDefined();
        }

        @Override public String map(String sourceValue, AQualifiedSourceAndTargetType types, AMapperWorker<?> worker, AMap<String, Object> context) {
            return sourceValue + " from qualifier";
        }

        @Override public void diff(ADiffBuilder diff, String sourceOld, String sourceNew, AQualifiedSourceAndTargetType types, AMapperDiffWorker<?> worker, AMap<String, Object> contextOld, AMap<String, Object> contextNew, APath path, boolean isDerived) {
        }
    };

    final AbstractJavaBeanValueMappingDef<String, String, JavaBeanMappingHelper> toQualifier = new AbstractJavaBeanValueMappingDef<String, String, JavaBeanMappingHelper>(String.class, String.class) {
        @Override public boolean canHandle(AQualifiedSourceAndTargetType types) {
            if (!super.canHandle(types)) {
                return false;
            }
            return types.targetQualifier.get("qualifier-test").isDefined();
        }

        @Override public String map(String sourceValue, AQualifiedSourceAndTargetType types, AMapperWorker<? extends JavaBeanMappingHelper> worker, AMap<String, Object> context) {
            return sourceValue + " to qualifier " + types.targetQualifier.get("qualifier-test").get();
        }

        @Override public void diff(ADiffBuilder diff, String sourceOld, String sourceNew, AQualifiedSourceAndTargetType types, AMapperDiffWorker<? extends JavaBeanMappingHelper> worker, AMap<String, Object> contextOld, AMap<String, Object> contextNew, APath path, boolean isDerived) {
        }
    };

    @Test
    public void testFromQualifiers() throws Exception {
        final JavaBeanMapper mapper = JavaBeanMapperBuilder.create()
                .withValueMapping(fromQualifier)
                .withValueMapping(toQualifier)
                .withBeanMapping(JavaBeanMapping.create(WithQualifiers.class, WithoutQualifiers.class).withMatchingPropsMappings())
                .build();

        final WithQualifiers orig = new WithQualifiers();
        orig.setS1("a");
        orig.setS2("b");

        final WithoutQualifiers mapped = mapper.map(orig, WithoutQualifiers.class);

        assertEquals("a from qualifier", mapped.getS1());
        assertEquals("b", mapped.getS2());
    }

    @Test
    public void testToQualifiers() throws Exception {
        final JavaBeanMapper mapper = JavaBeanMapperBuilder.create()
                .withValueMapping(fromQualifier)
                .withValueMapping(toQualifier)
                .withBeanMapping(JavaBeanMapping.create(WithQualifiers.class, WithoutQualifiers.class).withMatchingPropsMappings())
                .build();

        final WithoutQualifiers orig = new WithoutQualifiers();
        orig.setS1("a");
        orig.setS2("b");

        final WithQualifiers mapped = mapper.map(orig, WithQualifiers.class);

        assertEquals("a", mapped.getS1());
        assertEquals("b to qualifier xyz", mapped.getS2());
    }
}
