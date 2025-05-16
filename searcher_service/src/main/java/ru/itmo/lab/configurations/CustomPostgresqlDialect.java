package ru.itmo.lab.configurations;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.query.sqm.function.FunctionKind;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.query.sqm.produce.function.PatternFunctionDescriptorBuilder;
import org.hibernate.type.spi.TypeConfiguration;

public class CustomPostgresqlDialect extends PostgreSQLDialect {
    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        super.initializeFunctionRegistry(functionContributions);
        SqmFunctionRegistry registry = functionContributions.getFunctionRegistry();
        TypeConfiguration types = functionContributions.getTypeConfiguration();

        new PatternFunctionDescriptorBuilder(registry, "rating_to_int", FunctionKind.NORMAL, "rating_to_int(?1)")
                .setExactArgumentCount(1)
                .setInvariantType(types.getBasicTypeForJavaType(Integer.class))
                .register();
    }
}
