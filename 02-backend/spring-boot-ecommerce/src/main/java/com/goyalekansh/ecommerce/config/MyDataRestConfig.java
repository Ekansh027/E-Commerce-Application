package com.goyalekansh.ecommerce.config;

import com.goyalekansh.ecommerce.entity.Product;
import com.goyalekansh.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    
    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager){

        entityManager=theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        HttpMethod[] theUnsupportedActions={HttpMethod.POST, HttpMethod.PUT,HttpMethod.DELETE};

        //disable HttpMethods for Products: PUST, PUT, DELETE

        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));

        //disable HttpMethods for ProductCategory: PUST, PUT, DELETE

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));

             exposedIds(config);

    
    }

    private void exposedIds(RepositoryRestConfiguration config) {

        Set<EntityType<?>> entities= entityManager.getMetamodel().getEntities();

        List<Class> entityClasses= new ArrayList<>();

        for(EntityType tempEntityType: entities){

            entityClasses.add(tempEntityType.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);

        config.exposeIdsFor(domainTypes);



    }
}
