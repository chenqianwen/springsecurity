package com.example.security.rbac.repository.support;


import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.jpa.internal.metamodel.SingularAttributeImpl;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
public class ImoocImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    /**
     *
     */
    private static final long serialVersionUID = 769122522217805485L;

    @Override
    protected Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
        return super.toIdentifier("imooc_"+stringForm.toLowerCase(), buildingContext);
    }

}
